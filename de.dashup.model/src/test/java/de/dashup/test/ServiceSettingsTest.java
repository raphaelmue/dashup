package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DatabaseModels.DatabaseObject;
import de.dashup.shared.DatabaseModels.DatabaseSetting;
import de.dashup.shared.DatabaseModels.DatabaseUser;
import de.dashup.shared.Enums.Theme;
import de.dashup.shared.Settings;
import de.dashup.shared.User;
import de.dashup.util.string.Hash;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class ServiceSettingsTest {
    private static final String USER_EMAIL = "nobody@test.com";
    private static final String USER_USERNAME = "NobodyTest";
    private static final String USER_SALT = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
    private static final String USER_PASSWORD = "password";
    private static final String USER_HASHED_PASSWORD = Hash.create(USER_PASSWORD, USER_SALT);

    private static Database database;
    private static DashupService dashupService;
    private DatabaseUser testDatabaseUser;
    private User testUser;

    @BeforeAll
    public static void setupEnvironment() throws SQLException {
        database = UnitTestUtil.getDBInstance();
        dashupService = UnitTestUtil.getServiceInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
        testDatabaseUser = dashupService.checkCredentials(USER_EMAIL, USER_PASSWORD, false);
        testUser = dashupService.getUserById(testDatabaseUser.getID());
    }

    @Test
    public void testGetSettings() throws SQLException {
        Settings settings = testUser.getSettings();
        Assertions.assertNotNull(settings);
    }

    @Test
    public void testUpdatePassword() throws SQLException {
        final String newPassword = "test";
        final String invalidPassword = "invalid";

        //test if everything works fine with valid credentials
        dashupService.updatePassword(testDatabaseUser, USER_PASSWORD, newPassword);
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", USER_EMAIL);
        List<DatabaseObject> result = database.getObject(Database.Table.USERS, User.class, whereParams);
        Assertions.assertEquals(1, result.size());
        DatabaseUser databaseUser = (DatabaseUser) result.get(0);
        Assertions.assertFalse(databaseUser.getSalt().isEmpty());
        Assertions.assertFalse(databaseUser.getPassword().isEmpty());
        Assertions.assertNotEquals(USER_SALT, databaseUser.getSalt());
        Assertions.assertNotEquals(USER_HASHED_PASSWORD, databaseUser.getPassword());
        Assertions.assertEquals(Hash.create(newPassword, databaseUser.getSalt()), databaseUser.getPassword());

        //test if exception is thrown when entering wrong old password
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dashupService.updatePassword(testDatabaseUser, invalidPassword, newPassword));
    }

    @Test
    public void testUpdateSettings() throws SQLException {
        final String backgroundURL = "https://images.pexels.com/photos/556416/pexels-photo-556416.jpeg";
        final String invalidURL = "null";

        //test update w/o insert
        testUser.getSettings().setTheme(Theme.GREEN_NATURE);
        testUser.getSettings().setBackgroundImage(backgroundURL);

        dashupService.updateSettings(testUser);

        Settings updatedSettings = testUser.getSettings();

        Assertions.assertEquals(testUser.getSettings().getBackgroundImage(), updatedSettings.getBackgroundImage());
        Assertions.assertEquals(testUser.getSettings().getTheme(), updatedSettings.getTheme());
        Assertions.assertEquals(testUser.getSettings().getLanguage().toLanguageTag(),
                updatedSettings.getLanguage().toLanguageTag());

        //test update w/o insert and invalid URL
        testUser.getSettings().setBackgroundImage(invalidURL);
        Assertions.assertThrows(IllegalArgumentException.class, () -> dashupService.updateSettings(testUser));
    }

    @Test
    public void testUpdateLanguage() throws SQLException {
        testUser.getSettings().setLanguage(new Locale("de"));
        dashupService.updateLanguage(testUser);

        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", 1);
        List<DatabaseObject> result = database.getObject(Database.Table.SETTINGS, DatabaseSetting.class, whereParams);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testUser.getSettings().getLanguage().toLanguageTag(), ((DatabaseSetting) result.get(0)).getLanguage());
    }

    @Test
    public void testLoadLayout() throws SQLException {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", 1);
        List<DatabaseObject> result = database.getObject(Database.Table.SETTINGS, DatabaseSetting.class, whereParams);
        DatabaseSetting setting = (DatabaseSetting) result.get(0);
        Assertions.assertEquals(testUser.getSettings().getLanguage().toLanguageTag(), setting.getLanguage());
        Assertions.assertEquals(testUser.getSettings().getTheme().getName(), setting.getTheme());
        Assertions.assertEquals("null", setting.getBackgroundImage());
    }
}