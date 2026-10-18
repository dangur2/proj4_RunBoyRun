package level;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import entity.Player;
import item.GroundItem;
import item.Item;
import Camera.Camera;
import ui.GamePanel;
import audio.SoundEffect;

public class WorldSpawn {

    private final TileManager tm;
    private final ArrayList<GroundItem> groundItems = new ArrayList<>();
    private final ArrayList<Item> itemList = new ArrayList<>();
    

    public WorldSpawn(TileManager tm) {
        this.tm = tm;
        createItemList();
        generateItems(35);
    }

    private void createItemList() {
        itemList.add(new Item("red berry", true, "/assets/Sprites/redberry.png", 0,5));
        itemList.add(new Item("purple berry", true, "/assets/Sprites/purpleberry.png", 0,10));
        itemList.add(new Item("yellow berry", true, "/assets/Sprites/yellowberry.png", 0,15));
        itemList.add(new Item("pear", true, "/assets/Sprites/pear.png", 0,12));
        itemList.add(new Item("red apple", true, "/assets/Sprites/redapple.png", 0,7));
        itemList.add(new Item("green apple", true, "/assets/Sprites/greenapple.png", 0,12));
    }

    private void generateItems(int amount) {
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            groundItems.add(new GroundItem(itemList.get(r.nextInt(0,itemList.size())), tm.findFreePosition()));
        }
    }

    public void handleCollision(Player player, boolean ePressed, GamePanel ge) {
        for (GroundItem i : groundItems) {
            if (i.getBounds().intersects(player.getCollison()) && ePressed) {
                ge.playSound(SoundEffect.PICKUP);
                player.getInventory().addItem(i.getItem());
                groundItems.remove(i);
                break;
            }
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        for (GroundItem x : groundItems) {
            g2.drawImage(x.getItem().getItemImage(), (int) x.getBounds().getX() - (int) camera.getTx(), (int) x.getBounds().getY() - (int) camera.getTy(), null);
        }
    }
}
