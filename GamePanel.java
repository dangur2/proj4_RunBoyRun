import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel{

    private final Player player = new Player();
    private final KeyHandler kh = new KeyHandler();
    private final TileManager tm = new TileManager(this);

    public GamePanel(){
        setBackground(Color.blue);
        this.addKeyListener(kh);
    }
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tm.draw(g2);
        player.draw(g2);
    }
    public void tick(double dt){
        player.move(dt, kh.getDx(), kh.getDy(), kh.iswPressed(), kh.isaPressed(), kh.isdPressed(), kh.issPressed());
        repaint();
    }
}
