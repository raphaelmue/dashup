package de.dashup.test.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.test.SpringBootBase;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;


public class MarketplaceStepDefinitions {
    @Autowired
    private SpringBootBase springBootBase;

    @When("^User clicks on marketplace menu$")
    public void userClicksOnMarketplaceMenu() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-item-marketplace")));
        try {
            driver.findElement(By.id("li-for-nav-item-marketplace")).click();
        } catch (ElementNotInteractableException e) {
            //fallback for firefox
            driver.findElement(By.className("sidenav-trigger")).click();
            driver.findElement(By.id("li-for-nav-item-marketplace-sidenav")).click();
        }
    }

    @Then("^Marketplace menu opens up$")
    public void marketplaceMenuOpensUp() {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals(BASE_URL + "/marketplace/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-marketplace"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @And("^Three sections \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" are shown$")
    public void threeSectionsAndAreShown(String sectionName1, String sectionName2, String sectionName3) throws Throwable {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement element= driver.findElement(By.xpath("//*[contains(text(), '"+sectionName1+"')]"));
        Assertions.assertNotNull(element);
        element= driver.findElement(By.xpath("//*[contains(text(), '"+sectionName2+"')]"));
        Assertions.assertNotNull(element);
        element= driver.findElement(By.xpath("//*[contains(text(), '"+sectionName3+"')]"));
        Assertions.assertNotNull(element);
    }

    @Given("^User is located on marketplace menu$")
    public void userIsLocatedOnMarketplaceMenu() {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        try {
            driver.findElement(By.id("li-for-nav-item-marketplace")).click();
        } catch (ElementNotInteractableException e) {
            //fallback for firefox
            driver.findElement(By.className("sidenav-trigger")).click();
            driver.findElement(By.id("li-for-nav-item-marketplace")).click();
        }
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals(BASE_URL + "/marketplace/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-marketplace"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }
}
