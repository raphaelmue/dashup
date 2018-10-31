Feature: User would like to reorder in specific sections
  in order to customize his dashup.

  Scenario: User wants to add his sections to his dashup.
    Given User is logged into the system and is on the main page.
    When User clicks on add section button
    Then a pop-up will show up, asking the user for input.
    When User enters "<sectionName>"
    And User clicks on submit button
    Then a new section will be rendered, being titled with "<sectionName>"

  Scenario: User wants to reorder his panels.
    Given User is logged into the system and is on the main page.
    When User drags a panel to another position
    Then all the other panel will reorder and the dragged panel is on the new position.

  Scenario: User would like to delete a section
    Given User is logged in to dashup and is located on the main page.
    When User clicks the delete section button
    Then the section will be deleted and all panel will be moved to the .