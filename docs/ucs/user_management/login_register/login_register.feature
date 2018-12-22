Feature: Login

  As A basic user
  I want to register and login with my credentials
  So that I can use the whole functionality of dashup

  Scenario: Login
    Given User is located on login page
    When User submits username and password
    Then User is logged in

  Scenario: Invalid Credentials
    Given User is located on login page
    When User submits invalid username and password
    Then Error message is shown
    And User is not logged in.

  Scenario: Register
    Given User is located on register page
    When User submits his users details
    Then User is stored into database
    And User is logged in


