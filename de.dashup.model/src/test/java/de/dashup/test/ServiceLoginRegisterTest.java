package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;

public class ServiceLoginRegisterTest {

    private static Database database;
    private static DashupService dashupService;
    //---------------Setup---------------\\
    @BeforeAll
    public static void createDatabaseConnection() throws SQLException {
        database = UnitTestUtil.getDBInstance(false, Database.DatabaseName.TEST);
        dashupService = UnitTestUtil.getServiceInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
    }

    //---------------Login---------------\\
    @Test
    public void testLogin() throws SQLException{
        User user = dashupService.checkCredentials("nobody@test.com","password",false);
        Assertions.assertNotNull(user,"Could not get user with correct credentials!");
        Assertions.assertEquals("nobody@test.com",user.getEmail(),"User information are incorrect!");
        Assertions.assertNull(user.getToken(), "User has token even if rememberMe is false!");
        HashMap<String,Object> whereParams = new HashMap<>();
        whereParams.put("user_id",user.getId());
        Assertions.assertEquals(0,database.get(Database.Table.USERS_TOKENS,whereParams).length(),
                                                    "Database contains token even if rememberMe is false!");
    }

    @Test
    public void testLoginWithRememberMe() throws SQLException{
        User user = dashupService.checkCredentials("nobody@test.com","password",true);
        Assertions.assertNotNull(user,"Could not get user with correct credentials!");
        Assertions.assertEquals("nobody@test.com",user.getEmail(),"User information are incorrect!");
        Assertions.assertNotNull(user.getToken(), "No token is generated when rememberMe is true!");
        HashMap<String,Object> whereParams = new HashMap<>();
        whereParams.put("user_id",user.getId());
        Assertions.assertEquals(1,database.get(Database.Table.USERS_TOKENS,whereParams).length(),
                                            "Database contains no token even if rememberMe is true!");
    }

    @Test
    public void testLoginInvalidCredentials() throws SQLException{
        User user = dashupService.checkCredentials("nobody@test.com","invalid",false);
        Assertions.assertNull(user);
        user = dashupService.checkCredentials("invalid@test.com","password",false);
        Assertions.assertNull(user);
        user = dashupService.checkCredentials("","",false);
        Assertions.assertNull(user);
    }

    @Test
    public void testGetUserByToken() throws  SQLException{
        //this is working, otherwise testLoginWithRememberMe() would fail
        User user = dashupService.checkCredentials("nobody@test.com","password",true);

        User userByToken = dashupService.getUserByToken(user.getToken());
        Assertions.assertNotNull(userByToken,"Could not get correct user by token!");
        Assertions.assertEquals(user.getId(),userByToken.getId(), "Could not get correct user by token!");
        Assertions.assertEquals(user.getEmail(),userByToken.getEmail());
    }

    //---------------Register---------------\\
    @Test
    public void testRegisterUser() throws SQLException{
        final String newEmail = "new@email.de";
        final String newUserName = "newUser";
        final String newPassword = "password";

        User newUser = dashupService.registerUser(newEmail,newUserName,newPassword);
        Assertions.assertNotNull(newUser, "Could not create new user!");
        Assertions.assertEquals(newEmail,newUser.getEmail());
        Assertions.assertEquals(newUserName,newUser.getUserName());
        Assertions.assertNotEquals(newPassword,newUser.getPassword(), "Password is not hashed!");
    }

    @Test
    public void testRegisterUserWithEmailInUse() throws SQLException{
        final String newEmail = "nobody@test.com";
        final String newUserName = "newUser";
        final String newPassword = "password";

        User newUser = dashupService.registerUser(newEmail,newUserName,newPassword);
        Assertions.assertNull(newUser, "Can create new user with email which is already in use!");
    }
}
