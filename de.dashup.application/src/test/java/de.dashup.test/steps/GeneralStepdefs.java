package de.dashup.test.steps;

import cucumber.api.java.en.Given;
import de.dashup.model.db.Database;
import de.dashup.test.SpringBootBase;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() {
        driver.get(LOGIN_URL);
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("login-form")));
    }
}
