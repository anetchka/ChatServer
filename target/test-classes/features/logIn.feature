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
Feature: Log in
  

  @Fisrt
  Scenario Outline: Successful login
    Given the username <username> exists in db
    When  the username <username> and password <password> match
    Then the user <username> is online
    
     Examples: 
      | username  | password   | 
      | "JohnDoe" |     "1234" |

  @Last
  Scenario Outline: Failed login
    Given the username <username> exists in db
    When  the username <username> and password <password> don't match
    Then the user <username> is offline

    Examples: 
      | username  | password   | 
      | "JohnDoe" |     "123" | 
