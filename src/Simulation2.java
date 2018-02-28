import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Simulation2 {

    Scanner scanner = new Scanner(System.in);
    public static final double START_TIME = 6.0;
    public static final double CLOSE_TIME = 20.0;

    public void gameInterface(Simulation gameWorld, double time){

        System.out.println("Welcome to "+gameWorld.getRestaurant().getName());

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

                System.out.println(gameWorld.getRestaurant().getWealth());
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
                cook(gameWorld, commandList[1].trim(), time);
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
                break;



        }

    }


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
        time = timeHour % 16 + time;

        if (time > 20){
            time = (time - 20) + 6;
        }

        return time;
    }


    public void inventory(Simulation gameWorld, String type){

        if (type.equalsIgnoreCase("recipe")){

            ArrayList<Dish> recipeList = gameWorld.getRestaurant().getKitchen().getRecipe();
            System.out.println("The recipes in the inventory are:");
            for (Dish recipe : recipeList){
                System.out.println(recipe.getName());
            }

        }else if (type.equalsIgnoreCase("food")){

            ArrayList<Food> foodList = gameWorld.getRestaurant().getKitchen().getFood();
            System.out.println("The food items in the inventory are:");
            for (Food food : foodList){
                System.out.println(food.getName());
            }

        }else if (type.equalsIgnoreCase("equipment")) {

            ArrayList<Equipment> equipmentList = gameWorld.getRestaurant().getKitchen().getEquipment();
            System.out.println("The equipments in the inventory are:");
            for (Equipment equipment : equipmentList) {
                System.out.println(equipment.getName());

            }
        }else {
            System.out.println("The item type requested is not found");
        }

    }


    public void information(Simulation gameWorld, String item){



        ArrayList<Food> foodList = gameWorld.getRestaurant().getKitchen().getFood();
        for (Food food : foodList){

            if (item.equalsIgnoreCase(food.getName())){

                System.out.println("The item details are:");
                System.out.println("name: "+food.getName());
                System.out.println("base value: "+food.getValue());
                System.out.println("quantity available: "+food.getQuantity());
                System.out.println("finished food product: "+food.isFinished());
                return;

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
                return;

            }
        }

        ArrayList<Equipment> equipmentList = gameWorld.getRestaurant().getKitchen().getEquipment();
        for (Equipment equipment : equipmentList){

            if (item.equalsIgnoreCase(equipment.getName())){

                System.out.println("The item details are:");
                System.out.println("name: " + equipment.getName());
                System.out.println("base value: " + equipment.getValue());
                System.out.println("upkeep value: " + equipment.getUpkeepValue());
                return;

            }
        }
    }


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

    public void menuList(Simulation gameWorld){

        ArrayList<String> menu = gameWorld.getRestaurant().getMenu().getDish();
        ArrayList<Dish> recipeList = gameWorld.getRestaurant().getKitchen().getRecipe();
        for (String dish : menu){
            System.out.print(dish+" - ");
            for (Dish recipe : recipeList){
                if (recipe.getName().equalsIgnoreCase(dish)){
                    System.out.println(recipe.getValue()*1.5);
                    break;
                }
            }
        }
    }

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


            }
        }
    }

    public void listMarket(Simulation gameWorld){

        ArrayList<Food> foodList = gameWorld.getMarket().getFood();
        System.out.println("Food Items Available in market are: ");
        for (Food food : foodList){
            System.out.print(food.getName()+" - ");
            System.out.println(food.getValue());
        }

        System.out.println("---------------------");

        ArrayList<Recipe> recipeList = gameWorld.getMarket().getRecipe();
        System.out.println("Recipes available in the market are: ");
        for (Recipe recipe : recipeList){
            System.out.print(recipe.getName()+" - ");
            System.out.println(recipe.getRecipeValue());
        }

        System.out.println("---------------------");
        ArrayList<Equipment> equipmentList = gameWorld.getMarket().getEquipment();
        System.out.println("Equipments available in market are: ");
        for (Equipment equipment : equipmentList){
            System.out.print(equipment.getName()+" - ");
            System.out.println(equipment.getValue());
        }
        System.out.println("---------------------");
    }

    



    public static void main (String[] args){

        FileLoading fileLoader = new FileLoading();
        Simulation gameWorld = fileLoader.loadFile();
        double time = START_TIME;
        Simulation2 gameObject = new Simulation2();
        gameObject.gameInterface(gameWorld, time);


    }

}
