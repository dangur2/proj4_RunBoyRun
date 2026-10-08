import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Playing extends JPanel{

    private final CreateGround cg = new CreateGround();
    private final Player player = new Player();
    private final KeyHandler kh = new KeyHandler();

    public Playing(){
        setBackground(Color.blue);
        this.addKeyListener(kh);
    }
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.green);
        g2.fillRect(0,0,getWidth(),getHeight());
        player.draw(g2);
    }
    public void tick(double dt){
        player.move(dt, kh.getDx(), kh.getDy());
        cg.update();
        repaint();
    }
}
