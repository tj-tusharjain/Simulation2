import java.util.ArrayList;

public class Kitchen {

    ArrayList<Food> food = new ArrayList<>();
    ArrayList<Dish> dish = new ArrayList<>();
    ArrayList<Equipment> equipment = new ArrayList<>();

    public ArrayList<Food> getFood() {
        return food;
    }

    public void setFood(ArrayList<Food> food) {
        this.food = food;
    }

    public ArrayList<Dish> getRecipe() {
        return dish;
    }

    public void setRecipe(ArrayList<Dish> dish) {
        this.dish = dish;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }
}
