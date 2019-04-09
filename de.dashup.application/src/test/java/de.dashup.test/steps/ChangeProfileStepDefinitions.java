package de.dashup.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.application.local.format.I18N;
import de.dashup.model.db.Database;
import de.dashup.shared.DatabaseUser;
import de.dashup.shared.User;
import de.dashup.util.string.Hash;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ChangeProfileStepDefinitions {

    // --- Change user name --- \\

    @When("^User account information section$")
    public void userClicksOnAccountInformation() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("header-account-management")).click();
        Thread.sleep(1000);
    }

    @And("^User changes username to \"([^\"]*)\"$")
    public void userChangesUsernameTo(String userName) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("link-open-change-username")).click();
        WebElement userNameInput = driver.findElement(By.id("text-field-username"));
        userNameInput.clear();
        userNameInput.sendKeys(userName);
    }

    @And("^Username \"([^\"]*)\" is unique$")
    public void usernameIsUnique(String userName) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("user_name", userName);
        Assertions.assertEquals(0, GeneralStepDefinitions.getDatabase().get(Database.Table.USERS, whereParameters).length());
    }

    @And("^User submits new username$")
    public void userClicksOnSubmitIconForAccountInformation() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("btn-change-username")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.successChangedUserName"), toast.getText());
        Assertions.assertEquals("http://localhost:9004/settings/#", driver.getCurrentUrl());
    }

    @Then("^Username changes to \"([^\"]*)\"$")
    public void usernameChangesTo(String userName) throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("header-account-management")).click();
        Thread.sleep(1000);
        Assertions.assertEquals(userName, driver.findElement(By.id("label-username")).getText());
        driver.quit();
    }

    // --- Change email --- \\

    @And("^User changes e-mail to \"([^\"]*)\"$")
    public void userChangesEMailTo(String email) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("link-open-change-email")).click();
        WebElement userNameInput = driver.findElement(By.id("text-field-email"));
        userNameInput.clear();
        userNameInput.sendKeys(email);
    }

    @And("^E-mail \"([^\"]*)\" is unique$")
    public void eMailIsUnique(String email) throws SQLException {
        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", email);
        Assertions.assertEquals(0, GeneralStepDefinitions.getDatabase().get(Database.Table.USERS, whereParameters).length());
    }

    @And("^User submits new e-mail$")
    public void userSubmitsNewEMail() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("btn-change-email")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.successChangedEmail"), toast.getText());
        Assertions.assertEquals("http://localhost:9004/settings/#", driver.getCurrentUrl());
    }

    @Then("^E-mail changes to \"([^\"]*)\"$")
    public void eMailChangesTo(String email) throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("header-account-management")).click();
        Thread.sleep(1000);
        Assertions.assertEquals(email, driver.findElement(By.id("label-email")).getText());
        driver.quit();
    }

    // --- Change password --- \\

    @When("^User clicks on change password$")
    public void userClicksOnChangePassword() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("change-password-link")).click();
        Thread.sleep(1000);
    }

    @And("^User enters correct old password$")
    public void userEntersCorrectOldPassword() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement oldPasswordInput = driver.findElement(By.id("change-password-old"));
        oldPasswordInput.sendKeys("password");
    }

    @And("^User changes password to \"([^\"]*)\"$")
    public void userChangesPasswordTo(String password) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement newPasswordInput = driver.findElement(By.id("change-password-new"));
        newPasswordInput.sendKeys(password);
    }

    @And("^User repeats \"([^\"]*)\" in dialog as password$")
    public void userRepeatsInDialogAsPassword(String password) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement newPasswordRepeatInput = driver.findElement(By.id("change-password-new-repeat"));
        newPasswordRepeatInput.sendKeys(password);
    }

    @And("^New password is longer than eight characters$")
    public void newPasswordIsLongerThanEightCharacters() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement newPasswordInput = driver.findElement(By.id("change-password-new"));
        Assertions.assertTrue(newPasswordInput.getAttribute("value").length() >= 8);
    }

    @Then("^Password changes to \"([^\"]*)\"$")
    public void passwordChangesTo(String password) throws SQLException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("btn-submit-change-password")).click();

        Map<String, Object> whereParameters = new HashMap<>();
        whereParameters.put("email", "John.Doe@gmail.com");
        User user = (User) new User().fromDatabaseObject(GeneralStepDefinitions.getDatabase().getObject(Database.Table.USERS,
                DatabaseUser.class, whereParameters).get(0));
        Assertions.assertEquals(Hash.create(password, user.getSalt()), user.getPassword());

        driver.quit();
    }

    // --- Change first name --- \\

    @When("^User clicks personal information section$")
    public void userClicksPersonalInformationSection() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("header-personal-information")).click();
        Thread.sleep(1000);
    }

    @And("^User changes first name to \"([^\"]*)\"$")
    public void userChangesFirstNameTo(String firstName) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement firstNameInput = driver.findElement(By.id("text-field-personal-info-name"));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    @And("^User clicks on submit icon for personal information$")
    public void userClicksOnSubmitIconForPersonalInformation() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("btn-submit-personal-information")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.successChangedPersonalInformation"), toast.getText());
        Assertions.assertEquals("http://localhost:9004/settings/#", driver.getCurrentUrl());
    }

    @Then("^First name changes to \"([^\"]*)\"$")
    public void firstNameChangesTo(String firstName) throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("header-personal-information")).click();
        Thread.sleep(1000);
        WebElement firstNameInput = driver.findElement(By.id("text-field-personal-info-name"));
        Assertions.assertEquals(firstName, firstNameInput.getAttribute("value"));

        driver.quit();
    }

    @And("^User changes last name to \"([^\"]*)\"$")
    public void userChangesLastNameTo(String lastName)  {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement firstNameInput = driver.findElement(By.id("text-field-personal-info-surname"));
        firstNameInput.clear();
        firstNameInput.sendKeys(lastName);
    }

    @Then("^Last name changes to \"([^\"]*)\"$")
    public void lastNameChangesTo(String lastName) throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("header-personal-information")).click();
        Thread.sleep(1000);
        WebElement firstNameInput = driver.findElement(By.id("text-field-personal-info-surname"));
        Assertions.assertEquals(lastName, firstNameInput.getAttribute("value"));

        driver.quit();
    }
}
