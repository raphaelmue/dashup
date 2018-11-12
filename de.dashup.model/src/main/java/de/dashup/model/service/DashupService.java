package de.dashup.model.service;

import de.dashup.model.db.Database;
import de.dashup.shared.DatabaseObject;
import de.dashup.shared.User;
import de.dashup.util.string.Hash;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashupService {

    private Database database;

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
     * @param email    email of user
     * @param password password in plain text
     * @return User object, if credentials are correct, else null.
     */
    public User checkCredentials(String email, String password) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);

        List<? extends DatabaseObject> result = this.database.getObject(Database.Table.USERS, User.class, whereParameters);
        if (result != null && result.size() == 1) {
            User user = (User) new User().fromDatabaseObject(result.get(0));
            String hashedPassword = Hash.create(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }

        return null;
    }
}
