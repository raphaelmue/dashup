package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UnitTestUtil {
    public static Database getDBInstance(boolean local, Database.DatabaseName name) throws SQLException{
        Database.setHost(local);
        Database.setDbName(name);
        return Database.getInstance();
    }

    public static void setUpTestDataset(Database database) throws SQLException {
        database.clearDatabase();
        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);

        Map<String, Object> testDataMap = new HashMap<>();
        testDataMap.put("email", "nobody@test.com");
        testDataMap.put("user_name", "NobodyTest");
        testDataMap.put("name", "Nobody");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        database.insert(Database.Table.USERS, testDataMap);

        testDataMap.clear();
        testDataMap.put("email", "second@test.com");
        testDataMap.put("user_name", "SecondTest");
        testDataMap.put("name", "Second");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        database.insert(Database.Table.USERS, testDataMap);

        Assertions.assertEquals(2, database.get(Database.Table.USERS,new HashMap<>()).length());
    }
}
