Feature: Login / Register

  As a basic user
  I want to login or register to dashup
  In order to use the dashup platform

  Scenario: Successful login
    Given User is located on login page
    And E-mail is "John.Doe@gmail.com"
    And Password is "d(&!h8%ac4_Z"
    When User enters "John.Doe@gmail.com" as e-mail
    And User enters "d(&!h8%ac4_Z" as password
    And User presses login button
    Then User is logged in
    And User was navigated to central dashboard

  Scenario: Failed login
    Given User is located on login page
    And E-mail is "John.Doe@gmail.com"
    And Password is "d(&!h8%ac4_Z"
    When User enters "John.Doe@gmail.com" as e-mail
    And User enters "d(&1h8%ac4_Z" as password
    And User presses login button
    Then Login error message is displayed

Scenario: Navigate to registration page
    Given User is located on login page 
    When User clicks on link to register
    Then User is navigated to registration page

Scenario: Failed registration due to an invalid e-mail
    Given User is located on registration page
    And E-Mail "John.Doe@gmail.com" already exists
    When User enters "John Doe" as username
    And User enters "John.Doe@gmail.com" as e-mail
    And User enters "d(&1h8%ac4_Z" as password
    And User repeats "d(&1h8%ac4_Z" as password
    Then Registration error message is displayed stating that e-mail is invalid

Scenario: Failed registration due to an invalid password
    Given User is located on registration page
    And Minimum password length is set to eight
    When User enters "John Doe" as username
    And User enters "John.Doe@gmail.com" as e-mail
    And User repeats "d(&1h8%" as password
    And User enters "d(&1h8%" as password
    Then Registration error message is displayed stating that password is invalid

Scenario: Failed registration due to an not matching passwords
    Given User is located on registration page
    When User enters "John Doe" as username
    And User enters "John.Doe@gmail.com" as e-mail
    And User enters "d(&1h8%ac4_Z" as password
    And User repeats "d(&1h8%ac3_Z" as password
    Then Registration error message is displayed stating that passwords are not matching

Scenario: Successful registration
    Given User is located on registration page
    When User enters "John Doe" as username
    And User enters "John.Doe@gmail.com" as e-mail
    And User enters "d(&1h8%ac4_Z" as password
    And User repeats "d(&1h8%ac4_Z" as password
    And User presses start button
    Then Confirmation e-mail was sent
    When User opens mail client
    And User opens mail from dashup
    And User clicks on confirmation link
    Then User is navigated to central dashboard