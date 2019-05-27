package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.shared.DatabaseObject;
import de.dashup.shared.DatabaseUser;
import de.dashup.shared.User;
import de.dashup.shared.Widget;
import de.dashup.util.string.Hash;
import org.json.JSONArray;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@Tag("unit")
public class DatabaseUnitTest {
    private static Database database;

    @BeforeAll
    public static void createDatabaseConnection() throws SQLException {
        database = UnitTestUtil.getDBInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        UnitTestUtil.setUpTestDataset(database);
    }

    @Test
    public void testInsert() throws SQLException {
        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);

        Map<String, Object> testDataMap = new HashMap<>();
        testDataMap.put("email", "testInsert@test.com");
        testDataMap.put("user_name", "test");
        testDataMap.put("name", "Insert");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        database.insert(Database.Table.USERS, testDataMap);

        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "3");
        JSONArray result = database.get(Database.Table.USERS, whereParams);
        Assertions.assertEquals("test", result.getJSONObject(0).getString("user_name"));
        Assertions.assertEquals(hashedPassword, result.getJSONObject(0).getString("password"));
        Assertions.assertEquals("testInsert@test.com", result.getJSONObject(0).getString("email"));
        Assertions.assertEquals("Test", result.getJSONObject(0).getString("surname"));
        Assertions.assertEquals("Insert", result.getJSONObject(0).getString("name"));
        Assertions.assertEquals(salt, result.getJSONObject(0).getString("salt"));
    }

    @Test
    public void testReadLatestID() throws SQLException {
        Assertions.assertEquals(2, database.getLatestId(Database.Table.USERS));
    }

    @Test
    public void testDelete() throws SQLException {
        //delete users settings, without this deletion of user will fail due to foreign key constraints
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", "1");
        database.delete(Database.Table.USERS_SETTINGS, whereParams);
        Assertions.assertEquals(0, database.get(Database.Table.USERS_SETTINGS, whereParams).length());
        //delete panels in section of user, without this deletion of user will fail due to foreign key constraints
        whereParams.clear();
        whereParams.put("id","1");
        database.delete(Database.Table.SECTIONS_PANELS,whereParams);
        Assertions.assertEquals(0,database.get(Database.Table.SECTIONS_PANELS,whereParams).length());
        //delete sections of user, without this deletion of user will fail due to foreign key constraints
        whereParams.clear();
        whereParams.put("user_id", "1");
        database.delete(Database.Table.USER_SECTIONS,whereParams);
        Assertions.assertEquals(0,database.get(Database.Table.USER_SECTIONS,whereParams).length());
        //delete panels of user, without this deletion of user will fail due to foreign key constraints
        database.delete(Database.Table.PANELS,whereParams);
        Assertions.assertEquals(0,database.get(Database.Table.PANELS,whereParams).length());
        //delete User
        whereParams.clear();
        whereParams.put("id", "1");
        database.delete(Database.Table.USERS, whereParams);
        Assertions.assertEquals(0, database.get(Database.Table.USERS, whereParams).length());
        whereParams.clear();
        Assertions.assertEquals(1, database.get(Database.Table.USERS, whereParams).length());
    }

    @Test
    public void testGet() throws SQLException {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "1");
        JSONArray result = database.get(Database.Table.USERS, whereParams);
        Assertions.assertEquals(1, result.getJSONObject(0).getInt("id"));
    }

    @Test
    public void testUpdate() throws SQLException {
        //Update DB
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "1");
        HashMap<String, Object> toUpdate = new HashMap<>();
        toUpdate.put("name", "test");
        toUpdate.put("surname", "nobody");
        database.update(Database.Table.USERS, whereParams, toUpdate);

        //Read from DB to assert correct result
        JSONArray result = database.get(Database.Table.USERS, whereParams);
        Assertions.assertEquals("test", result.getJSONObject(0).getString("name"));
        Assertions.assertEquals("nobody", result.getJSONObject(0).getString("surname"));
    }

    @Test
    public void testGetWithOrderBy() throws SQLException {
        JSONArray result = database.get(Database.Table.USERS, new HashMap<>(), "id DESC");
        Assertions.assertEquals(2, result.getJSONObject(0).getInt("id"));
    }

    @Test
    public void testGetObject() throws SQLException {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "1");
        List<? extends DatabaseObject> result = database.getObject(Database.Table.USERS, DatabaseUser.class, whereParams);
        Assertions.assertEquals(1, result.size());
        User user = (User) new User().fromDatabaseObject(result.get(0));
        Assertions.assertEquals(1, user.getId());
    }

    @Test
    public void testGetObjectWithOrderBy() throws SQLException {
        List<? extends DatabaseObject> result = database.getObject(Database.Table.USERS, DatabaseUser.class, new HashMap<>(), "id DESC");
        Assertions.assertEquals(2, result.size());
        User user = (User) new User().fromDatabaseObject(result.get(0));
        Assertions.assertEquals(2, user.getId());
    }

    @Test
    public void getUserThatDoesNotExist() throws SQLException {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "3");
        JSONArray result = database.get(Database.Table.USERS, whereParams);
        Assertions.assertEquals(0, result.length());
    }

    @Test
    public void findBySearchAndFilter() throws SQLException{
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("name", "panel1");
        List<String> operatorList = new ArrayList<>();
        operatorList.add("=");

        whereParams.put("avg_of_ratings","75");
        operatorList.add(">=");

        whereParams.put("publication_date","2019-04-19");
        operatorList.add(">=");

        List<? extends DatabaseObject> result = database.findByRange(Database.Table.PANELS,whereParams,Widget.class,operatorList);
        Assertions.assertEquals(1,result.size());
        Widget widget = (Widget) new Widget().fromDatabaseObject(result.get(0));
        Assertions.assertEquals(1,widget.getId());
    }
}