/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modified 20171114 by Ian De Silva -- Updated to adhere to coding standards.
 *
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc326.coffeemaker.UICmd.AddInventory;
import edu.ncsu.csc326.coffeemaker.UICmd.ChooseRecipe;
import edu.ncsu.csc326.coffeemaker.UICmd.ChooseService;
import edu.ncsu.csc326.coffeemaker.UICmd.DescribeRecipe;
import edu.ncsu.csc326.coffeemaker.UICmd.InsertMoney;
import edu.ncsu.csc326.coffeemaker.UICmd.Reset;
import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Contains the step definitions for the cucumber tests.  This parses the
 * Gherkin steps and translates them into meaningful test steps.
 */

//Pasos de prueba
public class TestSteps {

    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;
    private Recipe recipe5;
    private Recipe recipe6 = new Recipe();
    private Recipe recipe7 = new Recipe();
    private CoffeeMakerUI coffeeMakerMain;
    private CoffeeMaker coffeeMaker;
    private RecipeBook recipeBook;


    private void initialize() {
        recipeBook = new RecipeBook();
        coffeeMaker = new CoffeeMaker(recipeBook, new Inventory());
        coffeeMakerMain = new CoffeeMakerUI(coffeeMaker);
    }
    //un libro de recetas vacío
    @Given("^an empty recipe book$")
    public void an_empty_recipe_book() throws Throwable {
        initialize();
    }
    //un libro de recetas sin recetas
    @Given("^a recipe book with no recipes$")
    public void a_recipe_book_with_no_recipes() throws Throwable {
        initialize();
    }
    //un libro de recetas predeterminado
    @Given("^a default recipe book$")
    public void a_default_recipe_book() throws Throwable {
        initialize();

        //Set up for r1 - Receta 1 (default)
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe1.setAmtChocolate("0");
        recipe1.setAmtCoffee("3");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setPrice("50");

        //Set up for r2 - Receta 2 (default)
        recipe2 = new Recipe();
        recipe2.setName("Mocha");
        recipe2.setAmtChocolate("20");
        recipe2.setAmtCoffee("3");
        recipe2.setAmtMilk("1");
        recipe2.setAmtSugar("1");
        recipe2.setPrice("75");

        //Set up for r3 - Receta 3 (default)
        recipe3 = new Recipe();
        recipe3.setName("Latte");
        recipe3.setAmtChocolate("0");
        recipe3.setAmtCoffee("3");
        recipe3.setAmtMilk("3");
        recipe3.setAmtSugar("1");
        recipe3.setPrice("100");

        //Set up for r4 - Receta 4 (default)
        recipe4 = new Recipe();
        recipe4.setName("Hot Chocolate");
        recipe4.setAmtChocolate("4");
        recipe4.setAmtCoffee("0");
        recipe4.setAmtMilk("1");
        recipe4.setAmtSugar("1");
        recipe4.setPrice("65");

        //Set up for r5 - Receta 5 agregada para prueba
        recipe5 = new Recipe();
        recipe5.setName("Super Hot Chocolate");
        recipe5.setAmtChocolate("6");
        recipe5.setAmtCoffee("0");
        recipe5.setAmtMilk("1");
        recipe5.setAmtSugar("1");
        recipe5.setPrice("100");

        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        recipeBook.addRecipe(recipe3);
        recipeBook.addRecipe(recipe4);
    }

    //Un libro de recetas con una receta vacía (la receta ha sido eliminada)
    @Given("^a recipe book with an empty recipe in it \\(recipe has been deleted\\)$")
    public void a_recipe_book_with_an_empty_recipe_in_it_recipe_has_been_deleted() throws Throwable {
        initialize();
        //create a new recipe - creamos la nueva receta
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe1.setAmtChocolate("0");
        recipe1.setAmtCoffee("3");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setPrice("50");
        //add the new recipe - Agregamos la nueva receta
        coffeeMakerMain.UI_Input(new ChooseService(1));
        DescribeRecipe describeRecipe1 = new DescribeRecipe(recipe1);
        coffeeMakerMain.UI_Input(describeRecipe1);
        //delete the recipe at [0] - eliminamos la receta en [0]
        coffeeMakerMain.UI_Input(new ChooseService(2));
        int recipeNumber = 0;
        coffeeMakerMain.UI_Input(new ChooseRecipe(recipeNumber));
    }
    //La cafetera está en estado de espera
    @And("^the coffee maker is in waiting state$")
    public void the_coffee_maker_is_in_waiting_state() throws Throwable {
        CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
        assertEquals(currentMode, CoffeeMakerUI.Mode.WAITING);
    }
    //El usuario selecciona un entero positivo válido que no es una opción de menú - excepcion "NumberFormatException"
    @When("^the user selects a valid positive integer that isnt a menu option \"([^\"]*)\"$")
    public void the_user_selects_a_valid_positive_integer_that_isnt_a_menu_option(String menuOption) throws Throwable {
        int menuOptionNum;
        //boolean success = true;
        try {
            menuOptionNum = Integer.parseInt(menuOption);
            coffeeMakerMain.UI_Input(new ChooseService(menuOptionNum));
        }
        catch(NumberFormatException e) {
            //success = false;
        }

    }
    //El usuario selecciona una cantidad entera negativa no válida
    @When("^the user selects an invalid negative integer amount \"([^\"]*)\"$")
    public void the_user_selects_an_invalid_negative_integer_amount(String menuOption) throws Throwable {
        int menuOptionNum;
        try {
            menuOptionNum = Integer.parseInt(menuOption);
            coffeeMakerMain.UI_Input(new ChooseService(menuOptionNum));
        }
        catch(NumberFormatException e) {
        }
    }
    //El usuario selecciona un carácter alfabético no válido que no es una selección de menú válida
    @When("^the user selects an invalid alpha character that is not a valid menu selection \"([^\"]*)\"$")//
    public void the_user_selects_an_invalid_alpha_character_that_is_not_a_valid_menu_selection(String menuOption) throws Throwable {
        int menuOptionNum;
        boolean success = true;
        try {
            menuOptionNum = Integer.parseInt(menuOption);
            coffeeMakerMain.UI_Input(new ChooseService(menuOptionNum));
        }
        catch(NumberFormatException e) {
            success = false;
        }
        assertFalse(success);
    }
    //El usuario selecciona un número decimal no válido que no es una opción de menú válida
    @When("^the user selects an invalid decimal number that isnt a valid menu option \"([^\"]*)\"$")//
    public void the_user_selects_an_invalid_decimal_number_that_isnt_a_valid_menu_option(String menuOption) throws Throwable {
        int menuOptionNum;
        boolean success = true;
        try {
            menuOptionNum = Integer.parseInt(menuOption);
            coffeeMakerMain.UI_Input(new ChooseService(menuOptionNum));
        }
        catch(NumberFormatException e) {
            success = false;
        }
        assertFalse(success);
    }

    //La usuario selecciona comprobar inventario
    @Given("the user selects Check Inventory")
    public void the_user_selects_menu_option() throws Throwable{
        int menuOptionNumber = 5;
        coffeeMakerMain.UI_Input(new ChooseService(menuOptionNumber));
    }
    //El usuario ha seleccionado añadir una receta
    @When("the user has selected add a recipe")
    public void the_user_selects_add_a_recipe() throws Throwable{
        coffeeMakerMain.UI_Input(new ChooseService(1));
    }
    //El usuario selecciona eliminar una receta
    @When("the user has selected delete a recipe")
    public void the_user_selects_delete_a_recipe() throws Throwable{
        coffeeMakerMain.UI_Input(new ChooseService(2));
    }
    //El usuario selecciona editar una receta
    @Given("^the user has selected edit a recipe$")
    public void the_user_has_selected_edit_a_recipe() throws Throwable {
        coffeeMakerMain.UI_Input(new ChooseService(3));
    }
    //El usuario ha seleccionado agregar inventario
    @Given("^the user has selected add inventory$")//
    public void the_user_has_selected_add_inventory() throws Throwable {
        coffeeMakerMain.UI_Input(new ChooseService(4));

    }
    //El usuario ha seleccionado comprar café.
    @Given("^the user has selected purchase coffee$")//
    public void the_user_has_selected_purchase_coffee() throws Throwable {
        coffeeMakerMain.UI_Input(new ChooseService(6));
    }
    //El usuario ha elegido comprar un número de receta válido
    @Given("^the user has chosen to purchase valid recipe number \"([^\"]*)\"$")//
    public void the_user_has_chosen_to_purchase_recipe_number(String recipe) throws Throwable {
        int recipeNum;
        Boolean success = true;
        ChooseRecipe chooseRecipe;
        try {
            recipeNum = Integer.parseInt(recipe) - 1;
            chooseRecipe = new ChooseRecipe(recipeNum);
            coffeeMakerMain.UI_Input(chooseRecipe);
        }
        catch(NumberFormatException e) {
            success = false;
        }
        assertTrue(success);
    }
    //El usuario ha proporcionado el pago exacto.
    @Given("^the user has provided exact payment \"([^\"]*)\"$")//
    public void the_user_has_provided_sufficient(String payment) throws Throwable {
        int paymentNum;
        boolean success = true;
        InsertMoney insertMoney;
        try {
            paymentNum = Integer.parseInt(payment);
            insertMoney = new InsertMoney(paymentNum);
            coffeeMakerMain.defaultCommands(insertMoney);
            assertTrue(coffeeMakerMain.getMoneyInserted() == paymentNum);

        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertTrue(success);
    }
    //El usuario paga más que el costo de la bebida
    @Given("^the user pays more than the cost of the beverage \"([^\"]*)\"$")//
    public void the_user_pays_more_than_the_cost_of_the_beverage(String payment) throws Throwable {
        int paymentNum;
        boolean success = true;
        InsertMoney insertMoney;
        try {
            paymentNum = Integer.parseInt(payment);
            insertMoney = new InsertMoney(paymentNum);
            coffeeMakerMain.defaultCommands(insertMoney);
            assertTrue(coffeeMakerMain.getMoneyInserted() == paymentNum);

        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertTrue(success);
    }
    //El usuario no ha realizado el pago suficiente
    @Given("^the user has not provided sufficient payment \"([^\"]*)\"$")//
    public void the_user_has_not_provided_sufficient_payment(String payment) throws Throwable {
        int paymentNum;
        boolean success = true;
        InsertMoney insertMoney;
        try {
            paymentNum = Integer.parseInt(payment);
            insertMoney = new InsertMoney(paymentNum);
            coffeeMakerMain.defaultCommands(insertMoney);
            assertTrue(coffeeMakerMain.getMoneyInserted() == paymentNum);

        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertTrue(success);
    }
    //Debe haber cambio proporcionado
    @Then("^there should be change provided \"([^\"]*)\"$")//
    public void there_should_be_change_provided(String expChange) throws Throwable {
        int expChangeNum;
        int actChange;
        boolean success = true;
        try {
            expChangeNum = Integer.parseInt(expChange);
            actChange = coffeeMakerMain.moneyInTray;
            assertTrue(expChangeNum == actChange);
        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertTrue(success);
    }
    //No deberia haber cambio proporcionado
    @When("^there should be no change provided \"([^\"]*)\"$")//
    public void there_should_be_no_change_provided(String expChange) throws Throwable {
        int expChangeNum;
        int actChange;
        boolean success = true;
        try {
            expChangeNum = Integer.parseInt(expChange);
            actChange = coffeeMakerMain.moneyInTray;
            assertTrue(expChangeNum == actChange);
        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertTrue(success);
    }
    //El pago debe ser reembolsado
    @Then("^the payment should be refunded \"([^\"]*)\"$")//
    public void the_payment_should_be_refunded(String expChange) throws Throwable {
        int expChangeNum;
        int actChange;
        boolean success = true;
        try {
            expChangeNum = Integer.parseInt(expChange);
            actChange = coffeeMakerMain.moneyInTray;
            assertTrue(expChangeNum == actChange);
        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertTrue(success);

    }
    //La cafetera cambia al modo "--"
    @Then("the coffee maker transitions into \"([^\"]*)\" mode")//
    public void the_coffee_maker_transitions_into_correct_mode(String arg1) throws Throwable {
        CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
        assertEquals(currentMode.toString(), arg1);
    }
    //El usuario ingresa valores válidos para "PRECIO" , "CAFE", AZUCAR","LECHE","CHOCOLATE" y "NOMBRE" (RECETA)
    @When("^the user enter valid values for the \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")//
    public void the_user_enter_valid_values_for_the_and(String price, String coffee, String sugar, String milk, String chocolate, String name) throws Throwable {
        recipe6.setPrice(price);
        recipe6.setAmtCoffee(coffee);
        recipe6.setAmtSugar(sugar);
        recipe6.setAmtMilk(milk);
        recipe6.setAmtChocolate(chocolate);
        recipe6.setName(name);

    }
    //El usuario inteta agregar la misma receta con los valores de "PRECIO" , "CAFE", AZUCAR","LECHE","CHOCOLATE" y "NOMBRE" (RECETA)
    @When("^the user tries to add the same recipe again \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")//
    public void the_user_tries_to_add_the_same_recipe_again_and(String price, String coffee, String sugar, String milk, String chocolate, String name) throws Throwable {
        recipe7.setPrice(price);
        recipe7.setAmtCoffee(coffee);
        recipe7.setAmtSugar(sugar);
        recipe7.setAmtMilk(milk);
        recipe7.setAmtChocolate(chocolate);
        recipe7.setName(name);
    }
    //El usuario ingresa valores actualizados y validos para la receta
    @Given("^the user enters updated and valid values for the \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")//
    public void the_user_enters_updated_and_valid_values_for_the_and(String price, String coffee, String sugar, String milk, String chocolate) throws Throwable {
        recipe1.setPrice(price);
        recipe1.setAmtCoffee(coffee);
        recipe1.setAmtSugar(sugar);
        recipe1.setAmtMilk(milk);
        recipe1.setAmtChocolate(chocolate);
    }
    //El usuario selecciona eliminar un número de la receta válida "
    @When ("^the user elects to delete a valid recipe number \"([^\"]*)\"$")//
    public void the_user_elects_to_delete(String recipe)throws Throwable {
        try{
            // No se puede llamar al metodo RecipeListSelection porque es privado
            // Can't call recipeListSelection method because it's private

            // Convertir el numero de seleccion del menu en un indice de matriz de recetas
            // Convert menu selection number to recipeBook array index
            int recipeNumber = Integer.parseInt(recipe) -1;
            coffeeMakerMain.UI_Input(new ChooseRecipe(recipeNumber));
        }
        catch (NumberFormatException e){
        }
    }
    //El usuario elige editar un número de receta válido
    @Given("^the user elects to edit a valid recipe number \"([^\"]*)\"$")//
    public void the_user_elects_to_edit_a_valid_recipe_number(String recipe) throws Throwable {
        boolean success = true;
        try{
            // Convertir el número de selección del menú en un índice de matriz de recetario
            // Convert menu selection number to recipeBook array index

            // No se puede llamar al método recipeListSelection porque es privado
            // Can't call recipeListSelection method because it's private
            int recipeNumber = Integer.parseInt(recipe) -1;
            coffeeMakerMain.UI_Input(new ChooseRecipe(recipeNumber));
            CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
            if(!currentMode.toString().equals("EDIT_RECIPE")){
                success = false;
            }
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            success = false;
        }
        assertTrue(success);
    }
    //el usuario no puede editar una receta si ingresa un número de receta que no es válido
    @Then("^the user cannot edit a recipe if they enter a recipe number that is not valid \"([^\"]*)\"$")//
    public void the_user_cannot_edit_a_recipe_if_they_enter_a_recipe_number_that_is_not_valid(String recipe) throws Throwable {
        boolean success = true;
        try{
            // Convierte el número de selección del menú en un índice de matriz de recetario
            // No se puede llamar al método recipeListSelection porque es privado
            // Convert menu selection number to recipeBook array index
            // Can't call recipeListSelection method because it's private
            int recipeNumber = Integer.parseInt(recipe) -1;
            coffeeMakerMain.UI_Input(new ChooseRecipe(recipeNumber));
            CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
            if(currentStatus.toString().equals("OUT_OF_RANGE")){
                success = false;
            }

        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            success = false;
        }
        assertFalse(success);
    }
    //el usuario no puede editar una receta si intenta cambiar el "-" de la receta
    @Then("^the user cannot edit a recipe if they try to change the \"([^\"]*)\" of the recipe \"([^\"]*)\"$")//
    public void the_user_cannot_edit_a_recipe_if_they_try_to_change_the_of_the_recipe(String name, String recipe) throws Throwable {
        boolean success = true;
        recipe1.setName(name);
        CoffeeMakerUI.Status currentStatus;
        CoffeeMakerUI.Mode currentMode;
        try{
            // convierte el número de selección del menú en un índice de matriz de recetario
            // no se puede llamar al método recipeListSelection porque es privado
            // convert menu selection number to recipeBook array index
            // can't call recipeListSelection method because it's private
            int recipeNumber = Integer.parseInt(recipe) - 1;
            currentStatus = coffeeMakerMain.getStatus();
            assertEquals(currentStatus.toString(), "OK");
            currentMode = coffeeMakerMain.getMode();
            assertEquals(currentMode.toString(), "EDIT_RECIPE");
            coffeeMakerMain.UI_Input(new ChooseRecipe(recipeNumber));
            DescribeRecipe describeRecipe1 = new DescribeRecipe(recipe1);
            coffeeMakerMain.UI_Input(describeRecipe1);
            currentStatus = coffeeMakerMain.getStatus();
            if(currentStatus.toString().equals("RECIPE_NOT_ADDED")) {
                success = false;
            }
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            success = false;
        }
        assertFalse(success);
    }
    //el usuario no puede borrar la receta si ingresa un número de receta que no es válido
    @Then("^the user cannot delete the recipe if they enter a recipe number that is not valid \"([^\"]*)\"$")//
    public void the_user_elects_to_delete_recipe_number_that_is_not_valid(String recipe) throws Throwable {
        boolean success = true;
        try{
            // convierte el número de selección del menú en un índice de matriz de recetario
            // no se puede llamar al método recipeListSelection porque es privado
            // convert menu selection number to recipeBook array index
            // can't call recipeListSelection method because it's private
            int recipeNumber = Integer.parseInt(recipe) -1;
            coffeeMakerMain.UI_Input(new ChooseRecipe(recipeNumber));
            CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
            if(currentMode.toString() != "OK") {
                success = false;
            }
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            success = false;

        }
        assertFalse(success);
    }
    //el usuario no puede agregar la receta si se ingresan valores no válidos para "-", "-", "-", "-", "-" y "-" (receta)
    @Then("^the user cannot add the recipe if invalid values are entered for the \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")//
    public void the_user_enters_at_least_one_invalid_value_for_the_and(String price, String coffee, String sugar, String milk, String chocolate, String name) throws Throwable {
        boolean success = true;
        try {
            recipe6.setPrice(price);
            recipe6.setAmtCoffee(coffee);
            recipe6.setAmtSugar(sugar);
            recipe6.setAmtMilk(milk);
            recipe6.setAmtChocolate(chocolate);
            recipe6.setName(name);

        } catch (RecipeException e) {
            success = false;
        }
        assertFalse(success);
    }
    //la receta no se puede agregar la segunda vez
    @Then("^the recipe can't be added the second time$")//
    public void the_recipe_can_t_be_added_the_nd_time() throws Throwable {


    }
    //el usuario no puede actualizar el inventario si ingresa cantidades \ (no enteras \) inválidas para uno de "-", "-", "-" y "-"
    @Then("^the user cannot update the inventory if they enter invalid \\(non-integer\\) amounts for one of \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")//
    public void the_user_cannot_update_the_inventory_if_they_enter_invalid_non_integer_amounts_for_one_of_and(String coffee, String sugar, String milk, String chocolate) throws Throwable {
        Boolean success = true;
        AddInventory addInventory;
        try{
            addInventory = new AddInventory(Integer.parseInt(coffee),
                    Integer.parseInt(milk),
                    Integer.parseInt(sugar),
                    Integer.parseInt(chocolate));
            coffeeMakerMain.UI_Input(addInventory);
        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertFalse(success);

    }
    //el usuario no puede actualizar el inventario si ingresa cantidades no válidas (enterOs) para uno de "-", "-", "-" y "-"
    @Then("^the user cannot update the inventory if they enter invalid \\(-ve integer\\) amounts for one of \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")//
    public void the_user_cannot_update_the_inventory_if_they_enter_invalid_ve_integer_amounts_for_one_of_and(String coffee, String sugar, String milk, String chocolate) throws Throwable {
        Boolean success = true;
        try{
            coffeeMaker.addInventory(coffee, milk, sugar, chocolate);
        }
        catch (NumberFormatException | InventoryException e) {
            success = false;
        }
        assertFalse(success);
    }
    //un usuario ingresa un valor no entero para la cantidad de café para agregar "-" se lanzará una excepción de inventario
    @When("^a user enters a non-int value for the amount of Coffee to add \"([^\"]*)\" an Inventory Exception will be thrown$")//
    public void a_user_enters_a_non_int_value_for_the_amount_of_Coffee_to_add_an_Inventory_Exception_will_be_thrown(String coffee) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        try {
            inventory.addCoffee(coffee);
        }
        catch (InventoryException e) {
            success = false;
        }
        assertFalse(success);
    }
    //un usuario ingresa un valor no entero para la cantidad de azúcar para agregar "-" se lanzará una excepción de inventario
    @When("^a user enters a non-int value for the amount of Sugar to add \"([^\"]*)\" an Inventory Exception will be thrown$")//
    public void a_user_enters_a_non_int_value_for_the_amount_of_Sugar_to_add_an_Inventory_Exception_will_be_thrown(String sugar) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        try {
            inventory.addSugar(sugar);
        }
        catch (InventoryException e) {
            success = false;
        }
        assertFalse(success);
    }
    //un usuario ingresa un valor no entero para la cantidad de leche para agregar "-" se lanzará una excepción de inventario
    @When("^a user enters a non-int value for the amount of Milk to add \"([^\"]*)\" an Inventory Exception will be thrown$")//
    public void a_user_enters_a_non_int_value_for_the_amount_of_Milk_to_add_an_Inventory_Exception_will_be_thrown(String milk) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        try {
            inventory.addMilk(milk);
        }
        catch (InventoryException e) {
            success = false;
        }
        assertFalse(success);
    }
    //un usuario ingresa un valor no entero para la cantidad de chocolate para agregar "-" se lanzará una excepción de inventario
    @When("^a user enters a non-int value for the amount of Chocolate to add \"([^\"]*)\" an Inventory Exception will be thrown$")//
    public void a_user_enters_a_non_int_value_for_the_amount_of_Chocolate_to_add_an_Inventory_Exception_will_be_thrown(String chocolate) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        try {
            inventory.addChocolate(chocolate);
        }
        catch (InventoryException e) {
            success = false;
        }
        assertFalse(success);
    }
    //la receta se ha añadido correctamente a la cafetera
    @Then("^the recipe is successfully added to the coffee maker$")//
    public void the_recipe_is_successfully_added_to_the_coffee_maker() throws Throwable {
        DescribeRecipe describeRecipe6 = new DescribeRecipe(recipe6);
        coffeeMakerMain.UI_Input(describeRecipe6);
    }
    //el usuario puede actualizar correctamente el inventario si ingresa cantidades válidas para cafe, azucar,leche y chocolate
    @Then("^the user can successfully update the inventory if they enter valid amounts for \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")//
    public void the_user_can_successfully_update_the_inventory_if_they_enter_valid_amounts_for_and(String coffee, String sugar, String milk, String chocolate) throws Throwable {
        boolean success = true;
        try{
            AddInventory addInventory = new AddInventory(Integer.parseInt(coffee),
                    Integer.parseInt(milk),
                    Integer.parseInt(sugar),
                    Integer.parseInt(chocolate));
            coffeeMakerMain.UI_Input(addInventory);
        }
        catch (NumberFormatException e){
            success = false;
        }
        assertTrue(success);

    }
    //el usuario ingresa "-" para la cantidad de café, el inventario no se actualiza
    @When("^the user enters \"([^\"]*)\" for the amount of coffee the inventory is not updated$")//
    public void the_user_enters_for_the_amount_of_coffee_the_inventory_is_not_updated(String coffee) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        int coffeeNum;
        try {
            coffeeNum = Integer.parseInt(coffee);
            inventory.setCoffee(coffeeNum);
            if(inventory.getCoffee() == 15) {
                success = false;
            }
        }
        catch (NumberFormatException e) {
        }
        assertFalse(success);
    }
    //el usuario ingresa "-" para la cantidad de azucar, el inventario no se actualiza
    @When("^the user enters \"([^\"]*)\" for the amount of sugar the inventory is not updated$")//
    public void the_user_enters_for_the_amount_of_sugar_the_inventory_is_not_updated(String sugar) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        int sugarNum;
        try {
            sugarNum = Integer.parseInt(sugar);
            inventory.setSugar(sugarNum);
            if(inventory.getSugar() == 15) {
                success = false;
            }
        }
        catch (NumberFormatException e) {
        }
        assertFalse(success);
    }
    //el usuario ingresa "-" para la cantidad de leche, el inventario no se actualiza
    @When("^the user enters \"([^\"]*)\" for the amount of milk the inventory is not updated$")//
    public void the_user_enters_for_the_amount_of_milk_the_inventory_is_not_updated(String milk) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        int milkNum;
        try {
            milkNum = Integer.parseInt(milk);
            inventory.setMilk(milkNum);
            if(inventory.getMilk() == 15) {
                success = false;
            }
        }
        catch (NumberFormatException e) {
        }
        assertFalse(success);
    }
    //el usuario ingresa "-" para la cantidad de chocolate, el inventario no se actualiza
    @When("^the user enters \"([^\"]*)\" for the amount of chocolate the inventory is not updated$")//
    public void the_user_enters_for_the_amount_of_chocolate_the_inventory_is_not_updated(String chocolate) throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        int chocolateNum;
        try {
            chocolateNum = Integer.parseInt(chocolate);
            inventory.setChocolate(chocolateNum);
            if(inventory.getChocolate() == 15) {
                success = false;
            }
        }
        catch (NumberFormatException e) {
        }
        assertFalse(success);
    }
    //el usuario selecciona una receta para la que no hay suficiente café debe devolverse falso
    @When("^the user selects a recipe for for which there isnt enough coffee false should be returned$")//
    public void the_user_selects_a_recipe_for_for_which_there_isnt_enough_coffee_false_should_be_returned() throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        Recipe recipe7 = new Recipe();
        recipe7.setAmtCoffee("20");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("1");
        success = inventory.enoughIngredients(recipe7);
        assertFalse(success);
    }
    //el usuario selecciona una receta para la que no hay suficiente azucar debe devolverse falso
    @When("^the user selects a recipe for for which there isnt enough sugar false should be returned$")//
    public void the_user_selects_a_recipe_for_for_which_there_isnt_enough_sugar_false_should_be_returned() throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        Recipe recipe7 = new Recipe();
        recipe7.setAmtCoffee("1");
        recipe7.setAmtSugar("20");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("1");
        success = inventory.enoughIngredients(recipe7);
        assertFalse(success);
    }
    //el usuario selecciona una receta para la que no hay suficiente leche debe devolverse falso
    @When("^the user selects a recipe for for which there isnt enough milk false should be returned$")//
    public void the_user_selects_a_recipe_for_for_which_there_isnt_enough_milk_false_should_be_returned() throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        Recipe recipe7 = new Recipe();
        recipe7.setAmtCoffee("1");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("20");
        recipe7.setAmtChocolate("1");
        success = inventory.enoughIngredients(recipe7);
        assertFalse(success);
    }
    //el usuario selecciona una receta para la que no hay suficiente chocolate debe devolverse falso
    @When("^the user selects a recipe for for which there isnt enough chocolate false should be returned$")//
    public void the_user_selects_a_recipe_for_for_which_there_isnt_enough_chocolate_false_should_be_returned() throws Throwable {
        boolean success = true;
        Inventory inventory = new Inventory();
        Recipe recipe7 = new Recipe();
        recipe7.setAmtCoffee("1");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("20");
        success = inventory.enoughIngredients(recipe7);
        assertFalse(success);
    }
    //la receta se ha actualizado correctamente
    @Then("^the recipe is successfully updated$")//
    public void the_recipe_is_successfully_updated() throws Throwable {
        DescribeRecipe describeRecipe1 = new DescribeRecipe(recipe1);
        coffeeMakerMain.UI_Input(describeRecipe1);
    }

    @Then("^the current status of the coffee maker is \"([^\"]*)\"$")//
    public void the_status_is(String status) throws Throwable {
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertEquals(currentStatus.toString(), status);
    }
    //el estado actual de la cafetera es modo ...
    @Then("^the current mode of the coffee maker is \"([^\"]*)\"$")//
    public void the_current_mode_of_the_coffee_maker_is(String mode) throws Throwable {
        CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
        assertEquals(currentMode.toString(), mode);
    }
    //la receta se agrega a la cafetera
    @Then("^the recipe is added to the coffee maker$")//
    public void the_recipe_is_added_to_the_coffee_maker() throws Throwable {
        DescribeRecipe describeRecipe6 = new DescribeRecipe(recipe6);
        coffeeMakerMain.UI_Input(describeRecipe6);
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertEquals(currentStatus.toString(), "OK");
    }
    //la receta no se agrega a la cafetera
    @Then("^the recipe is not added to the coffee maker$")//
    public void the_recipe_is_not_added_to_the_coffee_maker() throws Throwable {
        DescribeRecipe describeRecipe6 = new DescribeRecipe(recipe6);
        coffeeMakerMain.UI_Input(describeRecipe6);
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertEquals(currentStatus.toString(), "RECIPE_NOT_ADDED");
        CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
        assertEquals(currentMode.toString(), "WAITING");
    }

    //la receta no se puede agregar una  segunda vez  con el mimo nombre"nombre"
    @Then("^the recipe cant be added the second time \"([^\"]*)\"$")//
    public void the_recipe_cant_be_added_the_second_time(String name) throws Throwable {
        int found = 0;
        //boolean success = true;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe recipe : recipes) {
            if(recipe != null && recipe.getName().equals(name)) {
                found++;
            }
        }
        assertTrue(found == 1);
        coffeeMakerMain.UI_Input(new ChooseService(1));
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertEquals(currentStatus.toString(), "OK");
        CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
        assertEquals(currentMode.toString(), "ADD_RECIPE");
        DescribeRecipe describeRecipe7 = new DescribeRecipe(recipe7);
        coffeeMakerMain.UI_Input(describeRecipe7);
        currentStatus = coffeeMakerMain.getStatus();
        assertEquals(currentStatus.toString(), "RECIPE_NOT_ADDED");
        currentMode = coffeeMakerMain.getMode();
        assertEquals(currentMode.toString(), "WAITING");
    }
    //la lista de recetas disponibles ahora incluye "nombre"
    @Then("^the list of available recipes now includes \"([^\"]*)\"$")//
    public void the_list_of_available_recipes_now_includes(String name) throws Throwable {
        Boolean found = false;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe recipe : recipes) {
            if(recipe != null && recipe.getName().equals(name)) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Then("^the list of available recipes only incliudes \"([^\"]*)\" once$")//
    public void the_list_of_available_recipes_only_incliudes_once(String name) throws Throwable {
        int found = 0;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe recipe : recipes) {
            if(recipe != null && recipe.getName().equals(name)) {
                found++;
            }
        }
        assertTrue(found == 1);
    }
    //la lista de recetas disponibles solo incluye "nombre" una vez
    @Then("^the price of the \"([^\"]*)\" recipe is updated appropriately \"([^\"]*)\"$")//
    public void the_price_of_the_recipe_is_updated_appropriately(String name, String price) throws Throwable {
        int priceNum;
        boolean success = false;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe r : recipes) {
            if(r.getName().equals(name)) {
                try{
                    priceNum = Integer.parseInt(price);
                    if(r.getPrice() == priceNum) {
                        success = true;
                    }
                }
                catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                }
            }
        }
        assertTrue(success);

    }
    //la cantidad de café de la receta "nombre" se actualiza adecuadamente
    @Then("^the coffee amount of the \"([^\"]*)\" recipe is updated appropriately \"([^\"]*)\"$")//
    public void the_coffee_amount_of_the_recipe_is_updated_appropriately(String name, String coffeeAmt) throws Throwable {
        int coffeeAmtNum;
        Recipe editedRecipe = null;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe r : recipes) {
            if(r.getName().equals(name)) {
                editedRecipe = r;
            }
        }
        if(editedRecipe != null){
            try{
                coffeeAmtNum = Integer.parseInt(coffeeAmt);
                assertTrue(editedRecipe.getAmtCoffee() == coffeeAmtNum);
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            }
        }
        else {
            assertFalse(editedRecipe == null);
        }
    }
    //la cantidad de azucar de la receta "nombre" se actualiza adecuadamente
    @Then("^the sugar amount of the \"([^\"]*)\" recipe is updated appropriately \"([^\"]*)\"$")//
    public void the_sugar_amount_of_the_recipe_is_updated_appropriately(String name, String sugarAmt) throws Throwable {
        int sugarAmtNum;
        Recipe editedRecipe = null;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe r : recipes) {
            if(r.getName().equals(name)) {
                editedRecipe = r;
            }
        }
        if(editedRecipe != null){
            try{
                sugarAmtNum = Integer.parseInt(sugarAmt);
                assertTrue(editedRecipe.getAmtSugar() == sugarAmtNum);
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            }
        }
        else {
            assertFalse(editedRecipe == null);
        }
    }
    //la cantidad de leche de la receta "nombre" se actualiza adecuadamente
    @Then("^the milk amount of the \"([^\"]*)\" recipe is updated appropriately \"([^\"]*)\"$")//
    public void the_milk_amount_of_the_recipe_is_updated_appropriately(String name, String milkAmt) throws Throwable {
        int milkAmtNum;
        Recipe editedRecipe = null;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe r : recipes) {
            if(r.getName().equals(name)) {
                editedRecipe = r;
            }
        }
        if(editedRecipe != null){
            try{
                milkAmtNum = Integer.parseInt(milkAmt);
                assertTrue(editedRecipe.getAmtMilk() == milkAmtNum);
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            }
        }
        else {
            assertFalse(editedRecipe == null);
        }
    }
    //la cantidad de chocolate de la receta "nombre" se actualiza adecuadamente
    @Then("^the chocolate amount of the \"([^\"]*)\" recipe is updated appropriately \"([^\"]*)\"$")//
    public void the_chocolate_amount_of_the_recipe_is_updated_appropriately(String name, String chocAmt) throws Throwable {
        int chocAmtNum;
        Recipe editedRecipe = null;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe r : recipes) {
            if(r.getName().equals(name)) {
                editedRecipe = r;
            }
        }
        if(editedRecipe != null){
            try{
                chocAmtNum = Integer.parseInt(chocAmt);
                assertTrue(editedRecipe.getAmtChocolate() == chocAmtNum);
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            }
        }
        else {
            assertFalse(editedRecipe == null);
        }
    }
    //Se ha actualizado el inventario de "café", "azúcar", "leche" y "chocolate".
    @Then("^the inventory of \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" has been updated$")//
    public void the_inventory_of_and_has_been_updated(String coffee, String sugar, String milk, String chocolate) throws Throwable {
        Boolean success;
        int expCoffeeInv;
        int expSugarInv;
        int expMilkInv;
        int expChocolateInv;
        int actCoffeeInv;
        int actSugarInv;
        int actMilkInv;
        int actChocolateInv;
        try{
            expCoffeeInv = Integer.parseInt(coffee) + 15;
            expSugarInv = Integer.parseInt(sugar) + 15;
            expMilkInv = Integer.parseInt(milk) + 15;
            expChocolateInv = Integer.parseInt(chocolate) + 15;
            String cmInventory = coffeeMaker.checkInventory();
            String[] tokens = cmInventory.split("\\s");
            actCoffeeInv = Integer.parseInt(tokens[1]);
            actMilkInv = Integer.parseInt(tokens[3]);
            actSugarInv = Integer.parseInt(tokens[5]);
            actChocolateInv = Integer.parseInt(tokens[7]);
            success = (expCoffeeInv == actCoffeeInv && expSugarInv == actSugarInv && expMilkInv == actMilkInv && expChocolateInv == actChocolateInv);
        }
        catch (NumberFormatException e){
            success = false;
        }
        assertTrue(success);
    }
    //el inventario debería haber sido disminuido apropiadamente "receta"
    @Then("^the inventory should have been decremented appropriately \"([^\"]*)\"$")//
    public void the_inventory_should_have_been_decremented_appropriately(String recipe) throws Throwable {
        int recipeNum;
        Boolean success;
        int expCoffeeInv;
        int expSugarInv;
        int expMilkInv;
        int expChocolateInv;
        int actCoffeeInv;
        int actSugarInv;
        int actMilkInv;
        int actChocolateInv;
        try {
            recipeNum = Integer.parseInt(recipe) - 1;
            Recipe[] recipies = recipeBook.getRecipes();
            expCoffeeInv = 15 - recipies[recipeNum].getAmtCoffee();
            expSugarInv = 15 - recipies[recipeNum].getAmtSugar();
            expMilkInv = 15 - recipies[recipeNum].getAmtMilk();
            expChocolateInv = 15 - recipies[recipeNum].getAmtChocolate();
            String cmInventory = coffeeMaker.checkInventory();
            String[] tokens = cmInventory.split("\\s");
            actCoffeeInv = Integer.parseInt(tokens[1]);
            actMilkInv = Integer.parseInt(tokens[3]);
            actSugarInv = Integer.parseInt(tokens[5]);
            actChocolateInv = Integer.parseInt(tokens[7]);
            success = (expCoffeeInv == actCoffeeInv && expSugarInv == actSugarInv && expMilkInv == actMilkInv && expChocolateInv == actChocolateInv);

        }
        catch (NumberFormatException e) {
            success = false;
        }
        assertTrue(success);
    }
    //el inventario no debe decrementarse
    @Then("^the inventory should not be decremented$")//
    public void the_inventory_not_be_decremented() throws Throwable {
        Boolean success;
        int actCoffeeInv;
        int actSugarInv;
        int actMilkInv;
        int actChocolateInv;
        int	expCoffeeInv = 15;
        int	expSugarInv = 15;
        int expMilkInv = 15;
        int expChocolateInv = 15;
        String cmInventory = coffeeMaker.checkInventory();
        String[] tokens = cmInventory.split("\\s");
        actCoffeeInv = Integer.parseInt(tokens[1]);
        actSugarInv = Integer.parseInt(tokens[3]);
        actMilkInv = Integer.parseInt(tokens[5]);
        actChocolateInv = Integer.parseInt(tokens[7]);
        success = (expCoffeeInv == actCoffeeInv && expSugarInv == actSugarInv && expMilkInv == actMilkInv && expChocolateInv == actChocolateInv);
        assertTrue(success);
    }
    //la lista de recetas disponibles no incluye el nombre
    @Then("^the list of available recipes does not include \"([^\"]*)\"$")//
    public void the_list_of_available_recipes_does_not_include(String name) throws Throwable {
        Boolean found = false;
        Recipe[] recipes = recipeBook.getRecipes();
        for(Recipe recipe : recipes) {
            if(recipe != null && recipe.getName().equals(name)) {
                found = true;
            }
        }
        assertFalse(found);
    }
    //el inventario no ha sido actualizado
    @Then("^the inventory has not been updated$")//
    public void the_inventory_has_not_been_updated() throws Throwable {
        Boolean success;
        int expCoffeeInv = 15;
        int expSugarInv = 15;
        int expMilkInv = 15;
        int expChocolateInv = 15;
        int actCoffeeInv;
        int actSugarInv;
        int actMilkInv;
        int actChocolateInv;
        try{
            String cmInventory = coffeeMaker.checkInventory();
            String[] tokens = cmInventory.split("\\s");
            actCoffeeInv = Integer.parseInt(tokens[1]);
            actSugarInv = Integer.parseInt(tokens[3]);
            actMilkInv = Integer.parseInt(tokens[5]);
            actChocolateInv = Integer.parseInt(tokens[7]);
            success = (expCoffeeInv == actCoffeeInv && expSugarInv == actSugarInv && expMilkInv == actMilkInv && expChocolateInv == actChocolateInv);
        }
        catch (NumberFormatException e){
            success = false;
        }
        assertTrue(success);
    }
    //no hay inventario suficiente para la receta seleccionada
    @Given("^there is insufficient inventory for the recipe selected$")//
    public void there_is_insufficient_inventory_for_the_recipe_selected() throws Throwable {
        // hace café varias veces hasta que no hay suficiente inventario para cualquier receta
        //makes coffee multiple times until there is insufficient inventory for any recipe
        int coffeeInv;
        int chocolateInv;
        String cmInventory = coffeeMaker.checkInventory();
        String[] tokens = cmInventory.split("\\s");
        coffeeInv = Integer.parseInt(tokens[1]);
        chocolateInv = Integer.parseInt(tokens[7]);
        while(coffeeInv >= 3 && chocolateInv >= 4) {
            coffeeMaker.makeCoffee(0, 50);
            coffeeMaker.makeCoffee(3, 65);
            cmInventory = coffeeMaker.checkInventory();
            tokens = cmInventory.split("\\s");
            coffeeInv = Integer.parseInt(tokens[1]);
            chocolateInv = Integer.parseInt(tokens[7]);
        }
        assertTrue(coffeeInv < 3 || chocolateInv < 4);
    }
    //el usuario intenta hacer café especificando una receta vacía, fallará y se le devolverá el pago
    @When("^the user attempts to make coffee specifying an empty recipe, it will fail and their payment will be returned$")//
    public void the_user_attempts_to_make_coffee_specifying_an_empty_recipe_it_will_fail_and_their_payment_will_be_returned() throws Throwable {
        boolean success = true;
        int recipeNum = 1;
        int payment = 50;
        int change = 0;
        change = coffeeMaker.makeCoffee(recipeNum, payment);
        if(change == payment) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta hacer café con pago insuficiente, fallará y se le devolverá el pago
    @When("^the user attempts to make coffee with insufficient payment, it will fail and their payment will be returned$")//
    public void the_user_attempts_to_make_coffee_with_insufficient_payment_it_will_fail_and_their_payment_will_be_returned() throws Throwable {
        boolean success = true;
        int recipeNum = 1;
        int payment = 50;
        int change = 0;
        change = coffeeMaker.makeCoffee(recipeNum, payment);
        if(change == payment) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta crear una receta con un valor negativo de cholate, fallará
    @When("^the user tries to create a recipe with a -ve int as the amount of chocolate, it will fail$")//
    public void the_user_tries_to_create_a_recipe_with_a_ve_int_as_the_amount_of_chocolate_it_will_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        try {
            recipe7.setAmtChocolate("-1");
        }
        catch (RecipeException e) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta crear una receta con un valor negativo de cafe, fallará
    @When("^the user tries to create a recipe with a -ve int as the amount of coffee, it will fail$")//
    public void the_user_tries_to_create_a_recipe_with_a_ve_int_as_the_amount_of_coffee_it_will_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        try {
            recipe7.setAmtCoffee("-1");
        }
        catch (RecipeException e) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta crear una receta con un valor negativo de azucar, fallará
    @When("^the user tries to create a recipe with a -ve int as the amount of sugar, it will fail$")//
    public void the_user_tries_to_create_a_recipe_with_a_ve_int_as_the_amount_of_sugar_it_will_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        try {
            recipe7.setAmtSugar("-1");
        }
        catch (RecipeException e) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta crear una receta con un valor negativo de leche, fallará
    @When("^the user tries to create a recipe with a -ve int as the amount of milk, it will fail$")//
    public void the_user_tries_to_create_a_recipe_with_a_ve_int_as_the_amount_of_milk_it_will_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        try {
            recipe7.setAmtMilk("-1");
        }
        catch (RecipeException e) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta crear una receta con nulo como nombre, fallará
    @When("^the user tries to create a recipe with null as the name, it will fail$")//
    public void the_user_tries_to_create_a_recipe_with_null_as_the_name_it_will_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        try {
            recipe7.setAmtMilk("-1");
        }
        catch (RecipeException e) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta crear una receta con un valor entero negativo como precio, fallará
    @When("^the user tries to create a recipe with a -ve int as the price, it will fail$")//
    public void the_user_tries_to_create_a_recipe_with_a_ve_int_as_the_price_it_will_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        try {
            recipe7.setPrice("-50");
        }
        catch (RecipeException e) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta actualizar una receta con nulo como nombre, fallará
    @When("^the user tries to update a recipe with null as the name, it will fail$")//
    public void the_user_tries_to_update_a_recipe_with_null_as_the_name_it_will_fail() throws Throwable {
        Recipe recipe7 = new Recipe();
        recipe7.setName("coffee");
        assertTrue(recipe7.getName().equals("coffee"));
        recipe7.setName(null);
        assertTrue(recipe7.getName().equals("coffee"));
    }
    //se utiliza el método de código hash de receta, siempre debe devolver el mismo resultado para una receta determinada
    @When("^the recipe hashcode method is used, it should always returns the same result for a given recipe$")//
    public void the_recipe_hashcode_method_is_used_it_should_always_returns_the_same_result_for_a_given_recipe() throws Throwable {
        Recipe recipe7 = new Recipe();
        recipe7.setName("coffee");
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        int hc1 = recipe7.hashCode();
        int hc2 = recipe7.hashCode();
        assertTrue(hc1 == hc2);
    }
    //dos recetas que son iguales se comparan utilizando el método de iguales, debería tener éxito
    @When("^two recipes that are the same are compared using the equals method, it should be successful$")//
    public void two_recipes_that_are_the_same_are_compared_using_the_equals_method_it_should_be_successful() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        recipe7.setName("coffee");
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        Recipe recipe8 = recipe7;
        success = recipe7.equals(recipe8);
        assertTrue(success);
    }
    //dos recetas que no son iguales se comparan utilizando el método de iguales, debería fallar
    @When("^two recipes that are not the same are compared using the equals method, it should fail$")//
    public void two_recipes_that_are_not_the_same_are_compared_using_the_equals_method_it_should_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        recipe7.setName("coffee");
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        Recipe recipe8 = new Recipe();
        recipe8.setName("latte");
        recipe8.setAmtCoffee("3");
        recipe8.setAmtSugar("1");
        recipe8.setAmtMilk("3");
        recipe8.setAmtChocolate("0");
        success = recipe7.equals(recipe8);
        assertFalse(success);
    }
    //el comando toString se llama en una receta, el nombre se devuelve correctamente
    @When("^the toString command is called on a Recipe, the name is returned correctly$")//
    public void the_toString_command_is_called_on_a_Recipe_the_name_is_returned_correctly() throws Throwable {
        Recipe recipe7 = new Recipe();
        recipe7.setName("coffee");
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        assertTrue(recipe7.toString().equals("coffee"));
    }
    //una receta y un inventario se comparan utilizando el método de iguales, debería fallar
    @When("^a recipe and an inventory are compared using the equals method, it should fail$")//
    public void a_recipe_and_an_inventory_are_compared_using_the_equals_method_it_should_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        recipe7.setName("coffee");
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        Inventory inventory2 = new Inventory();
        success = recipe7.equals(inventory2);
        assertFalse(success);
    }

    @When("^two recipes are compared and the name of the first one is null, it should fail$")//
    public void two_recipes_are_compared_and_the_name_of_the_first_one_is_null_it_should_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        Recipe recipe8 = new Recipe();
        recipe8.setName("coffee");
        recipe8.setAmtCoffee("3");
        recipe8.setAmtSugar("1");
        recipe8.setAmtMilk("1");
        recipe8.setAmtChocolate("0");
        success = recipe7.equals(recipe8);
        assertFalse(success);
    }
    //se comparan dos recetas y el nombre de la segunda es nulo, debería fallar
    @When("^two recipes are compared and the name of the second one is null, it should fail$")///
    public void two_recipes_are_compared_and_the_name_of_the_second_one_is_null_it_should_fail() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        recipe7.setName("coffee");
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        Recipe recipe8 = new Recipe();
        recipe8.setAmtCoffee("3");
        recipe8.setAmtSugar("1");
        recipe8.setAmtMilk("1");
        recipe8.setAmtChocolate("0");
        success = recipe7.equals(recipe8);
        assertFalse(success);
    }
    //se comparan dos recetas y el nombre de ambas es nulo, debería tener éxito
    @When("^two recipes are compared and the name of both is null, it should succeed$")//
    public void two_recipes_are_compared_and_the_name_of_both_is_null_it_should_succeed() throws Throwable {
        boolean success = true;
        Recipe recipe7 = new Recipe();
        recipe7.setAmtCoffee("3");
        recipe7.setAmtSugar("1");
        recipe7.setAmtMilk("1");
        recipe7.setAmtChocolate("0");
        Recipe recipe8 = new Recipe();
        recipe8.setAmtCoffee("3");
        recipe8.setAmtSugar("1");
        recipe8.setAmtMilk("1");
        recipe8.setAmtChocolate("0");
        success = recipe7.equals(recipe8);
        assertTrue(success);
    }
    //un usuario intenta hacer café y no hay recetas, su cambio debe ser devuelto
    @When("^a user tries to make coffee and there are not recipes, their change should be returned$")//
    public void a_user_tries_to_make_coffee_and_there_are_not_recipes_their_change_should_be_returned() throws Throwable {
        initialize();
        boolean success = true;
        int recipeNum = 1;
        int payment = 50;
        int change = 0;
        change = coffeeMaker.makeCoffee(recipeNum, payment);
        if(change == payment) {
            success = false;
        }
        assertFalse(success);
    }
    //el usuario intenta hacer café con un pago que no es entero positivo, fallará
    @When("^the user attempts to make coffee with payment that is not a pos int, it will fail$")//
    public void the_user_attempts_to_make_coffee_with_payment_that_is_not_a_pos_int_it_will_fail() throws Throwable {
        InsertMoney insertMoney = new InsertMoney(-1);
        coffeeMakerMain.UI_Input(insertMoney);
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertTrue(currentStatus.toString().equals("OUT_OF_RANGE"));
    }
    //se llama a getRecipes, se devuelve una lista válida de recetas
    @When("^getRecipes is called, a valid list of recipes is returned$")//
    public void getrecipes_is_called_a_valid_list_of_recipes_is_returned() throws Throwable {
        Recipe[] recipes = coffeeMakerMain.getRecipes();
        int count = 0;
        for(Recipe recipe : recipes) {
            if(!(recipe.getName() == null)) {
                count++;
            }
        }
        assertTrue(count == 3);
    }
    //la cafetera está en modo agregar receta y se envía un objeto que no describe la receta, debería fallar
    @When("^the coffee maker is in add recipe mode and an object is submitted that is not describe recipe, it should fail$")//
    public void the_coffee_maker_is_in_add_recipe_mode_and_an_object_is_submitted_that_is_not_describe_recipe_it_should_fail() throws Throwable {
        ChooseRecipe chooseRecipe = new ChooseRecipe(1);
        coffeeMakerMain.UI_Input(chooseRecipe);
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertTrue(currentStatus.toString().equals("WRONG_MODE"));
    }
    //la cafetera se reinicia, debería volver al modo de espera
    @When("^the coffee maker is reset, it should return to waiting mode$")//
    public void the_coffee_maker_is_reset_it_should_return_to_waiting_mode() throws Throwable {
        Reset reset = new Reset();
        coffeeMakerMain.UI_Input(reset);
        CoffeeMakerUI.Mode currentMode = coffeeMakerMain.getMode();
        assertTrue(currentMode.toString().equals("WAITING"));
    }
    //el usuario intenta actualizar la receta con una receta nula, debería fallar
    @When("^the user attempts to update the recipe with a null recipe, it should fail$")//
    public void the_user_attempts_to_update_the_recipe_with_a_null_recipe_it_should_fail() throws Throwable {
        Recipe recipe7 = new Recipe();
        DescribeRecipe describeRecipe1 = new DescribeRecipe(recipe7);
        coffeeMakerMain.UI_Input(describeRecipe1);
    }
    //el usuario intenta editar una receta que no existe - menos de cero - debería fallar
    @When("^the user attempts to edit a recipe that doesnt exist - less than zero - it should fail$")//
    public void the_user_attempts_to_edit_a_recipe_that_doesnt_exist_less_than_zero_it_should_fail() throws Throwable {
        String result;
        recipe7 = new Recipe();
        recipe7.setName("Coffee");
        recipe7.setAmtChocolate("0");
        recipe7.setAmtCoffee("3");
        recipe7.setAmtMilk("1");
        recipe7.setAmtSugar("1");
        recipe7.setPrice("50");
        result = recipeBook.editRecipe(-1, recipe7);
        assertTrue(result == null);
    }
    //el usuario intenta hacer café debería fallar
    @When("^the user attempts to make coffee it should fail$")//
    public void the_user_attempts_to_make_coffee_it_should_fail() throws Throwable {
        int change = coffeeMaker.makeCoffee(1, 50);
        assertTrue(change ==  50);
    }
    //se ingresa un comando que no es un comando "addInventory", debería fallar
    @When("^a command is entered that isn't an addInventory command, it should fail$")//
    public void a_command_is_entered_that_isn_t_an_addInventory_command_it_should_fail() throws Throwable {
        ChooseRecipe chooseRecipe = new ChooseRecipe(1);
        coffeeMakerMain.UI_Input(chooseRecipe);
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertTrue(currentStatus.toString().equals("WRONG_MODE"));
    }
    //el usuario muestra recetas que debería tener éxito
    @When("^the user displays recipes it should succeed$")//
    public void the_user_displays_recipes_it_should_succeed() throws Throwable {
        coffeeMakerMain.displayRecipes();
        CoffeeMakerUI.Status currentStatus = coffeeMakerMain.getStatus();
        assertTrue(currentStatus.toString().equals("OK"));
    }

}



