@AirBnbAutomation
Feature: Automate AirBnb Website

  @WishListValidation
  Scenario Outline: Validate that the wishlist functionality is working as expected
    Given Goto airbnb website
    Then login on the website with "<Email>" and "<Password>" and change currency to euro
    Then Search for an "<Type>" in "<City>" in the month of "<Month>" with "<NumberOfAdults>" adults "<NumberOfKids>" kids and "<NumberOfPets>" pet costs uptos "<Cost>" euro
    Then look at first listing view pictures and cancellation policy and add to wishlist
    Then look at second listing view pictures and cancellation policy and add that to wishlist
    Then look at thrid listing view pictures and cancellation policy and add that to wishlist
    Then validate that 3 listings are available in wishlist
    #Additonal Step to remove wishlist items to re-run the test cases multiple times with no wishlist items saved before
    Then remove all items from wishlist
    Then logout from the application

    Examples: 
      | Email                   | Password | Type         | City      | Month | NumberOfAdults | NumberOfKids | NumberOfPets | Cost |
      | phanipramoddk@gmail.com | Q1!qqqqq | Entire place | Barcelona | July  |              2 |            2 |            1 | 400 |
