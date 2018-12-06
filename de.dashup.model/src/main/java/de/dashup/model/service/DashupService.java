package de.dashup.model.service;

import de.dashup.model.builder.PanelLoader;
import de.dashup.model.db.Database;
import de.dashup.shared.*;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;
import org.json.JSONArray;

import java.sql.SQLException;
import java.time.LocalDate;
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
