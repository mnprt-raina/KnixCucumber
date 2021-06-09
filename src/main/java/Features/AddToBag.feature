Feature: Add to Bag
 
Scenario: Unable to Add to bag
Given User launches KNIX application on chrome
Then User clicks promopop
Then Validate user is unable to add item to the bag 


Scenario: Able to Add to bag
Given User launches KNIX application on edge
Then User clicks promopop
Then Validate user is able to add dataitem to the bag