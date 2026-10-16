
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory = new ArrayList<>();

    public Inventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void addItem(Item x) {
        //increase quantity first if item exists, then adds a new item
        for (Item y : inventory) {
            if (y.getItemName().equals(x.getItemName())) {
                y.increaseQuantity(1);
                return;
            }
        }
        x.increaseQuantity(1);
        inventory.add(x);
    }

    public void removeItem(int x) {
        if (inventory.get(x) != null) {
            inventory.remove(x);
        }
    }

    public int useItem(Item x) {
        //decreases quantity first if item exist, remove secondly
        for (Item y : inventory) {
            if (x != null && y.getItemName().equals(x.getItemName()) && x.getItemConsumable() == true) {
                int heal = x.use();
                x.decreaseQuantity();
                if (x.getQuantity() == 0) {
                    inventory.remove(x);
                } 
                return heal;
            }
        }
        return 0;
    }

    public Item getItem(int x) {
        return this.inventory.get(x);
    }

    public int getInventorySize() {
        return this.inventory.size();
    }
}
