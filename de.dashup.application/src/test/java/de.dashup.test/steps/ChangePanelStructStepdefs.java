package de.dashup.test.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class ChangePanelStructStepdefs {

    private WebDriver driver;
    private String pathToChromeDriver= "C:\\Users\\D070546\\Documents\\chromedriver_win32\\chromedriver.exe";

    private Point sectionPos;

    @When("^The user clicks on the rearrangement button$")
    public void the_user_clicks_on_the_rearrangement_button() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.id("nav-item-layout-link"));
        if (element == null){
            Assert.fail();
        }
        element.click();
    }

    @Then("^Dashup's general panel structure is now editable as change structure mode is activated$")
    public void dashup_s_general_panel_structure_is_now_editable_as_change_structure_mode_is_activated() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        Assert.assertEquals("dashup - Welcome",driver.getTitle());
        driver.close();
    }

    @Given("^Change structure mode is activated$")
    public void change_structure_mode_is_activated() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        Assert.assertEquals("dashup - Welcome",driver.getTitle());
    }

    @When("^User clicks on add section button$")
    public void user_clicks_on_add_section_button() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("add_section_btn")).click();
    }

    @When("^User enters \"([^\"]*)\"$")
    public void user_enters(String arg1) throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        driver.findElement(By.id("new_section_id")).sendKeys(arg1);
    }

    @Then("^A new empty section will be rendered, being titled with \"([^\"]*)\"$")
    public void a_new_empty_section_will_be_rendered_being_titled_with(String arg1) throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        String newSection = driver.findElement(By.id("new_section_id")).getText();
        Assert.assertEquals(arg1,newSection);
        driver.close();
    }

    @When("^User clicks on the name of a section$")
    public void user_clicks_on_the_name_of_a_section() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element=driver.findElement(By.id("someTestSection"));
        element.click();
    }

    @And("^User enters \"([^\"]*)\" to this section$")
    public void userEntersToThisSection(String arg0) throws Throwable {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element=driver.findElement(By.id("someTestSection"));
        element.sendKeys(arg0);
    }

    @Then("^section will be renamed to \"([^\"]*)\"$")
    public void section_will_be_renamed_to(String arg1) throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element=driver.findElement(By.id("someTestSection"));
        Assert.assertEquals(element.getText(),arg1);
        driver.close();
    }

    @When("^User clicks on the remove icon of a section$")
    public void user_clicks_on_the_remove_icon_of_a_section() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element=driver.findElement(By.id("someTestSection_delete_btn"));
        element.click();
    }

    @Then("^Section will be removed$")
    public void section_will_be_removed() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement removedSection= driver.findElement(By.id("removedSection"));
        Assert.assertEquals(null,removedSection);
        driver.close();
    }

    @Then("^All panels will be moved to the section above$")
    public void all_panels_will_be_moved_to_the_section_above() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User drags a panel to another section$")
    public void user_drags_a_panel_to_another_section() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^All the other panel will reorder and the dragged panel is in the other section$")
    public void all_the_other_panel_will_reorder_and_the_dragged_panel_is_in_the_other_section() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User drags a section to another position$")
    public void user_drags_a_section_to_another_position() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.id("dragPoint"));
        sectionPos = element.getLocation();
        Actions builder = new Actions(driver);

        Action dragAndDrop = builder.clickAndHold(element)
                .moveByOffset(0,0)
                .release(element)
                .build();

        dragAndDrop.perform();
    }

    @Then("^Section will be reordered to the specified position with all its containing panels$")
    public void section_will_be_reordered_to_the_specified_position_with_all_its_containing_panels() throws Exception {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.id("dragPoint"));
        Assert.assertNotEquals(sectionPos,element.getLocation());
        driver.close();
    }

    @When("^User right clicks on a panel$")
    public void user_right_clicks_on_a_panel() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Size menu will pop up containing the three options small, medium and large$")
    public void size_menu_will_pop_up_containing_the_three_options_small_medium_and_large() throws Exception {
        // Write code here that turns the phrase above intgio concrete actions
        throw new PendingException();
    }

    @When("^User selects menu entry \"([^\"]*)\"$")
    public void user_selects_menu_entry(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^panel will be resized to \"([^\"]*)\"$")
    public void panel_will_be_resized_to(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User has made changes$")
    public void user_has_made_changes() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^All changes will come into effect$")
    public void all_changes_will_come_into_effect() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User has made a change$")
    public void user_has_made_a_change() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The latest change will be undone$")
    public void the_latest_change_will_be_undone() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User is on the layout page$")
    public void userIsOnTheLayoutPage() throws Throwable {
        WebDriver driver = GeneralStepdefs.getDriver();
        WebElement element = driver.findElement(By.id("nav-item-layout-link"));
        if (element == null){
            Assert.fail();
        }
        element.click();
        Assert.assertEquals("dashup - Welcome",driver.getTitle());
    }
}
