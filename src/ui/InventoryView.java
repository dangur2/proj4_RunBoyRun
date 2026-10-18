package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import audio.SoundEffect;
import entity.Player;
import util.TimeScore;

public class InventoryView{
    //bry dig inte om alla dessa variabler :3
    private BufferedImage inventoryBackground, inventoryTooltip, tooltipDestroy, tooltipUse;
    private final Player player;
    private final int slotSpacing = 49;
    private final int itemPaddingY = 7;
    private final int itemPaddingX = 5;
    private final int numberPadding = 10;
    private final int highlightPadding = 6;
    private final int hotbarX = 191;
    private final int hotbarY = 415;
    private final int column = 9;
    private final int slotSize = 46;
    private final int tooltipPadding = 65;
    private final int tooltipTextPaddingX = 35;
    private final int tooltipTextPaddingY = 30;
    private final ArrayList<Rectangle> slots = new ArrayList<>();
    private final ArrayList<Rectangle> destroyTooltip = new ArrayList<>();
    private final ArrayList<Rectangle> useTooltip = new ArrayList<>();
    private boolean showTooltip = false;
    private int selectedSlot;
    private final TimeScore ts;
    private final GamePanel gp;

    public InventoryView(Player player, GamePanel gp, TimeScore ts) {
        this.player = player;
        this.ts = ts;
        this.gp = gp;
        loadBackground();
        createSlots();
        createDestroyTooltip();
        createUseTooltip();
    }


    public void LeftClick(int x, int y) {
        //ta bort en item om du vänsterklickar på tooltippen när den är uppe
        if (destroyTooltip.get(selectedSlot).contains(x, y) && showTooltip) {
            player.getInventory().removeItem(selectedSlot);
            showTooltip = false;
            return;
        }
        if (useTooltip.get(selectedSlot).contains(x,y) && showTooltip) {
            int heal = player.getInventory().useItem(player.getInventory().getItem(selectedSlot));
            player.increaseHealth(heal);
            ts.addScore(heal);
            gp.playSound(SoundEffect.USE);
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


    public void RightClick(int x, int y) {
        //om du högerklickar en slot, oavsett om den är highlightad eller inte, sålänge den har en item, öppna tooltip på den itemen
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).contains(x, y)) {
                selectedSlot = i;
                if (slots.get(selectedSlot).contains(x, y) && selectedSlot < player.getInventory().getInventorySize()) {
                    showTooltip = !showTooltip;
                }
            }
        }
    }


    private void createDestroyTooltip() {
        for (int i = 0; i < column; i++) {
            destroyTooltip.add(new Rectangle(hotbarX + tooltipTextPaddingX + (i % column * slotSpacing), hotbarY - tooltipTextPaddingY, 61, 15));
        }
    }
    private void createUseTooltip() {
        for (int i = 0; i < column; i++) {
            useTooltip.add(new Rectangle(hotbarX + tooltipTextPaddingX + (i % column * slotSpacing), hotbarY - (tooltipTextPaddingY+20), 61, 15));
        }
    }

    private void createSlots() {
        for (int i = 0; i < column; i++) {
            slots.add(new Rectangle(hotbarX + highlightPadding + (i % column * slotSpacing), hotbarY + highlightPadding, slotSize, slotSize));
        }
    }

    private void loadBackground() {
        try {
            inventoryBackground = ImageIO.read(getClass().getResourceAsStream("/assets/Background/inventory_background.png"));
            inventoryTooltip = ImageIO.read(getClass().getResourceAsStream("/assets/Background/hotbarTooltip.png"));
            tooltipDestroy = ImageIO.read(getClass().getResourceAsStream("/assets/Font/tooltipDestroy.png"));
            tooltipUse = ImageIO.read(getClass().getResourceAsStream("/assets/Font/tooltipUse.png"));
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
                    g2.drawImage(inventoryTooltip, slots.get(i).x, slots.get(i).y - tooltipPadding, 120, 60, null);
                    g2.drawImage(tooltipDestroy, destroyTooltip.get(i).x, destroyTooltip.get(i).y, null);
                    g2.drawImage(tooltipUse, useTooltip.get(i).x, useTooltip.get(i).y, null);
                }
            }
        }
        for (int i = 0; i < player.getInventory().getInventorySize(); i++) { //ritar item med rätt siffra av mängd item + bilden av itemen.
            g2.setColor(Color.white);
            g2.drawString(Integer.toString(player.getInventory().getItem(i).getQuantity()), slots.get(i).x, slots.get(i).y + numberPadding);
            g2.drawImage(player.getInventory().getItem(i).getItemImage(), slots.get(i).x + itemPaddingX, slots.get(i).y + itemPaddingY, null);
        }
    }

    
}
