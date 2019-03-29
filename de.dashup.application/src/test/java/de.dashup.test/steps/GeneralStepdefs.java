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

    public static void setDatabase(Database database) {
        GeneralStepdefs.database = database;
    }

    public static Database getDatabase() {
        return database;
    }

    @Given("^User is registered for dashup$")
    public void userIsRegisteredForDashup() throws SQLException, IOException {

    }

    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() {
        driver.get(LOGIN_URL);
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("login-form")));
    }
}
