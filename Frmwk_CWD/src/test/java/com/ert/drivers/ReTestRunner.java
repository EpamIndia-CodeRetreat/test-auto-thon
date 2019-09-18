//package com.ert.drivers;
//
//import com.ert.config.driverConfigs.DriverConfiguration;
//import com.ert.config.variables.GlobalVariables;
//import com.ert.libs.commonUtils.Utilities;
//import com.vimalselvam.cucumber.listener.ExtentProperties;
//import com.vimalselvam.cucumber.listener.Reporter;
//import cucumber.api.CucumberOptions;
//import cucumber.api.testng.CucumberFeatureWrapper;
//import cucumber.api.testng.TestNGCucumberRunner;
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.WebDriver;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import java.io.File;
//
//@CucumberOptions(
//        features = "@rerun/failed_scenarios.txt",
//        glue = {"com/ert/step_definitions"},
//        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:",
//                "html:target/cucumber-reports/cucumber-pretty"}
//)
//public class ReTestRunner {
//    private TestNGCucumberRunner testNGCucumberRunner;
//    private WebDriver driver;
//    private Logger logger;
//
//
//    /**
//     * Method invoked before the feature execution to initialize the Spring Container and setup data in Feature files
//     *
//     * @throws Exception
//     */
//    @BeforeClass(alwaysRun = true)
//    public void setUpClass() throws Exception {
//        System.setProperty("log_timestamp", Utilities.generateTimestamp("yyyyMMddHHmmss"));
//        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
//        extentProperties.setReportPath("Results/" + System.getProperty("log_timestamp") + "/Results-" + System.getProperty("log_timestamp") + ".html");
//        logger = LogManager.getLogger("ApplicationLogs");
//        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//        GlobalVariables.applicationContext = new AnnotationConfigApplicationContext(DriverConfiguration.class);        
//        driver = GlobalVariables.applicationContext.getBean("chrome", WebDriver.class);
//    }
//
//    /**
//     * Method to trigger the execution of the feature files
//     *
//     * @param cucumberFeature
//     */
//    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
//    public void feature(CucumberFeatureWrapper cucumberFeature) {    	
//        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
//    }
//
//    /**
//     * Method to supply features to feature(..)
//     *
//     * @return
//     */
//    @DataProvider
//    public Object[][] features() {
//        return testNGCucumberRunner.provideFeatures();
//    }
//
//    /**
//     * Method invoked after the feature execution to clean the data appended in Feature files
//     *
//     * @throws Exception
//     */
//    @AfterClass(alwaysRun = true)
//    public void tearDownClass() throws Exception {
//        testNGCucumberRunner.finish();
//        driver.quit();
//        logger.log(Level.INFO, "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        logger.log(Level.INFO, "-------------------------------------------------------------------------------------#END#--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        logger.log(Level.INFO, "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
//        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
//        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
//        Reporter.setSystemInfo("Machine", "Windows 10 -" + " 64 Bit");
//
//        Reporter.getExtentReport().flush();
//
//        Utilities.deleteAllExampleFromExecutedFeatureFile(testNGCucumberRunner);
//    }
//}