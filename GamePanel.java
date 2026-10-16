import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
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
    private final InventoryScreen is;
    private final MouseHandler mh;
    private final WorldSpawn worldSpawn;
    private final PlayerHealthView phv;
    private final Sound sound;
    private final TimeScoreView tsv;
    private final TimeScore ts;

    public GamePanel(MapType mapType){
        this.tm = new TileManager(this, mapType);
        this.player = new Player(tm,this);
        this.camera = new Camera(worldSizeX,worldSizeY,screenSizeX,screenSizeY);
        this.worldSpawn = new WorldSpawn(tm);
        this.phv = new PlayerHealthView();
        this.sound = new Sound();
        this.tsv = new TimeScoreView();
        this.ts = new TimeScore();
        this.is = new InventoryScreen(player,this, ts);
        this.mh = new MouseHandler(is);

        this.addKeyListener(kh);
        this.addMouseListener(mh);
        playMusic();
        setPreferredSize(new Dimension(screenSizeX, screenSizeY));
    }
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tm.draw(g2, camera);
        worldSpawn.draw(g2, camera);
        player.draw(g2, camera);
        is.draw(g2);
        phv.draw(g2,player.getHealth());
        tsv.draw(g2, ts.getTime(), ts.getScore());
    }
    public void tick(double dt){
        player.move(dt, kh.getDx(), kh.getDy(), worldSizeX, worldSizeY, tm);
        player.hurt();
        ts.calculate();
        camera.follow(player);
        worldSpawn.handleCollision(player,kh.isePressed(), this);
        repaint();
    }
    public void playMusic(){
        sound.playMusic();
        sound.loopMusic();
    }
    public void stopMusic(){
        sound.stopMusic();
    }
    public void playSound(SoundEffect soundEffect){
        sound.playSound(soundEffect);
    }
}
