package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.shared.*;
import de.dashup.shared.DatabaseModels.*;
import de.dashup.shared.Enums.Size;
import de.dashup.shared.Enums.Theme;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DashupService {

    private Database database;
    private Map<Integer,User> users;
    private final RandomString randomString = new RandomString();

    private static DashupService INSTANCE;

    public static DashupService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DashupService();
        }
        return INSTANCE;
    }

    private DashupService() {
        this.users = new HashMap<>();
        try {
            this.database = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User checkCredentials(String email, String password, boolean rememberMe) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        List<DatabaseObject> result;

        whereParameters.put("email", email);
        result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        whereParameters.clear();

        if (result.size() == 1) {
            DatabaseUser user = (DatabaseUser) result.get(0);

            whereParameters.put("user_id", user.getID());
            result = this.database.getObject(Database.Table.SECTIONS, DatabaseSection.class, whereParameters);
            whereParameters.clear();

            Map<Integer, Object> remainingSections = new HashMap<>();
            Map<Integer, Object> remainingWidgets = new HashMap<>();
            List<Section> sections = new ArrayList<>();
            DatabaseSection currentSection = null;
            DatabaseSectionWidgets currentSectionWidget = null;

            for(DatabaseObject object: result){
                DatabaseSection section = (DatabaseSection) object;
                if(section.getPredecessorID() == null){
                    currentSection = section;
                }
                remainingSections.put(section.getID(), section);
            }

            while(remainingSections.size() > 0){
                whereParameters.put("section_id", currentSection.getID());
                result = this.database.getObject(Database.Table.SECTION_WIDGETS, DatabaseSectionWidgets.class, whereParameters);
                whereParameters.clear();

                for(DatabaseObject object: result){
                    DatabaseSectionWidgets sectionWidget = (DatabaseSectionWidgets) object;
                    if(sectionWidget.getPredecessorID() == null){
                        currentSectionWidget = sectionWidget;
                    }
                    remainingWidgets.put(sectionWidget.getID(), sectionWidget);
                }

                List<Widget> widgets = new ArrayList<>();
                while(remainingWidgets.size() > 0){
                    whereParameters.put("id", currentSectionWidget.getPanelID());
                    result = this.database.getObject(Database.Table.WIDGETS, DatabaseWidget.class, whereParameters);
                    whereParameters.clear();

                    DatabaseWidget widget = (DatabaseWidget) result.get(0);
                    Size size = Size.getSizeByName(currentSectionWidget.getSize());
                    String htmlContent;
                    switch(size){
                        case SMALL: htmlContent = widget.getHtmlSmall();
                        case MEDIUM: htmlContent = widget.getHtmlMedium();
                        case LARGE: htmlContent = widget.getHtmlLarge();
                        default: htmlContent = "";
                    }
                    Widget predecessor = (Widget) remainingWidgets.get(currentSectionWidget.getPredecessorID());
                    widgets.add(new Widget(widget.getID(), size, htmlContent, predecessor));
                    currentSectionWidget = (DatabaseSectionWidgets) remainingWidgets.get(widget.getID());
                    remainingWidgets.remove(widget.getID());
                }
                Section predecessor = (Section) remainingSections.get(currentSection.getPredecessorID());
                sections.add(new Section(currentSection.getID(), currentSection.getName(), predecessor, widgets));
                currentSection = (DatabaseSection) remainingSections.get(currentSection.getID());
                remainingSections.remove(currentSection.getID());
            }

            String hashedPassword = Hash.create(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {

                whereParameters.put("id", user.getID());
                result = this.database.getObject(Database.Table.SETTINGS, DatabaseSetting.class, whereParameters);
                whereParameters.clear();

                DatabaseSetting setting = (DatabaseSetting) result.get(0);
                Locale language = Locale.forLanguageTag(setting.getLanguage().isEmpty() ? "en" : setting.getLanguage());
                Theme theme = Theme.getThemeByName(setting.getTheme());
                Settings settings = new Settings(language, theme, setting.getBackgroundImage());
                String token = null;
                if (rememberMe) {
                    token = this.randomString.nextString(64);
                    HashMap<String, Object> values = new HashMap<>();
                    values.put("user_id", user.getID());
                    values.put("token", token);
                    values.put("expiry_date", LocalDate.now().plusMonths(1));
                    this.database.insert(Database.Table.TOKENS, values);
                }
                return new User(user.getID(), token, settings, new Layout(sections));
            }
        }

        return null;
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

    private ArrayList<Panel> orderPanels(ArrayList<Panel> panels) {
        ArrayList<Panel> result = new ArrayList<>();
        while (!panels.isEmpty()) {
            for (Panel panel : panels) {
                if (result.isEmpty() && panel.getPredecessor() == -1) {
                    result.add(panel);
                    panels.remove(panel);
                    break;
                } else if (!result.isEmpty() && panel.getPredecessor() == result.get(result.size() - 1).getId()) {
                    result.add(panel);
                    panels.remove(panel);
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
}
