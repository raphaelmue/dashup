package de.dashup.test.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.test.SpringBootBase;
import org.junit.Assert;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class GeneralStepdefs extends SpringBootBase {

    private final String LOGIN_URL = "http://localhost:9004/entry/login";

    private static WebDriver driver;
    //public static String chromeDriverPath = "/usr/bin/chromedriver";
    public static String chromeDriverPath = "C:/Users/D070546/Documents/chromedriver_win32/chromedriver.exe";


    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() throws Throwable {
        final DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        final ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.setBinary("/usr/bin/chromium-browser");
        //chromeOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        System.setProperty("webdriver.chrome.driver",chromeDriverPath);
        driver = new ChromeDriver(desiredCapabilities);
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
