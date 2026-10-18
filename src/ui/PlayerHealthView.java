
package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PlayerHealthView {

    private BufferedImage healthBarSprite;


    public PlayerHealthView() {
        loadSprites();
    }
    private void loadSprites() {
        try {
            healthBarSprite = ImageIO.read(getClass().getResourceAsStream("/assets/Font/healthBar.png"));
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2, int playerHealth) {
        double fillHealth = ((double) playerHealth / 100) * (double) 146;
        g2.drawImage(healthBarSprite, 10, 20,null);
        g2.setColor(Color.red);
        g2.fillRect(12, 35, (int) fillHealth, 18);
    }
}
