Feature: Post a message on slack
#  1
  Scenario: Create a message with POST method
    When the user creates a message on Slack with Post method
    Then the user verifies the message with get method