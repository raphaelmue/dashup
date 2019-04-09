package de.dashup.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.application.local.format.I18N;
import de.dashup.model.db.Database;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ChangeProfileStepDefinitions {

    @When("^User clicks on edit icon for account information$")
    public void userClicksOnAccountInformation() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("header-account-management")).click();
    }

    @And("^User changes username to \"([^\"]*)\"$")
    public void userChangesUsernameTo(String userName) throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Thread.sleep(1000);
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

    @And("^User clicks on submit icon for account information$")
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
}
