package de.dashup.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.dashup.test.SpringBootBase;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


public class LayoutModeStepdefs {
    @Autowired
    private SpringBootBase springBootBase;

    @Given("^User is located on the central dashboard$")
    public void userIsLocatedOnTheCentralDashboard(){
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.get(BASE_URL);
        Assertions.assertEquals("dashup", driver.getTitle());
    }

    @When("^User clicks on edit icon$")
    public void userClicksOnEditIcon()
    {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("enter-layout-mode")).click();
    }

    @Then("^Central dashboard can now be edited$")
    public void centralDashboardCanNowBeEdited() {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        Assertions.assertEquals(BASE_URL + "/layoutMode/", driver.getCurrentUrl());
    }


    @Given("^User enabled edit mode$")
    public void userEnabledEditMode() {
        final String BASE_URL = "http://localhost:" + springBootBase.getPort();
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("enter-layout-mode")).click();
        Assertions.assertEquals(BASE_URL + "/layoutMode/", driver.getCurrentUrl());
    }


    @When("^User clicks on add section button$")
    public void userClicksOnAddSectionButton() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.id("add-section-button")).click();
    }

    @Then("^New section is added with a placeholder name$")
    public void newSectionIsAddedWithAPlaceholderName() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement sectionElement = driver.findElement(By.id("n0"));
        Assertions.assertNotNull(sectionElement);

        WebElement inputElement = sectionElement.findElement(By.tagName("input"));
        String inputValue = inputElement.getAttribute("value");
        Assertions.assertEquals("New Section",inputValue);
    }

    @When("^User clicks on the name of a section$")
    public void userClicksOnTheNameOfASection() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement sectionElement = driver.findElement(By.id("s1"));
        Assertions.assertNotNull(sectionElement);

        WebElement inputElement = sectionElement.findElement(By.tagName("input"));
        inputElement.click();
    }


    @And("^User enters \"([^\"]*)\"$")
    public void userEnters(String newSectionName){

        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement sectionElement = driver.findElement(By.id("s1"));
        Assertions.assertNotNull(sectionElement);

        WebElement inputElement = sectionElement.findElement(By.tagName("input"));
        inputElement.clear();
        inputElement.sendKeys(newSectionName);
    }


    @And("^User unfocuses section name$")
    public void userUnfocusesSectionName() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        driver.findElement(By.className("container")).click();
    }


    @Then("^Section will be renamed to \"([^\"]*)\"$")
    public void sectionWillBeRenamedTo(String expectedSectionName){
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement sectionElement = driver.findElement(By.id("s1"));
        Assertions.assertNotNull(sectionElement);

        WebElement inputElement = sectionElement.findElement(By.tagName("input"));
        String newSectionName = inputElement.getAttribute("value");
        Assertions.assertEquals(expectedSectionName,newSectionName);
    }

    @When("^User clicks on the remove icon of a section$")
    public void userClicksOnTheRemoveIconOfASection() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        WebElement sectionElement = driver.findElement(By.id("s1"));
        WebElement minusElement = sectionElement.findElement(By.className("section-minus"));
        minusElement.click();
    }

    @Then("^Section will be removed with all its containing panels$")
    public void sectionWillBeRemovedWithAllItsContainingPanels() {
        WebDriver driver = GeneralStepDefinitions.getDriver();
        List<WebElement> sectionElements = driver.findElements(By.className("wrapper"));

        WebElement secondSection = sectionElements.get(0);
        WebElement inputElement = secondSection.findElement(By.tagName("input"));
        String newSectionName = inputElement.getAttribute("value");
        Assertions.assertEquals("section2",newSectionName);
    }
}
