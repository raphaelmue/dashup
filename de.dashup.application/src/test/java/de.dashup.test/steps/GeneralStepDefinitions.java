package de.dashup.test.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import de.dashup.model.db.Database;
import de.dashup.test.SpringBootBase;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


@Ignore
public class GeneralStepDefinitions extends SpringBootBase {
    private final static String LOGIN_URL = "http://localhost:9004/login";

    private static WebDriver driver;
    private static Database database;

    public static void setDriver(WebDriver driver) {
        GeneralStepDefinitions.driver = driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDatabase(Database database) {
        GeneralStepDefinitions.database = database;
    }

    public static Database getDatabase() {
        return database;
    }

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() {
        driver.get(LOGIN_URL);
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("login-form")));
    }

    @When("^User submits e-mail and password$")
    public void userSubmitsEMailAndPassword() {
        driver.findElement(By.id("text-field-login-email")).sendKeys("John.Doe@gmail.com");
        driver.findElement(By.id("text-field-login-password")).sendKeys("password");
        //uncheck remember me box
        driver.findElement(By.id("label-remember-me-checkbox")).click();
        //click login button
        driver.findElement(By.id("btn-login-submit")).click();
    }

    @Given("^User is located on central dashboard$")
    public void userIsLocatedOnCentralDashboard() {
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }
}
