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
    When User changes theme to '<themes>'
    Then Theme of dashup changes to '<themes>'

    Examples:
    | themes        |
    | Blue Sky      |
    | Green Nature  |
    | Red Love      |
    | White Diamond |
    | Black Night   |

  Scenario: Set background image
    Given User is located on settings menu
    When User sets background image URL to 'https://www.wallpaper.com/mountains.png'
    Then Picture provided by URL is displayed in image preview

  Scenario Outline: Undo changes
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValue>"
    And User is located on settings menu
    When User clicks on abandon icon
    Then Key "<settings>" will be restored to "<latestValue>"

    Examples:
    | settings | latestValue                             | newValue                            |
    | theme    | Blue Sky                                | White Diamond                       |
    | picture  | https://www.wallpaper.com/mountains.png | https://www.wallpaper.com/beach.png |
