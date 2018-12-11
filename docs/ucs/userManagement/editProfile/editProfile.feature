Feature: edit profile

  As a basic user
  I want to change my login, personal information and system configurations
  This should be possible on a edit profile page to which I can navigate from main page

Background: User is logged into dashup
Given User is located on login page
When User submits username and password
Then User is logged in

  Scenario: User wants to navigate to edit profile page
    Given User is located on the main page
    When User opens up the dropdown
    And User clicks on the navigation button to edit profile
    Then The User is located on the edit profile page

  Scenario: User wants to change his password
    Given User is located on the edit profile Page
    When User clicks on change password
    Then A popup show up asking for new password
    When User enters "password123" as new password
    And clicks submit
    Then the password is changed

  Scenario: User wants to change date of birth
    Given User is located on the edit profile page
    When User changes date of birth to "01-01-1999"
    And User clicks submit
    Then the date of birth should be changed to "01-01-1999"