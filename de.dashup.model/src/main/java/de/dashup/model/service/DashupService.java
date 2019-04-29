package de.dashup.model.service;

import de.dashup.model.builder.PanelLoader;
import de.dashup.model.db.Database;
import de.dashup.shared.*;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DashupService {

    private Database database;
    private final PanelLoader panelLoader;
    private final RandomString randomString = new RandomString();

    private static DashupService INSTANCE;

    public static DashupService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DashupService();
        }
        return INSTANCE;
    }

    private DashupService() {
        try {
            this.database = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.panelLoader = PanelLoader.getInstance();
    }

    /**
     * Checks the credentials when logging in.
     *
     * @param email      email of user
     * @param password   password in plain text
     * @param rememberMe stores a token, so that the user does not have to login each time, if true
     * @return User object, if credentials are correct, else null.
     */
    public User checkCredentials(String email, String password, boolean rememberMe) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result != null && result.size() == 1) {
            User user = (User) new User().fromDatabaseObject(result.get(0));
            user = this.getSectionsAndPanels(user);
            String hashedPassword = Hash.create(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                user.setSettings(this.getSettingsOfUser(user));
                if (rememberMe) {
                    String token = this.randomString.nextString(64);
                    user.setToken(token);
                    HashMap<String, Object> values = new HashMap<>();
                    values.put("user_id", user.getId());
                    values.put("token", token);
                    values.put("expire_date", LocalDate.now().plusMonths(1));
                    this.database.insert(Database.Table.USERS_TOKENS, values);
                }
                return user;
            }
        }

        return null;
    }

    public User getSectionsAndPanels(User user) throws SQLException {
        ArrayList<Section> sections = new ArrayList<>();

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USER_SECTIONS, Section.class, whereParameters, "predecessor_id ASC");
        if (result != null) {
            for (DatabaseObject databaseObject : result) {
                Section section = (Section) databaseObject;
                Map<String, Object> innerWhereParameters = new HashMap<>();
                innerWhereParameters.put("section_id", section.getId());
                JSONArray innerResult = this.database.get(Database.Table.SECTIONS_PANELS, innerWhereParameters, "panel_predecessor ASC");
                if (innerResult != null && innerResult.length() > 0) {
                    ArrayList<Panel> panels = new ArrayList<>();
                    for (int i = 0; i < innerResult.length(); i++) {
                        Panel panel = panelLoader.loadPanel(innerResult.getJSONObject(i).getInt("panel_id"),
                                Panel.Size.getSizeByName(innerResult.getJSONObject(i).getString("size")));
                        panel.setPredecessor(innerResult.getJSONObject(i).getInt("panel_predecessor"));
                        panels.add(panel);
                    }
                    section.setPanels(panels);
                }
                sections.add(section);
            }
        }
        user.setSections(sections);
        return user;
    }

    public Panel getPanelById(int id) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", id);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, Panel.class, whereParameters);
        if (result != null && result.size() == 1) {
            return (Panel) new Panel().fromDatabaseObject(result.get(0));
        }
        return null;
    }

    public User getUserByToken(String token) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("token", token);

        JSONArray result = this.database.get(Database.Table.USERS_TOKENS, whereParameters);
        if (result != null && result.length() > 0) {
            whereParameters.clear();
            whereParameters.put("id", result.getJSONObject(0).get("user_id"));
            User user = (User) new User().fromDatabaseObject(
                    this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters).get(0));
            user.setToken(token);

            return user;
        }

        return null;
    }

    public Settings getSettingsOfUser(User user) throws SQLException {
        Settings settings = new Settings();

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());

        JSONObject jsonObject = this.database.get(Database.Table.USERS_SETTINGS, whereParameters).getJSONObject(0);
        settings.setLanguage(Locale.forLanguageTag(jsonObject.getString("language").isEmpty() ?
                "en" : jsonObject.getString("language")));
        settings.setTheme(Settings.Theme.getThemeByTechnicalName(jsonObject.getString("theme")));
        settings.setBackgroundImage(jsonObject.getString("background_image").equals("null") ? "" : jsonObject.getString("background_image"));

        return settings;
    }

    /**
     * Deletes a token from the database
     *
     * @param token token to be deleted
     * @throws SQLException thrown if something went wrong executing the SQL statement
     */
    public void deleteToken(String token) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("token", token);
        this.database.delete(Database.Table.USERS_TOKENS, whereParameters);
    }

    public User registerUser(String email, String userName, String password) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result == null || result.size() == 0) {
            String salt = this.randomString.nextString(32);
            String hashedPassword = Hash.create(password, salt);

            Map<String, Object> values = new HashMap<>();
            values.put("email", email);
            values.put("user_name", userName);
            values.put("password", hashedPassword);
            values.put("salt", salt);

            this.database.insert(Database.Table.USERS, values);
            Settings defaultSettings = new Settings();
            return new User(this.database.getLatestId(Database.Table.USERS), email, userName, "", "", hashedPassword, salt, defaultSettings);
        }

        return null;
    }

    public void updatePassword(User user, String oldPassword, String newPassword) throws SQLException, IllegalArgumentException {
        if (user.getPassword().equals(Hash.create(oldPassword, user.getSalt()))) {
            String newSalt = this.randomString.nextString(32);
            String newHashedPassword = Hash.create(newPassword, newSalt);

            Map<String, Object> whereParameters = new HashMap<>();
            whereParameters.put("id", user.getId());

            Map<String, Object> values = new HashMap<>();
            values.put("password", newHashedPassword);
            values.put("salt", newSalt);

            this.database.update(Database.Table.USERS, whereParameters, values);

            user.setPassword(newHashedPassword);
            user.setSalt(newSalt);
        } else {
            throw new IllegalArgumentException("Passworts does not match");
        }
    }

    public void updateNameAndSurname(User user, String newName, String newSurname) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("name", newName);
        values.put("surname", newSurname);

        this.database.update(Database.Table.USERS, whereParameters, values);

        user.setName(newName);
        user.setSurname(newSurname);
    }

    public void updateSettings(User user, boolean insert) throws SQLException {
        if (!user.getSettings().getBackgroundImage().isEmpty() && !isValidURL(user.getSettings().getBackgroundImage()) && !insert) {
            throw new IllegalArgumentException("URL is not valid.");
        }

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("user_id", user.getId());
        values.put("background_image", user.getSettings().getBackgroundImage());
        values.put("theme", user.getSettings().getTheme().getTechnicalName());
        values.put("language", user.getSettings().getLanguage().toLanguageTag());

        if (insert) {
            this.database.insert(Database.Table.USERS_SETTINGS, values);
        } else {
            this.database.update(Database.Table.USERS_SETTINGS, whereParameters, values);
        }
    }

    private boolean isValidURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public Map<String, String> loadLayout(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        JSONObject jsonObject = this.database.get(Database.Table.USERS_SETTINGS, whereParameters).getJSONObject(0);

        Map<String, String> result = new HashMap<>();
        Set<String> iterSet = jsonObject.keySet();
        for (String key : iterSet) {
            result.put(key, jsonObject.get(key).toString());
        }
        result.remove("id");
        result.remove("user_id");
        return result;
    }

    public void updateLanguage(User user) throws SQLException {
        Map<String, Object> whereParameter = new HashMap<>();
        whereParameter.put("user_id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("language", user.getSettings().getLanguage().toLanguageTag());

        this.database.update(Database.Table.USERS_SETTINGS, whereParameter, values);
    }

    private void updateSection(User user, String sectionName, int sectionId, int predecessor) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        whereParameters.put("section_id", sectionId);

        Map<String, Object> values = new HashMap<>();

        if (!"%old%".equals(sectionName)) {

            values.put("section_name", sectionName);
        }
        values.put("predecessor_id", predecessor);

        if (!values.isEmpty()) {
            this.database.update(Database.Table.USER_SECTIONS, whereParameters, values);
        }
    }

    private void deleteSection(User user, int section_id) throws SQLException {
        deletePanelsOfSection(section_id);
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("section_id", section_id);
        whereParameters.put("user_id", user.getId());
        database.delete(Database.Table.USER_SECTIONS, whereParameters);
    }

    private void deletePanelsOfSection(int section_id) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("section_id", section_id);
        database.delete(Database.Table.SECTIONS_PANELS, whereParameters);

    }

    private int addSection(User user, String sectionName, int order) throws SQLException, NumberFormatException {
        if (sectionName == null) {
            sectionName = "New Section";
        }

        Map<String, Object> values = new HashMap<>();
        values.put("section_name", sectionName);
        values.put("user_id", user.getId());
        values.put("predecessor_id", order);

        this.database.insert(Database.Table.USER_SECTIONS, values);

        JSONObject jsonObject = this.database.get(Database.Table.USER_SECTIONS, values).getJSONObject(0);
        return jsonObject.getInt("section_id");

    }

    private void addPanel(int section_id, int panel_id, int panel_predecessor, String size) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put("section_id", section_id);
        values.put("panel_id", panel_id);
        values.put("panel_predecessor", panel_predecessor);
        values.put("size", size);

        this.database.insert(Database.Table.SECTIONS_PANELS, values);

    }

    public void processLayoutModeChanges(LayoutModeStructure layoutModeStructure, User user) throws SQLException {
        List<LayoutModeSection> sectionsToDelete = layoutModeStructure.getSectionsToDelete();
        deleteSections(sectionsToDelete, user);

        List<LayoutModeSection> sectionAndPanelOrder = layoutModeStructure.getSectionPanelOrder();
        saveNewSectionAndPanelStructure(sectionAndPanelOrder, user);
    }

    private void saveNewSectionAndPanelStructure(List<LayoutModeSection> sections, User user) throws NumberFormatException, SQLException {
        for (int i = 0; i < sections.size(); i++) {

            String sectionIdFrontend = sections.get(i).getSectionId();
            String sectionName = sections.get(i).getSectionName();

            int sectionId = convertId(sectionIdFrontend, "s");


            if (sectionId == -1) {
                sectionId = addSection(user, sectionName, i);

            } else if (sectionId > -1) {
                updateSection(user, sectionName, sectionId, i);
                deletePanelsOfSection(sectionId);
            }

            List<LayoutModePanel> panels = sections.get(i).getPanelStructure();
            for (int j = 0; j < panels.size(); j++) {
                String panelIdString = panels.get(j).getPanelId();
                int panelId = convertId(panelIdString, "p");
                String size = panels.get(j).getPanelSize();

                addPanel(sectionId, panelId, j, size);
            }
        }
    }

    private void deleteSections(List<LayoutModeSection> sectionsToDelete, User user) throws SQLException {
        for (LayoutModeSection section : sectionsToDelete) {

            int sectionId = convertId(section.getSectionId(), "s");
            deleteSection(user, sectionId);
        }
    }

    private int convertId(String id, String expectedPrefix) {
        String technicalId = id.substring(1);
        String idPrefix = id.substring(0, 1);

        if (idPrefix.equals(expectedPrefix)) {
            return Integer.valueOf(technicalId);
        }

        return -1;
    }
}
