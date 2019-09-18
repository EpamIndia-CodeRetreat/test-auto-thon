package com.ert.stepdefs;

import com.ert.config.variables.GlobalVariables;
import com.ert.libs.pages.EventPageForUser;
import com.ert.libs.pages.HomePage;
import com.ert.libs.pages.MobileAppHomePage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class MobileAppHomeStepDefinitions {
	private MobileAppHomePage homePage;

	private Logger logger = LogManager.getLogger("ApplicationLogs");
	private String sStatus;

	@Given("^The Events app home page is displayed in mobile app$")
	public void moileAppEventsHomePage() {
		homePage = GlobalVariables.applicationContext.getBean(MobileAppHomePage.class);
		sStatus = homePage.verifyMobileAppHomePage();
		if (!sStatus.equals("")) {
			logger.log(Level.ERROR,
					"StepDefinitions.moileAppEventsHomePage(..) step failed : User failed to open the Events home page. "
							+ sStatus);
			Assert.fail("User failed to open the Events home page. " + sStatus);
		}
		logger.log(Level.INFO, "StepDefinitions.moileAppEventsHomePage(..) step passed");
	}

	@When("^The User click on Get started button$")
	public void clickOnGetStartedInApp() {
		homePage = GlobalVariables.applicationContext.getBean(MobileAppHomePage.class);
		sStatus = homePage.clickGetStarted();
		if (!sStatus.equals("")) {
			logger.log(Level.ERROR,
					"StepDefinitions.clickOnGetStartedInApp(..) step failed : User failed to click on Get started. "
							+ sStatus);
			Assert.fail("User failed to click on Get started. " + sStatus);
		}
		logger.log(Level.INFO, "StepDefinitions.clickOnGetStartedInApp(..) step passed");
	}

	@Then("^The User navigated from home page to other page$")
	public void navigatedFromHomePage() {
		homePage = GlobalVariables.applicationContext.getBean(MobileAppHomePage.class);
		sStatus = homePage.verifyPageIsNavigatedFromGetStarted();
		if (!sStatus.equals("")) {
			logger.log(Level.ERROR,
					"StepDefinitions.verifyPageIsNavigatedFromGetStarted(..) step failed : not naviagted from home page " + sStatus);
			Assert.fail("Not naviagted from home page. " + sStatus);
		}
		logger.log(Level.INFO, "StepDefinitions.verifyPageIsNavigatedFromGetStarted(..) step passed");

	}

}
