package de.dashup.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.application.local.format.I18N;
import de.dashup.model.db.Database;
import de.dashup.test.utils.DriverUtil;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class LoginRegisterStepdefs {

    //--------------- Login ---------------\\
    @And("^User registered with e-mail \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userRegisteredWithEMailAndPassword(String arg0, String arg1) throws Throwable {
        Database database = GeneralStepdefs.getDatabase();
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", arg0);
        Assertions.assertEquals(1, database.get(Database.Table.USERS, whereParams).length());
        Assertions.assertEquals(Hash.create(arg1, database.get(Database.Table.USERS, whereParams).getJSONObject(0).getString("salt")),
                database.get(Database.Table.USERS, whereParams).getJSONObject(0).getString("password"));
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

    @And("^User unchecks remember me option$")
    public void userUnchecksRememberMeOption() {
        WebDriver driver = GeneralStepdefs.getDriver();
        //uncheck remember me box
        driver.findElement(By.id("label-remember-me-checkbox")).click();
    }

    @And("^User presses login button$")
    public void userPressesLoginButton() {
        WebDriver driver = GeneralStepdefs.getDriver();
        //click login button
        driver.findElement(By.id("btn-login-submit")).click();
    }

    @Then("^User is logged in$")
    public void userIsLoggedIn() {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @And("^User was navigated to central dashboard$")
    public void userWasNavigatedToCentralDashboard() {
        WebDriver driver = GeneralStepdefs.getDriver();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @And("^User can close dashup and open it again without being logged out$")
    public void userCanCloseDashupAndOpenItAgainWithoutBeingLoggedOut() throws IOException, SQLException {
        WebDriver driver = GeneralStepdefs.getDriver();
        Database database = GeneralStepdefs.getDatabase();
        //driver.quit() deletes all cookies so we need to carry our cookie to the next session manually
        //we assert that the cookie is present and not expired so we can be sure that in a normal setup without webdriver
        //this cookie will be there at next browser start up
        Cookie token = driver.manage().getCookieNamed("token");
        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.getExpiry().after(new Date()));
        //we assert that the cookie value is correct and the same as the token on the DB
        HashMap<String,Object> whereParams = new HashMap<>();
        whereParams.put("user_id","1");
        Assertions.assertEquals(database.get(Database.Table.USERS_TOKENS,whereParams).getJSONObject(0)
                                                            .getString("token"),token.getValue());
        driver.quit();
        WebDriver newWindowDriver = DriverUtil.setUpDriver();
        //needed to set the cookie to the correct URL
        Cookie cookie = new Cookie(token.getName(),token.getValue(),"127.0.0.1",token.getPath(),token.getExpiry(),token.isSecure(),token.isHttpOnly());
        newWindowDriver.get("http://127.0.0.1:9004/thisIsA404Page.txt");
        newWindowDriver.manage().addCookie(cookie);
        //we expect that user is logged in directly
        newWindowDriver.get("http://127.0.0.1:9004/");
        Assertions.assertEquals("http://127.0.0.1:9004/", newWindowDriver.getCurrentUrl());
        WebElement element = newWindowDriver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @Then("^Login error message is displayed$")
    public void loginErrorMessageIsDisplayed() {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("Your credentials are invalid!", element.getText());
        Assertions.assertEquals("http://localhost:9004/login/#", driver.getCurrentUrl());
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
        Assertions.assertEquals("http://localhost:9004/register", driver.getCurrentUrl());
        Assertions.assertNotNull(driver.findElement(By.id("text-field-register-email")));
    }

    //--------------- Registering ---------------\\
    @Given("^User is located on registration page$")
    public void userIsLocatedOnRegistrationPage() {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.get("http://localhost:9004/register");
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/register", driver.getCurrentUrl());
        Assertions.assertNotNull(driver.findElement(By.id("text-field-register-email")));
    }

    @And("^E-mail \"([^\"]*)\" already exists$")
    public void eMailAlreadyExists(String arg0) throws SQLException {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", arg0);
        Assertions.assertEquals(1, GeneralStepdefs.getDatabase().get(Database.Table.USERS, whereParams).length());
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

    @Then("^Registration error message is displayed stating that password is invalid$")
    public void registrationErrorMessageIsDisplayedStatingThatPasswordIsInvalid() {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals(I18N.get("i18n.passwordLength"), element.getText());
    }

    @Then("^Registration error message is displayed stating that e-mail is invalid$")
    public void registrationErrorMessageIsDisplayedStatingThatEMailIsInvalid() throws InterruptedException {
        WebDriver driver = GeneralStepdefs.getDriver();
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("The email is already registered.", element.getText());
        Assertions.assertEquals("http://localhost:9004/register/#", driver.getCurrentUrl());
    }

    @Then("^Registration error message is displayed stating that passwords are not matching$")
    public void registrationErrorMessageIsDisplayedStatingThatPasswordsAreNotMatching() {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("Your passwords are not matching.", element.getText());
        Assertions.assertEquals("http://localhost:9004/register", driver.getCurrentUrl());
    }

    @And("^User presses start button$")
    public void userPressesStartButton() {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("submit-registration")).click();
    }
}
