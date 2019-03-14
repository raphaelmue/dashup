Feature: Change Layout

  As A basic user
  I want to customize the layout of dashup
  In order to design dashup the way I want

  Scenario: Open layout settings menu
    Given User is logged in to dashup
    And User is on the central dashboard
    When The user clicks on the settings menu
    Then The Setting menu opens up

  Scenario: Navigate back to dashboard
    Given User is located on the layout settings menu
    When User clicks on dashboard icon or navigates back over the navigation bar
    Then User will be directed back to the central dashboard

  Scenario Outline: Customize theming
    Given User is located on the layout settings menu
    When User changes the theme to '<themes>'
    Then theme of dashup changes to '<themes>'

    Examples:
    | themes        |
    | Blue Sky      |
    | Green Nature  |
    | Red Love      |
    | White Diamond |
    | Black Night   |

  Scenario Outline: Customize font
    Given User is located on the layout settings menu
    When User changes the font to '<fonts>'
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
    Given User is located on the layout settings menu
    When User uploads a picture
    Then Picture is displayed in the image preview

  Scenario Outline: Undo changes
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValues>"
    And User is located on the layout settings menu
    When User clicks on abandon icon
    Then Key "<settings>" will be restored to "<latestValue>".

    Examples:
    | settings        | latestValue | newValue      |
    | font            | Lora        | Inconsolata   |
    | theme           | Blue Sky    | White Diamond |

  Scenario Outline: Confirm changes
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValues>"
    And User is located on the layout settings menu
    When User clicks on confirm icon
    Then Key "<settings>" will be changes to "<newValue>".

    Examples:
    | settings        | latestValue | newValue |
    | font            | Roboto      | Poppins  |
    | theme           | Black Night | Red Love |