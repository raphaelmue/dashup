package de.dashup.test.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GeneralStepdefs {

    private final String LOGIN_URL = "http://localhost:8080/entry/login";

    private static WebDriver driver;
    private String pathToChromeDriver= "C:\\Users\\D070546\\Documents\\chromedriver_win32\\chromedriver.exe";

    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() throws Throwable {
        System.setProperty("webdriver.chrome.driver",pathToChromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
    }

    @When("^User submits username and password$")
    public void user_submits_username_and_password() throws Exception {
        driver.findElement(By.id("email")).sendKeys("raphael@muesseler.de");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("submit_button")).click();
    }

    @Then("^User is logged in$")
    public void user_is_logged_in() throws Exception {
        Assert.assertEquals("dashup",driver.getTitle());
    }
    public static WebDriver getDriver(){
        return driver;
    }
}
