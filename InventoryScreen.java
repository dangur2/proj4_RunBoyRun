
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InventoryScreen {

    private BufferedImage inventoryBackground, inventoryTooltip, tooltipText;
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
    private final int tooltipPadding = 45;
    private final int tooltipTextPaddingX = 35;
    private final int tooltipTextPaddingY = 28;
    private final ArrayList<Rectangle> slots = new ArrayList<>();
    private final ArrayList<Rectangle> tooltips = new ArrayList<>();
    private boolean showTooltip = false;
    private int selectedSlot;

    public InventoryScreen(Inventory inventory) {
        this.inventory = inventory;
        loadBackground();
        createSlots();
        createTooltip();
    }

    public void handleLeftClick(int x, int y) {
        //ta bort en item om du vänsterklickar på tooltippen när den är uppe
        if (tooltips.get(selectedSlot).contains(x, y) && showTooltip) {
            inventory.removeItem(selectedSlot);
            showTooltip = false;
            return;
        }
        //vänsterklick på en slot gör den till selected slot som highlightar den slotten
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(x, y)) {
                showTooltip = false;
                selectedSlot = i;
            }
        }
        
    }

    public void handleRightClick(int x, int y) {
        //om du högerklickar en slot, oavsett om den är highlightad eller inte, sålänge den har en item, öppna tooltip på den itemen
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(x, y)) {
                selectedSlot = i;
                if (slots.get(selectedSlot).contains(x, y) && selectedSlot < inventory.getInventorySize()) {
                    showTooltip = !showTooltip;
                }
            }
        }
    }

    private void createTooltip() {
        for (int i = 0; i < column; i++) {
            tooltips.add(new Rectangle(hotbarX + tooltipTextPaddingX + (i % column * slotSpacing), hotbarY - tooltipTextPaddingY, 61, 15));
        }
    }

    private void createSlots() {
        for (int i = 0; i < column; i++) {
            slots.add(new Rectangle(hotbarX + highlightPadding + (i % column * slotSpacing), hotbarY + highlightPadding, slotSize, slotSize));
        }
    }

    private void loadBackground() {
        try {
            inventoryBackground = ImageIO.read(getClass().getResourceAsStream("/Background/inventory_background.png"));
            inventoryTooltip = ImageIO.read(getClass().getResourceAsStream("/Background/hotbarTooltip.png"));
            tooltipText = ImageIO.read(getClass().getResourceAsStream("/Font/tooltipText.png"));
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(inventoryBackground, hotbarX, hotbarY, null); //ritar backgrunden av hotbaren
        for (int i = 0; i < column; i++) {
            if (i == selectedSlot) { //ritar highlight runt en slot
                g2.setColor(Color.white);
                g2.drawRect(slots.get(i).x, slots.get(i).y, slotSize, slotSize);
                if (showTooltip) {
                    g2.drawImage(inventoryTooltip, slots.get(i).x, slots.get(i).y - tooltipPadding, 120, 40, null);
                    g2.drawImage(tooltipText, tooltips.get(i).x, tooltips.get(i).y, null);
                }
            }
        }
        for (int i = 0; i < inventory.getInventorySize(); i++) { //ritar item med rätt siffra av mängd item + bilden av itemen.
            g2.setColor(Color.white);
            g2.drawString(Integer.toString(inventory.getItem(i).getQuantity()), slots.get(i).x, slots.get(i).y + numberPadding);
            g2.drawImage(inventory.getItem(i).getItemImage(), slots.get(i).x + itemPaddingX, slots.get(i).y + itemPaddingY, null);
        }
    }
}
