package de.dashup.test.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ChangeLayoutStepdefs {
    //--------------- Navigation ---------------\\
    @When("^User clicks on settings menu$")
    public void userClicksOnSettingsMenu() {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("nav-item-settings")).click();
    }

    @Then("^Settings menu opens up$")
    public void settingsMenuOpensUp() {
        WebDriver driver = GeneralStepdefs.getDriver();
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
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("nav-item-settings")).click();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/settings/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-settings"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @When("^User clicks on dashboard icon$")
    public void userClicksOnDashboardIcon() {
        //TODO
    }

    @When("^User navigates back over the navigation bar$")
    public void userNavigatesBackOverTheNavigationBar() {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.linkText("dashup")).click();
    }

    @Then("^User will be directed back to central dashboard$")
    public void userWillBeDirectedBackToCentralDashboard() {
        WebDriver driver = GeneralStepdefs.getDriver();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals("http://localhost:9004/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-dashboard"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
        driver.quit();
    }

    //--------------- Themes ---------------\\
    @When("^User changes theme to \"([^\"]*)\"$")
    public void userChangesThemeTo(String arg0) throws Throwable {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement header = driver.findElement(By.id("header-layout"));
        header.click();
        WebElement input = header.findElement(By.xpath("//input[@class=\"select-dropdown dropdown-trigger\"]"));
        Thread.sleep(1000);
        input.click();
        WebElement selectedTheme = input.findElement(By.xpath("//span[text()='"+arg0+"']"));
        Thread.sleep(1000);
        selectedTheme.click();
        driver.findElement(By.id("save-layout-changes")).click();
    }

    @Then("^Theme of dashup changes to \"([^\"]*)\"$")
    public void themeOfDashupChangesTo(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement headTag = driver.findElement(By.tagName("head"));
        List<WebElement> links = headTag.findElements(By.xpath("//link[@rel=\"stylesheet\"]"));
        boolean found = false;
        for (WebElement link: links) {
            if(link.getAttribute("href").contains("/styles/themes/theme."+arg0.replace(' ','-').toLowerCase()+".style.css")){
                found=true;
            }
        }
        if (!found){
            Assertions.fail("Current stylesheet does not match selected theme!");
        }
        driver.quit();
    }

    //--------------- Background ---------------\\
    @When("^User provides the valid image URL \"([^\"]*)\"$")
    public void userProvidesTheValidImageURL(String arg0) throws InterruptedException {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement header = driver.findElement(By.id("header-layout"));
        header.click();
        Thread.sleep(1000);
        WebElement input = driver.findElement(By.id("text-field-background-image"));
        input.sendKeys(arg0);
        driver.findElement(By.id("save-layout-changes")).click();
        driver.findElement(By.linkText("dashup")).click();
    }

    @Then("^Picture with URL \"([^\"]*)\" is displayed as background image$")
    public void pictureWithURLIsDisplayedAsBackgroundImage(String arg0) throws Throwable {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement styleTag = driver.findElement(By.tagName("head")).findElement(By.xpath("//style"));
        Thread.sleep(1000);
        Assertions.assertTrue(styleTag.getAttribute("innerText").contains(arg0));
        driver.quit();
    }
}
