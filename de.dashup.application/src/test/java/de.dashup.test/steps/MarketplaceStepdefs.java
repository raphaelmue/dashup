package de.dashup.test.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;

public class MarketplaceStepdefs {
    @Given("^User is located on login page$")
    public void userIsLocatedOnLoginPage() throws Throwable {
        System.out.println("I am at the LogIn Page");
    }
}
