package de.dashup.test.utils;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.dashup.model.db.Database;
import de.dashup.test.steps.GeneralStepDefinitions;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CucumberHooks {

    @Before(order = 1)
    public void doDatabaseSetup() throws SQLException, IOException {
        //change param of setHost to true if you need to test on local DB
        InputStream is = DriverUtil.class.getResourceAsStream("/testing.properties");
        Properties p = new Properties();
        p.load(is);
        Database.setHost(Boolean.valueOf(p.getProperty("project.testing.localhost")));
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            Database.setDbName(Database.DatabaseName.TEST);
        } else {
            Database.setDbName(Database.DatabaseName.JENKINS);
        }
        Database database = Database.getInstance();
        database.clearDatabase();
        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);

        Map<String, Object> testDataMap = new HashMap<>();
        testDataMap.put("email", "John.Doe@gmail.com");
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
        GeneralStepDefinitions.setDatabase(database);
    }

    @Before(order = 2)
    public void doDriverSetup() throws IOException {
        DriverUtil.createDriverInstance();
    }

    @After(order = 1)
    public void doTearDown() {
        GeneralStepDefinitions.getDriver().quit();
    }

    @After(order = 2)
    public void clearDatabase() throws SQLException {
        GeneralStepDefinitions.getDatabase().clearDatabase();
    }
}
