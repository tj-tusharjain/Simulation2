public class Recipe {

    String name;
    String[] ingredients;
    String[] equipment;
    int time;
    int recipeValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getEquipment() {
        return equipment;
    }

    public void setEquipment(String[] equipment) {
        this.equipment = equipment;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRecipeValue() {
        return recipeValue;
    }

    public void setRecipeValue(int recipeValue) {
        this.recipeValue = recipeValue;
    }
}
