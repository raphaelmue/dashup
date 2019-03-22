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

    @BeforeAll
    public static void createDatabaseConnection() throws SQLException {
        database = UnitTestUtil.getDBInstance(false, Database.DatabaseName.TEST);
        dashupService = UnitTestUtil.getServiceInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
    }

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
}
