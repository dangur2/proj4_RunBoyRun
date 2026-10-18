package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import app.GameEngine;
import audio.SoundEffect;
import entity.Player;
import input.ClickListener;
import input.KeyHandler;
import input.MouseHandler;
import level.MapType;
import level.TileManager;
import level.WorldSpawn;
import Camera.Camera;
import util.TimeScore;

public class GamePanel extends JPanel implements ClickListener {

    public final int tileSize = 32;
    public final int screenCol = 800 / tileSize;
    public final int screenRow = 480 / tileSize;
    public final int screenSizeX = screenCol * tileSize;
    public final int screenSizeY = screenRow * tileSize;
    public final int worldCol = 150;
    public final int worldRow = 150;
    public final int worldSizeX = worldCol * tileSize;
    public final int worldSizeY = worldRow * tileSize;

    private final KeyHandler kh = new KeyHandler();
    private final TileManager tm;
    private final Player player;
    private final Camera camera;
    private final InventoryView is;
    private final WorldSpawn worldSpawn;
    private final PlayerHealthView phv;
    private final TimeScoreView tsv;
    private final TimeScore ts;
    private final PlayerLostView plv;
    private final GameEngine ge;

    public GamePanel(MapType mapType, MouseHandler mh, GameEngine ge) {
        this.tm = new TileManager(this, mapType);
        this.player = new Player(tm, this);
        this.camera = new Camera(worldSizeX, worldSizeY, screenSizeX, screenSizeY);
        this.worldSpawn = new WorldSpawn(tm);
        this.phv = new PlayerHealthView();
        this.tsv = new TimeScoreView();
        this.ts = new TimeScore();
        this.is = new InventoryView(player, this, ts);
        this.plv = new PlayerLostView(ge);
        this.ge = ge;

        this.addKeyListener(kh);
        this.addMouseListener(mh);

        setPreferredSize(new Dimension(screenSizeX, screenSizeY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tm.draw(g2, camera);
        worldSpawn.draw(g2, camera);
        player.draw(g2, camera);
        is.draw(g2);
        phv.draw(g2, player.getHealth());
        tsv.draw(g2, ts.getTime(), ts.getScore());
        //om spelaren dör, rita detta också
        if (player.isDead()) {
            plv.draw(g2, ts.getTime(),ts.getScore());
        }
    }

    public void tick(double dt) {
        //medans spelaren lever
        if (!player.isDead()) {
            player.move(dt, kh.getDx(), kh.getDy(), worldSizeX, worldSizeY, tm);
            player.hurt(ts.getScore());
            ts.calculate();
            camera.follow(player);
            worldSpawn.handleCollision(player, kh.isePressed(), this);
        }
        repaint();
    }

    @Override
    public void OnLeftClick(int x, int y) {
        if (!player.isDead()) {
            is.LeftClick(x, y);
        }
        else {
            plv.OnLeftClick(x, y);
        }
    }
    @Override
    public void OnRightClick(int x, int y) {
        if (!player.isDead()) {
            is.RightClick(x, y);
        }
        
    }
    public void playSound(SoundEffect i){
        ge.playSound(i);
    }
}
