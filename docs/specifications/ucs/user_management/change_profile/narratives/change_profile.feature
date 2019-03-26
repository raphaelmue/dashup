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
    When User changes  username to 'Jane Doe'
    And Username is unique
    Then username changes to 'Jane Doe'

  Scenario: Change e-mail
    Given User is located on settings menu
    When User changes e-mail to 'Jane.Doe@gmail.com'
    And e-mail is unique
    Then e-mail changes to 'Jane.Doe@gmail.com'

  Scenario: Change password
    Given User is located on settings menu
    When User changes password to 'imthegreatest'
    And User repeates 'imthegreatest' as password
    And new password is longer than eight characters
    Then password changes to 'imthegreatest'

  Scenario: Change first name
    Given User is located on settings menu
    When User changes first name to 'Jane'
    Then first name changes to 'Jane'

  Scenario: Change last name
    Given User is located on settings menu
    When User changes last name to 'Doe'
    Then last name changes to 'Doe'

  Scenario: Change birthday
    Given User is located on settings menu
    When User changes birthday to '13.05.1994'
    Then birthday changes to '13.05.1994'
  
  Scenario: Change company
    Given User is located on settings menu
    When User changes company to 'VMware Corporation'
    Then company changes to 'VMware Corporation'

  Scenario: Change biography
    Given User is located on settings menu
    When User changes biography to 'My cool biography'
    Then biography changes to 'My cool biography'

  Scenario: Change language
    Given User is located on settings menu
    When User changes language to 'German'
    Then language changes to 'German'

  Scenario Outline: Undo changes
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValue>"
    And User is located on settings menu
    When User clicks on abandon icon
    Then Key "<settings>" will be restored to "<latestValue>"

    Examples:
    | settings | latestValue | newValue      |
    | company  | Google LLC  | Facebook Inc. |
    | username | John Doe    | Mr. T         |

  Scenario Outline: Confirm changes
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValues>"
    And User is located on settings menu
    When User clicks on confirm icon
    Then Key "<settings>" will be changes to "<newValue>"

    Examples:
    | settings   | latestValue | newValue |
    | first name | John        | Mary     |
    | last name  | Doe         | Poppins  |