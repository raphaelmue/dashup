Feature: Change Layout

  As a basic user
  I want to customize the layout of dashup
  In order to design dashup the way I want

Background: Autheticated
Given User is located on login page
When User submits e-mail and password
Then User is logged in

  Scenario: Open settings menu
    Given User is located on central dashboard
    When User clicks on settings menu
    Then Settings menu opens up

  Scenario: Navigate back to dashboard
    Given User is located on settings menu
    When User clicks on dashboard icon or navigates back over the navigation bar
    Then User will be directed back to central dashboard

  Scenario Outline: Customize theming
    Given User is located on settings menu
    When User changes theme to '<themes>'
    Then Theme of dashup changes to '<themes>'

    Examples:
    | themes        |
    | Blue Sky      |
    | Green Nature  |
    | Red Love      |
    | White Diamond |
    | Black Night   |

  Scenario Outline: Customize font
    Given User is located on settings menu
    When User changes font to '<fonts>'
    Then Font of dashup changes to '<fonts>'

    Examples:
    | fonts        |
    | Roboto       |
    | Montserrat   |
    | Poppins      |
    | Lora         |
    | Sniglet      |
    | Inconsolata  |
    | Indie Flower |
    | Anton        |
    | Lobster      |
    | Pacifico     |
    | Cinzel       |
    | Fredoka One  |
    | Biblo        |
    | Orbitron     |

  Scenario: Set background image
    Given User is located on settings menu
    When User uploads a picture
    Then Picture is displayed in image preview

  Scenario Outline: Undo changes
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValues>"
    And User is located on settings menu
    When User clicks on abandon icon
    Then Key "<settings>" will be restored to "<latestValue>"

    Examples:
    | settings | latestValue | newValue      |
    | font     | Lora        | Inconsolata   |
    | theme    | Blue Sky    | White Diamond |

  Scenario Outline: Confirm changes
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValues>"
    And User is located on settings menu
    When User clicks on confirm icon
    Then Key "<settings>" will be changes to "<newValue>"

    Examples:
    | settings | latestValue | newValue |
    | font     | Roboto      | Poppins  |
    | theme    | Black Night | Red Love |