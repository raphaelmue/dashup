package de.dashup.test.utils;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.dashup.model.db.Database;
import de.dashup.test.steps.GeneralStepdefs;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;
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

public class CucumberHooks {
    private WebDriver driver;

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
        if (name != null) {
            switch (name) {
                case "chrome":
                    driver = this.setUpChromeDriver();
                    break;
                case "firefox":
                    driver = this.setUpFirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("The argument " + name + " was read from properties, but is not expected!");
            }
        } else {
            driver = this.setUpChromeDriver();
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

    private WebDriver setUpChromeDriver() {
        final DesiredCapabilities desiredChromeCapabilities = DesiredCapabilities.chrome();
        final ChromeOptions chromeOptions = new ChromeOptions();

        if (DriverUtil.getPathToChromeBinary() != null) {
            chromeOptions.setBinary(DriverUtil.getPathToChromeBinary());
        }

        desiredChromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        System.setProperty("webdriver.chrome.driver", DriverUtil.getDriverPath("chrome"));
        WebDriver returningDriver = new ChromeDriver(desiredChromeCapabilities);
        returningDriver.manage().window().maximize();
        return returningDriver;
    }

    private WebDriver setUpFirefoxDriver() {
        final DesiredCapabilities desiredFirefoxCapabilities = DesiredCapabilities.firefox();
        final FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (DriverUtil.getPathToChromeBinary() != null) {
            firefoxOptions.setBinary(DriverUtil.getPathToFirefoxBinary());
        }

        desiredFirefoxCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        System.setProperty("webdriver.gecko.driver", DriverUtil.getDriverPath("firefox"));
        WebDriver returningDriver = new FirefoxDriver(desiredFirefoxCapabilities);
        returningDriver.manage().window().maximize();
        return returningDriver;
    }
}
