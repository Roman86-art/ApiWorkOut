Feature: Post Slack UI and verify with Get on Api
#4
  Scenario: post ui and verify with get method

    When the user sends message to slack with Selenium Webdriver in UI
    Then the user verifies the message with get method
