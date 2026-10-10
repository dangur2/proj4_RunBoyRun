public class Camera {
    private double tx, ty;
    private final GamePanel gp;
    public Camera(GamePanel gp){
        this.gp = gp;
    }
    public void follow(Player player){
        tx = player.getX() - (gp.screenSizeX / 2) + (gp.tileSize / 2); //400 - (800 / 2)  + 32
        ty = player.getY() - (gp.screenSizeY / 2) + (gp.tileSize / 2); 
        if (ty <= 0) {
            ty = 0;
        }
        if (ty >= gp.worldSizeY) {
            ty = gp.worldSizeY;
        }
        if (tx <= 0) {
            tx = 0;
        }
        if (tx >= gp.worldSizeX) {
            tx = gp.worldSizeX;
        }

    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }
}
