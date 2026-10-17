
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    private ClickListener active;

    public MouseHandler() {
        
    }

    public void setActive(ClickListener active){
        this.active = active;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                active.OnLeftClick(e.getX(), e.getY());
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                active.OnRightClick(e.getX(), e.getY());
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
