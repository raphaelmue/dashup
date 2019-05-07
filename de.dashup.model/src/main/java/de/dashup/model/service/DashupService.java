package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.model.exceptions.EmailAlreadyInUseException;
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

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USER_SECTIONS, Section.class, whereParameters);
        if (result != null) {
            for (DatabaseObject databaseObject : result) {
                Section section = (Section) databaseObject;
                Map<String, Object> innerWhereParameters = new HashMap<>();
                innerWhereParameters.put("section_id", section.getId());
                JSONArray innerResult = this.database.get(Database.Table.SECTIONS_PANELS, innerWhereParameters);
                if (innerResult != null && innerResult.length() > 0) {
                    ArrayList<Widget> widgets = new ArrayList<>();
                    for (int i = 0; i < innerResult.length(); i++) {
                        Widget widget = this.getPanelById(innerResult.getJSONObject(i).getInt("panel_id"));
                        widget.setSize(Widget.Size.getSizeByName(innerResult.getJSONObject(i).getString("size")));
                        widget.setPredecessor(innerResult.getJSONObject(i).getInt("panel_predecessor"));
                        widgets.add(widget);
                    }
                    section.setWidgets(this.orderPanels(widgets));
                }
                sections.add(section);
            }
        }
        user.setSections(this.orderSections(sections));
        return user;
    }

    public Widget getPanelById(int id) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", id);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, Widget.class, whereParameters);
        if (result != null && result.size() == 1) {
            return (Widget) new Widget().fromDatabaseObject(result.get(0));
        }
        return null;
    }

    private ArrayList<Section> orderSections(ArrayList<Section> sections) {
        ArrayList<Section> result = new ArrayList<>();
        while (!sections.isEmpty()) {
            for (Section section : sections) {
                if (result.isEmpty() && section.getPredecessor() == -1) {
                    result.add(section);
                    sections.remove(section);
                    break;
                } else if (!result.isEmpty() && section.getPredecessor() == result.get(result.size() - 1).getId()) {
                    result.add(section);
                    sections.remove(section);
                    break;
                }
            }
        }
        return result;
    }

    private ArrayList<Widget> orderPanels(ArrayList<Widget> widgets) {
        ArrayList<Widget> result = new ArrayList<>();
        while (!widgets.isEmpty()) {
            for (Widget widget : widgets) {
                if (result.isEmpty() && widget.getPredecessor() == -1) {
                    result.add(widget);
                    widgets.remove(widget);
                    break;
                } else if (!result.isEmpty() && widget.getPredecessor() == result.get(result.size() - 1).getId()) {
                    result.add(widget);
                    widgets.remove(widget);
                    break;
                }
            }
        }
        return result;
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
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    public void updateEmail(User user) throws SQLException, EmailAlreadyInUseException {
        if (!Validator.validate(user.getEmail(), Validator.EMAIL_REGEX)) {
            throw new IllegalArgumentException("Email is not valid.");
        }

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());

        JSONArray result = this.database.get(Database.Table.USERS, values);
        if (result.length() > 0) {
            throw new EmailAlreadyInUseException(user.getEmail());
        }

        this.database.update(Database.Table.USERS, whereParameters, values);

        user.setEmail(user.getEmail());
    }

    public void updateUserName(User user) throws SQLException, EmailAlreadyInUseException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("user_name", user.getUserName());

        JSONArray result = this.database.get(Database.Table.USERS, values);
        if (result.length() > 0) {
            throw new EmailAlreadyInUseException(user.getUserName());
        }

        this.database.update(Database.Table.USERS, whereParameters, values);
    }

    public void updatePersonalInformation(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("name", user.getName());
        values.put("surname", user.getSurname());
        values.put("birth_date", user.getBirthDate());
        values.put("company", user.getCompany());
        values.put("bio", user.getBio());

        this.database.update(Database.Table.USERS, whereParameters, values);
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
        Iterator<String> iter = iterSet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
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

    public void updateSection(User user, String sectionName, int sectionId, int predecessor, int successor) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        whereParameters.put("section_id", sectionId);

        Map<String, Object> values = new HashMap<>();

        if (!"%old%".equals(sectionName)) {

            values.put("section_name", sectionName);
        }
        values.put("predecessor_id", predecessor);
        values.put("successor_id", successor);

        if (!values.isEmpty()) {
            this.database.update(Database.Table.USER_SECTIONS, whereParameters, values);
        }
    }

    public void deleteSection(User user, int section_id) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("section_id", section_id);
        whereParameters.put("user_id", user.getId());
        database.delete(Database.Table.USER_SECTIONS, whereParameters);
    }

    public void addSection(User user, String sectionName, int predecessor, int successor) throws SQLException {
        if (sectionName == null) {
            sectionName = "New Section";
        }

        Map<String, Object> values = new HashMap<>();
        values.put("section_name", sectionName);
        values.put("user_id", user.getId());
        values.put("predecessor_id", predecessor);
        values.put("successor_id", successor);

        this.database.insert(Database.Table.USER_SECTIONS, values);
    }

    // --- DRAFTS --- \\

    public void getUsersDrafts(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        whereParameters.put("visibility", false);

        List<Draft> drafts = new ArrayList<>();
        for (DatabaseObject databaseObject : this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters)) {
            drafts.add(new Draft().fromDatabaseObject(databaseObject));
        }
        user.setDrafts(drafts);
    }

    public Draft createDraft(User user, String draftName) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", user.getId());
        values.put("name", draftName);
        values.put("visibility", false);

        this.database.insert(Database.Table.PANELS, values);

        Draft draft = new Draft();
        draft.setId(this.database.getLatestId(Database.Table.PANELS));
        draft.setName(draftName);

        return draft;
    }

    public void updateWidgetInformation(Widget widget) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", widget.getId());

        Map<String, Object> values = new HashMap<>();
        if (widget.getName() != null) {
            values.put("name", widget.getName());
        }
        if (widget.getCodeSmall() != null) {
            values.put("code_small", widget.getCodeSmall());
        }
        if (widget.getCodeMedium() != null) {
            values.put("code_medium", widget.getCodeMedium());
        }
        if (widget.getCodeLarge() != null) {
            values.put("code_large", widget.getCodeLarge());
        }
        if (widget.getShortDescription() != null) {
            values.put("short_description", widget.getShortDescription());
        }
        if (widget.getDescription() != null) {
            values.put("descriptions", widget.getDescription());
        }

        this.database.update(Database.Table.PANELS, whereParameters, values);
    }

    public void deleteDraft(int draftId) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", draftId);

        this.database.delete(Database.Table.PANELS, whereParameters);
    }

    public void publishDraft(int draftId) throws SQLException, IllegalArgumentException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", draftId);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters);
        if (result != null && result.size() > 0) {
            Draft draft = new Draft().fromDatabaseObject(result.get(0));
            if (!draft.isValid()) {
                throw new IllegalArgumentException("Draft is not valid");
            }

            Map<String, Object> values = new HashMap<>();
            values.put("visibility", true);
            values.put("publication_date", LocalDate.now());

            this.database.update(Database.Table.PANELS, whereParameters, values);
        }
    }

    // --- WIDGETS --- \\

    public List<Widget> getUsersWidgets(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        whereParameters.put("visibility", true);

        List<Widget> widgets = new ArrayList<>();
        for (DatabaseObject databaseObject : this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters)) {
            widgets.add(new Widget().fromDatabaseObject(databaseObject));
        }
        return widgets;
    }
}
