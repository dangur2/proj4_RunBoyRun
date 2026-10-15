
import java.awt.Graphics2D;
import java.util.ArrayList;

public class WorldSpawn {

    private final TileManager tm;
    private final ArrayList<GroundItem> groundItems = new ArrayList<>();
    

    public WorldSpawn(TileManager tm) {
        this.tm = tm;
        generateItems(25);
    }

    private void generateItems(int amount) {
        for (int i = 0; i < amount; i++) {
            groundItems.add(new GroundItem(new Item("red berry", false, "/Sprites/redberry.png", 0), tm.findFreePosition()));
            groundItems.add(new GroundItem(new Item("purple berry", false, "/Sprites/purpleberry.png", 0), tm.findFreePosition()));
        }
    }

    public void handleCollision(Player player, boolean ePressed) {
        
        for (GroundItem i : groundItems) {
            if (i.getBounds().intersects(player.getCollison()) && ePressed) {
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
