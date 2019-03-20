package de.dashup.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.model.db.Database;
import de.dashup.test.SpringBootBase;
import de.dashup.test.utils.DriverUtil;
import de.dashup.util.string.Hash;
import org.junit.Assert;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Ignore
public class GeneralStepdefs extends SpringBootBase {

    private final String LOGIN_URL = "http://localhost:9004/entry/login";

    private static WebDriver driver;


    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() throws Throwable {
        final DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        final ChromeOptions chromeOptions = new ChromeOptions();

        if(DriverUtil.getPathToChromeBinary()!=null) {
            chromeOptions.setBinary(DriverUtil.getPathToChromeBinary());
        }

        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        System.setProperty("webdriver.chrome.driver", DriverUtil.getDriverPath());
        driver = new ChromeDriver(desiredCapabilities);
        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
    }

    @When("^User submits username and password$")
    public void user_submits_username_and_password() throws Exception {
        driver.findElement(By.id("email")).sendKeys("nobody@test.com");
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

    @And("^Database is setup for testing$")
    public void databaseIsSetupForTesting() throws SQLException {
        //change param of setHost to true if you need to test on local DB
        Database.setHost(false);
        Database.setDbName(Database.DatabaseName.TEST);
        Database database = Database.getInstance();
        database.clearDatabase();
        String salt = "VQoX3kxwjX3gOOY1Jixk)Dc$0y$e4B!9";
        String hashedPassword = Hash.create("password", salt);

        Map<String, Object> values = new HashMap<>();
        values.put("email", "nobody@test.com");
        values.put("name", "Nobody");
        values.put("surname", "Test");
        values.put("password", hashedPassword);
        values.put("salt", salt);

        database.insert(Database.Table.USERS, values);

        values.clear();
        values.put("user_id","1");
        values.put("background_color","#ffffff");
        values.put("background_image","https://stmed.net/sites/default/files/sky-wallpapers-28043-2711012.jpg");
        values.put("heading_size","30");
        values.put("heading_color","#5569ff");
        values.put("font_heading","Arial Black");
        values.put("font_text","Verdana");

        database.insert(Database.Table.USERS_SETTINGS,values);

        values.clear();
        values.put("user_id","1");
        values.put("section_id","1");
        values.put("section_name","Test Section");
        values.put("section_order","0");

        database.insert(Database.Table.USER_SECTIONS,values);
    }
}
