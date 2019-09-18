package com.ert.libs.apiUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;


public class RestExecutor {

    RequestSpecification httpRequest;

    /**
     * Constructor for initializing httpRequest.
     *
     * @param URI
     */
    public RestExecutor(String URI) {
        httpRequest = RestAssured.given().baseUri(URI);
    }

    public RestExecutor() {
        httpRequest = RestAssured.given();
    }

    /**
     * Method for getting responses.
     *
     * @param path
     * @param headers
     * @return RestValidator
     */
    public RestValidator get(String path, HashMap<String, String> headers) {
        Response response;

        //Setting the headers for the request
        if (headers != null) {
            httpRequest.headers(headers);
        }

        httpRequest.basePath(path);
        response = httpRequest.relaxedHTTPSValidation().get();

        return new RestValidator(response);
    }

    /**
     * Method for getting responses.
     *
     * @param path
     * @param headers
     * @return RestValidator
     */
    public RestValidator get(String path, Map<String, String> headers, String paramName, String paramValue) {
        Response response;

        httpRequest = httpRequest.param(paramName, paramValue);

        //Setting the headers for the request
        if (headers != null) {
            httpRequest.headers(headers);
        }

        httpRequest.basePath(path);
        response = httpRequest.relaxedHTTPSValidation().get();
        
        return new RestValidator(response);
    }

    /**
     * Method for http POST apiUtils call.
     *
     * @param path
     * @param headers
     * @param content
     * @param contentType
     * @return RestValidator
     */
    public RestValidator post(String path, HashMap<String, String> headers, String content, String contentType) {

        Response response;

        //Setting the headers for the request
        if (headers != null) {
            httpRequest.headers(headers);
        }

        httpRequest.basePath(path);

        //Setting request body
        httpRequest.body(content);

        //Setting content type
        httpRequest.contentType(contentType);

        response = httpRequest.post();

        return new RestValidator(response);
    }

    /**
     * Method for http PUT apiUtils call.
     *
     * @param path
     * @param headers
     * @param content
     * @param contentType
     * @return RestValidator
     */
    public RestValidator put(String path, HashMap<String, String> headers, String content, String contentType) {
        Response response;

        //Setting the headers for the request
        if (headers != null) {
            httpRequest.headers(headers);
        }

        httpRequest.basePath(path);

        //Setting request body
        httpRequest.body(content);

        //Setting content type
        httpRequest.contentType(contentType);

        response = httpRequest.put();

        return new RestValidator(response);
    }

    /**
     * Method to delete.
     *
     * @param path
     * @param headers
     * @return RestValidator
     */
    public RestValidator delete(String path, HashMap<String, String> headers) {
        Response response;

        //Setting the headers for the request
        if (headers != null) {
            httpRequest.headers(headers);
        }

        httpRequest.basePath(path);

        response = httpRequest.delete();

        return new RestValidator(response);
    }

    /**
     * Method to delete.
     *
     * @param path
     * @return
     */
    public RestValidator delete(String path) {
        Response response;

        httpRequest.basePath(path);

        response = httpRequest.delete();

        return new RestValidator(response);
    }

    public RestValidator patch(String path, HashMap<String, String> headers, String content, String contentType) {
        Response response;

        //Setting the headers for the request
        if (headers != null) {
            httpRequest.headers(headers);
        }

        httpRequest.basePath(path);

        //Setting request body
        httpRequest.body(content);

        //Setting content type
        httpRequest.contentType(contentType);

        response = httpRequest.patch();

        return new RestValidator(response);
    }
}
