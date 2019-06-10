package de.dashup.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.application.local.format.I18N;
import de.dashup.test.SpringBootBase;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChangeLayoutStepDefinitions {

    @Autowired
    private SpringBootBase springBootBase;

    //--------------- Themes ---------------\\
    @When("^User changes theme to \"([^\"]*)\"$")
    public void userChangesThemeTo(String newTheme) throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement header = driver.findElement(By.id("header-layout"));
        header.click();
        WebElement input = header.findElement(By.xpath("//input[@class=\"select-dropdown dropdown-trigger\"]"));

        Thread.sleep(1000);
        input.click();
        WebElement selectedTheme = input.findElement(By.xpath("//span[text()='" + newTheme + "']"));
        Thread.sleep(1000);
        selectedTheme.click();
    }

    @And("^User clicks on submit icon for layout settings$")
    public void userClicksOnSubmitIconForLayoutSettings() throws InterruptedException {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("save-layout-changes")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.successChangedLayout"), toast.getText());
        Assertions.assertEquals(BASE_URL + "/settings/#", driver.getCurrentUrl());
    }

    @Then("^Theme of dashup changes to \"([^\"]*)\"$")
    public void themeOfDashupChangesTo(String newTheme) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement headTag = driver.findElement(By.tagName("head"));
        List<WebElement> links = headTag.findElements(By.xpath("//link[@rel=\"stylesheet\"]"));
        boolean found = false;
        for (WebElement link : links) {
            if (link.getAttribute("href").contains("/styles/themes/theme." + newTheme.replace(' ', '-').toLowerCase() + ".style.css")) {
                found = true;
            }
        }
        if (!found) {
            Assertions.fail("Current stylesheet does not match selected theme!");
        }
    }

    //--------------- Background ---------------\\
    @When("^User provides the valid image URL \"([^\"]*)\"$")
    public void userProvidesTheValidImageURL(String url) throws InterruptedException {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement header = driver.findElement(By.id("header-layout"));
        header.click();
        Thread.sleep(1000);
        WebElement input = driver.findElement(By.id("text-field-background-image"));
        input.sendKeys(url);
        driver.findElement(By.id("save-layout-changes")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.successChangedLayout"), toast.getText());
        Assertions.assertEquals(BASE_URL + "/settings/#", driver.getCurrentUrl());
        driver.findElement(By.linkText("dashup")).click();
    }

    @Then("^Picture with URL \"([^\"]*)\" is displayed as background image$")
    public void pictureWithURLIsDisplayedAsBackgroundImage(String url) throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Thread.sleep(1000);
        WebElement headTag = driver.findElement(By.tagName("head"));
        WebElement styleTag = headTag.findElement(By.xpath(".//style"));
        Thread.sleep(1000);
        Assertions.assertTrue(styleTag.getAttribute("innerText").contains(url));
    }

    //--------------- Undo ---------------\\
    @Given("^User has made a change, key \"([^\"]*)\" was changed from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void userHasMadeAChangeKeyWasChangedFromTo(String setting, String oldSetting, String newSetting) throws Throwable {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        try {
            driver.findElement(By.id("li-for-nav-item-settings")).click();
        } catch (ElementNotInteractableException e) {
            //fallback for firefox
            driver.findElement(By.className("sidenav-trigger")).click();
            driver.findElement(By.id("li-for-nav-item-settings-sidenav")).click();
        }
        switch (setting) {
            case "theme":
                this.userChangesThemeTo(oldSetting);
                this.userClicksOnSubmitIconForLayoutSettings();
                WebElement header = driver.findElement(By.id("header-layout"));
                header.click();
                Thread.sleep(1000);
                WebElement input = header.findElement(By.xpath("//input[@class=\"select-dropdown dropdown-trigger\"]"));
                Thread.sleep(1000);
                input.click();
                WebElement selectedTheme = input.findElement(By.xpath("//span[text()='" + newSetting + "']"));
                Thread.sleep(1000);
                selectedTheme.click();
                break;
            case "background":
                WebElement headerBackground = driver.findElement(By.id("header-layout"));
                headerBackground.click();
                Thread.sleep(1000);
                WebElement backgroundInput = driver.findElement(By.id("text-field-background-image"));
                backgroundInput.sendKeys(setting);
                break;
            default:
                throw new IllegalArgumentException("The argument " + setting + " is not expected in this test!");
        }
    }

    @When("^User clicks on abandon icon$")
    public void userClicksOnAbandonIcon() throws InterruptedException {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("btn-undo-layout-changes")).click();
        Thread.sleep(1000);
        WebElement toast = driver.findElement(By.className("toast"));
        Assertions.assertNotNull(toast);
        Assertions.assertEquals(I18N.get("i18n.undoComplete"), toast.getText());
        Assertions.assertEquals(BASE_URL + "/settings/#", driver.getCurrentUrl());
    }

    @Then("^Key \"([^\"]*)\" will be restored to \"([^\"]*)\"$")
    public void keyWillBeRestoredTo(String setting, String oldSetting) throws Throwable {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        switch (setting) {
            case "theme":
                Thread.sleep(1000);
                WebElement header = driver.findElement(By.id("header-layout"));
                header.click();
                WebElement input = header.findElement(By.xpath("//input[@class=\"select-dropdown dropdown-trigger\"]"));
                Thread.sleep(1000);
                input.click();
                WebElement selectedTheme = input.findElement(By.xpath("//span[text()='" + oldSetting + "']"));
                Thread.sleep(1000);
                WebElement listElementForSelectedTheme = selectedTheme.findElement(By.xpath("./.."));
                Assertions.assertEquals("selected", listElementForSelectedTheme.getAttribute("class"));
                this.themeOfDashupChangesTo(oldSetting);
                break;
            case "background":
                driver.findElement(By.linkText("dashup")).click();
                Thread.sleep(1000);
                WebElement headTag = driver.findElement(By.tagName("head"));
                WebElement styleTag = headTag.findElement(By.xpath(".//style"));
                Thread.sleep(1000);
                Assertions.assertTrue(styleTag.getAttribute("innerText").contains("url('')"));
                break;
            default:
                throw new IllegalArgumentException("The argument " + setting + " is not expected in this test!");
        }
    }
}
