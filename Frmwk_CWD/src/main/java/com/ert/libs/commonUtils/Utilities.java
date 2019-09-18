package com.ert.libs.commonUtils;

import com.ert.config.variables.GlobalVariables;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utilities {

    /**
     * Method to generate timestamp in specific format
     *
     * @param format
     * @return
     */
    public static String generateTimestamp(String format) {
        String timeStamp = new SimpleDateFormat(format).format(new Timestamp(System.currentTimeMillis()));
        return timeStamp;
    }

    /**
     * Method to generate random number based on number of digits given
     *
     * @param noOfDigits
     * @return
     */
    public static String generateRandomNumber(int noOfDigits) {

        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < noOfDigits; i++) {
            int num = (int) (Math.random() * 10);
            randomNumber.append((int) num);
        }

        return randomNumber.toString();

    }

    /**
     * Method to return the string value from json from the given Json path
     *
     * @param jsonPath
     * @return
     */
    public static String getStringValueFromJson(String jsonPath) {
        try {
            return JsonPath.parse(GlobalVariables.testDataJson).read(jsonPath);
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to return the string value from json from the given Json path for provide Json
     *
     * @param jsonPath
     * @return
     */
    public static String getStringValueFromJson(String jsonPath, String json) {
        try {
            return JsonPath.parse(json).read(jsonPath);
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to return List of Map values from json from the given Json path
     *
     * @param jsonPath
     * @return
     */
    public static List<Map<String, String>> getListOfHashMapFromJson(String jsonPath) {
        try {
            return JsonPath.parse(GlobalVariables.testDataJson).read(jsonPath);
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to return List of Map values from json from the given Json path
     *
     * @param jsonPath
     * @return
     */
    public static List<String> getListOfStringFromJson(String jsonPath) {
        try {
            return JsonPath.parse(GlobalVariables.testDataJson).read(jsonPath);
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to return List of Map values from provided json from the given Json
     * path
     *
     * @param jsonPath
     * @return
     */
    public static List<Map<String, String>> getListOfHashMapFromJson(String jsonPath, String json) {
        try {
            return JsonPath.parse(json).read(jsonPath);
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to return Map values from json from the given Json path
     *
     * @param jsonPath
     * @return
     */
    public static Map<String, String> getMapFromJson(String jsonPath) {
        try {
            return JsonPath.parse(GlobalVariables.testDataJson).read(jsonPath);
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to return Map of Map values from json from the given Json path
     *
     * @param jsonPath
     * @return
     */
    public static Map<String, Map<String, String>> getMapOfMapFromJson(String jsonPath) {
        try {
            return JsonPath.parse(GlobalVariables.testDataJson).read(jsonPath);
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to return Map values from the entire json
     *
     * @param json
     * @return
     */
    public static Map<String, String> getMapFromJsonString(String json) {
        try {
            return JsonPath.parse(json).read("$");
        } catch (Exception e) {
            logExceptions(e);
            return null;
        }
    }

    /**
     * Method to generate random alphanumeric string based on number of chars given
     *
     * @param noOfChars
     * @return
     */
    public static String generateRandomAlphanumeric(int noOfChars) {

        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder builder = new StringBuilder();
        while (noOfChars-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();

    }

    /**
     * Method to get Json String from file
     *
     * @param testdataFile
     * @return
     * @throws IOException
     */
    public static String getJson(File testdataFile) throws IOException {

        return FileUtils.readFileToString(testdataFile, "UTF-8");

    }

    /**
     * Method to delete all example data provided for the specific feature file
     *
     * @param filePath
     * @throws IOException
     */
    public static void deleteAllExamples(String filePath) throws IOException {

        List<String> listOfTestCaseIds = getListOfScenarioOutline(filePath);

        StringBuilder builder = new StringBuilder();

        for (String tcId : listOfTestCaseIds) {

            File testdataFile = new File("src/test/resources/features/" + filePath);
            String fileContents = getJson(testdataFile);

            int tcIdLocation = fileContents.indexOf(tcId);
            int exampleLocation = fileContents.indexOf("| Iteration |", tcIdLocation) + 13;

            builder.append(fileContents.substring(0, exampleLocation));
            builder.append("\n");
            int nextCaseOrEOF = fileContents.indexOf("@", exampleLocation);
            if (nextCaseOrEOF != -1) {
                builder.append(fileContents.substring(nextCaseOrEOF));
            }

            if (exampleLocation != 12) {
                try (PrintWriter out = new PrintWriter("src/test/resources/features/" + filePath)) {
                    out.println(builder.toString());
                }
            }

        }

    }

    /**
     * Method to add all example data present for the specific feature file
     *
     * @param filePath
     * @throws IOException
     */
    public static void updateFeatureFileByIncludingExamples(String filePath) throws IOException {

        List<String> listOfTestCaseIds = getListOfScenarioOutline(filePath);

        for (String tcId : listOfTestCaseIds) {

            String data = new Utilities().getDataForTestcase(tcId);
            updateDataForExamples(tcId, data, filePath);

        }

    }

    /**
     * Method to add example data present for specific testcase id in specific
     * feature file
     *
     * @param tcId
     * @param data
     * @param filePath
     * @throws IOException
     */
    private static void updateDataForExamples(String tcId, String data, String filePath) throws IOException {

        File testdataFile = new File("src/test/resources/features/" + filePath);
        String fileContents = getJson(testdataFile);

        StringBuilder builder = new StringBuilder();

        int tcIdLocation = fileContents.indexOf(tcId);
        int exampleLocation = fileContents.indexOf("| Iteration |", tcIdLocation) + 13;

        builder.append(fileContents.substring(0, exampleLocation));
        builder.append(data);
        builder.append(fileContents.substring(exampleLocation));

        if (exampleLocation != 12) {
            try (PrintWriter out = new PrintWriter("src/test/resources/features/" + filePath)) {
                out.println(builder.toString());
            }
        }

    }

    /**
     * Method to analyze and get all the scenario outlines present in the specific
     * feature file
     *
     * @param filePath
     * @return
     */
    private static List<String> getListOfScenarioOutline(String filePath) {

        List<String> listOfTestCaseIds = new ArrayList<>();
        String testId;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/test/resources/features/" + filePath));
            String line = reader.readLine();
            while (line != null) {

                if (line.contains("@")) {
                    testId = line.trim().substring(1);
                    line = reader.readLine();
                    if (line.contains("Scenario Outline")) {
                        listOfTestCaseIds.add(testId);
                    }
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            logExceptions(e);
        }

        return listOfTestCaseIds;

    }

    /**
     * Method to log exception in the report
     *
     * @param e
     */
    public static void logExceptions(Exception e) {
        LogManager.getLogger("ApplicationLogs").log(Level.ERROR, "Exception Thrown." + e.toString().split("\n")[0]);
    }

    /**
     * Method to verify if all the projects have been loaded in view
     *
     * @param driver
     * @param noOfSeconds
     * @return
     */
    public static boolean verifyIfProjectsHaveLoaded(WebDriver driver, int noOfSeconds) {

        try {
            new WebDriverWait(driver, noOfSeconds).until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@id='result-count']/p[not(text()='Result Count: 0')]")));
        } catch (Exception e) {
            logExceptions(e);
            return false;
        }

        return true;
    }

    /**
     * Method to update all feature file those are going to be executed.
     *
     * @param testNGCucumberRunner
     * @throws Exception
     */
    public static void updateAllExecutingFeatureFile(TestNGCucumberRunner testNGCucumberRunner) throws Exception {
        Class testngRunner = testNGCucumberRunner.getClass();

        Method getFeatures = testngRunner.getDeclaredMethod("getFeatures");

        getFeatures.setAccessible(true);

        List<CucumberFeature> features = (List<CucumberFeature>) getFeatures.invoke(testNGCucumberRunner);

        for (CucumberFeature feature : features) {
            Utilities.updateFeatureFileByIncludingExamples(feature.getGherkinFeature().getFeature().getName() + ".feature");
        }
    }

    /**
     * Method to update all feature file those are going to be executed.
     *
     * @param testNGCucumberRunner
     * @throws Exception
     */
    public static void deleteAllExampleFromExecutedFeatureFile(TestNGCucumberRunner testNGCucumberRunner)
            throws Exception {
        Class testngRunner = testNGCucumberRunner.getClass();

        Method getFeatures = testngRunner.getDeclaredMethod("getFeatures");

        getFeatures.setAccessible(true);

        List<CucumberFeature> features = (List<CucumberFeature>) getFeatures.invoke(testNGCucumberRunner);

        for (CucumberFeature feature : features) {
            Utilities.deleteAllExamples(feature.getGherkinFeature().getFeature().getName() + ".feature");
        }
    }

    public static void syncApp(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to retrieve all data for specific testcase id
     *
     * @param tcId
     * @return
     * @throws IOException
     */
    private String getDataForTestcase(String tcId) throws IOException {

        File testdataFile = new File(getClass().getClassLoader().getResource(tcId + ".json").getFile());
        GlobalVariables.testDataJson = Utilities.getJson(testdataFile);

        List<Map<String, String>> testcaseData = Utilities.getListOfHashMapFromJson("Data");

        String data = "\n";

        int index = 1;

        for (Map<String, String> setOfdata : testcaseData) {
            if (setOfdata.get("AddToScenario").equalsIgnoreCase("Yes")) {
                data = data + "\t| " + Integer.toString(index) + " |\n";
            }
            index++;
        }

        return data;
    }
}