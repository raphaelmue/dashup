package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.util.string.Hash;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUnitTest {
    private static Database database;

    @BeforeAll
    public static void createDatabaseConnection() throws SQLException {
        Database.setHost(false);
        Database.setDbName(Database.DatabaseName.TEST);
        database = Database.getInstance();
    }

    @BeforeEach
    public void cleanDB() throws SQLException {
        database.clearDatabase();
        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);

        Map<String, Object> testDataMap = new HashMap<>();
        testDataMap.put("email", "nobody@test.com");
        testDataMap.put("name", "Nobody");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        database.insert(Database.Table.USERS, testDataMap);

        testDataMap.clear();
        testDataMap.put("email", "second@test.com");
        testDataMap.put("name", "Second");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        database.insert(Database.Table.USERS, testDataMap);
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
    }

    @Test
    public void testSimpleGet() throws SQLException {
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
        HashMap<String, Object> whereParams = new HashMap<>();
        JSONArray result = database.get(Database.Table.USERS, whereParams, "id DESC");
        Assertions.assertEquals(2, result.getJSONObject(0).getInt("id"));
    }
}
