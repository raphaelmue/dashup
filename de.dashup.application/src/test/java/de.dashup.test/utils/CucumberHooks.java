package de.dashup.test.utils;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.dashup.model.db.Database;
import de.dashup.test.steps.GeneralStepdefs;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CucumberHooks {

    @Before(order = 1)
    public void doDatabaseSetup() throws SQLException {
        //change param of setHost to true if you need to test on local DB
        Database.setHost(false);
        Database.setDbName(Database.DatabaseName.TEST);
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
        GeneralStepdefs.setDatabase(database);
    }

    @Before(order = 2)
    public void doDriverSetup() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("../../../../my.properties");
        Properties p = new Properties();
        p.load(is);
        String name = p.getProperty("test.browser");
        WebDriver driver;
        if (name != null) {
            switch (name) {
                case "chrome":
                    driver = DriverUtil.setUpChromeDriver();
                    break;
                case "firefox":
                    driver = DriverUtil.setUpFirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("The argument " + name + " was read from properties, but is not expected!");
            }
        } else {
            driver = DriverUtil.setUpChromeDriver();
        }
        GeneralStepdefs.setDriver(driver);
    }

    @After(order = 1)
    public void doTearDown() {
        GeneralStepdefs.getDriver().quit();
    }

    @After(order = 2)
    public void clearDatabase() throws SQLException{
        GeneralStepdefs.getDatabase().clearDatabase();
    }
}
