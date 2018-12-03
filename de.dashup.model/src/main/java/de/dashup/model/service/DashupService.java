package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.shared.DatabaseObject;
import de.dashup.shared.DatabaseUser;
import de.dashup.shared.User;
import de.dashup.util.string.Hash;
import de.dashup.util.string.RandomString;
import org.json.JSONArray;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashupService {

    private Database database;
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

    public User changePassword(User user, String oldPassword, String newPassword) throws SQLException, IllegalArgumentException {
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
                                int heading_size, String heading_color, String font) throws SQLException {

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("id", user.getId());

        Map<String, Object> values = new HashMap<>();
        values.put("background_color", background_color);
        values.put("background_image", background_image);
        values.put("heading_size", heading_size);
        values.put("heading_color", heading_color);
        values.put("font", font);

        this.database.update(Database.Table.USER_LAYOUT, whereParameters, values);
        return true;
    }
}
