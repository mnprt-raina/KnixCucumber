Feature: Add to Bag
 
Scenario: Unable to Add to bag
Given User launches KNIX application
Then User clicks promopop
Then User clicks addtobag
Then Validate user is unable to add item to the bag


Scenario: Able to Add to bag
Given User launches KNIX application
Then User clicks promopop
Then User clicks sizedropdown
Then User clicks addtobag
Then Validate user is unable to add item to the bag