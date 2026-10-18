package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TimeScoreView {
    private BufferedImage timeScore;

    public TimeScoreView() {
        loadSprites();
    }
    private void loadSprites() {
        try {
            timeScore = ImageIO.read(getClass().getResourceAsStream("/assets/Font/timeScore.png"));
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2, int time, int playerScore) {
        g2.drawImage(timeScore, 12, 60,null);
        
        g2.setFont(new Font("MONOSPACED", Font.BOLD, 18));

        //playerscore med 3d effekt.
        g2.setColor(Color.black);
        g2.drawString(Integer.toString(playerScore), 68, 72);
        g2.setColor(Color.white);
        g2.drawString(Integer.toString(playerScore), 67, 72);

        //time med 3d effekt.
        g2.setColor(Color.black);
        g2.drawString(Integer.toString(time), 61, 88);
        g2.setColor(Color.white);
        g2.drawString(Integer.toString(time), 60, 88);
        
        
    }
}
