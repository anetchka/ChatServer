#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Register a user
  The server inserts the user into the db if the user doesn't exist

  @First
  Scenario Outline: Username doesn't exist in the chat
    Given the username <username> doesn't exist in the chat
    When the user registers with username <username> and password <password>
    Then the server conformation with username <username> and password <password>

     Examples: 
      | username    | password | 
      | "JohnDoe"   | "1231"   | 

  @Last
  Scenario Outline: User exists in the chat
    Given the username <username> exists in the chat
    When the user registers with username <username> and password <password>
    Then the server asks to enter a new username <username> and  password <password>
   
    Examples: 
      | username    | password | 
      | "JohnDoe"   | "1231"   |

