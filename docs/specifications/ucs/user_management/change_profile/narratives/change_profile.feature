Feature: Change Profile

  As a basic user
  I want to change my personal profile
  In order to update authentication credentials as well as profile or language settings

Background: User is logged into dashup
Given User is located on login page
When User submits e-mail and password
Then User is logged in

  Scenario: Open settings menu
    Given User is on central dashboard
    When User clicks on settings menu
    Then Settings menu opens up

  Scenario: Change username
    Given User is located on settings menu
    When User clicks on edit icon for account information
    And User changes username to 'Jane Doe'
    And Username is unique
    And User clicks on submit icon for account information
    Then username changes to 'Jane Doe'

  Scenario: Change e-mail
    Given User is located on settings menu
    When User clicks on edit icon for account information
    And User changes e-mail to 'Jane.Doe@gmail.com'
    And e-mail is unique
    And User clicks on submit icon for account information
    Then e-mail changes to 'Jane.Doe@gmail.com'

  Scenario: Change password
    Given User is located on settings menu
    When User clicks on change password
    And User enters correct old password
    And User changes password to 'imthegreatest'
    And User repeates 'imthegreatest' as password
    And new password is longer than eight characters
    Then password changes to 'imthegreatest'

  Scenario: Change first name
    Given User is located on settings menu
    When User clicks on edit icon for personal information
    And User changes first name to 'Jane'
    And User clicks on submit icon for personal information
    Then first name changes to 'Jane'

  Scenario: Change last name
    Given User is located on settings menu
    When User clicks on edit icon for personal information
    And User changes last name to 'Doe'
    And User clicks on submit icon for personal information
    Then last name changes to 'Doe'

  Scenario: Change birthday
    Given User is located on settings menu
    When User clicks on edit icon for personal information
    And User changes birthday to '13.05.1994'
    And User clicks on submit icon for personal information
    Then birthday changes to '13.05.1994'
  
  Scenario: Change company
    Given User is located on settings menu
    When User clicks on edit icon for personal information
    And User changes company to 'VMware Corporation'
    And User clicks on submit icon for personal information
    Then company changes to 'VMware Corporation'

  Scenario: Change biography
    Given User is located on settings menu
    When User clicks on edit icon for personal information
    And User changes biography to 'My cool biography'
    And User clicks on submit icon for personal information
    Then biography changes to 'My cool biography'

  Scenario: Change language
    Given User is located on settings menu
    When User clicks on edit icon for other information
    And User changes language to 'German'
    And User clicks on submit icon for other information
    Then language changes to 'German'

  Scenario Outline: Undo changes
    Given User is located on settings menu
    When User clicks on edit icon for "<section>" information
    And User changed key "<settings>" from "<latestValue>" to "<newValue>"
    When User clicks on abandon icon
    Then Key "<settings>" will be restored to "<latestValue>"

    Examples:
    | section | settings | latestValue | newValue      |
    | personal| company  | Google LLC  | Facebook Inc. |
    | account | username | John Doe    | Mr. T         |
    | other   | language | German      | English       |
