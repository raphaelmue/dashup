Feature: Change Layout

  As a basic user
  I want to customize the layout of dashup
  In order to design dashup the way I want


Background: Authenticated
Given User is located on login page
  When User submits e-mail and password
  Then User is logged in

  Scenario: Open settings menu
    Given User is located on central dashboard
    When User clicks on settings menu
    Then Settings menu opens up

  Scenario: Navigate back to dashboard via dashboard icon
    Given User is located on settings menu
    When User clicks on dashboard icon
    Then User will be directed back to central dashboard

  Scenario: Navigate back to dashboard via navigation bar
    Given User is located on settings menu
    When User navigates back over the navigation bar
    Then User will be directed back to central dashboard

  Scenario Outline: Customize theming
    Given User is located on settings menu
    When User changes theme to "<themes>"
    Then Theme of dashup changes to "<themes>"

    Examples:
    | themes        |
    | Blue Sky      |
    | Green Nature  |
    | Red Love      |
    | White Diamond |
    | Black Night   |

#--------------- Not implemented yet ---------------#
#  Scenario Outline: Customize font
#    Given User is located on settings menu
#    When User changes font to '<fonts>'
#    Then Font of dashup changes to '<fonts>'

#    Examples:
#    | fonts        |
#    | Roboto       |
#    | Montserrat   |
#    | Poppins      |
#    | Lora         |
#    | Sniglet      |
#    | Inconsolata  |
#    | Indie Flower |
#    | Anton        |
#    | Lobster      |
#    | Pacifico     |
#    | Cinzel       |
#    | Fredoka One  |
#    | Biblo        |
#    | Orbitron     |

  Scenario: Set background image
    Given User is located on settings menu
    When User provides the valid image URL "https://images.pexels.com/photos/1450360/pexels-photo-1450360.jpeg"
    Then Picture with URL "https://images.pexels.com/photos/1450360/pexels-photo-1450360.jpeg" is displayed as background image

  Scenario Outline: Undo changes
    Given User is located on settings menu
    And   User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValue>"
    When User clicks on abandon icon
    Then Key "<settings>" will be restored to "<latestValue>"

    Examples:
    | settings      | latestValue | newValue      |
    | theme         | Blue Sky    | White Diamond |
    | background    |             | https://images.pexels.com/photos/1450360/pexels-photo-1450360.jpeg|