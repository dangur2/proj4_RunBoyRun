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
    public final int worldCol = 50;
    public final int worldRow = 50;
    public final int worldSizeX = worldCol * tileSize;
    public final int worldSizeY = worldRow * tileSize;

    private final Player player = new Player();
    private final KeyHandler kh = new KeyHandler();
    private final TileManager tm = new TileManager(this);
    private final Camera camera = new Camera(this);

    public GamePanel(){
        this.addKeyListener(kh);
        setPreferredSize(new Dimension(screenSizeX, screenSizeY));
    }
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        System.out.println(getWidth() + " get() "+getHeight());
        System.out.println(screenSizeX + " screensize "+screenSizeY);
        Graphics2D g2 = (Graphics2D)g;
        tm.draw(g2, camera);
        player.draw(g2, camera);
    }
    public void tick(double dt){
        player.move(dt, kh.getDx(), kh.getDy(), kh.iswPressed(), kh.isaPressed(), kh.isdPressed(), kh.issPressed(), worldSizeX, worldSizeY);
        camera.follow(player);
        repaint();
    }
}
