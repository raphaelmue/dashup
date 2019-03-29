package de.dashup.test.utils;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import de.dashup.test.steps.GeneralStepdefs;

public class CucumberHooks {
    @Before
    public void doDriverSetup(){

    }

    @After
    public void doTearDown(){
        GeneralStepdefs.getDriver().quit();
    }
}
