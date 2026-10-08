import java.awt.image.BufferedImage;

public class Entity {
    public double speed;
    public double x,y;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2,idle1,idle2;
    public enum Direction{UP,DOWN,RIGHT,LEFT,IDLE};
    public Direction direction;
    public int spriteSizeX = 64;
    public int spriteSizeY = 64;

    public int spriteCounter = 0;
    public int spriteNum = 1;

}
