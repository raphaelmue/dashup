package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.model.exceptions.EmailAlreadyInUseException;
import de.dashup.model.exceptions.InvalidCodeException;
import de.dashup.model.exceptions.MissingInformationException;
import de.dashup.shared.*;
import de.dashup.shared.layout.*;
import de.dashup.shared.widgets.*;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashupService {

    /**
     * Literals
     */
    private static final String EMAIL = "email";
    private static final String USER_ID = "user_id";
    private static final String TOKEN = "token";
    private static final String SECTION_INDEX = "section_index";
    private static final String WIDGET_INDEX = "widget_index";
    private static final String PANEL_ID = "panel_id";
    private static final String PROPERTY = "property";
    private static final String DEFAULT_VALUE = "default_value";
    private static final String PROPERTY_ID = "property_id";
    private static final String VALUE = "value";
    private static final String TAG_ID = "tag_id";
    private static final String VISIBILITY = "visibility";
    private static final String CATEGORY = "category";
    private static final String LANGUAGE = "language";
    private static final String BACKGROUND_IMAGE = "background_image";
    private static final String CONTENT = "content";
    private static final String SELECTED = "selected";

    private Database database;
    private final RandomString randomString = new RandomString();

    private static DashupService instance;

    public static DashupService getInstance() {
        if (instance == null) {
            instance = new DashupService();
        }
        return instance;
    }

    private DashupService() {
        try {
            this.database = Database.getInstance();
        } catch (SQLException e) {
            Logger.getLogger("Dashup").log(Level.SEVERE, e.getMessage(), e);
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
        whereParameters.put(EMAIL, email);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result != null && result.size() == 1) {
            User user = new User().fromDatabaseObject(result.get(0));
            user = this.getSectionsAndPanels(user);
            String hashedPassword = Hash.create(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                user.setSettings(this.getSettingsOfUser(user));
                if (rememberMe) {
                    String token = this.randomString.nextString(64);
                    user.setToken(token);
                    HashMap<String, Object> values = new HashMap<>();
                    values.put(USER_ID, user.getId());
                    values.put(TOKEN, token);
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
        whereParameters.put(USER_ID, user.getId());

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USER_SECTIONS, Section.class, whereParameters, SECTION_INDEX);
        if (result != null) {
            for (DatabaseObject databaseObject : result) {
                Section section = (Section) databaseObject;
                Map<String, Object> innerWhereParameters = new HashMap<>();
                innerWhereParameters.put("id", section.getId());
                JSONArray innerResult = this.database.get(Database.Table.SECTIONS_PANELS, innerWhereParameters, WIDGET_INDEX);
                if (innerResult != null && innerResult.length() > 0) {
                    List<Widget> widgets = new ArrayList<>();
                    for (int i = 0; i < innerResult.length(); i++) {
                        Widget widget = this.getPanelById(innerResult.getJSONObject(i).getInt(PANEL_ID));
                        widget.setSize(Widget.Size.getSizeByName(innerResult.getJSONObject(i).getString("size")));
                        widget.setIndex(innerResult.getJSONObject(i).getInt(WIDGET_INDEX));
                        this.getPropertiesOfWidget(user, widget);
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

    private void getPropertiesOfWidget(User user, Widget widget) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("widget_id", widget.getId());

        boolean propertiesFound = false;
        JSONArray jsonDefaultProperties = this.database.get(Database.Table.PROPERTIES, whereParameters);
        for (int i = 0; i < jsonDefaultProperties.length(); i++) {
            JSONObject jsonObject = jsonDefaultProperties.getJSONObject(i);
            widget.getProperties().put(jsonObject.getString(PROPERTY),
                    new Property(jsonObject.getInt("id"),
                            jsonObject.getString(PROPERTY),
                            jsonObject.getString("name"),
                            jsonObject.getString("type"),
                            jsonObject.getString(DEFAULT_VALUE), null));
            propertiesFound = true;
        }

        if (propertiesFound) {
            whereParameters.clear();
            whereParameters.put(USER_ID, user.getId());
            for (Map.Entry<String, Property> propertyEntry : widget.getProperties().entrySet()) {
                whereParameters.put(PROPERTY_ID, propertyEntry.getValue().getId());
                JSONArray jsonProperty = this.database.get(Database.Table.USERS_PROPERTIES, whereParameters);
                if (jsonProperty.length() > 0) {
                    propertyEntry.getValue().setValue(jsonProperty.getJSONObject(0).getString(VALUE));
                }
            }
        }
    }

    public void updateWidgetProperties(Widget widget, List<Integer> propertiesToDelete) throws SQLException {
        for (Map.Entry<String, Property> propertyEntry : widget.getProperties().entrySet()) {
            if (propertyEntry.getValue().getId() == -1) {
                Map<String, Object> values = new HashMap<>();
                values.put("widget_id", widget.getId());
                values.put(PROPERTY, propertyEntry.getValue().getProperty());
                values.put("name", propertyEntry.getValue().getName());
                values.put("type", propertyEntry.getValue().getType());
                values.put(DEFAULT_VALUE, propertyEntry.getValue().getDefaultValue());
                this.database.insert(Database.Table.PROPERTIES, values);
            } else {
                Map<String, Object> whereParameters = new HashMap<>();
                whereParameters.put("id", propertyEntry.getValue().getId());

                Map<String, Object> values = new HashMap<>();
                values.put(PROPERTY, propertyEntry.getValue().getProperty());
                values.put("name", propertyEntry.getValue().getName());
                values.put("type", propertyEntry.getValue().getType());
                values.put(DEFAULT_VALUE, propertyEntry.getValue().getDefaultValue());

                this.database.update(Database.Table.PROPERTIES, whereParameters, values);
            }
        }

        for (int propertyId : propertiesToDelete) {
            Map<String, Object> whereParameters = new HashMap<>();
            whereParameters.put(PROPERTY_ID, propertyId);
            this.database.delete(Database.Table.USERS_PROPERTIES, whereParameters);

            whereParameters.clear();
            whereParameters.put("id", propertyId);
            this.database.delete(Database.Table.PROPERTIES, whereParameters);
        }
    }

    public void deleteWidgetProperties(List<Integer> propertiesToDelete) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        for (int propertyId : propertiesToDelete) {
            whereParameters.put("id", propertyId);
            this.database.delete(Database.Table.PROPERTIES, whereParameters);
            whereParameters.clear();
        }
    }

    public void updateUsersWidgetProperties(User user, Widget widget) throws SQLException {
        for (Map.Entry<String, Property> propertyEntry : widget.getProperties().entrySet()) {
            Map<String, Object> whereParameters = new HashMap<>();
            whereParameters.put(USER_ID, user.getId());
            whereParameters.put(PROPERTY_ID, propertyEntry.getValue().getId());

            JSONArray result = this.database.get(Database.Table.USERS_PROPERTIES, whereParameters);
            Map<String, Object> values = new HashMap<>();
            values.put(VALUE, propertyEntry.getValue().getValue());
            if (result.length() > 0) {
                this.database.update(Database.Table.USERS_PROPERTIES, whereParameters, values);
            } else {
                values.putAll(whereParameters);
                this.database.insert(Database.Table.USERS_PROPERTIES, values);
            }
        }
    }

    public Widget getPanelById(int id) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", id);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters);
        if (result != null && result.size() == 1) {
            return new Widget().fromDatabaseObject(result.get(0));
        }
        return null;
    }

    //-------------- Marketplace --------------\\
    public List<String> getTagsByPanelId(int widgetId) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        Map<String, String> onParameters = new HashMap<>();
        ArrayList<String> returningValue = new ArrayList<>();
        whereParameters.put(PANEL_ID, widgetId);
        onParameters.put(TAG_ID, "id");
        JSONArray databaseResult = this.database.get(Database.Table.PANELS_TAGS, Database.Table.TAGS, onParameters, whereParameters);
        for (int i = 0; i < databaseResult.length(); i++) {
            returningValue.add(databaseResult.getJSONObject(i).getString("text"));
        }
        return returningValue;
    }

    public List<Rating> getRatingsByWidgetID(int widgetId) throws SQLException {
        ArrayList<Rating> returningValue = new ArrayList<>();
        Map<String, String> onParameters = new HashMap<>();
        Map<String, Object> whereParameters = new HashMap<>();
        onParameters.put(USER_ID, "id");
        whereParameters.put(PANEL_ID, widgetId);
        List<? extends DatabaseObject> databaseResult = database.getObject(Database.Table.RATINGS, Database.Table.USERS, Rating.class, onParameters, whereParameters, "ratings.changed_on DESC");
        for (DatabaseObject databaseObject : databaseResult) {
            Rating rating = (Rating) databaseObject;
            returningValue.add(rating);
        }
        return returningValue;
    }

    public List<Widget> getTopWidgets(String sortByField) throws SQLException {
        ArrayList<Widget> returningValue = new ArrayList<>();
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(VISIBILITY, 1);
        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters, sortByField + " DESC");
        if (result != null && result.size() >= 4) {
            for (int i = 0; i < 4; i++) {
                Widget widget = new Widget().fromDatabaseObject(result.get(i));
                widget.setShortDescription(this.shortenShortDescOfPanel(widget.getShortDescription()));
                returningValue.add(widget);
            }
        } else if (result != null) {
            for (DatabaseObject databaseObject : result) {
                Widget widget = new Widget().fromDatabaseObject(databaseObject);
                widget.setShortDescription(this.shortenShortDescOfPanel(widget.getShortDescription()));
                returningValue.add(widget);
            }
        }
        return returningValue;
    }

    public Map<Widget, Rating> getFeaturedWidgets(int[] widgetIds) throws SQLException {
        Map<Widget, Rating> returningValue = new HashMap<>();
        for (int widgetId : widgetIds) {
            returningValue.put(this.getPanelById(widgetId), this.getTopRating(widgetId));
        }
        return returningValue;
    }

    public List<Widget> getSimilarWidgets(String category, int currentId) throws SQLException {
        ArrayList<Widget> returningValue = new ArrayList<>();
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(CATEGORY, category);
        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters);
        Collections.shuffle(result);
        if (result.size() > 3) {
            for (int i = 0; i < 3; i++) {
                Widget widget = new Widget().fromDatabaseObject(result.get(i));
                widget.setShortDescription(this.shortenShortDescOfPanel(widget.getShortDescription()));
                //exclude widget we are currently on
                if (widget.getId() != currentId) {
                    returningValue.add(widget);
                }
            }
        } else {
            for (DatabaseObject databaseObject : result) {
                Widget widget = new Widget().fromDatabaseObject(databaseObject);
                widget.setShortDescription(this.shortenShortDescOfPanel(widget.getShortDescription()));
                //exclude widget we are currently on
                if (widget.getId() != currentId) {
                    returningValue.add(widget);
                }
            }
            //fill rest of similar section with most popular widgets
            result = this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, new HashMap<>(),
                    "number_of_downloads DESC LIMIT " + (3 - returningValue.size() + 1));
            Collections.shuffle(result);
            for (DatabaseObject databaseObject : result) {
                Widget widget = new Widget().fromDatabaseObject(databaseObject);
                widget.setShortDescription(this.shortenShortDescOfPanel(widget.getShortDescription()));
                //exclude widget we are currently on
                if (widget.getId() != currentId) {
                    returningValue.add(widget);
                }
                if (returningValue.size() == 3){
                    break;
                }
            }
        }
        return returningValue;
    }

    private Rating getTopRating(int widgetId) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(PANEL_ID, widgetId);
        Map<String, String> onParameters = new HashMap<>();
        onParameters.put(USER_ID, "id");
        List<? extends DatabaseObject> result = database.getObject(Database.Table.RATINGS, Database.Table.USERS, Rating.class, onParameters, whereParameters, "rating DESC");
        if (!result.isEmpty()) {
            return (Rating) result.get(0);
        } else {
            return new Rating();
        }
    }

    private String shortenShortDescOfPanel(String shortDescr) {
        if (shortDescr.length() >= 100) {
            for (int charPosition = 99; charPosition > 0; charPosition--) {
                if (shortDescr.charAt(charPosition) == ' ') {
                    return shortDescr.substring(0, charPosition) + "...";
                }
            }
        }
        return shortDescr;
    }

    public boolean addRating(User user, String title, String text, int rating, int widgetId) {
        Map<String, Object> insertValue = new HashMap<>();
        insertValue.put(USER_ID, user.getId());
        insertValue.put(PANEL_ID, widgetId);
        insertValue.put("rating", rating);
        insertValue.put("title", title);
        insertValue.put("text", text);
        insertValue.put("changed_on", new Date());
        try {
            database.insert(Database.Table.RATINGS, insertValue);
        } catch (SQLException e) {
            return false;
        }
        try {
            Widget widget = this.getPanelById(widgetId);
            Map<String, Object> whereParameters = new HashMap<>();
            whereParameters.put("id", widgetId);
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("number_of_ratings", widget.getNumberOfRatings() + 1);
            updateValues.put("avg_of_ratings", ((widget.getAverageRating() * widget.getNumberOfRatings()) + rating) / (widget.getNumberOfRatings() + 1));
            database.update(Database.Table.PANELS, whereParameters, updateValues);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean addWidgetToPersonalDashup(User user, int widgetId, int sectionId, String widgetSize) throws SQLException {
        Section sectionToAddTo = null;
        this.getSectionsAndPanels(user);
        if (sectionId > 0) {
            for (Section section : user.getSections()) {
                if (section.getId() == sectionId) {
                    sectionToAddTo = section;
                    break;
                }
            }
            if (sectionToAddTo == null) {
                return false;
            }
            try {
                this.addWidgetToSection(this.getPanelById(widgetId), sectionToAddTo, widgetSize);
            } catch (SQLException e) {
                return false;
            }
        } else {
            try {
                sectionToAddTo = new Section(0, this.getPanelById(widgetId).getName(), user.getSections().size());
                sectionToAddTo.setId(this.addNewSection(user, sectionToAddTo));
                this.addWidgetToSection(this.getPanelById(widgetId), sectionToAddTo, widgetSize);
            } catch (SQLException e) {
                return false;
            }
        }
        try {
            Widget widget = this.getPanelById(widgetId);
            Map<String, Object> whereParameters = new HashMap<>();
            whereParameters.put("id", widgetId);
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("number_of_downloads", widget.getNumberOfDownloads() + 1);
            database.update(Database.Table.PANELS, whereParameters, updateValues);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public User getUserByToken(String token) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(TOKEN, token);

        JSONArray result = this.database.get(Database.Table.USERS_TOKENS, whereParameters);
        if (result != null && result.length() > 0) {
            whereParameters.clear();
            whereParameters.put("id", result.getJSONObject(0).get(USER_ID));
            User user = new User().fromDatabaseObject(
                    this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters).get(0));
            user.setToken(token);

            return user;
        }

        return null;
    }

    public User getUserById(int userId) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", userId);
        return new User().fromDatabaseObject(this.database.getObject(Database.Table.USERS, User.class, whereParameters).get(0));
    }

    public Settings getSettingsOfUser(User user) throws SQLException {
        Settings settings = new Settings();

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(USER_ID, user.getId());

        JSONObject jsonObject = this.database.get(Database.Table.USERS_SETTINGS, whereParameters).getJSONObject(0);
        settings.setLanguage(Locale.forLanguageTag(jsonObject.getString(LANGUAGE).isEmpty() ?
                "en" : jsonObject.getString(LANGUAGE)));
        settings.setTheme(Settings.Theme.getThemeByTechnicalName(jsonObject.getString("theme")));
        settings.setBackgroundImage(jsonObject.getString(BACKGROUND_IMAGE).equals("null") ? "" : jsonObject.getString(BACKGROUND_IMAGE));

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
        whereParameters.put(TOKEN, token);
        this.database.delete(Database.Table.USERS_TOKENS, whereParameters);
    }

    public User registerUser(String email, String userName, String password) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(EMAIL, email);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result == null || result.isEmpty()) {
            String salt = this.randomString.nextString(32);
            String hashedPassword = Hash.create(password, salt);

            Map<String, Object> values = new HashMap<>();
            values.put(EMAIL, email);
            values.put("user_name", userName);
            values.put("password", hashedPassword);
            values.put("salt", salt);

            this.database.insert(Database.Table.USERS, values);
            Settings defaultSettings = new Settings();
            return new User(this.database.getLatestId(Database.Table.USERS), email, userName, "", "", hashedPassword, salt, defaultSettings);
        }

        return null;
    }

    public void updatePassword(User user, String oldPassword, String newPassword) throws SQLException {
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
        values.put(EMAIL, user.getEmail());

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
        whereParameters.put(USER_ID, user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put(USER_ID, user.getId());
        values.put(BACKGROUND_IMAGE, user.getSettings().getBackgroundImage());
        values.put("theme", user.getSettings().getTheme().getTechnicalName());
        values.put(LANGUAGE, user.getSettings().getLanguage().toLanguageTag());

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
        whereParameters.put(USER_ID, user.getId());
        JSONObject jsonObject = this.database.get(Database.Table.USERS_SETTINGS, whereParameters).getJSONObject(0);

        Map<String, String> result = new HashMap<>();
        Set<String> iterSet = jsonObject.keySet();
        for (String key : iterSet) {
            result.put(key, jsonObject.get(key).toString());
        }
        result.remove("id");
        result.remove(USER_ID);
        return result;
    }

    public void updateLanguage(User user) throws SQLException {
        Map<String, Object> whereParameter = new HashMap<>();
        whereParameter.put(USER_ID, user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put(LANGUAGE, user.getSettings().getLanguage().toLanguageTag());

        this.database.update(Database.Table.USERS_SETTINGS, whereParameter, values);
    }

    private void updateSection(User user, Section section) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(USER_ID, user.getId());
        whereParameters.put("id", section.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("section_name", section.getName());
        values.put(SECTION_INDEX, section.getIndex());

        this.database.update(Database.Table.USER_SECTIONS, whereParameters, values);
    }

    private void deleteSection(User user, Section section) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", section.getId());
        whereParameters.put(USER_ID, user.getId());
        database.delete(Database.Table.USER_SECTIONS, whereParameters);
    }

    private void deleteWidgetsOfSection(Section section) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", section.getId());
        database.delete(Database.Table.SECTIONS_PANELS, whereParameters);

    }

    public int addNewSection(User user, Section section) throws SQLException {
        Map<String, Object> values = new HashMap<>();

        values.put("section_name", Objects.requireNonNullElse(section.getName(), "-"));
        values.put(USER_ID, user.getId());
        values.put(SECTION_INDEX, section.getIndex());

        this.database.insert(Database.Table.USER_SECTIONS, values);

        return database.getLatestId(Database.Table.USER_SECTIONS);
    }

    public void addWidgetToSection(Widget widget, Section section, String size) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put("id", section.getId());
        values.put(PANEL_ID, widget.getId());
        values.put(WIDGET_INDEX, widget.getIndex());
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

    // --- WIDGETS --- \\

    public void saveTodoWidgetState(User user, Todo todo) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put(USER_ID, user.getId());
        this.database.delete(Database.Table.TODOS, values);
        for (Task task : todo.getList()) {
            values.put(CONTENT, task.getContent());
            values.put(SELECTED, task.getSelected());
            this.database.insert(Database.Table.TODOS, values);
        }
    }

    public Todo loadTodoWidgetState(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(USER_ID, user.getId());
        JSONArray innerResult = this.database.get(Database.Table.TODOS, whereParameters);
        List<Task> entries = new ArrayList<>();
        for (int i = 0; i < innerResult.length(); i++) {
            JSONObject result = innerResult.getJSONObject(i);
            Task task = new Task(result.getString(CONTENT), result.getBoolean(SELECTED));
            entries.add(task);
        }
        return new Todo(entries);
    }

    public void saveFinanceChartWidgetState(User user, FinanceChart chart) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put(USER_ID, user.getId());
        this.database.delete(Database.Table.FINANCES, values);
        if(chart.getChart() != null) {
            for (SpendingChart spending : chart.getChart()) {
                values.put(CATEGORY, spending.getCategory());
                values.put(VALUE, spending.getValue());
                this.database.insert(Database.Table.FINANCES, values);
            }
        }
    }

    public void saveFinanceListWidgetState(User user, FinanceList list) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put(USER_ID, user.getId());
        this.database.delete(Database.Table.FINANCES, values);
        for (SpendingList spending : list.getFinanceList()) {
            values.put(CONTENT, spending.getContent());
            values.put(SELECTED, spending.getSelected());
            this.database.insert(Database.Table.FINANCES, values);
        }
    }

    public FinanceChart loadFinanceChartWidgetState(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(USER_ID, user.getId());
        JSONArray innerResult = this.database.get(Database.Table.FINANCES, whereParameters);
        List<SpendingChart> entries = new ArrayList<>();
        for (int i = 0; i < innerResult.length(); i++) {
            JSONObject result = innerResult.getJSONObject(i);
            SpendingChart spending;
            if (result.getString(CONTENT).isEmpty()) {
                spending = new SpendingChart(result.getString(CATEGORY), result.getInt(VALUE));
            } else {
                String[] split = result.getString(CONTENT).split(": ");
                spending = new SpendingChart(split[0], Integer.valueOf(split[1].substring(0, split[1].length() - 2)));
            }
            entries.add(spending);
        }
        return new FinanceChart(entries);
    }

    public FinanceList loadFinanceListWidgetState(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(USER_ID, user.getId());
        JSONArray innerResult = this.database.get(Database.Table.FINANCES, whereParameters);
        List<SpendingList> entries = new ArrayList<>();
        for (int i = 0; i < innerResult.length(); i++) {
            JSONObject result = innerResult.getJSONObject(i);
            SpendingList spending;
            if (result.getString(CONTENT).isEmpty()) {
                spending = new SpendingList(String.format("%s: %d €", result.getString(CATEGORY), result.getInt(VALUE)), false);
            } else {
                spending = new SpendingList(result.getString(CONTENT), result.getBoolean(SELECTED));
            }
            entries.add(spending);
        }
        return new FinanceList(entries);
    }

    // --- DRAFTS --- \\

    public void getUsersDrafts(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(USER_ID, user.getId());
        whereParameters.put(VISIBILITY, false);

        List<Draft> drafts = new ArrayList<>();
        for (DatabaseObject databaseObject : this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters)) {
            Draft draft = new Draft().fromDatabaseObject(databaseObject);
            this.getPropertiesOfWidget(user, draft);
            drafts.add(draft);
        }
        user.setDrafts(drafts);
    }

    public Draft createDraft(User user, String draftName) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put(USER_ID, user.getId());
        values.put("name", draftName);
        values.put(VISIBILITY, false);

        this.database.insert(Database.Table.PANELS, values);

        Draft draft = new Draft();
        draft.setId(this.database.getLatestId(Database.Table.PANELS));
        draft.setName(draftName);

        return draft;
    }

    public void updateWidgetInformation(Widget widget) throws SQLException, MissingInformationException, InvalidCodeException {
        this.updateWidgetInformation(widget, true);
    }

    public void updateWidgetInformation(Widget widget, boolean checkAfterUpdate) throws SQLException, MissingInformationException, InvalidCodeException {
        if (!checkAfterUpdate) {
            checkWidget(widget);
        }

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
        if (widget.getCategoryObject() != null) {
            values.put(CATEGORY, widget.getCategory());
        }

        this.database.update(Database.Table.PANELS, whereParameters, values);

        if (widget.getTags() != null) {
            this.updateWidgetTags(widget, widget.getTags());
        }

        if (checkAfterUpdate) {
            checkWidget(widget);
        }
    }

    private void checkWidget(Widget widget) throws MissingInformationException, InvalidCodeException {
        if ((Validator.isNullOrEmpty(widget.getName()) ||
                Validator.isNullOrEmpty(widget.getDescription()) ||
                Validator.isNullOrEmpty(widget.getShortDescription()) ||
                Validator.isNullOrEmpty(widget.getCode(Widget.Size.SMALL)) ||
                Validator.isNullOrEmpty(widget.getCode(Widget.Size.MEDIUM)) ||
                Validator.isNullOrEmpty(widget.getCode(Widget.Size.LARGE)))) {
            throw new MissingInformationException(Draft.class);
        }
        if (!Validator.validateWidget(widget, true)) {
            throw new InvalidCodeException(widget);
        }
    }

    public void deleteDraft(int draftId) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(PANEL_ID, draftId);

        this.database.delete(Database.Table.SECTIONS_PANELS, whereParameters);

        this.database.delete(Database.Table.PANELS_TAGS, whereParameters);

        whereParameters.clear();
        whereParameters.put("id", draftId);

        this.database.delete(Database.Table.PANELS, whereParameters);
    }

    public void publishDraft(int draftId) throws SQLException, MissingInformationException, InvalidCodeException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", draftId);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters);
        if (result != null && !result.isEmpty()) {
            Draft draft = new Draft().fromDatabaseObject(result.get(0));
            checkWidget(draft);

            Map<String, Object> values = new HashMap<>();
            values.put(VISIBILITY, true);
            values.put("publication_date", LocalDate.now());
            values.put("code_small", draft.getCodeSmall());
            values.put("code_medium", draft.getCodeMedium());
            values.put("code_large", draft.getCodeLarge());

            this.database.update(Database.Table.PANELS, whereParameters, values);
        }
    }


    // --- WIDGETS --- \\
    public List<Widget> getUsersWidgets(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put(USER_ID, user.getId());
        whereParameters.put(VISIBILITY, true);

        List<Widget> widgets = new ArrayList<>();
        for (DatabaseObject databaseObject : this.database.getObject(Database.Table.PANELS, DatabaseWidget.class, whereParameters)) {
            Widget widget = new Widget().fromDatabaseObject(databaseObject);
            this.getPropertiesOfWidget(user, widget);
            widgets.add(widget);
        }
        return widgets;
    }

    public Widget getWidgetOfUser(User user, int widgetId) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", widgetId);
        whereParameters.put(USER_ID, user.getId());

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.PANELS, Widget.class, whereParameters);
        if (result != null && !result.isEmpty()) {
            return new Widget().fromDatabaseObject(result.get(0));
        }
        return null;
    }


    // --- TAGS --- \\
    public List<Tag> getAllTags() throws SQLException {
        List<Tag> tags = new ArrayList<>();
        JSONArray result = this.database.get(Database.Table.TAGS, new HashMap<>());
        for (int i = 0; i < result.length(); i++) {
            tags.add(new Tag(result.getJSONObject(i).getInt("id"), result.getJSONObject(i).getString("text")));
        }
        return tags;
    }

    public void getTagsByWidget(Widget widget) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        Map<String, String> onParameters = new HashMap<>();
        whereParameters.put(PANEL_ID, widget.getId());
        onParameters.put(TAG_ID, "id");
        JSONArray result = this.database.get(Database.Table.PANELS_TAGS, Database.Table.TAGS, onParameters, whereParameters);
        for (int i = 0; i < result.length(); i++) {
            widget.getTags().add(new Tag(result.getJSONObject(i).getInt("id"),
                    result.getJSONObject(i).getString("text")));
        }
    }

    private void updateWidgetTags(Widget widget, final Set<Tag> tags) throws SQLException {
        final Set<Tag> newTags = new HashSet<>(tags);
        widget.getTags().clear();
        this.getTagsByWidget(widget);

        Set<Tag> tagsToDelete = new HashSet<>();

        for (Tag tag : newTags) {
            if (!widget.getTags().contains(tag)) {
                Map<String, Object> values = new HashMap<>();
                values.put(PANEL_ID, widget.getId());
                values.put(TAG_ID, tag.getId());
                this.database.insert(Database.Table.PANELS_TAGS, values);
            }
            tagsToDelete.add(tag);
        }
        newTags.removeAll(tagsToDelete);
        widget.getTags().removeAll(tagsToDelete);
        if (!widget.getTags().isEmpty()) {
            for (Tag tag : widget.getTags()) {
                Map<String, Object> whereParameters = new HashMap<>();
                whereParameters.put(PANEL_ID, widget.getId());
                whereParameters.put(TAG_ID, tag.getId());
                this.database.delete(Database.Table.PANELS_TAGS, whereParameters);
            }
        }
    }
}