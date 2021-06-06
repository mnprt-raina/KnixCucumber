Feature: Size sort
 
Scenario: Size sort
Given User launches KNIX application
Then User clicks promopop
Then User clicks sizedropdown
Then User validates if buttontitlevalues in the sizelist is in a sorted order 