package com.ert.drivers;

import com.ert.config.driverConfigs.DriverConfiguration;
import com.ert.config.variables.GlobalVariables;
import com.ert.libs.commonUtils.Utilities;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(features = "src/test/resources/features", glue = {
        "com/ert/stepdefs"}, tags = "@Smoke", strict = true, plugin = {
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "json:target/cucumber-report.json", "html:target/cucumber-reports/cucumber-pretty"})
public class TestRunner extends AbstractTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;
    private Logger logger;

    /**
     * Method invoked before the feature execution to initialize the Spring
     * Container and setup data in Feature files
     *
     * @throws Exception
     */
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        String timeStamp = Utilities.generateTimestamp("yyyyMMddHHmmss");
        System.setProperty("log_timestamp", timeStamp);
        System.setProperty("extent.reporter.spark.out", "Results/" + timeStamp + "/");
        System.setProperty("screenshot.dir", "Results/" + timeStamp + "/Screenshots");
        logger = LogManager.getLogger("ApplicationLogs");
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());

        GlobalVariables.applicationContext = new AnnotationConfigApplicationContext(DriverConfiguration.class);
       // Utilities.deleteAllExampleFromExecutedFeatureFile(testNGCucumberRunner);
       // Utilities.updateAllExecutingFeatureFile(this.testNGCucumberRunner);
    }

    /**
     * Runs every scenario in the feature
     *
     * @param pickleWrapper, featureWrapper
     * @throws Throwable
     */
    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
        GlobalVariables.currentFeature = featureWrapper.toString() + ".feature";
        GlobalVariables.currentPickleWrapper = pickleWrapper;
        testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
    }

    /**
     * Returns two dimensional array of PickleEventWrapper scenarios with their
     * associated CucumberFeatureWrapper feature.
     *
     * @return a two dimensional array of scenarios features.
     */
    @DataProvider
    public Object[][] scenarios() {
        if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return testNGCucumberRunner.provideScenarios();
    }

    /**
     * Method invoked after the feature execution to clean the data appended in
     * Feature files
     *
     * @throws Exception
     */
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
        logger.log(Level.INFO,
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        logger.log(Level.INFO,
                "-------------------------------------------------------------------------------------#END#--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        logger.log(Level.INFO,
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        //Utilities.deleteAllExampleFromExecutedFeatureFile(testNGCucumberRunner);

        WebDriver driver = GlobalVariables.applicationContext.getBean("chrome", WebDriver.class);
        if (!driver.toString().contains("(null)")) {
            driver.quit();
        }

//        driver = GlobalVariables.applicationContext.getBean("mobile", AndroidDriver.class);
//        if (!driver.toString().contains("(null)")) {
//            driver.quit();
//        }

    }
}