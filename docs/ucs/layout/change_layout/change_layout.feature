Feature: Change layout
  in order to customize dashup layout.

  Scenario: The user would like to open up the layout settings menu.
    Given User is logged in to dashup and is located on the main page.
    When The user clicks on the layout edit button,
    Then The layout setting menu opens up.

  Scenario: The user wants to customize the background color.
    Given User is located on the layout settings menu
    When user changes the background color to '<colors'
    Then Background of dashup main page changes to '<colors>'
    And Color picker changes color to "<colors>"

  Examples:
  | colors |
  | #ff00ff |
  | #ffff00 |
  | #ff0000 |

  Scenario: The user made a mistake and would like to undo his changes.
    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValues>"
    When User clicked on undo button
    Then Settings will be restored to the latest state.

  Examples:
  | settings        | latestValue | newValue |
  | panelSize       | small       | medium   |
  | headingSize     | medium      | large    |
  | backgroundColor | #ff00ff     | #ff0000  |
  | headingColor    | #0000ff     | #ffff00  |