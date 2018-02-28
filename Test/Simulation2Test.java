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
    

}