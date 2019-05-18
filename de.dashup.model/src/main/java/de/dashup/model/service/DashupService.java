package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.model.exceptions.EmailAlreadyInUseException;
import de.dashup.shared.*;
import de.dashup.shared.layout.*;
import de.dashup.shared.widgets.Entry;
import de.dashup.shared.widgets.Todo;
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

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USER_SECTIONS, Section.class, whereParameters, "section_index");
        if (result != null) {
            for (DatabaseObject databaseObject : result) {
                Section section = (Section) databaseObject;
                Map<String, Object> innerWhereParameters = new HashMap<>();
                innerWhereParameters.put("id", section.getId());
                JSONArray innerResult = this.database.get(Database.Table.SECTIONS_PANELS, innerWhereParameters, "widget_index");
                if (innerResult != null && innerResult.length() > 0) {
                    List<Widget> widgets = new ArrayList<>();
                    for (int i = 0; i < innerResult.length(); i++) {
                        Widget widget = this.getPanelById(innerResult.getJSONObject(i).getInt("panel_id"));
                        widget.setSize(Widget.Size.getSizeByName(innerResult.getJSONObject(i).getString("size")));
                        widget.setIndex(innerResult.getJSONObject(i).getInt("widget_index"));
                        widgets.add(widget);
                    }
                    Collections.sort(widgets);
                    section.setWidgets(widgets);
                }
                sections.add(section);
            }
        }
        Collections.sort(sections);
        user.setSections(sections);
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

    private void updateSection(User user, Section section) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        whereParameters.put("id", section.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("section_name", section.getName());
        values.put("section_index", section.getIndex());

        this.database.update(Database.Table.USER_SECTIONS, whereParameters, values);
    }

    private void deleteSection(User user, Section section) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", section.getId());
        whereParameters.put("user_id", user.getId());
        database.delete(Database.Table.USER_SECTIONS, whereParameters);
    }

    private void deleteWidgetsOfSection(Section section) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", section.getId());
        database.delete(Database.Table.SECTIONS_PANELS, whereParameters);

    }

    private int addNewSection(User user, Section section) throws SQLException, NumberFormatException {

        Map<String, Object> values = new HashMap<>();

        values.put("section_name", Objects.requireNonNullElse(section.getName(), "-"));
        values.put("user_id", user.getId());
        values.put("section_index", section.getIndex());

        this.database.insert(Database.Table.USER_SECTIONS, values);

        return database.getLatestId(Database.Table.USER_SECTIONS);
    }

    private void addWidgetToSection(Widget widget,  Section section, String size) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put("id", section.getId());
        values.put("panel_id", widget.getId());
        values.put("widget_index", widget.getIndex());
        values.put("size", size);

        this.database.insert(Database.Table.SECTIONS_PANELS, values);
    }

    public void processLayoutModeChanges(LayoutModeStructureDTO layoutModeStructureDTO, User user) throws SQLException {
        List<LayoutModeSectionDTO> sectionsToDelete = layoutModeStructureDTO.getSectionsToDelete();

        for (LayoutModeSectionDTO section : sectionsToDelete) {
            Section sectionToDelete = section.toDataTransferObject();
            deleteWidgetsOfSection(sectionToDelete);
            deleteSection(user, sectionToDelete);
        }

        List<LayoutModeSectionDTO> sectionAndWidgetIndex = layoutModeStructureDTO.getSectionWidgetOrder();
        int sectionIndex = 0;

        for (LayoutModeSectionDTO sectionDTO : sectionAndWidgetIndex) {
            sectionDTO.setIndex(sectionIndex);
            Section sectionToProcess = sectionDTO.toDataTransferObject();

            if (sectionDTO.isNewSection()) {
                int newSectionId = addNewSection(user, sectionToProcess);
                sectionToProcess.setId(newSectionId);
            } else {
                updateSection(user, sectionToProcess);
                deleteWidgetsOfSection(sectionToProcess);
            }

            List<LayoutModeWidgetDTO> layoutModeWidgetsDTO = sectionDTO.getLayoutModeWidgets();
            int widgetIndex = 0;
            for (LayoutModeWidgetDTO widgetDTO : layoutModeWidgetsDTO) {
                widgetDTO.setIndex(widgetIndex);
                Widget widget = widgetDTO.toDataTransferObject();
                addWidgetToSection(widget, sectionToProcess, widgetDTO.getWidgetSize());
                widgetIndex++;
            }
            sectionIndex++;
        }
    }

    public void saveTodoWidgetState(User user, Todo todo) throws SQLException{
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", user.getId());
        this.database.delete(Database.Table.TODOS, values);
        for(Entry entry: todo.getList()){
            values.put("content", entry.getContent());
            values.put("selected", entry.getSelected());
            this.database.insert(Database.Table.TODOS, values);
        }
    }

    public Todo loadTodoWidgetState(User user) throws SQLException{
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        JSONArray innerResult = this.database.get(Database.Table.TODOS, whereParameters);
        List<Entry> entries = new ArrayList<>();
        for(int i = 0; i < innerResult.length(); i++){
            JSONObject result = innerResult.getJSONObject(i);
            Entry entry = new Entry(result.getString("content"), result.getBoolean("selected"));
            entries.add(entry);
        }
        return new Todo(entries);
    }

}