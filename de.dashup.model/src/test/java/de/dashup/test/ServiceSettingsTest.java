package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.models.DatabaseObject;
import de.dashup.shared.models.DatabaseUser;
import de.dashup.shared.enums.Theme;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ServiceSettingsTest {
    private static final String USER_EMAIL = "nobody@test.com";
    private static final String USER_USERNAME = "NobodyTest";
    private static final String USER_SALT = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
    private static final String USER_PASSWORD = "password";
    private static final String USER_HASHED_PASSWORD = Hash.create(USER_PASSWORD, USER_SALT);

    private static Database database;
    private static DashupService dashupService;
    private DatabaseUser testUser;

    @BeforeAll
    public static void setupEnvironment() throws SQLException {
        database = UnitTestUtil.getDBInstance();
        dashupService = UnitTestUtil.getServiceInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
        testUser = dashupService.checkCredentials(USER_EMAIL, USER_PASSWORD, false);
    }

    @Test
    public void testUpdatePassword() throws SQLException {
        final String newPassword = "test";
        final String invalidPassword = "invalid";

        //test if everything works fine with valid credentials
        dashupService.updatePassword(testUser, USER_PASSWORD, newPassword);
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", USER_EMAIL);
        List<DatabaseObject> result = database.getObject(Database.Table.USERS, DatabaseUser.class, whereParams);
        Assertions.assertEquals(1, result.size());
        DatabaseUser databaseUser = (DatabaseUser) result.get(0);
        Assertions.assertFalse(databaseUser.getSalt().isEmpty());
        Assertions.assertFalse(databaseUser.getPassword().isEmpty());
        Assertions.assertNotEquals(USER_SALT, databaseUser.getSalt());
        Assertions.assertNotEquals(USER_HASHED_PASSWORD, databaseUser.getPassword());
        Assertions.assertEquals(Hash.create(newPassword, databaseUser.getSalt()), databaseUser.getPassword());

        //test if exception is thrown when entering wrong old password
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dashupService.updatePassword(testUser, invalidPassword, newPassword));
    }

    @Test
    public void testUpdateSettings() throws SQLException {
        final String backgroundURL = "https://images.pexels.com/photos/556416/pexels-photo-556416.jpeg";
        final String invalidURL = "null";
        final String language = testUser.getLanguage();
        dashupService.updateSettings(testUser, backgroundURL, Theme.GREEN_NATURE.getName(), language);

        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", USER_EMAIL);
        List<DatabaseObject> result = database.getObject(Database.Table.USERS, DatabaseUser.class, whereParams);
        Assertions.assertEquals(1, result.size());
        testUser = (DatabaseUser) result.get(0);

        Assertions.assertEquals(testUser.getBackgroundImage(), backgroundURL);
        Assertions.assertEquals(testUser.getTheme(), Theme.GREEN_NATURE.getName());
        Assertions.assertEquals(testUser.getLanguage(), language);
        Assertions.assertThrows(IllegalArgumentException.class, () -> dashupService.updateSettings(testUser, invalidURL, testUser.getTheme(), testUser.getLanguage()));
    }

}