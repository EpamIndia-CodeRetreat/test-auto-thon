package com.ert.config.driverConfigs;

import com.ert.config.aspects.LoggingAspect;
import com.ert.config.variables.GlobalVariables;
import com.ert.libs.webActions.ElementActions;
import com.ert.libs.webActions.MobileElementActions;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.net.URL;

@Configuration
@EnableAspectJAutoProxy
@PropertySource({"appInfo.properties"})
@ComponentScan({"com.ert.libs.pages", "com.ert.config.aspects", "com.ert.libs.webActions"})
public class DriverConfiguration {

    @Autowired
    private Environment environment;

    /***
     * Creating ChromeDriver instance
     * @return driver instance
     */

    @Bean("chrome")
    @Lazy
    WebDriver chromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(environment.getProperty("url"));
        GlobalVariables.applicationUrl = environment.getProperty("url");
        return driver;
    }

    /***
     * Creating FirefoxDriver instance
     * @return driver instance
     */
    @Bean("firefox")
    @Lazy
    WebDriver firefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        FirefoxDriver driver = new FirefoxDriver();
        driver.get(environment.getProperty("url"));
        return driver;
    }

    /***
     * Creating mobileDriver instance
     * @return driver instance
     */

    @Bean("mobile")
    @Lazy
    WebDriver mobileDriver() {

        DesiredCapabilities desCap = new DesiredCapabilities();
        desCap.setCapability("platformName", "Android");
        desCap.setCapability("platformVersion", "9");
        desCap.setCapability("deviceName", "emulator-5554");
        desCap.setCapability("automationName", "UiAutomator2");
        desCap.setCapability("appPackage", "com.epam.events.app.debug");
        desCap.setCapability("appActivity", "com.epam.events.app.MainActivity");
        try {
            WebDriver driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), desCap);
            Thread.sleep(10000);
            return driver;
        } catch (Exception exp) {
            exp.printStackTrace();
            return null;
        }


    }

    /***
     * Creating LoggingAspect instance
     * @return LoggingAspect instance
     */
    @Bean
    public LoggingAspect myAspect() {
        return new LoggingAspect();
    }

    /***
     * Creating ElementActions instance
     * @return ElementActions instance
     */
    @Bean
    public ElementActions myElementAction() {
        return new ElementActions();
    }

    /***
     * Creating MobileElementActions instance
     * @return ElementActions instance
     */
    @Bean
    public MobileElementActions myMobileElementAction() {
        return new MobileElementActions();
    }
}