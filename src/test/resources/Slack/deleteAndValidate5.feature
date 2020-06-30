Feature: Api delete and validation function
#  5
  Scenario:Api layer delete and verify

    When the user creates a message on Slack with Post method
    When the deletes message from slack via POST request
    Then the user verifies the message is gone via GET request

