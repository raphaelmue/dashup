package de.dashup.test.steps;

import cucumber.api.java.en.Given;
import de.dashup.model.db.Database;
import de.dashup.test.SpringBootBase;
import de.dashup.test.utils.DriverUtil;
import de.dashup.util.string.Hash;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Ignore
public class GeneralStepdefs extends SpringBootBase {

    private final static String LOGIN_URL = "http://localhost:9004/login";

    private static WebDriver driver;
    private static Database database;

    public static void setDriver(WebDriver driver) {
        GeneralStepdefs.driver = driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static Database getDatabase() {
        return database;
    }

    public void setupDBForTesting() throws SQLException {
        //change param of setHost to true if you need to test on local DB
        Database.setHost(false);
        Database.setDbName(Database.DatabaseName.TEST);
        database = Database.getInstance();
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
    }

    @Given("^User is registered for dashup$")
    public void userIsRegisteredForDashup() throws SQLException, IOException {
        this.setupDBForTesting();

    }

    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() {
        driver.get(LOGIN_URL);
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("login-form")));
    }
}
