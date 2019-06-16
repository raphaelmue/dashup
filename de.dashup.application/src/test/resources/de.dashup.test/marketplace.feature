Feature: Marketplace

  As a basic user
  I want to browse the marketplace
  In order to find new widgets and add them into my central dashboard

  Background: Authenticated
    Given User is located on login page
    When User submits e-mail and password
    Then User is logged in

  @EndToEnd
  Scenario: Open marketplace menu
    Given User is located on central dashboard
    When User clicks on marketplace menu
    Then Marketplace menu opens up
    And Three sections "Featured", "Most popular" and "Best rating" are shown

  @EndToEnd
  Scenario: Navigate back to dashboard via dashboard icon
    Given User is located on marketplace menu
    When User clicks on dashboard icon
    Then User will be directed back to central dashboard

  @EndToEnd
  Scenario: Navigate back to dashboard via navigation bar
    Given User is located on marketplace menu
    When User navigates back over the navigation bar
    Then User will be directed back to central dashboard

  #Scenario Outline: Search widget
  #  Given User is located on marketplace menu
  #  When User the types in a search term "<searchTerms>"
  #  Then A list with matching "<widgets>" shows up

  #  Examples:
  #  | searchTerms  | widgets    |
  #  | calendar     | Calendar   |
  #  | money        | ShareValue |


  #Scenario Outline: Filter result set
  #  Given User is located on marketplace menu
  #  When User clicks on filter icon
  #  Then Filter menu will pop up
  #  When User changes filter "<filter>" to value "<value>"
  #  Then Result set will be restricted to all widgets that match "<filter>" equals to "<value>"

  #  Examples:
  #  | filter              | value        |
  #  | rating              | 4.0          |
  #  | categories          | money        |
  #  | tags                | productivity |
  #  | publisher           | dashup       |
  #  | date of publication | 04/11/2018   |

  @EndToEnd
  Scenario: Inspect widget
    Given User is located on marketplace menu
    When User clicks on a specific listed widget
    Then A detailed view of the widget will open up, containing the tabs overview, comments and similar
    And User is located on overview tab

#  Not testable due to star rating...
#  @EndToEnd
#  Scenario Outline: Rate and comment widget
#    Given  User is located on marketplace menu
#    And User clicks on a specific listed widget
#    When User clicks on rating tab
#    And User clicks comment icon
#    And User writes "<comment>"
#    And User chooses "<rating>"
#    And User clicks submit button
#    Then The appropriate comment containing text "<comment>" and rating <"rating"> will be displayed on the top of the comment section.
#
#    Examples:
#    | comment              | rating |
#    | Top panel            | 95     |
#    | Room for improvement | 35     |

  @EndToEnd
  Scenario: Inspect similar panel
    Given User opened a widget's detailed view in the marketplace menu
    When User clicks on similar tab
    And User clicks on a specific listed similar widget
    Then A detailed view of the selected widget will open up, containing the tabs overview, comments and similar

  #Scenario: Show more results
  #  Given User is located on marketplace menu
  #  And User scrolled down to the end of the page
  #  And More results are available
  #  When User clicks on the show more button
  #  Then More results will be loaded and shown on the marketplace list

  @EndToEnd
  Scenario: Add panel to dashup
    Given User is located on marketplace menu
    And User clicks on a specific listed widget
    When User clicks on the add button
    Then Widget was added to a new section section of central dashboard at the top, named after the widget