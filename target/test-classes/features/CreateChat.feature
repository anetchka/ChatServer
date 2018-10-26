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
Feature: Create chat
  A user can create a chat
  
  @First 
  Scenario Outline: Chat doesn't exist in user's list
    Given the chat name <chatname> is not in user's list <username>
    When the user <username> wants to create the chat <chatname>
    Then the user <username> creates the chat <chatname>
    And the user <username> is the admin of the chat <chatname>
    
    Examples: 
      | chatname  | username  | 
      | "GB"      | "AdamSmith" | 

  @Last
  Scenario Outline: Chat exists in user's list
    Given the chat name <chatname> is in  user's list <username>
    When the user <username> wants to create the chat <chatname>
    Then the user <username> is asked to use another chatname <anotherchatname>

    Examples: 
      | chatname  | username  | anotherchatname |
      | "GB"      | "AdamSmith" | "AT"            |
