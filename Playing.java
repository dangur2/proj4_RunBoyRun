import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Playing extends JPanel{

    private final CreateGround cg = new CreateGround();
    private final Player player = new Player(new ImageIcon(Player.class.getResource("/Sprites/character.png")));
    private final KeyHandler kh = new KeyHandler();

    public Playing(){
        setBackground(Color.blue);
        this.addKeyListener(kh);
    }
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(0,0,getWidth(),getHeight());
        ImageIcon i = player.getImage();
        i.paintIcon(this, g, (int) player.getX(), (int) player.getY());
        

    }
    public void tick(double dt){
        player.move(dt, kh.getDx(), kh.getDy());
        cg.update();
        repaint();
    }
}
