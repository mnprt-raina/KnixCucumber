Feature: Size shown below image
 
Scenario: Size shown below image
Given User launches KNIX application
Then User clicks promopop
Then User clicks sizedropdown
Then User validates fittingsize from the sizedropdown with imgcaption

#Examples:
#|Size|
# | size 1 | Fits 32A, 34A, 32B |
# | size 2 | Fits 36A, 34B, 32C |
# | size 3 | Fits 36B, 38B, 36C |
# | size 4 | Fits 34C, 32D, 34D |
# | size 5 | Fits 38C, 40C, 36D, 38D |
# | size 6 | Fits 32DD, 34DD, 32E, 34E |
# | size 6+| Fits 32F, 34F, 32G, 34G |
# | size 7 | Fits 36DD, 38DD, 36E, 38E |
#| size 7+| Fits 36F, 38F, 36G, 38G |
# | size 8 | Fits 40D, 42D, 40DD, 42DD, 40E, 42E |
# | size 8+|                                     |
    