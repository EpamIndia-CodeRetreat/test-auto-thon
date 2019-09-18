package com.ert.libs.pages;

import com.ert.config.variables.GlobalVariables;
import com.ert.libs.commonUtils.Utilities;
import com.ert.libs.webActions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HomePage {

    String xpathForEventCard = "//span[text()='eventTitle']/../../../../..";
    @FindBy(xpath = "//a[contains(text(),'Upcoming events')]")
    private WebElement upcomingEvents;
    @FindBy(xpath = "//input[@placeholder='Search by Event Name']")
    private WebElement searchBox;
    @FindBy(xpath = "//ul[@class='evnt-header-tools navbar-nav']//a[@id='navbarDropdown']")
    private WebElement eventProfilePhoto;
    @FindBy(xpath = "//a[text()='Admin Area']")
    private WebElement adminArea;

    private boolean bStatus;
    private WebDriver driver;
    @Autowired
    private Environment environment;
    @Autowired
    private ElementActions elementActions;

    public HomePage(@Autowired @Qualifier("chrome") WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void loginAsRegularUser() {
        driver.get(environment.getProperty("regularUserUrl"));
    }

    public String selectEvent(String eventTitle) {
        try {

            bStatus = elementActions.clickWebElementUsingJse(upcomingEvents);
            if (!bStatus) {
                return "Upcoming events button not clicked";
            }

            bStatus = elementActions.waitAndEnterTextUsingWebElement(searchBox, eventTitle, 30);
            if (!bStatus) {
                return "Failed to enter event title in search box";
            }

            By locatorForEventCard = By.xpath(xpathForEventCard.replace("eventTitle", eventTitle));

            bStatus = elementActions.waitForVisibilityOfElementLocatedBy(locatorForEventCard, GlobalVariables.shortWait);
            if (!bStatus) {
                return "Failed to wait for visibility of the event card";
            }

            WebElement eventCard = driver.findElement(locatorForEventCard);

            bStatus = elementActions.clickWebElementUsingJse(eventCard);
            if (!bStatus) {
                return "Failed to click on the event card";
            }

        } catch (Exception e) {
            Utilities.logExceptions(e);
            return "Exception Thrown";
        }
        return "";
    }

    public String verifyPageIsDisplayed() {
        try {
            bStatus = elementActions.waitForVisibilityOfElement(eventProfilePhoto, GlobalVariables.shortWait);
            if (!bStatus) {
                return "Failed to scroll to the event card";
            }

        } catch (Exception e) {
            Utilities.logExceptions(e);
            return "Exception Thrown";
        }
        return "";
    }

    public void loginAsAdminUser() {
        driver.get(environment.getProperty("url"));
    }

    /**
     * Method to click on Profile Photo
     *
     * @return
     */
    public String clickProfilePhoto() {

        try {
            bStatus = elementActions.clickUsingWebElement(eventProfilePhoto);
            if (!bStatus) {
                return "Failed to Event Profile Photo button";
            }

        } catch (Exception e) {
            Utilities.logExceptions(e);
            return "Exception Thrown";
        }
        return "";
    }

    /**
     * Method to click on Admin Area link
     *
     * @return
     */
    public String clickAdminArea() {

        try {

            bStatus = elementActions.clickUsingWebElement(adminArea);
            if (!bStatus) {
                return "Failed to Admin Area button";
            }

        } catch (Exception e) {
            Utilities.logExceptions(e);
            return "Exception Thrown";
        }
        return "";
    }
}
