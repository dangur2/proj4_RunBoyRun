
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player extends Entity {

    private final Inventory inventory;

    public Player(TileManager tm) {
        collArea = new Rectangle(10,25,12,7);
        inventory = new Inventory(new ArrayList<>());
        setStartingValues(tm.findFreePosition());
        setPlayerImage();
    }

    public Inventory getInventory(){
        return this.inventory;
    }

    public int getPlayerX(){
        return playerSizeX;
    }
    public int getPlayerY(){
        return playerSizeY;
    }

    private void setStartingValues(Point p) {
        speed = 150;
        x = p.x;
        y = p.y;
        playerCollision = true;
        direction = Direction.IDLE;
        System.out.println("Spawn x: "+x);
        System.out.println("Spawn y: "+y);
    }

    private void setPlayerImage() {
        //förladda sprites när man gör gubben
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_up_walk1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_up_walk2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_down_walk1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_down_walk2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_left_walk1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_left_walk2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_right_walk1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_right_walk2.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/character_idle2.png"));
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("no find sprite amigo", e);
        }
    }

    public void move(double dt, double dx, double dy, int worldSizeX, int worldsizeY, TileManager tm) {
        double length = Math.sqrt(dx * dx + dy * dy); //1,0 = 1, 0,0 = 0, 1,1 = 1,4

        if (length == 0) {
            direction = Direction.IDLE;
            updateSprite();
            return; //Om jag inte rör mig, ingen mening att fortsätta
        }

        dx = dx / length; //normalisera värdet, ex. 0,7 = 1 / 1.4 (om två directions hålls inne samtidigt)
        dy = dy / length;

         //kolla om tilen man går mot är solid för collision
        
        double newX = x + dx * speed * dt;
        double newY = y + dy * speed * dt;

        //kolla om du kan gå höger eller vänster
        if(tm.canStandAt(newX, y, collArea)){
            x = newX;
        }
        //kolla om du kan gå upp eller ner
        if (tm.canStandAt(x, newY, collArea)){
            y = newY;
        }
        

        //updatera riktningen på spelaren för matcha animationen
        if (dx >= 1) {
            direction = Direction.RIGHT;
        }
        if (dx <= -1) {
            direction = Direction.LEFT;
        }
        if (dy >= 1) {
            direction = Direction.DOWN;
        }
        if (dy <= -1) {
            direction = Direction.UP;
        }
        updateSprite();
    }
    private void updateSprite(){
        spriteCounter++;
        //hastigheten av hur snabbt den byter sprite vid gång
        if (spriteCounter > 15) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        BufferedImage image = null;

        switch (direction) {
            case Direction.UP -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case Direction.DOWN -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case Direction.RIGHT -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
            case Direction.LEFT -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case Direction.IDLE -> {
                if (spriteNum == 1) {
                    image = idle1;
                }
                if (spriteNum == 2) {
                    image = idle2;
                }
            }
        }
        g2.drawImage(image, ((int) x - (int) camera.getTx()), ((int) y - (int) camera.getTy()), playerSizeX, playerSizeY, null);
    }
    public Rectangle getBounds(){
        return collArea;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
