package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import app.GameEngine;
import app.GameState;
import input.ClickListener;
import level.MapType;

public class TitleScreen extends JPanel implements ClickListener {

    private BufferedImage title, backGround, playButton, exitButton;
    private final ArrayList<Rectangle> buttons = new ArrayList<>();
    private final GameEngine ge;

    public TitleScreen(GameEngine ge) {
        this.ge = ge;
        setPreferredSize(new Dimension(800, 480));
        loadSprites();
        createButtonBounds();
        addMouseListener(ge.getMh());
        

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, null);
        g.drawImage(title, 295, 150, null);
        g.drawImage(playButton, 319, 210, null);
        g.drawImage(exitButton, 319, 260, null);
    }

    private void createButtonBounds() {
        for (int i = 0; i < 2; i++) {
            buttons.add(new Rectangle(380, 219 + (i * 50), 48, 60));
        }
    }

    private void loadSprites() {
        try {
            playButton = ImageIO.read(getClass().getResourceAsStream("/assets/Font/playButton.png"));
            exitButton = ImageIO.read(getClass().getResourceAsStream("/assets/Font/exitButton.png"));
            backGround = ImageIO.read(getClass().getResourceAsStream("/assets/Background/Background_1.png"));
            title = ImageIO.read(getClass().getResourceAsStream("/assets/Font/Title.png"));
        } catch (Exception e) {
        }
    }

    @Override
    public void OnLeftClick(int x, int y) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).contains(x, y)) {
                switch (i) {
                    case 0 -> {
                        ge.setMapType(MapType.GENERATED);
                        ge.setCurrentState(GameState.PLAYING);
                    }
                    case 1 ->
                        System.exit(0);
                }
            }
        }
    }

    @Override
    public void OnRightClick(int x, int y) {

    }
}
