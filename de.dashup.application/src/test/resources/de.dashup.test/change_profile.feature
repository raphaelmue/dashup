Feature: Change Profile

  As a basic user
  I want to change my personal profile
  In order to update authentication credentials as well as profile or language settings

  Background: Authenticated
    Given User is located on login page
    When User submits e-mail and password
    Then User is logged in

  @EndToEnd @Smoke
  Scenario: Open settings menu
    Given User is located on central dashboard
    When User clicks on settings menu
    Then Settings menu opens up

  @EndToEnd
  Scenario: Navigate back to dashboard via dashboard icon
    Given User is located on settings menu
    When User clicks on dashboard icon
    Then User will be directed back to central dashboard

  @EndToEnd
  Scenario: Navigate back to dashboard via navigation bar
    Given User is located on settings menu
    When User navigates back over the navigation bar
    Then User will be directed back to central dashboard

  @EndToEnd
  Scenario: Change username
    Given User is located on settings menu
    When User clicks account information section
    And Username "Jane Doe" is unique
    And User changes username to "Jane Doe"
    And User submits new username
    Then Username changes to "Jane Doe"

  @EndToEnd
  Scenario: Change e-mail
    Given User is located on settings menu
    When User clicks account information section
    And User changes e-mail to "Jane.Doe@gmail.com"
    And E-mail "Jane.Doe@gmail.com" is unique
    And User submits new e-mail
    Then E-mail changes to "Jane.Doe@gmail.com"

  @EndToEnd
  Scenario: Change password
    Given User is located on settings menu
    When User clicks account information section
    And User clicks on change password
    And User enters correct old password "password"
    And User changes password to "imthegreatest"
    And User repeats "imthegreatest" in dialog as password
    And New password is longer than eight characters
    Then Password changes to "imthegreatest"

  @EndToEnd
  Scenario: Change first name
    Given User is located on settings menu
    When User clicks personal information section
    And User changes first name to "Jane"
    And User clicks on submit icon for personal information
    Then First name changes to "Jane"

  @EndToEnd
  Scenario: Change last name
    Given User is located on settings menu
    When User clicks personal information section
    And User changes last name to "Doe"
    And User clicks on submit icon for personal information
    Then Last name changes to "Doe"

  @EndToEnd
  Scenario: Change birthday
    Given User is located on settings menu
    When User clicks personal information section
    And User changes birthday to "1994-05-13"
    And User clicks on submit icon for personal information
    Then Birthday changes to "1994-05-13"

  @EndToEnd
  Scenario: Change company
    Given User is located on settings menu
    When User clicks personal information section
    And User changes company to "VMware Corporation"
    And User clicks on submit icon for personal information
    Then Company changes to "VMware Corporation"

  @EndToEnd
  Scenario: Change biography
    Given User is located on settings menu
    When User clicks personal information section
    And User changes biography to "My cool biography"
    And User clicks on submit icon for personal information
    Then Biography changes to "My cool biography"

#  Scenario: Change profile picture
#    Given User is located on settings menu
#    When User clicks on image upload
#    And User uploads an image
#    And User clicks on submit icon for personal information
#    Then Profile picture has changed

  @EndToEnd
  Scenario: Change language
    Given User is located on settings menu
    When User clicks others section
    And User changes language to "Deutsch"
    Then Language changes to "Deutsch"