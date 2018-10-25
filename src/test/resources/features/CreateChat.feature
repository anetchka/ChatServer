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
Feature: Title of your feature
  I want to use this template for my feature file

  @First 
  Scenario Outline: Chat doesn't exist in user's list
    Given the chat name <chatname> doesn't exist
    When the user <username> wants to create the chat <chatname>
    Then the user <username> creates the chat <chatname>
    And the user <username> is the admin of the chat <chatname>
    
    Examples: 
      | chatname  | username  | message               |
      | "GB"      | "JohnDoe" | "chat already exists" |

  @Last
  Scenario Outline: Chat exists in user's list
    Given the chat name <chatname> exists
    When the user <username> wants to create the chat <chatname>
    Then the user <username> gets message <message>

    Examples: 
      | chatname  | username  | message               |
      | "GB"      | "JohnDoe" | "chat already exists" |
