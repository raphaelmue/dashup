package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.shared.DatabaseModels.DatabaseObject;
import de.dashup.shared.DatabaseModels.DatabaseUser;
import de.dashup.util.string.Hash;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
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
        HashMap<String, Object> whereParams = new HashMap<>();
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
        List<DatabaseObject> result = database.getObject(Database.Table.USERS, DatabaseUser.class, whereParams);
        Assertions.assertEquals(1, result.size());
        DatabaseUser user = (DatabaseUser) result.get(0);
        Assertions.assertEquals(1, user.getID().intValue());
    }

    @Test
    public void testGetObjectWithOrderBy() throws SQLException {
        List<DatabaseObject> result = database.getObject(Database.Table.USERS, DatabaseUser.class, new HashMap<>(), "id DESC");
        Assertions.assertEquals(2, result.size());
        DatabaseUser user = (DatabaseUser) result.get(0);
        Assertions.assertEquals(2, user.getID().intValue());
    }

    @Test
    public void getUserThatDoesNotExist() throws SQLException {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "3");
        JSONArray result = database.get(Database.Table.USERS, whereParams);
        Assertions.assertEquals(0, result.length());
    }
}
