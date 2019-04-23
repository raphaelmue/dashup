package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.shared.*;
import de.dashup.shared.models.*;
import de.dashup.shared.enums.Size;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;
import de.dashup.util.string.URL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DashupService {

    private Database database;
    private final RandomString randomString;

    private static DashupService INSTANCE;

    public static DashupService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DashupService();
        }
        return INSTANCE;
    }

    private DashupService() {
        this.randomString = new RandomString();
        try {
            this.database = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseUser getUserById(Integer id) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", id.intValue());
        List<DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result.size() == 1) {
            DatabaseUser user = (DatabaseUser) result.get(0);
            return user;
        }
        return null;
    }

    public DatabaseUser getUserByToken(String token) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("token", token);
        List<DatabaseObject> result = this.database.getObject(Database.Table.TOKENS, DatabaseToken.class, whereParameters);
        whereParameters.clear();
        if (result.size() >= 1) {
            DatabaseToken databaseToken = (DatabaseToken) result.get(0);
            whereParameters.put("id", databaseToken.getUserID());
            result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
            if (result.size() == 1) {
                DatabaseUser user = (DatabaseUser) result.get(0);
                return user;
            }
            return null;
        }
        return null;
    }

    public DatabaseToken getTokenByUser(DatabaseUser user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getID());
        List<DatabaseObject> result = this.database.getObject(Database.Table.TOKENS, DatabaseToken.class, whereParameters);
        return (DatabaseToken) result.get(result.size() - 1);
    }

    public DatabaseUser checkCredentials(String email, String password, boolean rememberMe) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);
        List<DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result.size() == 1) {
            DatabaseUser user = (DatabaseUser) result.get(0);
            String hashedPassword = Hash.create(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                if (rememberMe) {
                    String token = this.randomString.nextString(64);
                    HashMap<String, Object> values = new HashMap<>();
                    values.put("user_id", user.getID());
                    values.put("token", token);
                    values.put("expiry_date", LocalDate.now().plusMonths(1));
                    this.database.insert(Database.Table.TOKENS, values);
                }
                return user;
            }
            return null;
    }
        return null;
    }

    public Layout loadUserLayout(DatabaseUser user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        Map<Integer, Object> sectionsByID = new HashMap<>();
        Map<Integer, Object> sectionsByPredecessorID = new HashMap<>();
        Map<Integer, Object> widgetsByID = new HashMap<>();
        Map<Integer, Object> widgetsByPredecessorID = new HashMap<>();
        List<Section> sections = new ArrayList<>();
        List<Widget> widgets = new ArrayList<>();
        DatabaseSection currentSection = null;
        DatabaseSectionWidgets currentSectionWidget = null;

        whereParameters.put("user_id", user.getID());
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
                whereParameters.put("id", currentSectionWidget.getWidgetID());
                result = this.database.getObject(Database.Table.WIDGETS, DatabaseWidget.class, whereParameters);
                whereParameters.clear();

                DatabaseWidget databaseWidget = (DatabaseWidget) result.get(0);
                Size size = Size.getSizeByName(currentSectionWidget.getSize());
                String htmlContent = "";
                switch(size){
                    case SMALL: htmlContent = databaseWidget.getHtmlSmall(); break;
                    case MEDIUM: htmlContent = databaseWidget.getHtmlMedium(); break;
                    case LARGE: htmlContent = databaseWidget.getHtmlLarge(); break;
                    default: htmlContent = databaseWidget.getHtmlLarge(); break;
                }
                Widget predecessor = (Widget) widgetsByID.get(currentSectionWidget.getPredecessorID());
                Widget widget = new Widget(databaseWidget.getID(), size, htmlContent, predecessor);
                widgets.add(widget);
                widgetsByID.put(currentSectionWidget.getID(), widget);
                currentSectionWidget = (DatabaseSectionWidgets) widgetsByPredecessorID.get(currentSectionWidget.getID());
            }

            Section predecessor = (Section) sectionsByID.get(currentSection.getPredecessorID());
            Section section = new Section(currentSection.getID(), currentSection.getName(), predecessor, widgets);
            sections.add(section);
            sectionsByID.put(currentSection.getID(), section);
            currentSection = (DatabaseSection) sectionsByPredecessorID.get(currentSection.getID());
            widgets = new ArrayList<>();
        }
        return new Layout(user, sections);
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
            values.put("username", username);
            values.put("password", hashedPassword);
            values.put("salt", salt);
            this.database.insert(Database.Table.USERS, values);

            whereParameters.put("id", this.database.getLatestId(Database.Table.USERS));
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

    public void updateNameAndSurname(DatabaseUser user, String newName, String newSurname) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getID());

        Map<String, Object> values = new HashMap<>();
        values.put("name", newName);
        values.put("surname", newSurname);

        this.database.update(Database.Table.USERS, whereParameters, values);
    }

    public void updateSettings(DatabaseUser user, String backgroundImage, String theme, String language) throws SQLException {
        if (!backgroundImage.isEmpty() && !URL.isValidURL(backgroundImage)) {
            throw new IllegalArgumentException("URL is not valid.");
        }

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getID());

        Map<String, Object> values = new HashMap<>();
        values.put("background_image", backgroundImage);
        values.put("theme", theme);
        values.put("language", language);

        this.database.update(Database.Table.USERS, whereParameters, values);
    }

    public void updateSection(DatabaseUser user, String name, Integer sectionID, Integer predecessorID) throws SQLException {
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

    public void addSection(DatabaseUser user, String name, Integer predecessorID) throws SQLException {
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