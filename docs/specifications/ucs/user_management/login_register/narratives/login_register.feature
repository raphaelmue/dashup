Feature: Login / Register

  As a basic user
  I want to login or register to dashup
  In order to use the dashup platform

  Scenario: Successful login without remember me
    Given User is located on login page
    And User registered with e-mail "John.Doe@gmail.com" and password "password"
    When User enters "John.Doe@gmail.com" as e-mail
    And User enters "password" as password
    And User unchecks remember me option
    And User presses login button
    Then User is logged in
    And User was navigated to central dashboard
    And User can close dashup and is not longer logged in

  Scenario: Successful login with remember me
    Given User is located on login page
    And User registered with e-mail "John.Doe@gmail.com" and password "password"
    When User enters "John.Doe@gmail.com" as e-mail
    And User enters "password" as password
    And User presses login button
    Then User is logged in
    And User was navigated to central dashboard
    And User can close dashup and open it again without being logged out

  Scenario: Failed login
    Given User is located on login page
    And User registered with e-mail "John.Doe@gmail.com" and password "password"
    When User enters "notValid@test.com" as e-mail
    And User enters "wrongPassword" as password
    And User presses login button
    Then Login error message is displayed

  Scenario: Navigate to registration page
    Given User is located on login page
    When User clicks on link to register
    Then User is navigated to registration page

  Scenario: Failed registration due to an invalid e-mail
    Given User is located on registration page
    And E-mail "John.Doe@gmail.com" already exists
    When User enters "John Doe" as username
    And User enters "John.Doe@gmail.com" as e-mail to registration formula
    And User enters "password" as password to registration formula
    And User repeats "password" as password
    And User presses start button
    Then Registration error message is displayed stating that e-mail is invalid

  Scenario: Failed registration due to an invalid password
    Given User is located on registration page
    When User enters "John Doe" as username
    And User enters "John.Doe@gmail.com" as e-mail to registration formula
    And User enters "short" as password to registration formula
    And User repeats "short" as password
    And User presses start button
    Then Registration error message is displayed stating that password is invalid

  Scenario: Failed registration due to an not matching passwords
    Given User is located on registration page
    When User enters "John Doe" as username
    And User enters "John.Doe@gmail.com" as e-mail to registration formula
    And User enters "d(&1h8%ac4_Z" as password to registration formula
    And User repeats "d(&1h8%ac3_Z" as password
    And User presses start button
    Then Registration error message is displayed stating that passwords are not matching

  Scenario: Successful registration
    Given User is located on registration page
    When User enters "John Doe" as username
    And User enters "John.Doe@test.com" as e-mail to registration formula
    And User enters "d(&1h8%ac4_Z" as password to registration formula
    And User repeats "d(&1h8%ac4_Z" as password
    And User presses start button
    #--Extension Point--
    #Then Confirmation e-mail was sent
    #When User opens mail client
    #And User opens mail from dashup
    #And User clicks on confirmation link
    Then User was navigated to central dashboard