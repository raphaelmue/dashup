package de.dashup.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.application.local.format.I18N;
import de.dashup.model.db.Database;
import de.dashup.test.SpringBootBase;
import de.dashup.test.utils.DriverUtil;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class LoginRegisterStepdefs {
    @Autowired
    private SpringBootBase springBootBase;

    //--------------- Login ---------------\\
    @And("^User registered with e-mail \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userRegisteredWithEMailAndPassword(String mail, String password) throws Throwable {
        Database database = GeneralStepDefinitions.getDatabase();
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", mail);
        Assertions.assertEquals(1, database.get(Database.Table.USERS, whereParams).length());
        Assertions.assertEquals(Hash.create(password, database.get(Database.Table.USERS, whereParams).getJSONObject(0).getString("salt")),
                database.get(Database.Table.USERS, whereParams).getJSONObject(0).getString("password"));
    }

    @When("^User enters \"([^\"]*)\" as e-mail$")
    public void userEntersAsEMail(String mail) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("text-field-login-email")).sendKeys(mail);
    }

    @And("^User enters \"([^\"]*)\" as password$")
    public void userEntersAsPassword(String password) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("text-field-login-password")).sendKeys(password);
    }

    @And("^User unchecks remember me option$")
    public void userUnchecksRememberMeOption() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        //uncheck remember me box
        driver.findElement(By.id("label-remember-me-checkbox")).click();
    }

    @And("^User presses login button$")
    public void userPressesLoginButton() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        //click login button
        driver.findElement(By.id("btn-login-submit")).click();
    }

    @Then("^User is logged in$")
    public void userIsLoggedIn() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement element = driver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @And("^User was navigated to central dashboard$")
    public void userWasNavigatedToCentralDashboard() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:"+ springBootBase.getPort()+"/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @And("^User can close dashup and is not longer logged in$")
    public void userCanCloseDashupAndIsNotLongerLoggedIn() {
        //we just need to check for the token cookie here, if this is not present the user is logged out when closing
        //the browser
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Cookie token = driver.manage().getCookieNamed("token");
        Assertions.assertNull(token);
    }

    @And("^User can close dashup and open it again without being logged out$")
    public void userCanCloseDashupAndOpenItAgainWithoutBeingLoggedOut() throws IOException, SQLException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Database database = GeneralStepDefinitions.getDatabase();
        //driver.quit() deletes all cookies so we need to carry our cookie to the next session manually
        //we assert that the cookie is present and not expired so we can be sure that in a normal setup without webdriver
        //this cookie will be there at next browser start up
        Cookie token = driver.manage().getCookieNamed("token");
        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.getExpiry().after(new Date()));
        //we assert that the cookie value is correct and the same as the token on the DB
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("user_id", "1");
        Assertions.assertEquals(database.get(Database.Table.USERS_TOKENS, whereParams).getJSONObject(0)
                .getString("token"), token.getValue());
        driver.quit();
        WebDriver newWindowDriver = DriverUtil.createDriverInstance();
        //needed to set the cookie to the correct URL; we need to switch to IP here due to some validation issues in firefox
        Cookie cookie = new Cookie(token.getName(), token.getValue(), "127.0.0.1", token.getPath(), token.getExpiry(), token.isSecure(), token.isHttpOnly());
        newWindowDriver.get("http://127.0.0.1:"+springBootBase.getPort()+"/thisIsA404Page.txt");
        newWindowDriver.manage().addCookie(cookie);
        //we expect that user is logged in directly
        newWindowDriver.get("http://127.0.0.1:"+springBootBase.getPort()+"/");
        Assertions.assertEquals("http://127.0.0.1:"+springBootBase.getPort()+"/", newWindowDriver.getCurrentUrl());
        WebElement element = newWindowDriver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @Then("^Login error message is displayed$")
    public void loginErrorMessageIsDisplayed() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("Your credentials are invalid!", element.getText());
        Assertions.assertEquals("http://localhost:"+springBootBase.getPort()+"/login/#", driver.getCurrentUrl());
    }

    //--------------- Navigation for registering ---------------\\
    @When("^User clicks on link to register$")
    public void userClicksOnLinkToRegister() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("href-registering")).click();
    }

    @Then("^User is navigated to registration page$")
    public void userIsNavigatedToRegistrationPage() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertEquals("http://localhost:"+springBootBase.getPort()+"/register", driver.getCurrentUrl());
        Assertions.assertNotNull(driver.findElement(By.id("text-field-register-email")));
    }

    //--------------- Registering ---------------\\
    @Given("^User is located on registration page$")
    public void userIsLocatedOnRegistrationPage() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.get("http://localhost:"+springBootBase.getPort()+"/register");
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:"+springBootBase.getPort()+"/register", driver.getCurrentUrl());
        Assertions.assertNotNull(driver.findElement(By.id("text-field-register-email")));
    }

    @And("^E-mail \"([^\"]*)\" already exists$")
    public void eMailAlreadyExists(String mail) throws SQLException {
        HashMap<String, Object> whereParams = new HashMap<>();
        whereParams.put("email", mail);
        Assertions.assertEquals(1, GeneralStepDefinitions.getDatabase().get(Database.Table.USERS, whereParams).length());
    }

    @When("^User enters \"([^\"]*)\" as username$")
    public void userEntersAsUsername(String userName) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("text-field-register-name")).sendKeys(userName);
    }

    @And("^User enters \"([^\"]*)\" as e-mail to registration formula$")
    public void userEntersAsEMailToRegistrationFormula(String mail) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("text-field-register-email")).sendKeys(mail);
    }

    @And("^User enters \"([^\"]*)\" as password to registration formula$")
    public void userEntersAsPasswordToRegistrationFormula(String password) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("text-field-register-password")).sendKeys(password);
    }

    @And("^User repeats \"([^\"]*)\" as password$")
    public void userRepeatsAsPassword(String password) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("text-field-register-repeat-password")).sendKeys(password);
    }

    @Then("^Registration error message is displayed stating that password is invalid$")
    public void registrationErrorMessageIsDisplayedStatingThatPasswordIsInvalid() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals(I18N.get("i18n.passwordLength"), element.getText());
    }

    @Then("^Registration error message is displayed stating that e-mail is invalid$")
    public void registrationErrorMessageIsDisplayedStatingThatEMailIsInvalid() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("The email is already registered.", element.getText());
        Assertions.assertEquals("http://localhost:"+springBootBase.getPort()+"/register/#", driver.getCurrentUrl());
    }

    @Then("^Registration error message is displayed stating that passwords are not matching$")
    public void registrationErrorMessageIsDisplayedStatingThatPasswordsAreNotMatching() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement element = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(element);
        Assertions.assertEquals("Your passwords are not matching.", element.getText());
        Assertions.assertEquals("http://localhost:"+springBootBase.getPort()+"/register", driver.getCurrentUrl());
    }

    @And("^User presses start button$")
    public void userPressesStartButton() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("submit-registration")).click();
    }
}
