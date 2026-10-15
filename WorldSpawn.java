
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldSpawn {

    private final TileManager tm;
    private final Map<Item,Point2D> groundItems = new HashMap<>();
    private final ArrayList<Rectangle> itemBounds = new ArrayList<>();

    public WorldSpawn(TileManager tm) {
        this.tm = tm;
        generateItems(25);
    }


    private void generateItems(int amount) {
        for (int i = 0; i < amount; i++) {
            groundItems.put(new Item("red berry", false, "/Sprites/redberry.png", 0), tm.findFreePosition());
            groundItems.put(new Item("purple berry", false, "/Sprites/purpleberry.png", 0), tm.findFreePosition());
        }
        generateBounds();
    }

    private void generateBounds() {
        for(Item x : groundItems.keySet()){
            itemBounds.add(new Rectangle((int) groundItems.get(x).getX(), (int) groundItems.get(x).getY(), 32, 32));
        }
    }
    public void handleCollision(Rectangle playerBounds){
        for (Rectangle x : itemBounds) {
            if (playerBounds.intersects(x)) {
                System.out.println("skriv meow");
        }
        }
        
    }


    public void draw(Graphics2D g2, Camera camera) {
        for(Item x : groundItems.keySet()){
            g2.drawImage(x.getItemImage(), (int) groundItems.get(x).getX() - (int) camera.getTx(), (int) groundItems.get(x).getY() - (int) camera.getTy(),null);
        }
    }
}
