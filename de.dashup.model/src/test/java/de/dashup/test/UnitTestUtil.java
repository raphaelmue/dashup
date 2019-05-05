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

        /*
          The following test dashboard structure will be generated
          -section1-
          .panel1
          .panel2
          -section2-
          #empty#
        */

        testDataMap.clear();
        testDataMap.put("user_id", 1);
        testDataMap.put("section_name", "section1");
        testDataMap.put("predecessor_id", 0);
        database.insert(Database.Table.USER_SECTIONS, testDataMap);

        testDataMap.clear();
        testDataMap.put("user_id", 1);
        testDataMap.put("section_name", "section2");
        testDataMap.put("predecessor_id", 1);
        database.insert(Database.Table.USER_SECTIONS, testDataMap);

        Assertions.assertEquals(2, database.get(Database.Table.USER_SECTIONS, new HashMap<>()).length());

        testDataMap.clear();
        testDataMap.put("user_id", 1);
        testDataMap.put("name", "panel1");
        testDataMap.put("short_description", "test panel 1");
        testDataMap.put("description", "long description 1");
        testDataMap.put("publication_date", "2019-03-19");
        testDataMap.put("visibility", 0);
        testDataMap.put("number_of_ratings", 10);
        testDataMap.put("avg_of_ratings", 75);
        testDataMap.put("code_small", "<h1>Widget 1</h1>");
        testDataMap.put("code_medium", "<h1>Widget 1</h1>");
        testDataMap.put("code_large", "<h1>Widget 1</h1>");
        database.insert(Database.Table.PANELS, testDataMap);

        testDataMap.clear();
        testDataMap.put("user_id", 1);
        testDataMap.put("name", "panel2");
        testDataMap.put("short_description", "test panel 2");
        testDataMap.put("description", "long description 2");
        testDataMap.put("publication_date", "2019-03-19");
        testDataMap.put("visibility", 0);
        testDataMap.put("number_of_ratings", 1);
        testDataMap.put("avg_of_ratings", 50);
        testDataMap.put("code_small", "<h1>Widget 2</h1>");
        testDataMap.put("code_medium", "<h1>Widget 2</h1>");
        testDataMap.put("code_large", "<h1>Widget 2</h1>");
        database.insert(Database.Table.PANELS, testDataMap);

        Assertions.assertEquals(2, database.get(Database.Table.PANELS, new HashMap<>()).length());

        testDataMap.clear();
        testDataMap.put("section_id", 1);
        testDataMap.put("panel_id", 1);
        testDataMap.put("panel_predecessor", 0);
        testDataMap.put("size", "medium");
        database.insert(Database.Table.SECTIONS_PANELS, testDataMap);

        testDataMap.clear();
        testDataMap.put("section_id", 1);
        testDataMap.put("panel_id", 2);
        testDataMap.put("panel_predecessor", 1);
        testDataMap.put("size", "medium");
        database.insert(Database.Table.SECTIONS_PANELS, testDataMap);

        Assertions.assertEquals(2, database.get(Database.Table.SECTIONS_PANELS, new HashMap<>()).length());

    }


}
