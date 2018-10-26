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
Feature: The user sends a message
  I want to use this template for my feature file

  @First
  Scenario Outline: Message is successfully sent
    Given the username <username> has chat <chatname>
    When the user <username> sends message <message> to the chat <chatname>
    Then the message <message> is added to username's <username> chat <chatname> history
    And the message<message> is sent successfully to chat <chatname> of user <username>
    
        Examples: 
      | username  | chatname              | message      |
      | "JohnDoe" | "SemesterColsing"     | "Let's meet" |
    

  @Last
  Scenario Outline: Message sending failed
    Given the username <username> doesn't have chat <chatname>
    When the user <username> sends message <message> to the chat <chatname>
    Then the message<message> is failed to be sent to chat <chatname> of user <username>

    Examples: 
      | username  | chatname              | message      |
      | "JohnDoe" | "SemesterColsing"     | "Let's meet" |
