Feature: CoffeeMakerFeature

  In this feature, we are going to test the user stories and use cases for the CoffeeMaker
  Example.  We have provided a CoffeeMakerMain.java file that you can use to examine the
  modal behavior in the coffee maker and issue UI commands to use it, so that we can
  adequately test the user stories.

  Hints: to catch the mistakes, you might need to add out of range cases for
  recipes and amounts of ingredients.  Also, be sure to check machine state
  after running the user story:
  - Are the amounts of ingredients correct?
  - Is the system in the right mode?
  - Is the status what you expect?
  - Is the change produced correct?

  Scenario: Set 1.1 - main menu / happy

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the coffee maker transitions into "ADD_RECIPE" mode
    And the current status of the coffee maker is "OK"


  Scenario: Set 1.2 - main menu / happy

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user has selected delete a recipe
    Then the coffee maker transitions into "DELETE_RECIPE" mode
    And the current status of the coffee maker is "OK"

  #noinspection CucumberMissedExamples
  Scenario Outline: Set 1.3 - main menu / happy

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user has selected edit a recipe
    Then the coffee maker transitions into "EDIT_RECIPE" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 1.4 - main menu / happy

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user has selected add inventory
    Then the coffee maker transitions into "ADD_INVENTORY" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 1.5 - main menu / happy

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user selects Check Inventory
    Then the coffee maker transitions into "CHECK_INVENTORY" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 1.6 - main menu / happy

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user has selected purchase coffee
    Then the coffee maker transitions into "PURCHASE_BEVERAGE" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 1.7 - main menu / error

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user selects a valid positive integer that isnt a menu option "<menuOption>"
    Then the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 1.8 - main menu / error

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user selects an invalid negative integer amount "-1"
    Then the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OK"


  Scenario: Set 1.9 - main menu / error

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user selects an invalid alpha character that is not a valid menu selection "a"
    Then the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 1.10 - main menu / error

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user selects an invalid decimal number that isnt a valid menu option "7.0"
    Then the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 2.1 - Add a Recipe - happy path

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add a recipe
    When the user enter valid values for the "50", "3", "1", "1", "0" and "Coffee"
    Then the recipe is added to the coffee maker
    And the list of available recipes now includes "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"

  Scenario: Set 2.2 - Add a Recipe, error path - recipe book is already full

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected add a recipe
    When the user enter valid values for the "60", "3", "3", "3", "0" and "Double Double"
    Then the recipe is not added to the coffee maker
    And the current status of the coffee maker is "RECIPE_NOT_ADDED"
    And the current mode of the coffee maker is "WAITING"

  Scenario: Set 2.3 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "a", "3", "1", "1", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.4 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "a", "1", "1", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.5 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "3", "a", "1", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.6 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "3", "1", "a", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.7 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "3", "1", "1", "a" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.8 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "2147483650", "3", "1", "1", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.9 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "2147483650", "1", "1", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.10 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "3", "2147483650", "1", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.11 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "3", "1", "2147483650", "0" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.12 - Add a Recipe, error path - invalid input

    Given an empty recipe book
    And the coffee maker is in waiting state
    When the user has selected add a recipe
    Then the user cannot add the recipe if invalid values are entered for the "50", "3", "1", "1", "2147483650" and "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_RECIPE"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 2.13 - Add a Recipe, error path - add the same recipe twice

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add a recipe
    And the user enter valid values for the "50", "3", "1", "1", "0" and "Coffee"
    And the recipe is added to the coffee maker
    And the list of available recipes now includes "Coffee"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    When the user tries to add the same recipe again "50", "3", "1", "1", "0" and "Coffee"
    Then the recipe cant be added the second time "Coffee"

  Scenario: Set 3.1 - Delete a Recipe, happy path

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected delete a recipe
    When the user elects to delete a valid recipe number "1"
    Then the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OK"
    And the list of available recipes does not include "Coffee"

  Scenario: Set 3.2 - Delete a Recipe, error path - invalid input

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected delete a recipe
    Then the user cannot delete the recipe if they enter a recipe number that is not valid "0"
    And the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OUT_OF_RANGE"

  Scenario: Set 3.3 - Delete a Recipe, error path - invalid input

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected delete a recipe
    Then the user cannot delete the recipe if they enter a recipe number that is not valid "6"
    And the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OUT_OF_RANGE"


  Scenario: Set 3.4 - Delete a Recipe, error path - invalid input

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected delete a recipe
    Then the user cannot delete the recipe if they enter a recipe number that is not valid "a"
    And the coffee maker transitions into "DELETE_RECIPE" mode
    And the current status of the coffee maker is "OK"

  Scenario: Set 3.5 - Delete a Recipe, error path - invalid input

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected delete a recipe
    Then the user cannot delete the recipe if they enter a recipe number that is not valid "-1"
    And the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OUT_OF_RANGE"

  Scenario: Set 3.6 - Delete a Recipe, error path - delete an empty recipe

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected delete a recipe
    Then the user cannot delete the recipe if they enter a recipe number that is not valid "1"
    And the coffee maker transitions into "WAITING" mode
    And the current status of the coffee maker is "OUT_OF_RANGE"

  Scenario: Set 4.1 - Edit a Recipe, happy path

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    And the user elects to edit a valid recipe number "1"
    And the user enters updated and valid values for the "65", "3", "1", "1" and "0"
    Then the recipe is successfully updated
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the price of the "Coffee" recipe is updated appropriately "65"

  Scenario: Set 4.2 - Edit a Recipe, happy path

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    And the user elects to edit a valid recipe number "1"
    And the user enters updated and valid values for the "50", "2", "1", "1" and "0"
    Then the recipe is successfully updated
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the coffee amount of the "Coffee" recipe is updated appropriately "2"

  Scenario: Set 4.3 - Edit a Recipe, happy path

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    And the user elects to edit a valid recipe number "1"
    And the user enters updated and valid values for the "50", "3", "2", "1" and "0"
    Then the recipe is successfully updated
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the sugar amount of the "Coffee" recipe is updated appropriately "2"

  Scenario: Set 4.4 - Edit a Recipe, happy path

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    And the user elects to edit a valid recipe number "1"
    And the user enters updated and valid values for the "50", "3", "1", "2" and "0"
    Then the recipe is successfully updated
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the milk amount of the "Coffee" recipe is updated appropriately "2"

  Scenario: Set 4.5 - Edit a Recipe, happy path

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    And the user elects to edit a valid recipe number "1"
    And the user enters updated and valid values for the "50", "3", "1", "1" and "1"
    Then the recipe is successfully updated
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the chocolate amount of the "Coffee" recipe is updated appropriately "1"

  Scenario: Set 4.6 - Edit a Recipe, error path - recipe number that doesnt exist (too high)

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    Then the user cannot edit a recipe if they enter a recipe number that is not valid "8"

  Scenario: Set 4.7 - Edit a Recipe, error path - recipe number that is empty

    Given a recipe book with an empty recipe in it (recipe has been deleted)
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    Then the user cannot edit a recipe if they enter a recipe number that is not valid "1"

  Scenario: Add Inventory - Set 5.1 Add Inventory - Happy Path, user enters valid amounts for all 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user can successfully update the inventory if they enter valid amounts for "20", "0", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the inventory of "20", "0", "0" and "0" has been updated

  Scenario: Add Inventory - Set 5.2 Add Inventory - Happy Path, user enters valid amounts for all 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user can successfully update the inventory if they enter valid amounts for "0", "20", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the inventory of "0", "20", "0" and "0" has been updated

  Scenario: Add Inventory - Set 5.3 Add Inventory - Happy Path, user enters valid amounts for all 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user can successfully update the inventory if they enter valid amounts for "0", "0", "20" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the inventory of "0", "0", "20" and "0" has been updated

  Scenario: Add Inventory - Set 5.4 Add Inventory - Happy Path, user enters valid amounts for all 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user can successfully update the inventory if they enter valid amounts for "0", "0", "0" and "20"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"
    And the inventory of "0", "0", "0" and "20" has been updated

  Scenario: Add Inventory - Set 5.5 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "a", "0", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated


  Scenario: Add Inventory - Set 5.6 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "0", "a", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.7 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "0", "0", "a" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.8 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "0", "0", "0" and "a"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.9 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "1.5", "0", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.10 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "0", "1.5", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.11 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "0", "0", "1.5" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.12 Add Inventory - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (non-integer) amounts for one of "0", "0", "0" and "1.5"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.13 Add Inventory - Error path, user enters invalid (-ve integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (-ve integer) amounts for one of "-1", "0", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.14 Add Inventory - Error path, user enters invalid (-ve integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (-ve integer) amounts for one of "0", "-1", "0" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.15 Add Inventory - Error path, user enters invalid (-ve integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (-ve integer) amounts for one of "0", "0", "-1" and "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Add Inventory - Set 5.16 Add Inventory - Error path, user enters invalid (-ve integer) amounts for some of the 4 ingredients

    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add inventory
    Then the user cannot update the inventory if they enter invalid (-ve integer) amounts for one of "0", "0", "0" and "-1"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "ADD_INVENTORY"
    And the inventory has not been updated

  Scenario: Set 6.1 - Check Inventory - Happy Path

    Given a default recipe book
    And the coffee maker is in waiting state
    When the user selects Check Inventory
    Then the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "CHECK_INVENTORY"

  Scenario: Set 7.1 - Purchase Beverage - Happy Path - exact change coffee

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected purchase coffee
    And the user has provided exact payment "50"
    When the user has chosen to purchase valid recipe number "1"
    Then the inventory should have been decremented appropriately "1"
    And there should be no change provided "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"

  Scenario: Set 7.2 - Purchase Beverage - Happy Path - exact change hot chocolate

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected purchase coffee
    And the user has provided exact payment "100"
    When the user has chosen to purchase valid recipe number "3"
    Then the inventory should have been decremented appropriately "3"
    And there should be no change provided "0"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"

  Scenario: Set 7.3 - Purchase Beverage - Happy Path - pays more than the cost (owed change)

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected purchase coffee
    And the user pays more than the cost of the beverage "60"
    When the user has chosen to purchase valid recipe number "1"
    Then the inventory should have been decremented appropriately "1"
    And there should be change provided "10"
    And the current status of the coffee maker is "OK"
    And the current mode of the coffee maker is "WAITING"

  Scenario: Set 7.5 - Purchase Beverage - Error Path - insufficient ingredients

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected purchase coffee
    And the user pays more than the cost of the beverage "80"
    When the user has chosen to purchase valid recipe number "2"
    Then the inventory should not be decremented
    And the payment should be refunded "0"
    And the current status of the coffee maker is "INSUFFICIENT_FUNDS"
    And the current mode of the coffee maker is "WAITING"

  Scenario: Set Inventory - Set 5.17 Set Inventory - Error path, user enters invalid (-ve integer) amounts for coffee

    When the user enters "-1" for the amount of coffee the inventory is not updated

  Scenario: Set Inventory - Set 5.18 Set Inventory - Error path, user enters invalid (-ve integer) amounts for sugar

    When the user enters "-1" for the amount of sugar the inventory is not updated

  Scenario: Set Inventory - Set 5.19 Set Inventory - Error path, user enters invalid (-ve integer) amounts for milk

    When the user enters "-1" for the amount of milk the inventory is not updated

  Scenario: Set Inventory - Set 5.20 Set Inventory - Error path, user enters invalid (-ve integer) amounts for chocolate

    When the user enters "-1" for the amount of chocolate the inventory is not updated

  Scenario: Set Inventory - Set 5.21 Set Inventory - Error path, user select recipe for which there arent enough ingredients (coffee)

    When the user selects a recipe for for which there isnt enough coffee false should be returned

  Scenario: Set Inventory - Set 5.22 Set Inventory - Error path, user select recipe for which there arent enough ingredients (sugar)

    When the user selects a recipe for for which there isnt enough sugar false should be returned

  Scenario: Set Inventory - Set 5.23 Set Inventory - Error path, user select recipe for which there arent enough ingredients (milk)

    When the user selects a recipe for for which there isnt enough milk false should be returned

  Scenario: Set Inventory - Set 5.24 Set Inventory - Error path, user select recipe for which there arent enough ingredients (chocolate)

    When the user selects a recipe for for which there isnt enough chocolate false should be returned

  Scenario: Add Sugar - Set 5.51 Add Sugar - Error path, user enters invalid (non-integer) amount

    When a user enters a non-int value for the amount of Coffee to add "a" an Inventory Exception will be thrown

  Scenario: Add Sugar - Set 5.61 Add Sugar - Error path, user enters invalid (non-integer) amount

    When a user enters a non-int value for the amount of Sugar to add "a" an Inventory Exception will be thrown

  Scenario: Add Milk - Set 5.71 Add Milk - Error path, user enters invalid (non-integer) amount

    When a user enters a non-int value for the amount of Milk to add "a" an Inventory Exception will be thrown

  Scenario: Add Chocolate - Set 5.81 Add Chocolate - Error path, user enters invalid (non-integer) amounts for some of the 4 ingredients

    When a user enters a non-int value for the amount of Chocolate to add "a" an Inventory Exception will be thrown

  Scenario: Set 8.1 - Create a Recipe - Error Path - User enters negative integer for amount of chocolate

    When the user tries to create a recipe with a -ve int as the amount of chocolate, it will fail

  Scenario: Set 8.2 - Create a Recipe - Error Path - User enters negative integer for amount of coffee

    When the user tries to create a recipe with a -ve int as the amount of coffee, it will fail

  Scenario: Set 8.3 - Create a Recipe - Error Path - User enters negative integer for amount of sugar

    When the user tries to create a recipe with a -ve int as the amount of sugar, it will fail

  Scenario: Set 8.4 - Create a Recipe - Error Path - User enters negative integer for amount of milk

    When the user tries to create a recipe with a -ve int as the amount of milk, it will fail

  Scenario: Set 8.5 - Create a Recipe - Error Path - User enters negative integer for price

    When the user tries to create a recipe with a -ve int as the price, it will fail

  Scenario: Set 8.6 - Create a Recipe - Error Path - User enters null value for the name of the recipe

    When the user tries to update a recipe with null as the name, it will fail

  Scenario: Set 8.7 - Create a Recipe - Happy Path - Recipe hashcode method

    When the recipe hashcode method is used, it should always returns the same result for a given recipe

  Scenario: Set 8.8 - Create a Recipe - Happy Path - Recipe Equals

    When two recipes that are the same are compared using the equals method, it should be successful

  Scenario: Set 8.9 - Create a Recipe - Error Path - Recipe Equals

    When two recipes that are not the same are compared using the equals method, it should fail

  Scenario: Set 8.10 - Create a Recipe - Happy Path - Recipe toString

    When the toString command is called on a Recipe, the name is returned correctly

  Scenario: Set 8.11 - Create a Recipe - Happy Path - Recipe Equals

    When a recipe and an inventory are compared using the equals method, it should fail

  Scenario: Set 7.7 - Purchase Beverage - Error Path - insufficient payment

    Given a default recipe book
    When the user attempts to make coffee with insufficient payment, it will fail and their payment will be returned

  Scenario: Set 7.9	- Purchase Beverage - Error Path - payment not positive integer
    Given a default recipe book
    When the user attempts to make coffee with payment that is not a pos int, it will fail

  Scenario: Set 9.1	- CoffeeMakerUI - Happy Path - get recipes
    Given a default recipe book
    When getRecipes is called, a valid list of recipes is returned

  Scenario: Set 9.2	- CoffeeMakerUI - Error Path - add recipe
    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add a recipe
    When the coffee maker is in add recipe mode and an object is submitted that is not describe recipe, it should fail

  Scenario: Set 9.3	- CoffeeMakerUI - Happy Path - reset
    Given an empty recipe book
    And the coffee maker is in waiting state
    And the user has selected add a recipe
    When the coffee maker is reset, it should return to waiting mode

  Scenario: Set 9.4 - Edit a Recipe, error path

    Given a default recipe book
    And the coffee maker is in waiting state
    And the user has selected edit a recipe
    And the user elects to edit a valid recipe number "1"
    When the user attempts to update the recipe with a null recipe, it should fail

  Scenario: Set 9.5 - Edit a Recipe, error path

    Given a default recipe book
    When the user attempts to edit a recipe that doesnt exist - less than zero - it should fail

  Scenario: Set 9.8 - Edit a Recipe, error path

    Given a default recipe book
    When the user displays recipes it should succeed

