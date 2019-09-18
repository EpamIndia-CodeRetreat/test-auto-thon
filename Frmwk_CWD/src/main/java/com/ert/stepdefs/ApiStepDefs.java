package com.ert.stepdefs;

import com.ert.config.variables.GlobalVariables;
import com.ert.libs.apiUtils.RestExecutor;
import com.ert.libs.apiUtils.RestValidator;
import com.ert.libs.commonUtils.Utilities;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ApiStepDefs {

    private RestExecutor restExecutor;
    private RestValidator restValidator;
    private String title;

    @Given("^The User retrieves list of all available events$")
    public void getAllEvents() {

        try {
            Map<String, String> apiDetails = Utilities.getMapFromJson("APIDetails");
            restExecutor = new RestExecutor(apiDetails.get("uri"));

            String paramName = apiDetails.get("paramName");
            String paramValue = GlobalVariables.eventTitle;

            DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat convertedFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date = originalFormat.parse(GlobalVariables.date);

            GlobalVariables.date = convertedFormat.format(date);

            restValidator = restExecutor.get(apiDetails.get("path"), null, paramName, paramValue);
        } catch (Exception e) {
            Utilities.logExceptions(e);
            Assert.fail("Exception Thrown");
        }
    }

    @When("^The Created Event is present in the repsonse$")
    public void validateIfCreatedEventIsPresent() {
        try {
            title = GlobalVariables.eventTitle;
            // Validating title
            restValidator.validateBody("events.find{ event -> event.title == '" + title + "'}" + ".title", title,
                    "Title is not present in event body");
        } catch (Exception e) {
            Utilities.logExceptions(e);
            Assert.fail("Exception Thrown");
        }
    }

    @Then("^The User validates the data of the created event$")
    public void validateCreatedEvent() {
        try {
            String url = title.toLowerCase().replace(" ", "");
            // Validating url
            restValidator.validateBody("events.find{ event -> event.title == '" + title + "'}" + ".url", url,
                    "Url is not as expected in event details");
            String date = GlobalVariables.date;

            // Validating dates
            restValidator.validateBody("events.find{ event -> event.title == '" + title + "'}" + ".dates", date,
                    "Date is not as expected in event details");
            boolean isPast = false;
            // Validating is_past
            restValidator.validateBody("events.find{ event -> event.title == '" + title + "'}" + ".is_past", isPast,
                    "isPast flag is not as expected in event details");
        } catch (Exception e) {
            Utilities.logExceptions(e);
            Assert.fail("Exception Thrown");
        }
    }

    @Then("^Store the event id for the event$")
    public void storeEventId() {
        Response response = restValidator.getResponse();

        try {
            GlobalVariables.eventId = response.getBody().jsonPath()
                    .getString("events.find{ event -> event.title == '" + title + "'}" + ".id");
        } catch (Exception e) {
            Utilities.logExceptions(e);
            Assert.fail("Exception Thrown");
        }
    }

    @When("^The User sends a request to retrieve details of \"([^\"]*)\" page$")
    public void retrieveDetail(String pageDetail) {
        try {
            Map<String, String> apiDetails = Utilities.getMapFromJson("APIDetails");
            restExecutor = new RestExecutor(apiDetails.get("uri"));

            String paramName = apiDetails.get("paramName");
            String paramValue = GlobalVariables.eventTitle;

            restValidator = restExecutor.get(
                    apiDetails.get("path") + "/" + GlobalVariables.eventId + "/pages/" + pageDetail, null, paramName,
                    paramValue);
        } catch (Exception e) {
            Utilities.logExceptions(e);
            Assert.fail("Exception Thrown");
        }
    }

    @Then("^The User validates the response to have the required properties for \"([^\"]*)\"$")
    public void validateDetail(String pageDetail) {
        try {
            Map<String, String> apiDetails = Utilities.getMapFromJson("APIDetails." + pageDetail);
            for (Map.Entry<String, String> entry : apiDetails.entrySet()) {
                if (entry.getValue().contains("GlobalVariables")) {
                    restValidator.validateBody(entry.getKey(),
                            GlobalVariables.class.getField(entry.getValue().substring(16)).get(null).toString(),
                            "Validation Failed");
                } else if (entry.getValue().equals("<event_url>")) {

                    restValidator.validateBody(entry.getKey(),
                            "https://autorqa.events.epam.com/events/" + GlobalVariables.eventTitle.toLowerCase(),
                            "Validation Failed");
                } else {
                    restValidator.validateBody(entry.getKey(), entry.getValue(), "Validation Failed");
                }
            }
        } catch (Exception e) {
            Utilities.logExceptions(e);
            Assert.fail("Exception Thrown");
        }

    }

}
