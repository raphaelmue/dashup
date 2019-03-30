Feature: Change Panel Structure

  As a basic user
  I want to customize the central dashboard of dashup
  In order to design dashup the way I want

  Background: Authenticated
    Given User is located on login page
    When User submits e-mail and password
    Then User is logged in

    Scenario: Enable edit mode
      Given User is located on the central dashboard
      When User clicks on edit icon
      Then Central dashboard can now be edited

    Scenario: Add section
      Given User enabled edit mode
      When User clicks on add section button
      Then New section is added with a placeholder name

    Scenario: Modify section
      Given User enabled edit mode
      When User clicks on the name of a section
      And User enters "my new panels"
      And User unfocuses section name
      Then Section will be renamed to "my panels"

    Scenario: Remove section
      Given User enabled edit mode
      When User clicks on the remove icon of a section
      Then Section will be removed with all its containing panels

    Scenario: Reorder panels
      Given User enabled edit mode
      When User drags a panel to another section
      Then All other panels will reorder and the dragged panel is at the desired position

    Scenario: Reorder sections
      Given User enabled edit mode
      When User drags a section to another position
      Then Sections will be reordered according to the changed section
      And Moved section still contains all its panels

    Scenario Outline: Resize panels.
      Given User enabled edit mode
      When User clicks on size icon
      Then Size menu will pop up containing the three options small, medium and large
      When User selects menu entry "<entry>"
      Then Panel will be resized to "<entry>"

      Examples:
        | entry  |
        | small  |
        | medium |
        | large  |

    Scenario Outline: Undo changes
      Given User has made a change, section "<section>" was renamed from "<latestValue>" to "<newValues>"
      And User enabled edit mode
      When User clicks on abandon icon
      Then Section "<section>" was renamed back to "<latestValue>"

      Examples:
      | section | latestValue | newValue |
      | one     | All Day     | Main     |
      | two     | Finance     | Stocks   |

    Scenario Outline: Confirm changes
      Given User has made a change, section "<section>" was renamed from "<latestValue>" to "<newValues>"
      And User enabled edit mode
      When User clicks on confirm icon
      Then Section "<section>" was renamed to "<newValue>"

      Examples:
      | section | latestValue | newValue        |
      | three   | Health      | Food            |
      | four    | Traffic     | Time Management |