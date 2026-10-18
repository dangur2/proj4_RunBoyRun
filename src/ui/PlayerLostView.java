
package ui;

import app.GameEngine;
import app.GameState;
import input.ClickListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import level.MapType;
public class PlayerLostView implements ClickListener {

    private final ArrayList<Rectangle> buttons = new ArrayList<>();
    private BufferedImage gameSummary;
    private final GameEngine ge;

    public PlayerLostView(GameEngine ge) {
        this.ge = ge;
        loadSprites();
        createBounds();
    }

    private void loadSprites() {
        try {
            gameSummary = ImageIO.read(getClass().getResourceAsStream("/assets/Background/Summary_background.png"));
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D g2, int time, int score) {
        g2.drawImage(gameSummary, 275, 150, null);
        
        g2.setColor(Color.black);
        g2.drawString(Integer.toString(time), 457, 243);
        g2.drawString(Integer.toString(score), 409, 220);
        g2.setColor(Color.white);
        g2.drawString(Integer.toString(time), 456, 243);
        g2.drawString(Integer.toString(score), 408, 220);
        
    }

    private void createBounds() {
        for (int i = 0; i < 2; i++) {
            buttons.add(new Rectangle(322 + (i * 107), 266, 47, 16));
        }
    }

    @Override
    public void OnLeftClick(int x, int y) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).contains(x, y)) {
                switch (i) {
                    case 0 -> { //retry
                        ge.setMapType(MapType.GENERATED);
                        ge.setCurrentState(GameState.PLAYING);
                    }
                    case 1 -> { //menu
                        ge.setCurrentState(GameState.MENU);
                    }
                }
            }
        }
    }

    @Override
    public void OnRightClick(int x, int y) {

    }
}
