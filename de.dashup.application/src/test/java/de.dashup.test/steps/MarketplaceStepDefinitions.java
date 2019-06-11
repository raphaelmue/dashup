package de.dashup.test.steps;

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

import java.util.List;


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
    public void threeSectionsAndAreShown(String sectionName1, String sectionName2, String sectionName3) {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), '" + sectionName1 + "')]"));
        Assertions.assertNotNull(element);
        element = driver.findElement(By.xpath("//*[contains(text(), '" + sectionName2 + "')]"));
        Assertions.assertNotNull(element);
        element = driver.findElement(By.xpath("//*[contains(text(), '" + sectionName3 + "')]"));
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
            driver.findElement(By.id("li-for-nav-item-marketplace-sidenav")).click();
        }
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals(BASE_URL + "/marketplace/", driver.getCurrentUrl());
        WebElement element = driver.findElement(By.id("nav-item-marketplace"));
        Assertions.assertNotNull(element);
        WebElement parent = element.findElement(By.xpath("./.."));
        Assertions.assertEquals("active", parent.getAttribute("class"));
    }

    @When("^User clicks on a specific listed widget$")
    public void userClicksOnASpecificListedWidget() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.className("carousel-item")).click();
        Thread.sleep(1000);
    }

    @Then("^A detailed view of the widget will open up, containing the tabs overview, comments and similar$")
    public void aDetailedViewOfTheWidgetWillOpenUpContainingTheTabsOverviewCommentsAndSimilar() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[@href='#overview-tab']")));
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[@href='#ratings-tab']")));
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[@href='#similar-tab']")));
        Assertions.assertEquals("panel2", driver.findElement(By.id("h-widget-title")).getText());
        Assertions.assertEquals("NobodyTest", driver.findElement(By.id("div-publisher-name")).getText());
    }

    @And("^User is located on overview tab$")
    public void userIsLocatedOnOverviewTab() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement overviewTab = driver.findElement(By.xpath("//a[@href='#overview-tab']"));
        Assertions.assertEquals("active", overviewTab.getAttribute("class"));
    }

    @Given("^User opened a widget's detailed view in the marketplace menu$")
    public void userOpenedAWidgetSDetailedViewInTheMarketplaceMenu() throws InterruptedException {
        this.userIsLocatedOnMarketplaceMenu();
        this.userClicksOnASpecificListedWidget();

    }

    @When("^User clicks on similar tab$")
    public void userClicksOnSimilarTab() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.xpath("//a[@href='#similar-tab']")).click();
    }

    @And("^User clicks on a specific listed similar widget$")
    public void userClicksOnASpecificListedSimilarWidget() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.xpath("//a[@href='/marketplace/detailView/2']")).click();
    }

    @Then("^A detailed view of the selected widget will open up, containing the tabs overview, comments and similar$")
    public void aDetailedViewOfTheSelectedWidgetWillOpenUpContainingTheTabsOverviewCommentsAndSimilar() {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertEquals("dashup", driver.getTitle());
        Assertions.assertEquals(BASE_URL + "/marketplace/detailView/2", driver.getCurrentUrl());
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[@href='#overview-tab']")));
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[@href='#ratings-tab']")));
        Assertions.assertNotNull(driver.findElement(By.xpath("//a[@href='#similar-tab']")));
    }

    @When("^User clicks on the add button$")
    public void userClicksOnTheAddButton() throws InterruptedException {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("btn-add-panel-to-dashup")).click();
        Assertions.assertNotNull(driver.findElement(By.id("dialog-add-panel-to-dashup")));
        WebElement element = driver.findElement(By.id("btn-add-widget"));
        Thread.sleep(1000);
        element.click();
        Thread.sleep(2000);
    }

    @Then("^Widget was added to a new section section of central dashboard at the top, named after the widget$")
    public void widgetWasAddedToANewSectionSectionOfCentralDashboardAtTheTopNamedAfterTheWidget() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.linkText("dashup")).click();
        List<WebElement> list = driver.findElements(By.className("widget"));
        Assertions.assertEquals(2,list.size());
    }
}
