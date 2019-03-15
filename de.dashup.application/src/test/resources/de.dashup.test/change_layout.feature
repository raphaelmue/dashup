Feature: Change Layout

#  As A basic user
#  I want to customize color settings and size of sections
#  So that I can clearly design my personal dashup the way I want

#  Background: User is logged into dashup
#    Given User is located on login page
#    And Database is setup for testing
#    When User submits username and password
#    Then User is logged in

#  Scenario: Open layout settings menu
#    Given User is on the main page
#    When The user clicks on the layout edit button
#    Then The layout setting menu opens up

#  Scenario: Go back to main page
#     Given User is located on the layout settings menu
#     When User clicks on back button or navigates back over the navigation bar
#     Then User will be directed back to the main page


#  Scenario: Customize background color
#    Given User is located on the layout settings menu
#    When User changes the background color
#    Then Background of dashup main page changes


#  Scenario Outline: Customize font color
#    Given User is located on the layout settings menu
#    When User changes the font color of section to '<colors>'
#    Then Section color of dashup main page changes to '<colors>'
#    And Color picker changes color to "<colors>"

#    Examples:
#    | colors  |
#    | #ff00ff |
#    | #ffff00 |
#    | #ff0000 |


#  Scenario Outline: Customize section heading size
#    Given User is located on the layout settings menu
#    When User changes the section heading size to '<size>'
#    Then Section color of dashup main page changes to '<size>'

#    Examples:
#    | size   |
#    | small  |
#    | medium |
#    | large  |


#  Scenario Outline: Undo changes
#    Given User has made a change, key "<settings>" was changed from "<latestValue>" to "<newValues>"
#    And User is located on the layout settings menu
#    When User clicks on undo button
#    Then Key "<settings>" will be restored to "<latestValue>".

#    Examples:
#    | settings        | latestValue | newValue |
#    | headingSize     | medium      | large    |
#    | backgroundColor | #ff00ff     | #ff0000  |
#    | headingColor    | #0000ff     | #ffff00  |
