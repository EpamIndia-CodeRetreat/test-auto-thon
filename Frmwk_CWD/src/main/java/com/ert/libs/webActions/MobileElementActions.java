package com.ert.libs.webActions;

import com.ert.config.variables.GlobalVariables;
import com.ert.libs.commonUtils.Utilities;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;

public class MobileElementActions {

    private static final By By = null;
	@Autowired
    @Qualifier("mobile")
    WebDriver driver;

    /**
     * To enter text using Locator with sendKeys
     *
     * @param locator
     * @param keysToSend
     * @return
     * @throws WebDriverException
     */
    public boolean enterTextUsingBy(By locator, String keysToSend) throws WebDriverException {
        scrollToViewWithByLocator(locator);
        this.driver.findElement(locator).sendKeys(keysToSend);

        return true;
    }

    /**
     * Capture the Text
     *
     * @param element
     * @return String
     */
    public String getText(WebElement element) {
        scrollToViewWithWebElement(element);
        return element.getText();
    }

    /**
     * Switch to Window by Index
     *
     * @param index
     * @return boolean
     * @throws WebDriverException
     */
    public boolean switchToWindowByIndex(int index) throws WebDriverException {
        int ctr = 0;
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (ctr == index) {
                this.driver.switchTo().window(handle);
                break;
            } else {
                ctr++;
            }
        }
        return true;
    }

    /**
     * Get the Locator by passing WebElement
     *
     * @param element
     * @return String
     */
    public String getLocator(WebElement element) {
        try {
            Object proxyOrigin = FieldUtils.readField(element, "h", true);
            Object locator = FieldUtils.readField(proxyOrigin, "locator", true);
            Object findBy = FieldUtils.readField(locator, "by", true);
            if (findBy != null) {
                return findBy.toString();
            }
        } catch (IllegalAccessException ignored) {
            Utilities.logExceptions(ignored);
        }
        return "[unknown]";
    }

    /**
     * Click Button using JavascriptExecutor
     *
     * @param element
     * @return boolean
     * @throws WebDriverException
     */
    public boolean clickWebElementUsingJse(WebElement element) throws WebDriverException {
        scrollToViewWithWebElement(element);
        JavascriptExecutor executor = (JavascriptExecutor) this.driver;
        executor.executeScript("arguments[0].click();", element);
        return true;
    }

    /**
     * Switch to Window by Index and return ParentWindow
     *
     * @param index
     * @return String
     * @throws WebDriverException
     */
    public String switchToWindowByIndexandReturnParentHandle(int index) throws WebDriverException {
        String parentHandle = this.driver.getWindowHandle();
        int ctr = 0;
        Set<String> windowHandles = this.driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (ctr == index) {
                this.driver.switchTo().window(handle);
                break;
            } else {
                ctr++;
            }
        }
        return parentHandle;
    }

    /**
     * To check if Popup Window is Closed
     *
     * @return boolean
     * @throws WebDriverException
     */
    public boolean checkIfPopupWindowClosed() throws WebDriverException {
        return (new WebDriverWait(this.driver, GlobalVariables.shortWait)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getWindowHandles().size() < 2;
            }
        });
    }

    /**
     * To Select a value from Dropdown using Visible Text
     *
     * @param element
     * @param visibleText
     * @return boolean
     * @throws WebDriverException
     */
    public boolean selectFromDropdownUsingVisibleText(WebElement element, String visibleText)
            throws WebDriverException {
        scrollToViewWithWebElement(element);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(visibleText);
        return true;
    }

    /**
     * To clear the Text using WebElement
     *
     * @param element
     * @return boolean
     * @throws WebDriverException
     */
    public boolean clearTextUsingWebElement(WebElement element) throws WebDriverException {
        scrollToViewWithWebElement(element);
        element.clear();
        return true;
    }

    /**
     * To clear and enter text using WebElement
     *
     * @param element
     * @param keysToSend
     * @return
     */
    public boolean clearAndEnterTextUsingWebElement(WebElement element, String keysToSend) {
        scrollToViewWithWebElement(element);
        element.clear();
        element.sendKeys(keysToSend);
        return true;
    }

    /**
     * To clear and enter text using WebElement for Editable div
     *
     * @param element
     * @param keysToSend
     * @return
     * @throws WebDriverException
     */
    public boolean clearAndEnterTextUsingWebElementForEditableDiv(WebElement element, String keysToSend) throws WebDriverException {
        scrollToViewWithWebElement(element);
        element.clear();
        Utilities.syncApp(GlobalVariables.shortSync / 2);
        JavascriptExecutor executor = (JavascriptExecutor) this.driver;
        executor.executeScript("arguments[0].textContent=arguments[1];", element, keysToSend);
        executor.executeScript("arguments[0].blur();", element, keysToSend);
        return true;
    }

    /**
     * To enter text using WebElement with sendKeys
     *
     * @param element
     * @param keysToSend
     * @return boolean
     * @throws WebDriverException
     */
    public boolean enterTextUsingWebElement(WebElement element, String keysToSend) throws WebDriverException {
        scrollToViewWithWebElement(element);
        element.sendKeys(keysToSend);
        return true;
    }

    /**
     * Waits for a specific amount of time and Enter Text using WebElement
     *
     * @param element
     * @param keysToSend
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitAndEnterTextUsingWebElement(WebElement element, String keysToSend, int noOfSeconds)
            throws Exception {

        if (!waitForVisibilityOfElement(element, noOfSeconds)) {
            return false;
        }
        if (!enterTextUsingWebElement(element, keysToSend)) {
            return false;
        }
        return true;
    }

    /**
     * Waits for a specific amount of time and Enter Text using Locator
     *
     * @param locator
     * @param keysToSend
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitAndEnterTextUsingBy(By locator, String keysToSend, int noOfSeconds) throws WebDriverException {

        if (!waitForVisibilityOfElementLocatedBy(locator, noOfSeconds)) {
            return false;
        }
        if (!enterTextUsingBy(locator, keysToSend)) {
            return false;
        }
        return true;
    }

    /**
     * Click Button using WebElement
     *
     * @param element
     * @return boolean
     * @throws WebDriverException
     */
    public boolean clickUsingWebElement(WebElement element) throws WebDriverException {
        //scrollToViewWithWebElement(element);
        element.click();
        return true;
    }

    /**
     * Click Button using Locator
     *
     * @param locator
     * @return boolean
     * @throws WebDriverException
     */
    public boolean clickUsingBy(By locator) throws WebDriverException {
        scrollToViewWithByLocator(locator);
        this.driver.findElement(locator).click();

        return true;
    }

    /**
     * Waits for a specific amount of time and Click Button using WebElement
     *
     * @param element
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitAndClickUsingWebElement(WebElement element, int noOfSeconds) throws WebDriverException {

        if (!waitForVisibilityOfElement(element, noOfSeconds)) {
            return false;
        }
        if (!clickUsingWebElement(element)) {
            return false;
        }

        return true;
    }

    /**
     * Waits for a specific amount of time and Click Button using Locator
     *
     * @param locator
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitAndClickUsingBy(By locator, int noOfSeconds) throws WebDriverException {

        if (!waitForVisibilityOfElementLocatedBy(locator, noOfSeconds)) {
            return false;
        }
        if (!clickUsingBy(locator)) {
            return false;
        }

        return true;
    }

    /**
     * Waits for a specific amount of time and Checks the Visibility of Element
     * using WebElement
     *
     * @param element
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitForVisibilityOfElement(WebElement element, int noOfSeconds) throws WebDriverException {

        new WebDriverWait(this.driver, noOfSeconds).until(ExpectedConditions.visibilityOf(element));
        return true;
    }

    
    /**
     * Waits for a specific amount of time and Checks the Visibility of Element
     * using WebElement
     *
     * @param element
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitForInvisibilityOfElement(WebElement element, int noOfSeconds) throws WebDriverException {

        new WebDriverWait(this.driver, noOfSeconds).until(ExpectedConditions.invisibilityOf(element));
        return true;
    }
    /**
     * Waits for a specific amount of time and Checks the Visibility of Element
     * using WebElement
     *
     * @param element
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitForInvisibilityOfElementLocated(String ele, int noOfSeconds) throws WebDriverException {

        new WebDriverWait(this.driver, noOfSeconds).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(ele)));
        return true;
    }
    /**
     * Waits for a specific amount of time and Checks the Visibility of Element
     * using Locator
     *
     * @param locator
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitForVisibilityOfElementLocatedBy(By locator, int noOfSeconds) throws WebDriverException {

        new WebDriverWait(this.driver, noOfSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return true;
    }

    /**
     * Waits for a specific amount of time and Checks the InVisibility of Element
     * using Locator
     *
     * @param locator
     * @param noOfSeconds
     * @return boolean
     * @throws WebDriverException
     */
    public boolean waitForInvisibilityOfElementBy(By locator, int noOfSeconds) throws WebDriverException {

        new WebDriverWait(this.driver, noOfSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
        return true;
    }

    /**
     * Waits for a specific amount of time and Switch to Window by Name
     *
     * @param frameName
     * @return boolean
     * @throws WebDriverException
     */
    public boolean switchToFrameByName(String frameName) throws WebDriverException {
        new WebDriverWait(this.driver, GlobalVariables.shortWait)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        return true;
    }

    /**
     * Scrolls to the Element using WebElement
     *
     * @param ele
     * @return boolean
     */
    public boolean scrollToViewWithWebElement(WebElement ele) {

        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript("arguments[0].scrollIntoView();", ele);
        return true;

    }

    /**
     * Scrolls to the Element using Locator
     *
     * @param locator
     * @return boolean
     */
    public boolean scrollToViewWithByLocator(By locator) {

        WebElement ele = this.driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript("arguments[0].scrollIntoView();", ele);
        return true;

    }

    /**
     * Switch to default Frame
     *
     * @return boolean
     */
    public boolean switchToDefaultContent() {
        this.driver.switchTo().defaultContent();
        return true;
    }

    /**
     * Mouse Hover Locator
     *
     * @param locator
     * @return
     */
    public boolean mouseHover(By locator) {
        Actions action = new Actions(driver);

        WebElement element = driver.findElement(locator);
        action.moveToElement(element).build().perform();
        return true;
    }

}
