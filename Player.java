

import javax.swing.ImageIcon;

public class Player {
    private double x,y;

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    private final ImageIcon charSprite;
    private final double playerSpeed = 200;

    public Player(ImageIcon image){
        charSprite = image;
    }
    public ImageIcon getImage(){
        return charSprite;
    }
    public void move(double dt, double dx, double dy){

        double length = Math.sqrt(dx*dx + dy*dy); //1,0 = 1, 0,0 = 0, 1,1 = 1,4

        if (length == 0) {
            return; //Om jag inte rör mig, ingen mening att fortsätta
        }
        dx = dx / length; //normalisera värdet, ex. 0,7 = 1 / 1.4 (om två directions hålls inne samtidigt)
        dy = dy / length;


        x += dx * playerSpeed * dt; // 0.7 * 5 = 3.5 vid diagonalen gör samma speed i diagonalen som i cardinal directions
        y += dy * playerSpeed * dt;
    }
}
