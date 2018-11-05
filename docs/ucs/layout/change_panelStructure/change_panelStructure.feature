Feature: Change Panel Structure

  As a basic user
  I want to customize the general arrangement of sections and panels
  So that I can have a better overview over my panel structure

  Scenario: Enable rearrangement menu
    Given User is logged into dashup
    And User is on the main page
    When The user clicks on the rearrangement button
    Then Dashup's general panel structure is now editable as change structure mode is activated


  Scenario: User wants to add a section to his dashup
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    When User clicks on add section button
    Then A pop-up will show up, asking the user for input
    When User enters "my panels"
    And User clicks on submit button
    Then A new empty section will be rendered, being titled with "my panels"


  Scenario: User wants to rename a section
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    When User clicks on the name of a section
    And User enters "my new panels"
    And User clicks on submit button
    Then section will be renamed to "my panels"


  Scenario: User wants to remove a section
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    When User clicks on the remove icon of a section
    Then Section will be removed
    And All panels will be moved to the section above


  Scenario: User wants to reorder his panels
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    When User drags a panel to another section
    Then All the other panel will reorder and the dragged panel is in the other section


  Scenario: User wants to reorder his sections
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    When User drags a section to another position
    Then Section will be reordered to the specified position with all its containing panels


  Scenario: User wants to resize a panel.
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    When User right clicks on a panel
    Then Size menu will pop up containing the three options small, medium and large
    When User selects menu entry "<entry>"
    Then panel will be resized to "<entry>"

    Examples:
      | entry  |
      | small  |
      | medium |
      | large  |


  Scenario: Submit changes
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    And User has made changes
    When User clicks on submit button
    Then All changes will come into effect


  Scenario: Undo changes
    Given User is logged into dashup
    And User is on the main page
    And Change structure mode is activated
    And User has made a change
    When User clicks on undo button
    Then The latest change will be undone