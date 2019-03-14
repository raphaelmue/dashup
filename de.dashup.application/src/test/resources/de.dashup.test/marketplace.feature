#Feature: Marketplace

#  As a basic user
#  I want to browse the marketplace
#  So that I can find new panels and add them into my dashup

  #Background: User is logged into dashup
   # Given User is located on login page
   # When User submits username and password
   # Then User is logged in

#  Scenario: Open marketplace
#    Given User is located on the main page
#    When User clicks on the marketplace button
#    Then The marketplace shows up


#  Scenario: Navigate back
#    Given User is located on the marketplace page
#    When User clicks on the back button or navigates back over the navigation bar
#    Then User will be directed back to the main page


#  Scenario Outline: Search panel
#    Given User is on the marketplace page
#    When User the types in a search term "<searchTerms>"
#    Then A list with matching "<microservices>" shows up

#    Examples:
#    | searchTerms  | microservices |
#    | calendar     | Calendar      |
#    | weather      | Weather      |


#  Scenario Outline: Filter result set
#    Given User is on the marketplace page
#    When User clicks on filter icon
#    Then Filter menu will pop up
#    When User changes filter "<filter>" to value "<value>"
#    Then Result set will be restricted to all panels that match "<filter>" equals to "<value>"

#    Examples:
#    | filter              | value        |
#    | rating              | 4.0          |
#    | category            | productivity |
#    | tags                | nerdy        |
#    | publisher           | dashup       |
#    | date of publication | 04/11/2018   |


#  Scenario Outline: User would like to rate and comment on a panel
#    Given Rating tab is opened of a panel's detailed view in the marketplace
#    When User writes "<comment>"
#    And User chooses "<rating>" of five stars
#    And User clicks submit button
#    Then The appropriate comment containing text "<comment>" and rating <"rating"> will be displayed on the top of the comment section.

#    Examples:
#    | comment              | rating |
#    | Top panel            | 5      |
#    | Room for improvement | 3.5    |


#  Scenario: Inspect panel
#    Given User is on the marketplace page
#    When User clicks on a specific listed panel
#    Then A detailed view of the panel will open up, containing the tabs overview, comments and similar


  Scenario: Inspect similar panel
    Given User is on the similar tab of a panel's detailed view in the marketplace
    When User clicks on a specific listed panel
    Then A detailed view of the panel will open up, containing the tabs overview, comments and similar


  Scenario: Show more results
    Given User is on the marketplace page
    And User scrolled down to the end of the page
    And More results are available
    When User clicks on the show more button
    Then more results will be loaded and shown on the marketplace list


  Scenario: Add panel to dashup
    Given User is on the marketplace page
    When User clicks on the add panel to dashboard button
    Then Panel with microservice will be added to dashups default section
    And Menu to add panel to dashup is set to disabled in the marketplace

  Scenario: User accessing marketplace while not being logged in
    Given User is not logged into dashup
    When User accesses marketplace
    And User tries to add a panel to dashup
    Then User will be directed to the login page
    When User enters right credentials
    Then User will be directed to the main page
    And Panel is added to dashup