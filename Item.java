
import java.awt.Image;
import javax.swing.ImageIcon;

public class Item {
    private final String name;
    private final boolean consumable;
    private final Image sprite;
    private int quantity;
    private final int heal;

    public Item(String name, boolean consumable, String spritePath, int quantity,int heal){
        this.name = name;
        this.consumable = consumable;
        this.sprite = new ImageIcon(Item.class.getResource(spritePath)).getImage();
        this.quantity = quantity;
        this.heal = heal;
    }
    public int use(){
        if(consumable){
            return heal;
        }
        return 0;
    }
    public void increaseQuantity(int x){
        this.quantity += x;
    }
    public void decreaseQuantity(){
        this.quantity--;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public String getItemName(){
        return this.name;
    }
    public boolean getItemConsumable(){
        return this.consumable;
    }
    public Image getItemImage(){
        return this.sprite;
    }
    
}
