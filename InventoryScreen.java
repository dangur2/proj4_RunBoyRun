
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InventoryScreen {

    private BufferedImage inventoryBackground;
    private final Inventory inventory;
    private final int slotSpacing = 49;
    private final int itemPaddingY = 7;
    private final int itemPaddingX = 5;
    private final int numberPadding = 10;
    private final int highlightPadding = 6;
    private final int hotbarX = 191;
    private final int hotbarY = 415;
    private final int column = 9;
    private final int slotSize = 46;
    private final ArrayList<Rectangle> slots = new ArrayList<>();
    private int selectedSlot;

    public InventoryScreen(Inventory inventory) {
        this.inventory = inventory;
        loadBackground();
        createSlots();
    }

    public void handleClick(int x, int y) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(x, y)) {
                selectedSlot = i;
            }
        }
    }

    private ArrayList<Rectangle> createSlots() {
        for (int i = 0; i < column; i++) {
            slots.add(new Rectangle(hotbarX + highlightPadding + (i % column * slotSpacing), hotbarY + highlightPadding, slotSize, slotSize));
        }
        return slots;
    }

    private void loadBackground() {
        try {
            inventoryBackground = ImageIO.read(getClass().getResourceAsStream("/Sprites/inventory_background.png"));
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(inventoryBackground, hotbarX, hotbarY, null);
        for (int i = 0; i < column; i++) {
            if (i == selectedSlot) {
                g2.setColor(Color.white);
                g2.drawRect(slots.get(i).x, slots.get(i).y, slotSize, slotSize);
            }
        }
        for (int i = 0; i < inventory.getInventorySize(); i++) {

            g2.setColor(Color.white);
            g2.drawString(Integer.toString(inventory.getItem(i).getQuantity()), slots.get(i).x, slots.get(i).y + numberPadding);
            g2.drawImage(inventory.getItem(i).getItemImage(), slots.get(i).x + itemPaddingX, slots.get(i).y + itemPaddingY, null);
        }
    }
}
