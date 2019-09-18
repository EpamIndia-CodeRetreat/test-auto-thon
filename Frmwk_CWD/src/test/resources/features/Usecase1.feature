@Smoke
Feature: Usecase1

  @TC1
  Scenario: Web - Verify Event Creation on WebUI
    Given The Events Page is Displayed
    When The User click on Profile photo at the top right corner
    And Clicks the Admin Area
    Then The Events Creation Page is Displayed
    When The User Creates a New Event
    Then The User is Navigated to the Created Event Page
    And The User validates the previously entered event details
    When The User clicks the speaker registration toggle
    And Adds "Proposed Talks" page
    And The User publishes the event

  @TC2
  Scenario: Web - Create a Proposed Talk and validate the information entered on WebUI
    Given The User logs in as a regular user
    When The User selects the created event
    Then The Propose A Talk button should be displayed
    When The User Clicks the Propose a Talk button
    And The User Fills the form and clicks on register
    Then The User should be presented with the suitable success message
    When The User logs in as admin user
    And The User click on Profile photo at the top right corner
    And Clicks the Admin Area
    And The User opens the created event
    And The User opens the Talks list
    Then The Proposed talk should be should be present
    When The User expands the talk
    Then The User validates the information for the talk

  @TC3
  Scenario: API - Get the list of all available events and validate the presence and data of the created event in that list using APIs
    Given The User retrieves list of all available events
    When The Created Event is present in the repsonse
    Then The User validates the data of the created event

  @TC4
  Scenario: Mobile - Verify events app is launched and able to click on get started on Mobile App
    Given The Events app home page is displayed in mobile app
    When The User click on Get started button