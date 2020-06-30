Feature: Amazon all links

  Scenario: validating all and working links
    When the user on amazon.com
    Then the user prints all links
    Then the user prints only working links