import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class that allows you to simulate a restaurant. It allows the user to buy, sell and cook food items.
 * Build recipes and sell items to different customers.
 */
public class Simulation2 {

    Scanner scanner = new Scanner(System.in);
    public static final double START_TIME = 6.0;
    public static final double CLOSE_TIME = 22.0;
    public static final double MIDNIGHT = 24.0;
    private static final int COMPLEXITY_THRESHOLD = 20;


    /**
     * A method to run the interface of the game, it prints the basic information and instructions.
     * @param gameWorld the layout of the game.
     * @param time the present time of the room.
     */
    public void gameInterface(Simulation gameWorld, double time){

        boolean gameRun = true;
        System.out.println("Welcome to "+gameWorld.getRestaurant().getName());

        while (gameRun){

            //To pay upkeep value for equipments at midnight
            if (time == MIDNIGHT){
                ArrayList<Equipment> equipmentLists = gameWorld.getRestaurant().getKitchen().getEquipment();
                double wealth = gameWorld.getRestaurant().getWealth();
                for (Equipment equipment : equipmentLists){
                    wealth -= equipment.getUpkeepValue();
                }

                gameWorld.getRestaurant().setWealth(wealth);
                if (gameWorld.getRestaurant().getWealth() < 0){
                    double wealthRest = gameWorld.getRestaurant().getWealth();
                    for (Equipment equipment : equipmentLists)    {
                        while (wealthRest < 0){
                            wealthRest -= equipment.getUpkeepValue();
                            equipmentLists.remove(equipment);
                            break;
                        }
                    }
                }
            }

            System.out.print("What would you like to do? : ");
            String command = scanner.nextLine();
            command.toLowerCase();

            String[] commandList = command.split(" +");
            ArrayList<String> checkList = new ArrayList<>(Arrays.asList("time", "add", "remove", "list")) ;

            String actionVariable = commandList[0];
            if (commandList.length >= 2){
                if (checkList.contains(commandList[1])){
                    actionVariable = commandList[0] + " " + commandList[1];
                }
            }

            switch (actionVariable){

                case "wealth":
                    if (commandList.length != 1){
                        System.out.println("I don't understand");
                        break;
                    }

                    getWealth(gameWorld);
                    break;

                case "time":
                    if (commandList.length != 1){
                        System.out.println("I don't understand");
                        break;
                    }

                    System.out.println("The current time is "+time);
                    break;

                case "pass time":
                    String timeChange = commandList[2];
                    passTime(time, timeChange);
                    break;

                case "inventory":
                    commandList = command.split(" +", 2);
                    String type = commandList[1];
                    inventory(gameWorld, type);
                    break;

                case "info":
                    commandList = command.split(" +", 2);
                    String item = commandList[1];
                    information(gameWorld, item);
                    break;

                case "cook":
                    commandList = command.split(" ", 2);
                    if (time < CLOSE_TIME){
                        cook(gameWorld, commandList[1].trim(), time);
                    }else {
                        System.out.println("The restaurant is closed");
                    }
                    break;

                case "menu add":
                    commandList = command.split(" ", 3);
                    String foodItem = commandList[2];
                    menuAdd(gameWorld, foodItem);
                    break;

                case "menu remove":
                    commandList = command.split(" ", 3);
                    String foodRemove = commandList[2];
                    menuRemove(gameWorld, foodRemove);
                    break;

                case "menu list":
                    menuList(gameWorld);
                    break;


                case "market":
                    market(gameWorld);
                    time += 1.0;
                    break;

                case "quit":
                    System.exit(-1);

                default:
                    System.out.println("I don't understand");

            }
        }

    }


    /**
     * A method that returns the current wealth of the restaurant.
     * @param gameWorld the current world of the game.
     * @return returns the wealth
     */
    public double getWealth(Simulation gameWorld){
        System.out.println(gameWorld.getRestaurant().getWealth());
        return gameWorld.getRestaurant().getWealth();
    }


    /**
     * A method that fast forwards the specified amount of time.
     * @param time the current time
     * @param timeChange the amount of time to be changed by
     * @return the new time
     */
    public double passTime(double time, String timeChange){
        for (int i = 0; i < timeChange.length(); i++){
            if (!Character.isDigit(timeChange.charAt(i))){
                System.out.println("Invalid time entry");
                return time;
            }
        }
        if (Integer.parseInt(timeChange) < 0){
            System.out.println("Invalid Entry");
            return time;
        }

        double timeHour = Integer.parseInt(timeChange) / 60;
        time = timeHour % 24 + time;

        if (time > 24){
            time = (time - 24);
        }

        return time;
    }


    /**
     * A method that prints the inventory of items present in the restaurant.
     * @param gameWorld the current world of the game.
     * @param type the type of items to be shown.
     * @return a string of the output
     */
    public String inventory(Simulation gameWorld, String type){
        String outputString = "";
        if (type.equalsIgnoreCase("recipe")){

            ArrayList<Dish> recipeList = gameWorld.getRestaurant().getKitchen().getRecipe();
            System.out.println("The recipes in the inventory are:");
            for (Dish recipe : recipeList){
                System.out.println(recipe.getName());
                outputString += recipe.getName();
            }

        }else if (type.equalsIgnoreCase("food")){

            ArrayList<Food> foodList = gameWorld.getRestaurant().getKitchen().getFood();
            System.out.println("The food items in the inventory are:");
            for (Food food : foodList){
                System.out.println(food.getName());
                outputString += food.getName();
            }

        }else if (type.equalsIgnoreCase("equipment")) {

            ArrayList<Equipment> equipmentList = gameWorld.getRestaurant().getKitchen().getEquipment();
            System.out.println("The equipments in the inventory are:");
            for (Equipment equipment : equipmentList) {
                System.out.println(equipment.getName());
                outputString += equipment.getName();

            }
        }else {
            System.out.println("The item type requested is not found");
        }

        return outputString;
    }


    /**
     * A method that prints the information for the item given
     * @param gameWorld the current world of the game.
     * @param item the item whose information is to be shown
     * @return
     */
    public String information(Simulation gameWorld, String item){

        String outputString = "";
        ArrayList<Food> foodList = gameWorld.getRestaurant().getKitchen().getFood();
        for (Food food : foodList){

            if (item.equalsIgnoreCase(food.getName())){

                System.out.println("The item details are:");
                System.out.println("name: "+food.getName());
                System.out.println("base value: "+food.getValue());
                System.out.println("quantity available: "+food.getQuantity());
                System.out.println("finished food product: "+food.isFinished());
                outputString = food.getName()+food.getValue()+food.getQuantity()+food.isFinished();
                return outputString;

            }
        }

        ArrayList<Dish> recipeList = gameWorld.getRestaurant().getKitchen().getRecipe();
        for (Dish recipe : recipeList){

            if (item.equalsIgnoreCase(recipe.getName())){

                System.out.println("The item details are:");
                System.out.println("name: "+recipe.getName());
                System.out.println("base value: "+recipe.getValue());
                System.out.println("Equipment needed: "+recipe.getRecipe().getEquipment().toString());
                System.out.println("Ingredients needed: "+recipe.getRecipe().getIngredients().toString());
                System.out.println("Recipe Value: "+recipe.getRecipe().getRecipeValue());
                System.out.println("Preparation Time: "+recipe.getRecipe().getTime());
                return outputString;

            }
        }

        ArrayList<Equipment> equipmentList = gameWorld.getRestaurant().getKitchen().getEquipment();
        for (Equipment equipment : equipmentList){

            if (item.equalsIgnoreCase(equipment.getName())){

                System.out.println("The item details are:");
                System.out.println("name: " + equipment.getName());
                System.out.println("base value: " + equipment.getValue());
                System.out.println("upkeep value: " + equipment.getUpkeepValue());
                return outputString;

            }
        }
        return outputString;
    }


    /**
     * A method that cooks the required dish.
     * @param gameWorld the current world of the game.
     * @param inputString the name and quantity of the food item
     * @param time the current time of the game
     */
    public void cook(Simulation gameWorld, String inputString, double time){

        int tempIndex = inputString.lastIndexOf(" ");
        String[] inputList =  {inputString.substring(0, tempIndex), inputString.substring(tempIndex)};
        String cookFood = inputList[0].trim();
        String cookQuantity = inputList[1].trim();
        ArrayList<String> menu = gameWorld.getRestaurant().getMenu().getDish();
        boolean itemFoundMenu = false;

        for (String dish : menu){
            if (dish.equalsIgnoreCase(cookFood)){
                itemFoundMenu = true;
            }
        }

        if (!itemFoundMenu){
            System.out.println("Can't cook, item not in menu");
            return;
        }

        for (int i = 0; i < cookQuantity.length(); i++){
            if (!Character.isDigit(cookQuantity.charAt(i))){
                System.out.println("Invalid time entry");
                return;
            }
        }

        Dish foodRecipe = new Dish();
        boolean foundDish = false;
        ArrayList<Dish> recipeList = gameWorld.getRestaurant().getKitchen().getRecipe();
        for (Dish recipe : recipeList){

            if (recipe.getName().equalsIgnoreCase(cookFood)){
                foundDish = true;
                foodRecipe = recipe;
            }
        }

        if (foundDish == false){
            System.out.println("Recipe for the dish not found");
            return;
        }

        String[] ingredientsList = foodRecipe.getRecipe().getIngredients();
        ArrayList<Food> requiredFood = new ArrayList<>();
        ArrayList<Food> availableIngredients = gameWorld.getRestaurant().getKitchen().getFood();
        for (String ingredient : ingredientsList){
            boolean foundIngredient = false;
            for (Food food : availableIngredients){

                if (food.getName().equalsIgnoreCase(ingredient)){
                    requiredFood.add(food);
                    gameWorld.getRestaurant().getKitchen().food.remove(food);
                    foundIngredient = true;
                    break;
                }
            }

            if (!foundIngredient){
                gameWorld.getRestaurant().getKitchen().food.addAll(requiredFood);
                System.out.println("Required ingredient not in stock: " + ingredient);
                return;
            }
        }

        String[] equipmentList = foodRecipe.getRecipe().getEquipment();
        ArrayList<Equipment> availableEquipment = gameWorld.getRestaurant().getKitchen().getEquipment();
        for (String equipment : equipmentList){
            boolean foundEquipment = false;
            for (Equipment equipmentAvailable : availableEquipment){

                if (equipmentAvailable.getName().equalsIgnoreCase(equipment)){

                    foundEquipment = true;
                    break;
                }
            }

            if (!foundEquipment){
                System.out.println("Required equipment not available: " + equipment);
                return;
            }
        }

        int lowestIngredientQty = requiredFood.get(0).getQuantity();
        int quantityToCook = Integer.parseInt(cookQuantity);
        for (Food food : requiredFood){
            if (lowestIngredientQty > food.getQuantity()){
                lowestIngredientQty = food.getQuantity();
            }
        }

        if (lowestIngredientQty < quantityToCook){
            quantityToCook = lowestIngredientQty;
        }

        for (Food food : requiredFood){
            if (food.getQuantity() > quantityToCook){
                food.setQuantity(food.getQuantity() - quantityToCook);
                gameWorld.getRestaurant().getKitchen().food.add(food);
            }
        }

        System.out.println("Cooked "+foodRecipe.getName()+": "+quantityToCook+" pieces.");
        double wealth = gameWorld.getRestaurant().getWealth();
        gameWorld.getRestaurant().setWealth(wealth + (foodRecipe.getValue())*1.5*quantityToCook);
        time = passTime(time, Integer.toString(foodRecipe.getRecipe().getTime()*quantityToCook));

    }


    /**
     * A method that allows the user to add an item to the menu
     * @param gameWorld the current world of the game.
     * @param foodName the name of the food item to be added
     */
    public void menuAdd(Simulation gameWorld, String foodName){

        String foodItem = "";
        ArrayList<Food> foodList = gameWorld.getRestaurant().getKitchen().getFood();
        for (Food food : foodList){
            if (foodName.equalsIgnoreCase(food.getName())){
                foodItem = foodName;
            }
        }

        ArrayList<Dish> recipeList = gameWorld.getRestaurant().getKitchen().getRecipe();
        for (Dish recipe : recipeList){
            if (foodName.equalsIgnoreCase(recipe.getName())){
                foodItem = foodName;
            }
        }

        if (foodItem.equals("")){
            System.out.println("Food Item not found, cannot add to menu");
            return;
        }

        gameWorld.getRestaurant().menu.dish.add(foodItem);
        System.out.println(foodItem+" added to the menu.");

    }

    /**
     *A method to remove a specified food item from the menu
     * @param gameWorld the current world of the game.
     * @param foodItem the food item to be removed.
     */
    public void menuRemove(Simulation gameWorld, String foodItem){

        ArrayList<String> menu = gameWorld.getRestaurant().getMenu().getDish();

        for (String dish : menu){
            if (dish.equalsIgnoreCase(foodItem)){
                gameWorld.getRestaurant().menu.dish.remove(dish);
                return;
            }
        }

        System.out.println("Dish does not exist in the menu");

    }

    /**
     *A method that shows the list of items in the menu
     * @param gameWorld the current world of the game.
     * @return the list of items in the menu
     */
    public String menuList(Simulation gameWorld){

        String outputString = "";

        ArrayList<String> menu = gameWorld.getRestaurant().getMenu().getDish();
        ArrayList<Dish> recipeList = gameWorld.getRestaurant().getKitchen().getRecipe();
        for (String dish : menu){
            System.out.print(dish+" - ");
            outputString += dish+" - ";
            for (Dish recipe : recipeList){
                if (recipe.getName().equalsIgnoreCase(dish)){
                    System.out.println(recipe.getValue()*1.5);
                    outputString+=recipe.getValue()*1.5;
                    break;
                }
            }
        }
        return outputString;
    }

    /**
     * A method that takes the user to the market, and allows us to access its functions
     * @param gameWorld the current world of the game.
     */
    public void market(Simulation gameWorld){

        boolean exitMarket = false;

        while (!exitMarket){
            System.out.println("You're in the market, what would you like to do?");
            String inputString = scanner.nextLine();

            String[] inputList = inputString.split(" ", 2);
            String actionVariable = inputList[0];

            switch (actionVariable){

                case "exit":
                    exitMarket = true;
                    break;

                case "list":
                    listMarket(gameWorld);
                    break;

                case "buy":
                    String inputItem = inputList[1];
                    buyMarket(gameWorld, inputItem);
                    break;

                case "sell":
                    String sellItem = inputList[1];
                    sellMarket(gameWorld, sellItem);
                    break;


            }
        }
    }

    /**
     * A method that lists all the items in the method
     * @param gameWorld the current world of the game.
     */
    public void listMarket(Simulation gameWorld){

        ArrayList<Food> foodList = gameWorld.getMarket().getFood();
        System.out.println("Food Items Available in market are: ");
        for (Food food : foodList){
            System.out.print(food.getName()+" - ");
            System.out.println(food.getValue());
        }


        ArrayList<Recipe> recipeList = gameWorld.getMarket().getRecipe();
        System.out.println("Recipes available in the market are: ");
        for (Recipe recipe : recipeList){
            System.out.print(recipe.getName()+" - ");
            System.out.println(recipe.getRecipeValue());
        }

        ArrayList<Equipment> equipmentList = gameWorld.getMarket().getEquipment();
        System.out.println("Equipments available in market are: ");
        for (Equipment equipment : equipmentList){
            System.out.print(equipment.getName()+" - ");
            System.out.println(equipment.getValue());
        }
    }

    /**
     * A method that allows you to buy items from the market
     * @param gameWorld the current world of the game.
     * @param inputItem the name of the item and the quantity to be purchased
     */
    public void buyMarket(Simulation gameWorld, String inputItem){

        int tempIndex = inputItem.lastIndexOf(" ");
        String[] inputList =  {inputItem.substring(0, tempIndex), inputItem.substring(tempIndex)};
        String buyItem = inputList[0].trim();
        String buyQuantity = inputList[1].trim();
        ArrayList<Food> foodList = gameWorld.getMarket().getFood();
        ArrayList<Food> foodListRest = gameWorld.getRestaurant().getKitchen().getFood();
        ArrayList<Recipe> recipeList = gameWorld.getMarket().getRecipe();
        ArrayList<Dish> recipeListRest = gameWorld.getRestaurant().getKitchen().getRecipe();
        ArrayList<Equipment> equipmentList = gameWorld.getMarket().getEquipment();
        ArrayList<Equipment> equipmentListRest = gameWorld.getRestaurant().getKitchen().getEquipment();

        double wealth = gameWorld.getRestaurant().getWealth();

        for (Food food : foodList){
            if (food.getName().equalsIgnoreCase(buyItem)){
                int quantity = Integer.parseInt(buyQuantity);
                boolean added = false;

                for (Food restFood : foodListRest){

                    if (restFood.getName().equalsIgnoreCase(buyItem)){
                        added = true;
                        gameWorld.getRestaurant().setWealth(wealth - food.getValue()*quantity);
                        restFood.setQuantity(restFood.getQuantity()+quantity);
                    }
                }
                if (!added){
                    Food addFood = food;
                    addFood.setQuantity(quantity);
                    gameWorld.getRestaurant().setWealth(wealth - food.getValue()*quantity);
                    gameWorld.getRestaurant().getKitchen().food.add(food);
                }
            }
        }

        for (Recipe recipe : recipeList){
            if (recipe.getName().equalsIgnoreCase(buyItem)){

                for (Dish restRecipe : recipeListRest){

                    if (restRecipe.getName().equalsIgnoreCase(buyItem)){
                        Dish dish = new Dish();
                        dish.setName(recipe.getName());
                        dish.setRecipe(recipe);
                        recipeListRest.add(dish);
                        gameWorld.getRestaurant().setWealth(wealth - dish.getRecipe().getRecipeValue());
                        return;
                    }
                }
            }
        }

        for (Equipment equipment : equipmentList){

            if (equipment.getName().equalsIgnoreCase(buyItem)){
                int quantity = Integer.parseInt(buyQuantity);
                boolean added = false;
                for (Equipment restEquipment : equipmentListRest){

                    if (restEquipment.getName().equalsIgnoreCase(buyItem)){
                        added = true;
                        restEquipment.setQuantity(restEquipment.getQuantity()+quantity);
                        gameWorld.getRestaurant().setWealth(wealth - restEquipment.getValue()*quantity);

                    }
                }
                if (!added){
                    equipment.setQuantity(quantity);
                    gameWorld.getRestaurant().setWealth(wealth - equipment.getValue()*quantity);
                    gameWorld.getRestaurant().getKitchen().equipment.add(equipment);
                }
            }
        }
    }

    /**
     * A method that allows you to sell an item in the market
     * @param gameWorld the current world of the game.
     * @param inputItem the item to be sold and the quantity to be sold.
     */
    public void sellMarket(Simulation gameWorld, String inputItem){

        int tempIndex = inputItem.lastIndexOf(" ");
        String[] inputList =  {inputItem.substring(0, tempIndex), inputItem.substring(tempIndex)};
        String sellItem = inputList[0].trim().toLowerCase();
        String sellQuantity = inputList[1].trim();
        boolean containsItem = false;
        int quantity = Integer.parseInt(sellQuantity);
        double wealth = gameWorld.getRestaurant().getWealth();

        for (Food food : gameWorld.getRestaurant().getKitchen().getFood()){
            if (food.getName().equalsIgnoreCase(sellItem)){
                if (quantity >= food.getQuantity()){
                    gameWorld.getRestaurant().setWealth(wealth + food.getValue()*0.5*food.getQuantity());
                    gameWorld.getRestaurant().getKitchen().food.remove(food);
                }else {
                    quantity = food.getQuantity() - quantity;
                    food.setQuantity(quantity);
                    gameWorld.getRestaurant().setWealth(wealth + food.getValue()*0.5*quantity);
                }
            }
        }

        for (Dish dish : gameWorld.getRestaurant().getKitchen().getRecipe()){
            if (dish.getName().equalsIgnoreCase(sellItem)){
                gameWorld.getRestaurant().setWealth(wealth + dish.getValue()*0.5);
                gameWorld.getRestaurant().getKitchen().dish.remove(dish);
            }
        }

        for (Equipment equipment : gameWorld.getRestaurant().getKitchen().getEquipment()){
            if (equipment.getName().equalsIgnoreCase(sellItem)){
                gameWorld.getRestaurant().setWealth(wealth + equipment.getValue()*0.5);
                gameWorld.getRestaurant().getKitchen().equipment.remove(equipment);
            }
        }
    }

    /**
     * A method to calculate the popularity of the restaurant
     * @param gameWorld the current world of the game.
     */
    public void calcPopularity(Simulation gameWorld) {
        int countComplex = 0;

        for (Dish food : gameWorld.getRestaurant().getKitchen().getRecipe()) {
            if (food.getRecipe().getTime() > COMPLEXITY_THRESHOLD) {
                countComplex++;
            }
        }
        int variety = gameWorld.getRestaurant().getMenu().getDish().size();
        double popularity = 10 - (variety * countComplex) % 10; /* gives a popularity value on the scale of 1 to 10 */

        gameWorld.getRestaurant().setPopularity(popularity);
    }



    public static void main (String[] args){

        FileLoading fileLoader = new FileLoading();
        Simulation gameWorld = fileLoader.loadFile();
        double time = START_TIME;
        Simulation2 gameObject = new Simulation2();
        gameObject.gameInterface(gameWorld, time);

    }

}
