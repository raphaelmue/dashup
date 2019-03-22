package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DatabaseObject;
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
    private User testUser;

    @BeforeAll
    public static void setupEnvironment() throws SQLException {
        database = UnitTestUtil.getDBInstance(false, Database.DatabaseName.TEST);
        dashupService = UnitTestUtil.getServiceInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
        testUser = new User(1, USER_EMAIL, USER_USERNAME, "", "", USER_HASHED_PASSWORD, USER_SALT, new Settings());
    }

    @Test
    public void testGetSettings() throws SQLException {
        Settings settings = dashupService.getSettingsOfUser(testUser);
        Assertions.assertNotNull(settings);
        Assertions.assertEquals(testUser.getSettings().getTheme().getName(), settings.getTheme().getName());
        Assertions.assertEquals(testUser.getSettings().getLanguage(), settings.getLanguage());
        Assertions.assertEquals("null", settings.getBackgroundImage());
    }

    @Test
    public void testUpdatePassword() throws SQLException {
        final String newPassword = "test";
        final String invalidPassword = "invalid";

        //test if everything works fine with valid credentials
        User updatedUser = dashupService.updatePassword(testUser, USER_PASSWORD, newPassword);
        Assertions.assertNotEquals(USER_HASHED_PASSWORD, updatedUser.getPassword());
        Assertions.assertNotEquals(USER_SALT, updatedUser.getSalt());
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", USER_EMAIL);
        List<? extends DatabaseObject> result = database.getObject(Database.Table.USERS, User.class, whereParams);
        Assertions.assertEquals(1, result.size());
        User databaseUser = new User();
        databaseUser.fromDatabaseObject(result.get(0));
        Assertions.assertEquals(updatedUser.getPassword(), databaseUser.getPassword(),
                "Password from returned user and password on DB differ!");

        //test if exception is thrown when entering wrong old password
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dashupService.updatePassword(testUser, invalidPassword, newPassword));
    }

    @Test
    public void testUpdateSettings() throws SQLException {
        final String backgroundURL = "https://images.pexels.com/photos/556416/pexels-photo-556416.jpeg";
        final String invalidURL = "null";

        //test update w/o insert
        testUser.getSettings().setTheme(Settings.Theme.BLACK_AND_WHITE);
        testUser.getSettings().setLanguage(new Locale("de"));
        testUser.getSettings().setBackgroundImage(backgroundURL);

        dashupService.updateSettings(testUser, false);

        Settings updatedSettings = dashupService.getSettingsOfUser(testUser);

        Assertions.assertEquals(testUser.getSettings().getBackgroundImage(), updatedSettings.getBackgroundImage());
        Assertions.assertEquals(testUser.getSettings().getTheme(), updatedSettings.getTheme());
        Assertions.assertEquals(testUser.getSettings().getLanguage().toLanguageTag(),
                updatedSettings.getLanguage().toLanguageTag());

        //test update w/o insert and invalid URL
        testUser.getSettings().setBackgroundImage(invalidURL);
        Assertions.assertThrows(IllegalArgumentException.class, () -> dashupService.updateSettings(testUser, false));

        //test update with insert
        //prep=delete settings for user with id 2 to be able to insert again
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", 2);
        database.delete(Database.Table.USERS_SETTINGS, whereParams);
        //new insert
        testUser.setId(2);
        dashupService.updateSettings(testUser, true);

        JSONArray result = database.get(Database.Table.USERS_SETTINGS, whereParams);
        Assertions.assertEquals(1, result.length());
        Assertions.assertEquals(testUser.getSettings().getBackgroundImage(),
                result.getJSONObject(0).getString("background_image"));
    }

    @Test
    public void testUpdateLanguage() throws SQLException {
        //execute method
        testUser.getSettings().setLanguage(new Locale("de"));
        dashupService.updateSettings(testUser);
        //assert result
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", 1);
        JSONArray result = database.get(Database.Table.USERS_SETTINGS, whereParams);
        Assertions.assertEquals(1, result.length());
        Assertions.assertEquals(testUser.getSettings().getLanguage().toLanguageTag(),
                result.getJSONObject(0).getString("language"));
    }

    @Test
    public void testLoadLayout() throws SQLException {
        Map<String, String> result = dashupService.loadLayout(testUser);
        Assertions.assertEquals(testUser.getSettings().getLanguage().toLanguageTag(), result.get("language"));
        Assertions.assertEquals(testUser.getSettings().getTheme().getTechnicalName(), result.get("theme"));
        Assertions.assertEquals("null", result.get("background_image"));
    }
}
