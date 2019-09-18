@Smoke
Feature: Usecase2

  @TC5
  Scenario: Web - Verify Event Creation with multiple pages on WebUI
    Given The User logs in as admin user
    When The User click on Profile photo at the top right corner
    And Clicks the Admin Area
    Then The Events Creation Page is Displayed
    When The User Creates a New Event
    Then The User is Navigated to the Created Event Page
    And The User validates the previously entered event details
    When The User adds the required pages
    And Opens page settings
    Then The User performs validation on the presence of all pages and their sequence
    When The User modifies "Agenda" page order to "5"
    Then Validate if "Agenda" page has become last in the list
    And The User publishes the event

  @TC6
  Scenario: Web - Verify Event details and page information on WebUI
    Given The User logs in as a regular user
    When The User selects the created event
    And The User Opens the "Details" page
    Then Validates the details for "Details" page
    When The User Opens the "Proposed talks" page
    Then Validates the details for "Proposed talks" page
    When The User Opens the "Speakers" page
    Then Validates the details for "Speakers" page
    When The User Opens the "Agenda" page
    Then Validates the details for "Agenda" page
    And The User checks the order of pages in top menu

  @TC7
  Scenario:  API - Verify Page properties for the created event using APIs
    Given The User retrieves list of all available events
    When The Created Event is present in the repsonse
    Then Store the event id for the event
    When The User sends a request to retrieve details of "details" page
    Then The User validates the response to have the required properties for "details"
    When The User sends a request to retrieve details of "proposed-talks" page
    Then The User validates the response to have the required properties for "proposed-talks"
    When The User sends a request to retrieve details of "agenda" page
    Then The User validates the response to have the required properties for "agenda"
    When The User sends a request to retrieve details of "speakers" page
    Then The User validates the response to have the required properties for "speakers"

  @TC8
  Scenario:  Mobile - Verify events app is launched and able to click on get started on Mobile App
    Given The Events app home page is displayed in mobile app
    When The User click on Get started button