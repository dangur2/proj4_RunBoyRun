import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory = new ArrayList<>();

    public Inventory(ArrayList<Item> inventory){
        this.inventory = inventory;
        addItem(new Item("log", false, "/Sprites/log.png", 0));
        addItem(new Item("shrimp", false, "/Sprites/shrimp.png", 0));
        addItem(new Item("pale energy", false, "/Sprites/pale wisp.png", 0));
        addItem(new Item("tin ore", false, "/Sprites/tin ore.png", 0));
        addItem(new Item("tin ore", false, "/Sprites/tin ore.png", 0));
        addItem(new Item("tin ore", false, "/Sprites/tin ore.png", 0));
        addItem(new Item("tin ore", false, "/Sprites/tin ore.png", 0));
        addItem(new Item("tin ore", false, "/Sprites/tin ore.png", 0));
        addItem(new Item("trout", false, "/Sprites/trout.png", 0));
        addItem(new Item("psyon wisp", false, "/Sprites/psyon wisp.png", 0));
        addItem(new Item("copper ore", false, "/Sprites/copper ore.png", 0));
        addItem(new Item("tin ore", false, "/Sprites/tin ore.png", 0));
        addItem(new Item("tin ore", false, "/Sprites/tin ore.png", 0));
    }
    public void addItem(Item x){
        for (Item y : inventory) {
            if(y.getItemName().equals(x.getItemName())){
                y.increaseQuantity(1);
                return;
            }
        }
        x.increaseQuantity(1);
        inventory.add(x);
    }
    public void removeItem(int x){
        if (inventory.get(x) != null) {
            inventory.remove(x);
        }
    }

    public Item getItem(int x){
        return this.inventory.get(x);
    }
    public int getInventorySize(){
        return this.inventory.size();
    }
}
