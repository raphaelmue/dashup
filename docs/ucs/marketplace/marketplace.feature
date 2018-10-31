Feature: Browse marketplace
  in order to find micro services for your personal dashup.

  Scenario: The user would like to open the marketplace.
    Given User is logged in to dashup and is located on the main page.
    When User clicks on the marketplace button,
    Then the marketplace shows up.

  Scenario: The User would like to find micro services applicable to his search term.
    Given The User is on the marketplace page
    When User the types in a search term "<searchTerm>"
    Then a list with matching micro services shows up.

  Examples:
  | searchTerms  | microServices |
  | calendar     | Calendar      |

  Scenario: User would like to rate and comment on a panel.
    Given Rating tab is opened of a panel's detailed view marketplace.
    When User writes "<comment>"
    And User chooses "<rating>" of five stars
    And User clicks submit button
    Then the appropriate comment containing text "<comment>" and rating <"rating"> will be displayed on the top of the comment section.

  Examples:
  | comment              | rating |
  | Top panel            | 5      |
  | Room for improvement | 3.5    |