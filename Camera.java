public class Camera {
    private double tx, ty;
    private final GamePanel gp;
    public Camera(GamePanel gp){
        this.gp = gp;
    }
    public void follow(Player player){
        tx = player.getX() + (player.playerSizeX / 2) - (gp.screenSizeX / 2); //tx är hur mycket du ska trycka allt åt sidan när du rör dig i X led
        ty = player.getY() + (player.playerSizeY / 2) - (gp.screenSizeY / 2); //ty är hur mycket du ska trycka allt åt sidan när du rör dig i Y led

        if (ty <= 0) {
            ty = 0;
        }
        if (ty >= gp.worldSizeY - gp.screenSizeY) {
            ty = gp.worldSizeY - gp.screenSizeY;
        }
        if (tx <= 0) {
            tx = 0;
        }
        if (tx >= gp.worldSizeX - gp.screenSizeX) {
            tx = gp.worldSizeX - gp.screenSizeX ;
        }

    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }
}
