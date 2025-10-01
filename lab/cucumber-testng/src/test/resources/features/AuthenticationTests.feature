Feature: Authentication Tests

  Scenario: Valid Authentication
    Given The user have valid credentials
    When User passed user-name and password in api request
    Then the response should have http 200 with json response containing token

  Scenario: Invalid Authentication
    Given The user have invalid credentials
    When User passed user-name and password in api request
    Then the response should have http 401 with json response containing error message