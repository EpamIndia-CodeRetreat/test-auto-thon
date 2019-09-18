package com.ert.config.variables;

import cucumber.api.Scenario;
import cucumber.api.testng.PickleEventWrapper;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class GlobalVariables {
    public static ConfigurableApplicationContext applicationContext;
    public static Scenario currentScenario;
    public static String applicationUrl;
    public static String currentTestcaseId;
    public static String testDataJson;

    public static int shortWait = 30;
    public static int mediumWait = 60;
    public static int longWait = 120;
    
    public static int shortSync = 1000;
    public static int mediumSync = 2000;
    public static int longSync = 3000;
	public static String currentFeature;
	public static PickleEventWrapper currentPickleWrapper;
	public static String eventTitle;
    public static String suggestedUrl;
    public static String date;
    public static List<String> pagesAdded;
    public static String eventId;

    private GlobalVariables() {
    }
}
