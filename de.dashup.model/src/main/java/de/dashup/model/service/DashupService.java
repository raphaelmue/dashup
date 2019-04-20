package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.shared.*;
import de.dashup.shared.DatabaseModels.*;
import de.dashup.shared.Enums.Size;
import de.dashup.shared.Enums.Theme;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DashupService {

    private Database database;
    private Map<Integer, User> users;
    private final RandomString randomString;

    private static DashupService INSTANCE;

    public static DashupService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DashupService();
        }
        return INSTANCE;
    }

    private DashupService() {
        this.users = new HashMap<>();
        this.randomString = new RandomString();
        try {
            this.database = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Layout loadUserLayout(Integer userID) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        Map<Integer, Object> sectionsByID = new HashMap<>();
        Map<Integer, Object> sectionsByPredecessorID = new HashMap<>();
        Map<Integer, Object> widgetsByID = new HashMap<>();
        Map<Integer, Object> widgetsByPredecessorID = new HashMap<>();
        List<Section> sections = new ArrayList<>();
        List<Widget> widgets = new ArrayList<>();
        DatabaseSection currentSection = null;
        DatabaseSectionWidgets currentSectionWidget = null;

        whereParameters.put("user_id", userID);
        List<DatabaseObject> result = this.database.getObject(Database.Table.SECTIONS, DatabaseSection.class, whereParameters);
        int amountSections = result.size();
        whereParameters.clear();

        for(DatabaseObject object: result){
            DatabaseSection section = (DatabaseSection) object;
            if(section.getPredecessorID() == null){
                currentSection = section;
            } else {
                sectionsByPredecessorID.put(section.getPredecessorID(), section);
            }
            sectionsByID.put(section.getID(), section);
        }

        while(sections.size() < amountSections){
            whereParameters.put("section_id", currentSection.getID());
            result = this.database.getObject(Database.Table.SECTION_WIDGETS, DatabaseSectionWidgets.class, whereParameters);
            int amountWidgets = result.size();
            whereParameters.clear();

            for(DatabaseObject object: result){
                DatabaseSectionWidgets sectionWidget = (DatabaseSectionWidgets) object;
                if(sectionWidget.getPredecessorID() == null){
                    currentSectionWidget = sectionWidget;
                } else {
                    widgetsByPredecessorID.put(sectionWidget.getPredecessorID(), sectionWidget);
                }
                widgetsByID.put(sectionWidget.getID(), sectionWidget);
            }

            while(widgets.size() < amountWidgets){
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
                Widget predecessor = (Widget) widgetsByID.get(currentSectionWidget.getPredecessorID());
                widgets.add(new Widget(widget.getID(), size, htmlContent, predecessor));
                currentSectionWidget = (DatabaseSectionWidgets) widgetsByPredecessorID.get(widget.getID());
            }

            Section predecessor = (Section) sectionsByID.get(currentSection.getPredecessorID());
            sections.add(new Section(currentSection.getID(), currentSection.getName(), predecessor, widgets));
            currentSection = (DatabaseSection) sectionsByPredecessorID.get(currentSection.getID());
            widgets.clear();
        }
        return new Layout(sections);
    }

    private Settings loadUserSettings(Integer userID) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", userID);
        List<DatabaseObject> result = this.database.getObject(Database.Table.SETTINGS, DatabaseSetting.class, whereParameters);

        DatabaseSetting setting = (DatabaseSetting) result.get(0);
        Locale language = Locale.forLanguageTag(setting.getLanguage().isEmpty() ? "en" : setting.getLanguage());
        Theme theme = Theme.getThemeByName(setting.getTheme());
        return new Settings(language, theme, setting.getBackgroundImage());
    }

    private User loadUser(DatabaseUser databaseUser, boolean rememberMe) throws SQLException {
        String token;
        if (rememberMe) {
            token = this.randomString.nextString(64);
            HashMap<String, Object> values = new HashMap<>();
            values.put("user_id", databaseUser.getID());
            values.put("token", token);
            values.put("expiry_date", LocalDate.now().plusMonths(1));
            this.database.insert(Database.Table.TOKENS, values);
        } else {
            token = null;
        }
        User user = new User(databaseUser.getID(), token, this.loadUserSettings(databaseUser.getID()), this.loadUserLayout(databaseUser.getID()));
        this.users.put(user.getID(), user);
        return user;
    }

    private boolean isValidURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public DatabaseUser checkCredentials(String email, String password, boolean rememberMe) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);
        List<DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result.size() == 1) {
            DatabaseUser user = (DatabaseUser) result.get(0);
            String hashedPassword = Hash.create(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
               this.loadUser(user, rememberMe);
               return user;
            }
            return null;
    }
        return null;
    }

    public User getUserById(Integer id){
        return users.get(id);
    }

    public DatabaseUser getDatabaseUserByToken(String token) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("token", token);
        List<DatabaseObject> result = this.database.getObject(Database.Table.TOKENS, DatabaseUser.class, whereParameters);
        whereParameters.clear();
        if (result.size() >= 1) {
            DatabaseToken databaseToken = (DatabaseToken) result.get(0);
            whereParameters.put("id", databaseToken.getUserID());
            DatabaseUser databaseUser = (DatabaseUser) (this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters)).get(0);
            return databaseUser;
        }
        return null;
    }

    public User getUserByToken(String token) throws SQLException {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            if(entry.getValue().getToken().equals(token)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void deleteToken(String token) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("token", token);
        this.database.delete(Database.Table.TOKENS, whereParameters);
    }

    public DatabaseUser registerUser(String email, String username, String password) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);
        List<DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        whereParameters.clear();

        if (result.size() == 0) {
            String salt = this.randomString.nextString(32);
            String hashedPassword = Hash.create(password, salt);

            Map<String, Object> values = new HashMap<>();
            values.put("email", email);
            values.put("user_name", username);
            values.put("password", hashedPassword);
            values.put("salt", salt);
            this.database.insert(Database.Table.USERS, values);
            values.clear();

            int userID = this.database.getLatestId(Database.Table.USERS);
            values.put("user_id", userID);
            values.put("theme", Theme.BLACK_NIGHT.getName());
            values.put("language", "en");
            this.database.insert(Database.Table.SETTINGS, values);

            whereParameters.put("user_id", userID);

            return (DatabaseUser) (this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters)).get(0);
        }

        return null;
    }

    public void updatePassword(DatabaseUser user, String oldPassword, String newPassword) throws SQLException, IllegalArgumentException {
        if (user.getPassword().equals(Hash.create(oldPassword, user.getSalt()))) {
            String newSalt = this.randomString.nextString(32);
            String newHashedPassword = Hash.create(newPassword, newSalt);

            Map<String, Object> whereParameters = new HashMap<>();
            whereParameters.put("id", user.getID());

            Map<String, Object> values = new HashMap<>();
            values.put("password", newHashedPassword);
            values.put("salt", newSalt);

            this.database.update(Database.Table.USERS, whereParameters, values);
        } else {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    public void updateNameAndSurname(User user, String newName, String newSurname) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getID());

        Map<String, Object> values = new HashMap<>();
        values.put("name", newName);
        values.put("surname", newSurname);

        this.database.update(Database.Table.USERS, whereParameters, values);
    }

    public void updateSettings(User user) throws SQLException {
        if (!user.getSettings().getBackgroundImage().isEmpty() && !isValidURL(user.getSettings().getBackgroundImage())) {
            throw new IllegalArgumentException("URL is not valid.");
        }

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getID());

        Map<String, Object> values = new HashMap<>();
        values.put("user_id", user.getID());
        values.put("background_image", user.getSettings().getBackgroundImage());
        values.put("theme", user.getSettings().getTheme().getName());
        values.put("language", user.getSettings().getLanguage().toLanguageTag());

        this.database.update(Database.Table.SETTINGS, whereParameters, values);
    }

    public void updateLanguage(User user) throws SQLException {
        Map<String, Object> whereParameter = new HashMap<>();
        whereParameter.put("user_id", user.getID());

        Map<String, Object> values = new HashMap<>();
        values.put("language", user.getSettings().getLanguage().toLanguageTag());

        this.database.update(Database.Table.SETTINGS, whereParameter, values);
    }

    public void updateSection(User user, String name, Integer sectionID, Integer predecessorID) throws SQLException {
        Map<String, Object> whereParameter = new HashMap<>();
        whereParameter.put("id", sectionID.intValue());

        Map<String, Object> values = new HashMap<>();
        values.put("user_id", user.getID());
        values.put("name", name);
        values.put("predecessor_id", predecessorID);

        this.database.update(Database.Table.SECTIONS, whereParameter, values);
    }

    public void deleteSection(Integer sectionID) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", sectionID);
        database.delete(Database.Table.SECTIONS, whereParameters);
        whereParameters.clear();

        whereParameters.put("section_id", sectionID);
        database.delete(Database.Table.SECTION_WIDGETS, whereParameters);
    }

    public void addSection(User user, String name, Integer predecessorID) throws SQLException {
        if (name == null) {
            name = "New Section";
        }

        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("user_id", user.getID());
        values.put("predecessor_id", predecessorID);

        this.database.insert(Database.Table.SECTIONS, values);
    }
}