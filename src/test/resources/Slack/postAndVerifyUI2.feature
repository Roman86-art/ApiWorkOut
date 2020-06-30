Feature: Slack Post and validate function
#2
  Scenario: post and validate on UI
    When the user sends a message to slack via POST request
    Then the user verifies the message with Selenium Webdriver in UI
