
package app;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import audio.Sound;
import audio.SoundEffect;
import input.MouseHandler;
import level.MapType;
import ui.TitleScreen;
import ui.CreateUI;
import ui.GamePanel;

public class GameEngine implements Runnable {

    private final CreateUI ui;
    private final int FPS = 60;
    private Thread gameThread;
    private volatile GameState currState;
    private final TitleScreen ts;
    private GamePanel gp;
    private final MouseHandler mh;
    private final Sound sound;
    private volatile boolean running;

    public GameEngine(CreateUI ui) {
        running = true;
        this.ui = ui;
        this.mh = new MouseHandler();
        this.ts = new TitleScreen(this);
        this.sound = new Sound();
        mh.setActive(ts);

        setCurrentState(GameState.MENU);

        sound.playMusic();
        sound.loopMusic();
        startThread();
    }

    public MouseHandler getMh() {
        return mh;
    }

    public void setCurrentState(GameState newState) {
        switch (newState) {
            case MENU -> {
                showPanel(ts);
                mh.setActive(ts);
            }
            case PLAYING -> {
                showPanel(gp);
                mh.setActive(gp);
            }
            case PAUSE -> {}
            case LOSE -> {}
        }
        this.currState = newState;
    }

    private void showPanel(JPanel x) {
        SwingUtilities.invokeLater(() -> {
            ui.setContentPane(x);
            ui.pack();
            x.requestFocusInWindow();
            ui.revalidate();
            ui.repaint();
        });
    }

    private void startThread() {
        gameThread = new Thread(this);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    public void setMapType(MapType mapType) {
        gp = new GamePanel(mapType, getMh(), this);
    }

    @Override
    public void run() {

        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        double dt = 1.0 / FPS;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                switch (currState) {
                    case PLAYING -> {
                        gp.tick(dt);
                    }
                    case LOSE -> {
                    }
                    case MENU -> {
                    }
                    case PAUSE -> {
                    }
                }
                delta--;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    public void playSound(SoundEffect soundEffect) {
        sound.playSound(soundEffect);
    }
}
