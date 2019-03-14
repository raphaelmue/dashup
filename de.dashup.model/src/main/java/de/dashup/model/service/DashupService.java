package de.dashup.model.service;

import de.dashup.model.builder.PanelLoader;
import de.dashup.model.db.Database;
import de.dashup.shared.*;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashupService {

    private Database database;
    private PanelLoader panelLoader;
    private RandomString randomString = new RandomString();

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
                if (rememberMe) {
                    user.setSettings(this.getSettingsOfUser(user));
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
                if (innerResult != null) {
                    ArrayList<Panel> panels = new ArrayList<>();
                    for (int j = 0; j < innerResult.length(); j++) {
                        panels.add(panelLoader.loadPanel(Integer.parseInt(innerResult.getJSONObject(j).get("panel_id").toString())));
                    }
                    section.setPanels(panels);
                }
                sections.add(section);
            }
        }
        user.setSections(sections);
        return user;
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
        whereParameters.put("id", user.getId());

        JSONObject jsonObject = this.database.get(Database.Table.USERS, whereParameters).getJSONObject(0);
        settings.setLanguage(Locale.forLanguageTag(jsonObject.getString("language").isEmpty() ?
                "en" : jsonObject.getString("language")));

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

    public User registerUser(String email, String name, String surname, String password) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USERS, DatabaseUser.class, whereParameters);
        if (result == null || result.size() == 0) {
            String salt = this.randomString.nextString(32);
            String hashedPassword = Hash.create(password, salt);

            Map<String, Object> values = new HashMap<>();
            values.put("email", email);
            values.put("name", name);
            values.put("surname", surname);
            values.put("password", hashedPassword);
            values.put("salt", salt);

            this.database.insert(Database.Table.USERS, values);
            return new User(this.database.getLatestId(Database.Table.USERS), email, name, surname, hashedPassword, salt);
        }

        return null;
    }

    public User updatePassword(User user, String oldPassword, String newPassword) throws SQLException, IllegalArgumentException {
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
            return user;
        } else {
            throw new IllegalArgumentException("Passworts does not match");
        }
    }

    public boolean changeLayout(User user, String background_color, String background_image,
                                int heading_size, String heading_color, String font_heading, String font_text, boolean insert){

        Pattern colorPattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
        Pattern urlPattern = Pattern.compile("(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)");
        String[] allowedFonts = {"Andale","Mono","Arial","Arial Black","Avant Garde","Calibri","Courier New","Helvetica","Impact","Times New Roman","Verdana"};

        boolean validColors = colorPattern.matcher(background_color).matches() && colorPattern.matcher(heading_color).matches();
        boolean validURL = urlPattern.matcher(background_image).matches();
        boolean validFonts = Arrays.asList(allowedFonts).contains(font_heading) && Arrays.asList(allowedFonts).contains(font_text);
        if(!(validColors && validFonts && heading_size >= 12 && heading_size <= 40 && validURL)){
            return false;
        }

        try {
            Map<String, Object> whereParameters = new HashMap<>();
            whereParameters.put("user_id", user.getId());

            Map<String, Object> values = new HashMap<>();
            values.put("background_color", background_color);
            values.put("background_image", background_image);
            values.put("heading_size", heading_size);
            values.put("heading_color", heading_color);
            values.put("font_heading", font_heading);
            values.put("font_text", font_text);

            if (insert) {
                this.database.insert(Database.Table.USER_LAYOUT, values);
            } else {
                this.database.update(Database.Table.USER_LAYOUT, whereParameters, values);
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Map<String, String> loadLayout(User user) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        JSONObject jsonObject = this.database.get(Database.Table.USER_LAYOUT, whereParameters).getJSONObject(0);

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

    public void updateSettings(User user) throws SQLException {
        Map<String, Object> whereParameter = new HashMap<>();
        whereParameter.put("id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("language", user.getSettings().getLanguage().toLanguageTag());

        this.database.update(Database.Table.USERS, whereParameter, values);
    }

    public void updateSection(User user,String section_name,int section_id,int section_order)throws SQLException
    {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_id", user.getId());
        whereParameters.put("section_id", section_id);

        Map<String, Object> values = new HashMap<>();

        if(section_name.equals("%old%")==false){

            values.put("section_name",section_name);
        }
        if(section_order!=-1)values.put("section_order",section_order);

        if(values.isEmpty()==false){
            this.database.update(Database.Table.USER_SECTIONS, whereParameters, values);
        }
        return;
    }

    public void deleteSection(User user,int section_id) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("section_id",section_id);
        whereParameters.put("user_id",user.getId());
        database.delete(Database.Table.USER_SECTIONS,whereParameters);
    }

    public void addSection(User user,String section_name,int section_order) throws SQLException {
        if(section_name==null)section_name="New Section";

        Map<String, Object> values = new HashMap<>();
        values.put("section_name",section_name);
        values.put("user_id", user.getId());

        this.database.insert(Database.Table.USER_SECTIONS, values);

        return;
    }
}
