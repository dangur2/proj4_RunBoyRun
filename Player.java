

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{
    public Player(){
        setStartingValues();
        setPlayerImage();
    }
    private void setStartingValues(){
        speed = 200;
        x = 350;
        y = 200;
        direction = Direction.IDLE;
    }

    private void setPlayerImage(){
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
            idle = ImageIO.read(getClass().getResourceAsStream("/Sprites/character.png"));
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("no find sprite amigo", e);
        }
    }
    public void move(double dt, double dx, double dy){

        double length = Math.sqrt(dx*dx + dy*dy); //1,0 = 1, 0,0 = 0, 1,1 = 1,4

        if (length == 0) {
            return; //Om jag inte rör mig, ingen mening att fortsätta
        }
        
        dx = dx / length; //normalisera värdet, ex. 0,7 = 1 / 1.4 (om två directions hålls inne samtidigt)
        dy = dy / length;

        x += dx * speed * dt; // 0.7 * 5 = 3.5 vid diagonalen gör samma speed i diagonalen som i cardinal directions
        y += dy * speed * dt;

        if(dx == 0 && dy == 0){
            direction = Direction.IDLE;
        }
        if(dx >= 1){
            direction = Direction.RIGHT;
        }
        if(dx <= -1){
            direction = Direction.LEFT;
        }
        if(dy >= 1){
            direction = Direction.DOWN;
        }
        if(dy <= -1){
            direction = Direction.UP;
        }

        spriteCounter++;
        if(spriteCounter > 15){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction){
            case Direction.UP -> {
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                
            }
            case Direction.DOWN -> {
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
            }
            case Direction.RIGHT-> {
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
            }
            case Direction.LEFT -> {
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
            }
            case Direction.IDLE -> {
                image = idle;
            }
        }
        g2.drawImage(image, (int) x, (int) y, spriteSizeX, spriteSizeY, null);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
