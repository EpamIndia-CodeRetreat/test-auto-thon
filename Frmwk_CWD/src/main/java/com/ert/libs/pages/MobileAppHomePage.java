package com.ert.libs.pages;

import com.ert.config.variables.GlobalVariables;
import com.ert.libs.commonUtils.Utilities;
import com.ert.libs.webActions.MobileElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MobileAppHomePage {

    @FindBy(xpath = "//*[@text='GET STARTED']")
    private WebElement getStarted;
    private String getStartedXpath = "//*[@text='GET STARTED']";
    private boolean bStatus;
    private WebDriver driver;
    @Autowired
    private Environment environment;
    @Autowired
    private MobileElementActions elementActions;

    public MobileAppHomePage(@Autowired @Qualifier("mobile") WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Method to verify Home page is displayed
     *
     * @return
     */

    public String verifyMobileAppHomePage() {
        try {
            bStatus = elementActions.waitForVisibilityOfElement(getStarted, GlobalVariables.shortWait);
            if (!bStatus) {
                return "Failed to scroll to the event card";
            }

        } catch (Exception e) {
            Utilities.logExceptions(e);
            return "Exception Thrown";
        }
        return "";
    }

    /**
     * Method to click on get started
     *
     * @return
     */
    public String clickGetStarted() {

        try {
            bStatus = elementActions.clickUsingWebElement(getStarted);
            if (!bStatus) {
                return "Failed to click on get started button";
            }

        } catch (Exception e) {
            Utilities.logExceptions(e);
            return "Exception Thrown";
        }
        return "";
    }

    /**
     * Method to verify navigated from get started
     *
     * @return
     */

    public String verifyPageIsNavigatedFromGetStarted() {
        try {
            bStatus = elementActions.waitForInvisibilityOfElementLocated(getStartedXpath, GlobalVariables.shortWait);
            if (!bStatus) {
                return "Failed to scroll to the event card";
            }

        } catch (Exception e) {
            Utilities.logExceptions(e);
            return "Exception Thrown";
        }
        return "";
    }

}
