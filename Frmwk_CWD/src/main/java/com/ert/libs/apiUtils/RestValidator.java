package com.ert.libs.apiUtils;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestValidator {

    public Response response;

    /**
     * Constructor to initialize response.
     *
     * @param response
     */
    public RestValidator(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    /**
     * Method for validating status code.
     *
     * @param expectedStatusCode
     * @return RestValidator
     */
    public RestValidator validateStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Incorrect Status Code");
        return this;
    }

    /**
     * Method to validate response body.
     *
     * @param expectedBody
     * @return RestValidator
     */
    public RestValidator validateStringInBody(String expectedBody) {
        Assert.assertEquals(response.getBody().asString(), expectedBody, "Response body not as expected");
        return this;
    }

    /**
     * Method to validate response body.
     *
     * @param expectedBody
     * @return RestValidator
     */
    public RestValidator validateBody(String path, String expectedBody, String message) {
        Assert.assertEquals(response.getBody().jsonPath().get(path).toString(), expectedBody, message);
        return this;
    }

    /**
     * Method to validate response body.
     *
     * @param expectedBody
     * @return RestValidator
     */
    public RestValidator validateBody(String path, Boolean expectedBody, String message) {
        Assert.assertEquals(response.getBody().jsonPath().get(path), expectedBody, message);
        return this;
    }

//    /**
//     * Method to validate response body.
//     *
//     * @param expectedBody
//     * @return RestValidator
//     */
//    public RestValidator validateBody(String path, String expectedBody) {
//        Assert.assertEquals(response.getBody().jsonPath().get(path).toString(), expectedBody, "Response body not as expected");
//        return this;
//    }

    /**
     * Method to validate response body.
     *
     * @param expected
     * @return RestValidator
     */
    public RestValidator validateBody(String path, JsonPath expected) {
        Assert.assertEquals(response.getBody().jsonPath().get(path).toString(), expected.get().toString(), "Response body not as expected");
        return this;
    }

    /**
     * Method to validate or check string is present in response body.
     *
     * @param expectedBody
     * @return RestValidator
     */
    public RestValidator expectedInBody(String expectedBody) {
        Assert.assertTrue(response.getBody().asString().contains(expectedBody), "Body does not contains string: " + expectedBody);
        return this;
    }

    /**
     * Method to validate status line.
     *
     * @param expectedMessage
     * @return RestValidator
     */
    public RestValidator validateResponseStatusLine(String expectedMessage) {
        Assert.assertEquals(response.getStatusLine(), expectedMessage, "Incorrect status line");
        return this;
    }

    /**
     * Method for validating headers.
     *
     * @param expectedHeaders
     * @return RestValidator
     */
    public RestValidator validateHeadres(Headers expectedHeaders) {
        List<Header> actualHeaders = response.getHeaders().asList();
        List<Header> headers = expectedHeaders.asList();
        for (Header header : headers) {
            if (!actualHeaders.contains(header)) {
                Assert.fail(header.toString() + " Header is not present");
            }
        }
        return this;
    }

    /**
     * Method for validating headers for expected headers in Map.
     *
     * @param expectedHeaders
     * @return RestValidator
     */
    public RestValidator validateHeadres(HashMap<String, String> expectedHeaders) {
        List<Header> actualHeaders = response.getHeaders().asList();
        List<Header> headers = new ArrayList<Header>();
        for (String key : expectedHeaders.keySet()) {
            headers.add(new Header(key, expectedHeaders.get(key)));
        }
        for (Header header : headers) {
            if (!actualHeaders.contains(header)) {
                Assert.fail(header.toString() + " Header is not present");
            }
        }
        return this;
    }
}
