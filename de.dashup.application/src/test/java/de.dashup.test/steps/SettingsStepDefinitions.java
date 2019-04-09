package de.dashup.test.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SettingsStepDefinitions {

    //--------------- Navigation ---------------\\
    @When("^User clicks on settings menu$")
    public void userClicksOnSettingsMenu() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-item-settings")));
        try {
            driver.findElement(By.id("li-for-nav-item-settings")).click();
        } catch (ElementNotInteractableException e) {
            //fallback for firefox
            driver.findElement(By.className("sidenav-trigger")).click();
            driver.findElement(By.id("li-for-nav-item-settings-sidenav")).click();
        }

    }

    @Then("^Settings menu opens up$")
    public void settingsMenuOpensUp() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/settings/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-settings"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
        driver.quit();
    }

    @Given("^User is located on settings menu$")
    public void userIsLocatedOnSettingsMenu() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        try {
            driver.findElement(By.id("li-for-nav-item-settings")).click();
        } catch (ElementNotInteractableException e) {
            //fallback for firefox
            driver.findElement(By.className("sidenav-trigger")).click();
            driver.findElement(By.id("li-for-nav-item-settings-sidenav")).click();
        }
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/settings/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-settings"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @When("^User clicks on dashboard icon$")
    public void userClicksOnDashboardIcon() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.className("brand-logo")).click();
    }

    @When("^User navigates back over the navigation bar$")
    public void userNavigatesBackOverTheNavigationBar() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.linkText("dashup")).click();
    }

    @Then("^User will be directed back to central dashboard$")
    public void userWillBeDirectedBackToCentralDashboard() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
        driver.quit();
    }

}
