package de.dashup.test.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MarketplaceStepdefs {

    private final String LOGIN_URL = "http://localhost:8080/entry/login";

    private WebDriver driver;
    private String pathToChromeDriver= "C:\\Users\\D070546\\Documents\\chromedriver_win32\\chromedriver.exe";

    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() throws Throwable {
        System.setProperty("webdriver.chrome.driver",pathToChromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
    }

    @When("^User submits username and password$")
    public void user_submits_username_and_password() throws Exception {
        driver.findElement(By.id("email")).sendKeys("raphael@muesseler.de");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("submit_button")).click();
    }

    @Then("^User is logged in$")
    public void user_is_logged_in() throws Exception {
        Assert.assertEquals("dashup",driver.getTitle());
        driver.close();
    }

    @Given("^User is located on the main page$")
    public void user_is_located_on_the_main_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks on the marketplace button$")
    public void user_clicks_on_the_marketplace_button() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The marketplace shows up$")
    public void the_marketplace_shows_up() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User is located on the marketplace page$")
    public void user_is_located_on_the_marketplace_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks on the back button or navigates back over the navigation bar$")
    public void user_clicks_on_the_back_button_or_navigates_back_over_the_navigation_bar() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User will be directed back to the main page$")
    public void user_will_be_directed_back_to_the_main_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User is on the marketplace page$")
    public void user_is_on_the_marketplace_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User the types in a search term \"([^\"]*)\"$")
    public void user_the_types_in_a_search_term(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^A list with matching \"([^\"]*)\" shows up$")
    public void a_list_with_matching_shows_up(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks on filter icon$")
    public void user_clicks_on_filter_icon() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Filter menu will pop up$")
    public void filter_menu_will_pop_up() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User changes filter \"([^\"]*)\" to value \"([^\"]*)\"$")
    public void user_changes_filter_to_value(String arg1, String arg2) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Result set will be restricted to all panels that match \"([^\"]*)\" equals to \"([^\"]*)\"$")
    public void result_set_will_be_restricted_to_all_panels_that_match_equals_to(String arg1, String arg2) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Rating tab is opened of a panel's detailed view in the marketplace$")
    public void rating_tab_is_opened_of_a_panel_s_detailed_view_in_the_marketplace() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User writes \"([^\"]*)\"$")
    public void user_writes(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User chooses \"([^\"]*)\" of five stars$")
    public void user_chooses_of_five_stars(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks submit button$")
    public void user_clicks_submit_button() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The appropriate comment containing text \"([^\"]*)\" and rating (.*) will be displayed on the top of the comment section\\.$")
    public void the_appropriate_comment_containing_text_and_rating_will_be_displayed_on_the_top_of_the_comment_section(String arg1, String arg2) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks on a specific listed panel$")
    public void user_clicks_on_a_specific_listed_panel() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^A detailed view of the panel will open up, containing the tabs overview, comments and similar$")
    public void a_detailed_view_of_the_panel_will_open_up_containing_the_tabs_overview_comments_and_similar() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User is on the similar tab of a panel's detailed view in the marketplace$")
    public void user_is_on_the_similar_tab_of_a_panel_s_detailed_view_in_the_marketplace() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User scrolled down to the end of the page$")
    public void user_scrolled_down_to_the_end_of_the_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^More results are available$")
    public void more_results_are_available() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks on the show more button$")
    public void user_clicks_on_the_show_more_button() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^more results will be loaded and shown on the marketplace list$")
    public void more_results_will_be_loaded_and_shown_on_the_marketplace_list() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User clicks on the add panel to dashboard button$")
    public void user_clicks_on_the_add_panel_to_dashboard_button() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Panel with microservice will be added to dashups default section$")
    public void panel_with_microservice_will_be_added_to_dashups_default_section() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Menu to add panel to dashup is set to disabled in the marketplace$")
    public void menu_to_add_panel_to_dashup_is_set_to_disabled_in_the_marketplace() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^User is not logged into dashup$")
    public void user_is_not_logged_into_dashup() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User accesses marketplace$")
    public void user_accesses_marketplace() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User tries to add a panel to dashup$")
    public void user_tries_to_add_a_panel_to_dashup() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User will be directed to the login page$")
    public void user_will_be_directed_to_the_login_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User enters right credentials$")
    public void user_enters_right_credentials() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^User will be directed to the main page$")
    public void user_will_be_directed_to_the_main_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Panel is added to dashup$")
    public void panel_is_added_to_dashup() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
