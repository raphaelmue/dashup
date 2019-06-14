Feature: Workbench

  As a basic user
  I want to create my own widget
  In order to customize my central dashboard to my personal needs

  Background: Authenticated
    Given User is located on login page
    When User submits e-mail and password
    Then User is logged in

  Scenario: Open workbench menu
    Given User is on central dashboard
    When User clicks on workbench menu
    Then Workbench menu opens up

  Scenario: Navigate back to dashboard via dashboard icon
    Given User is located on workbench menu
    When User clicks on dashboard icon
    Then User will be directed back to central dashboard

  Scenario: Navigate back to dashboard via navigation bar
    Given User is located on workbench menu
    When User navigates back over the navigation bar
    Then User will be directed back to central dashboard

  Scenario: Open custom widget
    Given User is located on workbench menu
    When User clicks on custom widget in the sidebar
    Then Builder tab has opened
    And Custom Widget is shown
    And Coding panel contains HTML code of widget

  Scenario: Add custom widget
    Given User is located on workbench menu
    When User clicks on add button in the sidebar
    Then New widget pop up opens up
    When User enters 'my-widget' as tag name
    And User chooses 'standard' template
    And User presses submit button
    Then Builder tab has opened
    And Custom widget of standard template is shown
    And Coding panel contains HTML code of standard template

  Scenario: Publish custom widget successful
    Given User is located on workbench menu
    And User opened a custom widget
    And User has provided a widget name for his widget in the metadata tab
    And User has provided a short description in the metadata tab
    And User has provided an overview text in the metadata tab
    And User has provided a category for his widget in the metadata tab
    And User has provided at least one tag for his widget in the metadata tab
    When User clicks on publish button in the builder tab
    Then Publish widget pop up opens up
    When User presses publish button
    Then Success message toast opens stating widget published successfully

  Scenario: Publish custom widget not successful because of unprovided metadata
    Given User is located on workbench menu
    And User opened a custom widget
    And User has not provided a category for his widget in the metadata tab
    When User clicks on publish button in the builder tab
    Then Publish widget pop up opens up
    And User presses publish button
    Then Failure message toast opens stating unprovided data

  Scenario: Publish custom widget not successful because of missing username
    Given User is located on workbench menu
    And User opened a custom widget
    And User has not provided a username in the account settings menu
    When User clicks on publish button in the builder tab
    Then Publish widget pop up opens up
    And User presses publish button
    Then Failure message toast opens stating unprovided username

  Scenario: Enter metadata
    Given User is located on workbench menu
    And User opened a custom widget
    When User clicks on metadata tab
    Then Metadata tab opens
    When User enters "My widget" as widget name
    And User enters "Short description" as short description
    And User enters "Very long description" as overview text
    And User selects "finance" as a category
    And User enters "finance" and "productivity" as tags
    And User clicks save button
    Then Success message toast opens stating metadata saved successfully

  Scenario: Enter custom properties fields
    Given User is located on workbench menu
    And User opened a custom widget
    When User clicks on properties tab
    Then properties tab opens
    When User clicks on add button
    And User enters "Location" as property name
    And User enters "location.value" as path to component property
    And User selects "text" as type
    And User enters "Karlsruhe" as default value
    And User clicks save button
    Then Success message toast opens stating metadata saved successfully

  Scenario: Delete custom widget
    Given User is located on workbench menu
    And User opened a custom widget
    When User clicks on delete button in the builder tab
    Then Confirmation pop up opens
    When User confirms delete action
    Then Success message toast opens stating widget deleted successfully

  Scenario: Add custom widget to central dashboard
    Given User is located on workbench menu
    And User opened a custom widget
    When User clicks on add button
    Then Success message toast opens stating widget added to central dashboard successfully
    And Widget was added to a new section section of central dashboard at the top, named after the widget
    And Button to add widget is set to disabled

  Scenario: Save changes
    Given User is located on workbench menu
    And User opened a custom widget
    And User made changes in the editor panel in the builder tab
    When User clicks on save button
    Then Success message toast opens stating widget saved successfully