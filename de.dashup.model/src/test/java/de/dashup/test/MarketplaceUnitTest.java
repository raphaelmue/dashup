package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.shared.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
@Tag("unit")
public class MarketplaceUnitTest {

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

    @Test
    public void testAddRating() throws SQLException {
        Assertions.assertEquals(0,database.get(Database.Table.RATINGS,new HashMap<>()).length());
        HashMap<String,Object> whereParams = new HashMap<>();
        whereParams.put("id",1);
        User user = (User) database.getObject(Database.Table.USERS, User.class,whereParams).get(0);
        dashupService.addRating(user,"Test","This is a test", 50, 1);
        Assertions.assertEquals(1,database.get(Database.Table.RATINGS,new HashMap<>()).length());
    }

    @Test
    public void testAddWidgetToExistingSection() throws SQLException {
        HashMap<String,Object> whereParams = new HashMap<>();
        whereParams.put("user_id",1);
        Assertions.assertEquals(2,database.get(Database.Table.USER_SECTIONS,whereParams).length());
        whereParams.clear();
        whereParams.put("id",1);
        Assertions.assertEquals(2,database.get(Database.Table.SECTIONS_PANELS,whereParams).length());
        whereParams.clear();
        whereParams.put("id",1);
        User user = (User) database.getObject(Database.Table.USERS, User.class,whereParams).get(0);

        dashupService.addWidgetToPersonalDashup(user,3,1, "small");

        whereParams.clear();
        whereParams.put("user_id",1);
        Assertions.assertEquals(2,database.get(Database.Table.USER_SECTIONS,whereParams).length());
        whereParams.clear();
        whereParams.put("id",1);
        Assertions.assertEquals(3,database.get(Database.Table.SECTIONS_PANELS,whereParams).length());
    }
}
