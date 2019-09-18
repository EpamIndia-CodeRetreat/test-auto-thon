package com.ert.stepdefs;

import com.ert.config.variables.GlobalVariables;
import com.ert.libs.pages.EventPageForUser;
import com.ert.libs.pages.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class HomeStepDefinitions {
    private HomePage homePage;

    private Logger logger = LogManager.getLogger("ApplicationLogs");
    private String sStatus;

    @Given("^The User logs in as a regular user$")
    public void loginAsRegularUser() {
        homePage = GlobalVariables.applicationContext.getBean(HomePage.class);
        homePage.loginAsRegularUser();
    }

    @Given("^The User logs in as admin user$")
    public void loginAsAdminUser() {
        homePage = GlobalVariables.applicationContext.getBean(HomePage.class);
        homePage.loginAsAdminUser();
    }

    @When("^The User selects the created event$")
    public void validateAndOpenCreatedEvent() {
        homePage = GlobalVariables.applicationContext.getBean(HomePage.class);
        sStatus = homePage.selectEvent(GlobalVariables.eventTitle);
        if (!sStatus.equals("")) {
            logger.log(Level.ERROR,
                    "StepDefinitions.validateAndOpenCreatedEvent(..) step failed : User failed to open the created event. "
                            + sStatus);
            Assert.fail("User failed to open the created event. " + sStatus);
        }
        logger.log(Level.INFO, "StepDefinitions.validateAndOpenCreatedEvent(..) step passed");
    }

    @Given("^The Events Page is Displayed$")
    public void verifyHomePageIsDisplayed() {
        homePage = GlobalVariables.applicationContext.getBean(HomePage.class);

        sStatus = homePage.verifyPageIsDisplayed();
        if (!sStatus.equals("")) {
            logger.log(Level.ERROR,
                    "StepDefinitions.verifyHomePageIsDisplayed(..) step failed : User logged in but  page not displayed. "
                            + sStatus);
            Assert.fail("User logged in but event page not displayed. " + sStatus);
        }
        logger.log(Level.INFO, "StepDefinitions.verifyHomePageIsDisplayed(..) step passed");
    }

    @When("^The User click on Profile photo at the top right corner$")
    public void clickonProfilePhoto() {
        homePage = GlobalVariables.applicationContext.getBean(HomePage.class);
        sStatus = homePage.clickProfilePhoto();
        if (!sStatus.equals("")) {
            logger.log(Level.ERROR,
                    "StepDefinitions.clickonProfilePhoto(..) step failed : Profile Photo is not clicked. "
                            + sStatus);
            Assert.fail("Profile Photo is not Clicked. " + sStatus);
        }
        logger.log(Level.INFO, "StepDefinitions.clickonProfilePhoto(..) step passed");

    }

    @Then("^Clicks the Admin Area$")
    public void clickonAdminArea() {
        homePage = GlobalVariables.applicationContext.getBean(HomePage.class);
        sStatus = homePage.clickAdminArea();
        if (!sStatus.equals("")) {
            logger.log(Level.ERROR,
                    "StepDefinitions.clickonAdminArea(..) step failed : Admin Area is not clicked. "
                            + sStatus);
            Assert.fail("Admin Area is not clicked. " + sStatus);
        }
        logger.log(Level.INFO, "StepDefinitions.clickonAdminArea(..) step passed");


    }

}
