Feature: Delete on Api and validate ON UI function
#  6
  Scenario: deletion and validation of different layers
  When the user creates a message with Post method
    Then the deletes message via POST request
  Then Verify the message is gone via Selenium Webdriver in UI