public class Equipment {

    String name;
    int upkeepValue;
    int value;
    int quantity = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpkeepValue() {
        return upkeepValue;
    }

    public void setUpkeepValue(int upkeepValue) {
        this.upkeepValue = upkeepValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
