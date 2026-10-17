
import java.awt.Point;
import java.awt.Rectangle;

public class GroundItem{
    private final Item item;
    private final Rectangle collision;
    
    public GroundItem(Item item, Point bounds){
        this.item = item;
        collision = new Rectangle((int)bounds.getX(), (int) bounds.getY(),32,32);
    }

    public Item getItem(){
        return item;
    }

    public Rectangle getBounds(){
        return collision;
    }
}
