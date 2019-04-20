package de.dashup.test;

import de.dashup.model.db.Database;
import de.dashup.model.service.DashupService;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class UnitTestUtil {
    static Database getDBInstance() throws SQLException {
        Database.setHost(false);
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            Database.setDbName(Database.DatabaseName.TEST);
        } else {
            Database.setDbName(Database.DatabaseName.JENKINS);
        }        return Database.getInstance();
    }

    static DashupService getServiceInstance() {
        return DashupService.getInstance();
    }

    static void setUpTestDataset(Database database) throws SQLException {
        database.clearDatabase();
        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);

        Map<String, Object> testDataMap = new HashMap<>();
        testDataMap.put("email", "nobody@test.com");
        testDataMap.put("username", "NobodyTest");
        testDataMap.put("name", "Nobody");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        database.insert(Database.Table.USERS, testDataMap);

        testDataMap.clear();
        testDataMap.put("email", "second@test.com");
        testDataMap.put("username", "SecondTest");
        testDataMap.put("name", "Second");
        testDataMap.put("surname", "Test");
        testDataMap.put("password", hashedPassword);
        testDataMap.put("salt", salt);
        database.insert(Database.Table.USERS, testDataMap);

        Assertions.assertEquals(2, database.get(Database.Table.USERS, new HashMap<>()).length());

        testDataMap.clear();
        testDataMap.put("token","t8KJgrLLuP51Tilw6SiXjqoyM0EFX6OxrbTG5giYbXRPoJk1dUOoUHRHbx7lTPiD");
        testDataMap.put("user_id","2");
        testDataMap.put("expiry_date","2099-01-01");
        database.insert(Database.Table.TOKENS, testDataMap);

        Assertions.assertEquals(1, database.get(Database.Table.TOKENS, new HashMap<>()).length());
    }
}