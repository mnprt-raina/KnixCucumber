Feature: Out Of Stock check
 
 
Scenario: User is unable to add any out of stock limited edition color to cart
Given User launches KNIX application on chrome
Then User clicks promopop
Then Validate limitedcolours for all fittingsize
