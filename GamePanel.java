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

    public GamePanel(MapType mapType){
        this.tm = new TileManager(this, mapType);
        this.player = new Player(tm);
        this.camera = new Camera(worldSizeX,worldSizeY,screenSizeX,screenSizeY);
        this.is = new InventoryScreen(player.getInventory());
        this.mh = new MouseHandler(is);
        this.worldSpawn = new WorldSpawn(tm);

        this.addKeyListener(kh);
        this.addMouseListener(mh);
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
        
    }
    public void tick(double dt){
        player.move(dt, kh.getDx(), kh.getDy(), worldSizeX, worldSizeY, tm);
        camera.follow(player);
        worldSpawn.handleCollision(player.getBounds());
        repaint();
    }
}
