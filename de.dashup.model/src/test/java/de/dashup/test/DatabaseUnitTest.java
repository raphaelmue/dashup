package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.util.string.Hash;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUnitTest {
    private Database database;

    @BeforeEach
    protected void cleanDB() {
        Database.setHost(false);
        Database.setDbName(Database.DatabaseName.TEST);
        try {
            database = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Could not get instance due to SQLException!");
        }
        try {
            database.clearDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Could not clear database due to SQLException!");
        }
        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);

        Map<String, Object> testDataMap = new HashMap<>();
        testDataMap.put("email", "nobody@test.com");
        testDataMap.put("name", "Nobody");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        try {
            database.insert(Database.Table.USERS, testDataMap);
        } catch (SQLException e) {
            Assert.fail("Could not insert test data set to database!");
        }

        testDataMap.clear();
        testDataMap.put("email", "second@test.com");
        testDataMap.put("name", "Second");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        try {
            database.insert(Database.Table.USERS, testDataMap);
        } catch (SQLException e) {
            Assert.fail("Could not insert test data set to database!");
        }
    }

    @Test
    protected void testReadLatestID() {
        try {
            Assert.assertEquals(2,database.getLatestId(Database.Table.USERS));
        }catch (SQLException e){
            Assert.fail("Could not read latest id due to SQLException!");
        }
    }

    @Test
    protected void testDelete() {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "1");
        try {
            database.delete(Database.Table.USERS, whereParams);
        } catch (SQLException e) {
            Assert.fail("Could not delete from DB due to SQLException!");
        }
    }

    @Test
    protected void testSimpleGet() {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "1");
        try {
            JSONArray result = database.get(Database.Table.USERS, whereParams);
            Assert.assertEquals(1, result.getJSONObject(0).getInt("id"));
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            Assert.fail("Could not read from database due to SQLException!");
        }
    }

    @Test
    protected void testUpdate() {
        //Update DB
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("id", "1");
        HashMap<String, Object> toUpdate = new HashMap<>();
        toUpdate.put("name", "test");
        toUpdate.put("surname", "nobody");
        try {
            database.update(Database.Table.USERS, whereParams, toUpdate);
        } catch (SQLException e) {
            Assert.fail("Could not update DB due to SQLException!");
        }
        //Read from DB to assert correct result
        try {
            JSONArray result = database.get(Database.Table.USERS, whereParams);
            Assert.assertEquals("test", result.getJSONObject(0).getString("name"));
            Assert.assertEquals("nobody", result.getJSONObject(0).getString("surname"));
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            Assert.fail("Could not read from database due to SQLException!");
        }
    }

    @Test
    protected void testGetWithOrderBy() {
        HashMap<String, Object> whereParams = new HashMap<>();
        try {
            JSONArray result = database.get(Database.Table.USERS, whereParams, "id DESC");
            Assert.assertEquals(2, result.getJSONObject(0).getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Could not read DB due to SQLException!");
        }
    }
}
