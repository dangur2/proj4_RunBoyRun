
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean aPressed, dPressed, wPressed, sPressed;
    private double dx, dy;

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A ->
                aPressed = true;
            case KeyEvent.VK_D ->
                dPressed = true;
            case KeyEvent.VK_W ->
                wPressed = true;
            case KeyEvent.VK_S ->
                sPressed = true;
        }
        updateDirection();
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A ->
                aPressed = false;
            case KeyEvent.VK_D ->
                dPressed = false;
            case KeyEvent.VK_W ->
                wPressed = false;
            case KeyEvent.VK_S ->
                sPressed = false;
        }
        updateDirection();
    }

    private void updateDirection() {
        dx = 0;
        dy = 0;
        
        if(wPressed && !sPressed){
            dy = -1;
        } else if (sPressed && !wPressed){
            dy = 1;
        }
        if(aPressed && !dPressed){
            dx = -1;
        } else if (dPressed && !aPressed){
            dx = 1;
        }
    }
}
