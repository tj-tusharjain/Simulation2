import java.util.ArrayList;

public class Market {

    ArrayList<Food> food = new ArrayList<>();
    ArrayList<Equipment> equipment = new ArrayList<>();
    ArrayList<Recipe> recipe = new ArrayList<>();


    public ArrayList<Food> getFood() {
        return food;
    }

    public void setFood(ArrayList<Food> food) {
        this.food = food;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

    public ArrayList<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(ArrayList<Recipe> recipe) {
        this.recipe = recipe;
    }
}
