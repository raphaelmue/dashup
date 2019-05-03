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
        }
        return Database.getInstance();
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

        Assertions.assertEquals(2, database.get(Database.Table.USERS, new HashMap<>()).length());

        testDataMap.clear();
        testDataMap.put("user_id", "1");
        testDataMap.put("theme", "blue-sky");
        testDataMap.put("language", "en");
        database.insert(Database.Table.USERS_SETTINGS, testDataMap);

        Assertions.assertEquals(1, database.get(Database.Table.USERS_SETTINGS, new HashMap<>()).length());

        testDataMap.clear();
        testDataMap.put("token", "t8KJgrLLuP51Tilw6SiXjqoyM0EFX6OxrbTG5giYbXRPoJk1dUOoUHRHbx7lTPiD");
        testDataMap.put("user_id", "2");
        testDataMap.put("expire_date", "2099-01-01");
        database.insert(Database.Table.USERS_TOKENS, testDataMap);

        Assertions.assertEquals(1, database.get(Database.Table.USERS_TOKENS, new HashMap<>()).length());
    }

    static void setUpTestDashup(Database database) throws SQLException {

        /*
          The following test dashboard structure will be generated
          -section1-
          .panel1
          .panel2
          -section2-
          #empty#
        */

        Map<String, Object> testData = new HashMap<>();
        testData.put("user_id", 1);
        testData.put("section_name", "section1");
        testData.put("predecessor_id", 0);

        database.insert(Database.Table.USER_SECTIONS, testData);

        testData.clear();
        testData.put("user_id", 1);
        testData.put("section_name", "section2");
        testData.put("predecessor_id", 1);
        database.insert(Database.Table.USER_SECTIONS, testData);

        testData.clear();
        testData.put("user_id",1);
        testData.put("name","panel1");
        testData.put("short_description","test panel 1");
        testData.put("description","long description 1");
        testData.put("publication_date","2019-03-19");
        testData.put("visibility",0);
        testData.put("number_of_ratings",2);
        testData.put("avg_of_ratings",50);
        testData.put("code_small","<h1>Test Panel 1</h1>");
        testData.put("code_medium","<h1>Test Panel 1</h1>");
        testData.put("code_large","<h1>Test Panel 1</h1>");
        database.insert(Database.Table.PANELS,testData);

        testData.clear();
        testData.put("user_id",1);
        testData.put("name","panel2");
        testData.put("short_description","test panel 2");
        testData.put("description","long description 2");
        testData.put("publication_date","2019-03-19");
        testData.put("visibility",0);
        testData.put("number_of_ratings",3);
        testData.put("avg_of_ratings",75);
        testData.put("code_small","<h1>Test Panel 2</h1>");
        testData.put("code_medium","<h1>Test Panel 2</h1>");
        testData.put("code_large","<h1>Test Panel 12</h1>");
        database.insert(Database.Table.PANELS,testData);

        testData.clear();
        testData.put("section_id",1);
        testData.put("panel_id",1);
        testData.put("panel_predecessor",0);
        testData.put("size","medium");
        database.insert(Database.Table.SECTIONS_PANELS,testData);

        testData.clear();
        testData.put("section_id",1);
        testData.put("panel_id",2);
        testData.put("panel_predecessor",1);
        testData.put("size","medium");
        database.insert(Database.Table.SECTIONS_PANELS,testData);
    }
}
