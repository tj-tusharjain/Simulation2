import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Simulation2Test {

    Simulation2 gameObject = new Simulation2();
    FileLoading fileLoader = new FileLoading();
    Simulation gameWorld = new Simulation();

    @Before
    public void Initialization(){
        gameWorld = fileLoader.loadFile();
    }

    @Test
    public void getWealthTest(){
        double wealth = gameObject.getWealth(gameWorld);
        assertEquals(50000.0, wealth);
    }

    @Test
    public void passTimeTest(){
        double time = 6.0;
        double presentTime = gameObject.passTime(time, "300");
        assertEquals(11.0, presentTime);
    }

    @Test
    public void inventoryTest(){
        String outputString = gameObject.inventory(gameWorld, "food");
        String compareString = "tofuflouroilricebroccoli";
        assertEquals(compareString, outputString);

    }

    @Test
    public void informationTest(){

        String outputString = gameObject.information(gameWorld, "rice");
        String compareString = "rice41false";
        assertEquals(compareString, outputString);

    }

    @Test
    public void cookTest(){

        gameObject.menuAdd(gameWorld, "Tofu Fried Wonton");
        String inputString = "Tofu Fried Wonton 1";
        double time = 6.0;
        gameObject.cook(gameWorld, inputString, time);
        assertEquals(50015.0, gameWorld.getRestaurant().getWealth());

    }

    @Test
    public void menuAddTest(){

        gameObject.menuAdd(gameWorld, "Fried Rice");
        String firstMenuItem = gameWorld.getRestaurant().getMenu().getDish().get(0);
        assertEquals("Fried Rice", firstMenuItem);

    }

    @Test
    public void menuRemoveTest(){

        gameObject.menuAdd(gameWorld, "Fried Rice");
        gameObject.menuRemove(gameWorld, "Fried Rice");
        int menuSize = gameWorld.getRestaurant().getMenu().getDish().size();
        assertEquals(0, menuSize);
    }

    @Test
    public void menuList(){

        gameObject.menuAdd(gameWorld, "Fried Rice");
        gameObject.menuAdd(gameWorld, "Tofu Fried Wonton");
        String menu = gameObject.menuList(gameWorld);
        String compareString = "Fried Rice - 18.0Tofu Fried Wonton - 15.0";

        assertEquals(compareString, menu);
    }

    @Test
    public void buyMarketTest(){

        gameObject.buyMarket(gameWorld, "paneer 1");
        Food foodItem = new Food();
        for (Food food : gameWorld.getRestaurant().getKitchen().getFood()){
            if (food.getName().equalsIgnoreCase("paneer")){
                foodItem = food;
            }
        }
        boolean containsItem = gameWorld.getRestaurant().getKitchen().getFood().contains(foodItem);
        assertEquals(true, containsItem);

    }

    @Test
    public void sellMarketTest() {

        gameObject.sellMarket(gameWorld, "tofu 1");
        boolean contains = false;

        for (Food food : gameWorld.getRestaurant().getKitchen().getFood()) {
            if (food.getName().equalsIgnoreCase("tofu")) {
                contains = true;
            }
        }
        assertEquals(false, contains);
    }
}