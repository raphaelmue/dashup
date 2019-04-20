package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.DatabaseModels.DatabaseUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class ServiceLoginRegisterTest {

    private static Database database;
    private static DashupService dashupService;

    //---------------Setup---------------\\
    @BeforeAll
    public static void setupEnvironment() throws SQLException {
        database = UnitTestUtil.getDBInstance();
        dashupService = UnitTestUtil.getServiceInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
    }

    //---------------Login---------------\\
    @Test
    public void testLogin() throws SQLException {
        final String correctMail = "nobody@test.com";
        final String correctPassword = "password";

        DatabaseUser user = dashupService.checkCredentials(correctMail, correctPassword, false);
        Assertions.assertNotNull(user, "Could not get user with correct credentials!");
        Assertions.assertEquals(correctMail, user.getEmail(), "User information are incorrect!");

        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", user.getID());
        Assertions.assertEquals(0, database.get(Database.Table.TOKENS, whereParams).length(),
                "Database contains token even if rememberMe is false!");
    }

    @Test
    public void testLoginWithRememberMe() throws SQLException {
        final String correctMail = "nobody@test.com";
        final String correctPassword = "password";

        DatabaseUser databaseUser = dashupService.checkCredentials(correctMail, correctPassword, true);
        Assertions.assertNotNull(databaseUser, "Could not get user with correct credentials!");
        Assertions.assertEquals(correctMail, databaseUser.getEmail(), "User information are incorrect!");

        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", databaseUser.getID());
        Assertions.assertEquals(1, database.get(Database.Table.TOKENS, whereParams).length(),
                "Database contains no token even if rememberMe is true!");
    }

    @Test
    public void testLoginInvalidCredentials() throws SQLException {
        final String correctMail = "nobody@test.com";
        final String incorrectMail = "invalid@test.com";
        final String correctPassword = "password";
        final String incorrectPassword = "invalid";

        DatabaseUser databaseUser = dashupService.checkCredentials(correctMail, incorrectPassword, false);
        Assertions.assertNull(databaseUser);
        databaseUser = dashupService.checkCredentials(incorrectMail, correctPassword, false);
        Assertions.assertNull(databaseUser);
        databaseUser = dashupService.checkCredentials("", "", false);
        Assertions.assertNull(databaseUser);
    }

    //---------------Logout---------------\\
    @Test
    public void testDeleteToken() throws SQLException{
        final String tokenToBeDeleted = "t8KJgrLLuP51Tilw6SiXjqoyM0EFX6OxrbTG5giYbXRPoJk1dUOoUHRHbx7lTPiD";

        dashupService.deleteToken(tokenToBeDeleted);

        Assertions.assertEquals(0, database.get(Database.Table.TOKENS, new HashMap<>()).length());
    }

    //---------------Register---------------\\
    @Test
    public void testRegisterUser() throws SQLException {
        final String newEmail = "new@email.de";
        final String newUserName = "newUser";
        final String newPassword = "password";

        DatabaseUser newUser = dashupService.registerUser(newEmail, newUserName, newPassword);
        Assertions.assertNotNull(newUser, "Could not create new user!");
        Assertions.assertEquals(newEmail, newUser.getEmail());
        Assertions.assertEquals(newUserName, newUser.getUsername());
        Assertions.assertNotEquals(newPassword, newUser.getPassword(), "Password is not hashed!");
    }

    @Test
    public void testRegisterUserWithEmailInUse() throws SQLException {
        final String emailInUse = "nobody@test.com";
        final String newUserName = "newUser";
        final String newPassword = "password";

        DatabaseUser newUser = dashupService.registerUser(emailInUse, newUserName, newPassword);
        Assertions.assertNull(newUser, "Can create new user with email which is already in use!");
    }
}