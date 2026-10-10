
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity {

    public Player() {
        setStartingValues();
        setPlayerImage();
    }

    public int getPlayerX(){
        return playerSizeX;
    }
    public int getPlayerY(){
        return playerSizeY;
    }

    private void setStartingValues() {
        speed = 200;
        x = 750;
        y = 750;
        direction = Direction.IDLE;
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

    public void move(double dt, double dx, double dy, boolean wPressed, boolean aPressed, boolean sPressed, boolean dPressed, int worldSizeX, int worldsizeY) {
        double length = Math.sqrt(dx * dx + dy * dy); //1,0 = 1, 0,0 = 0, 1,1 = 1,4

        if (length == 0) {
            direction = Direction.IDLE;
            updateSprite();
            return; //Om jag inte rör mig, ingen mening att fortsätta
        }

        dx = dx / length; //normalisera värdet, ex. 0,7 = 1 / 1.4 (om två directions hålls inne samtidigt)
        dy = dy / length;

        
        x += dx * speed * dt; // 0.7 * 5 = 3.5 vid diagonalen gör samma speed i diagonalen som i cardinal directions
        y += dy * speed * dt;
        
        if (x < 0 ) {
            x = 0;
        } else if (x > worldSizeX - playerSizeX) {
            x = worldSizeX - playerSizeX;
        }
        if (y < 0) {
            y = 0;
        } else if (y > worldsizeY - playerSizeY) {
            y = worldsizeY - playerSizeY;
        }

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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
