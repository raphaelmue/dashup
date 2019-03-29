package de.dashup.test.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.application.local.format.I18N;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ChangeLayoutStepdefs {
    //--------------- Navigation ---------------\\
    @When("^User clicks on settings menu$")
    public void userClicksOnSettingsMenu() {
        WebDriver driver = GeneralStepdefs.getDriver();
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
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.className("brand-logo")).click();
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
    public void userChangesThemeTo(String arg0) throws InterruptedException {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement header = driver.findElement(By.id("header-layout"));
        header.click();
        WebElement input = header.findElement(By.xpath("//input[@class=\"select-dropdown dropdown-trigger\"]"));
        Thread.sleep(1000);
        input.click();
        WebElement selectedTheme = input.findElement(By.xpath("//span[text()='" + arg0 + "']"));
        Thread.sleep(1000);
        selectedTheme.click();
        driver.findElement(By.id("save-layout-changes")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.successChangedLayout"), toast.getText());
        Assertions.assertEquals("http://localhost:9004/settings/#", driver.getCurrentUrl());
    }

    @Then("^Theme of dashup changes to \"([^\"]*)\"$")
    public void themeOfDashupChangesTo(String arg0) {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement headTag = driver.findElement(By.tagName("head"));
        List<WebElement> links = headTag.findElements(By.xpath("//link[@rel=\"stylesheet\"]"));
        boolean found = false;
        for (WebElement link : links) {
            if (link.getAttribute("href").contains("/styles/themes/theme." + arg0.replace(' ', '-').toLowerCase() + ".style.css")) {
                found = true;
            }
        }
        if (!found) {
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
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.successChangedLayout"), toast.getText());
        Assertions.assertEquals("http://localhost:9004/settings/#", driver.getCurrentUrl());
        driver.findElement(By.linkText("dashup")).click();
    }

    @Then("^Picture with URL \"([^\"]*)\" is displayed as background image$")
    public void pictureWithURLIsDisplayedAsBackgroundImage(String arg0) throws InterruptedException {
        WebDriver driver = GeneralStepdefs.getDriver();
        Thread.sleep(1000);
        WebElement headTag = driver.findElement(By.tagName("head"));
        WebElement styleTag = headTag.findElement(By.xpath(".//style"));
        Thread.sleep(1000);
        Assertions.assertTrue(styleTag.getAttribute("innerText").contains(arg0));
        driver.quit();
    }

    //--------------- Undo ---------------\\
    @Given("^User has made a change, key \"([^\"]*)\" was changed from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void userHasMadeAChangeKeyWasChangedFromTo(String arg0, String arg1, String arg2) throws Throwable {
        WebDriver driver = GeneralStepdefs.getDriver();
        try {
            driver.findElement(By.id("li-for-nav-item-settings")).click();
        } catch (ElementNotInteractableException e) {
            //fallback for firefox
            driver.findElement(By.className("sidenav-trigger")).click();
            driver.findElement(By.id("li-for-nav-item-settings-sidenav")).click();
        }
        switch (arg0) {
            case "theme":
                this.userChangesThemeTo(arg1);
                WebElement header = driver.findElement(By.id("header-layout"));
                header.click();
                WebElement input = header.findElement(By.xpath("//input[@class=\"select-dropdown dropdown-trigger\"]"));
                Thread.sleep(1000);
                input.click();
                WebElement selectedTheme = input.findElement(By.xpath("//span[text()='" + arg2 + "']"));
                Thread.sleep(1000);
                selectedTheme.click();
                break;
            case "font":
                //Not implemented yet
                break;
        }
    }

    @When("^User clicks on abandon icon$")
    public void userClicksOnAbandonIcon() throws InterruptedException {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("btn-undo-layout-changes")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.undoComplete"), toast.getText());
        Assertions.assertEquals("http://localhost:9004/settings/#", driver.getCurrentUrl());
    }

    @Then("^Key \"([^\"]*)\" will be restored to \"([^\"]*)\"$")
    public void keyWillBeRestoredTo(String arg0, String arg1) throws Throwable {
        WebDriver driver = GeneralStepdefs.getDriver();
        switch (arg0) {
            case "theme":
                Thread.sleep(1000);
                WebElement header = driver.findElement(By.id("header-layout"));
                header.click();
                WebElement input = header.findElement(By.xpath("//input[@class=\"select-dropdown dropdown-trigger\"]"));
                Thread.sleep(1000);
                input.click();
                WebElement selectedTheme = input.findElement(By.xpath("//span[text()='" + arg1 + "']"));
                Thread.sleep(1000);
                WebElement listElementForSelectedTheme = selectedTheme.findElement(By.xpath("./.."));
                Assertions.assertEquals("selected", listElementForSelectedTheme.getAttribute("class"));
                this.themeOfDashupChangesTo(arg1);
                break;
            case "font":
                //Not implemented yet
                break;
        }
        driver.quit();
    }
}
