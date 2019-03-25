package de.dashup.test.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.model.db.Database;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.SQLException;
import java.util.HashMap;

public class LoginRegisterStepdefs {

    //--------------- Login ---------------\\
    @And("^User registered with e-mail \"([^\"]*)\"$")
    public void userRegisteredWithEMail(String arg0) throws SQLException {
        Database database = GeneralStepdefs.getDatabase();
        HashMap<String,Object> whereParams = new HashMap<>();
        whereParams.put("email",arg0);
        Assertions.assertEquals(1, database.get(Database.Table.USERS,whereParams).length());
    }

    @And("^User registered with \"([^\"]*)\"$")
    public void userRegisteredWith(String arg0)  {
    }

    @When("^User enters \"([^\"]*)\" as e-mail$")
    public void userEntersAsEMail(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("text-field-login-email")).sendKeys(arg0);
    }

    @And("^User enters \"([^\"]*)\" as password$")
    public void userEntersAsPassword(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("text-field-login-password")).sendKeys(arg0);
    }

    @And("^User presses login button$")
    public void userPressesLoginButton() {
        WebDriver driver = GeneralStepdefs.getDriver();
        //uncheck remember me box
        driver.findElement(By.id("label-remember-me-checkbox")).click();
        //click login button
        driver.findElement(By.id("btn-login-submit")).click();
    }

    @Then("^User is logged in$")
    public void userIsLoggedIn() {
        //nothing to check here; just for understanding in feature file
    }

    @And("^User was navigated to central dashboard$")
    public void userWasNavigatedToCentralDashboard() {
        WebDriver driver = GeneralStepdefs.getDriver();
        Assertions.assertEquals("dashup",driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/",driver.getCurrentUrl());
        driver.quit();
    }

    @Then("^Login error message is displayed$")
    public void loginErrorMessageIsDisplayed() {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("http://localhost:9004/login/#",driver.getCurrentUrl());
        driver.quit();
    }

    //--------------- Navigation for registering ---------------\\
    @When("^User clicks on link to register$")
    public void userClicksOnLinkToRegister() {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("href-registering")).click();
    }

    @Then("^User is navigated to registration page$")
    public void userIsNavigatedToRegistrationPage() {
        WebDriver driver = GeneralStepdefs.getDriver();
        Assertions.assertEquals("http://localhost:9004/register",driver.getCurrentUrl());
        Assertions.assertNotNull(driver.findElement(By.id("text-field-register-email")));
        driver.quit();
    }

    //--------------- Registering ---------------\\
    @Given("^User is located on registration page$")
    public void userIsLocatedOnRegistrationPage() {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.get("http://localhost:9004/register");
        Assertions.assertEquals("dashup",driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/register",driver.getCurrentUrl());
        Assertions.assertNotNull(driver.findElement(By.id("text-field-register-email")));
    }

    @And("^E-mail \"([^\"]*)\" already exists$")
    public void eMailAlreadyExists(String arg0) throws SQLException {
        HashMap<String,Object> whereParams = new HashMap<>();
        whereParams.put("email",arg0);
        Assertions.assertEquals(1,GeneralStepdefs.getDatabase().get(Database.Table.USERS,whereParams));
    }

    @When("^User enters \"([^\"]*)\" as username$")
    public void userEntersAsUsername(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("text-field-register-name")).sendKeys(arg0);
    }

    @And("^User enters \"([^\"]*)\" as e-mail to registration formula$")
    public void userEntersAsEMailToRegistrationFormula(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("text-field-register-email")).sendKeys(arg0);
    }

    @And("^User enters \"([^\"]*)\" as password to registration formula$")
    public void userEntersAsPasswordToRegistrationFormula(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("text-field-register-password")).sendKeys(arg0);
    }

    @And("^User repeats \"([^\"]*)\" as password$")
    public void userRepeatsAsPassword(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("text-field-register-repeat-password")).sendKeys(arg0);
    }

    @Then("^Registration error message is displayed stating that e-mail is invalid$")
    public void registrationErrorMessageIsDisplayedStatingThatEMailIsInvalid() {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("http://localhost:9004/register/#",driver.getCurrentUrl());
        driver.quit();
    }

    @Then("^Registration error message is displayed stating that passwords are not matching$")
    public void registrationErrorMessageIsDisplayedStatingThatPasswordsAreNotMatching() {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("http://localhost:9004/register/#",driver.getCurrentUrl());
        driver.quit();
    }

    @And("^User presses start button$")
    public void userPressesStartButton() {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("submit-registration")).click();
    }
}
