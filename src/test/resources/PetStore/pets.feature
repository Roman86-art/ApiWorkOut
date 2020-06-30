Feature: PetStore Functionality

  Scenario: Pet creation and validation
    When the user creates pets with id, name and status
      | id     | 9595      |
      | name   | Rex      |
      | status | Adapt |
    Then the user validates status code is OK
    And the user validates pets with created  id, name, status is created
      | id     | 9595    |
      | name   | Rex      |
      | status | Adapt |


