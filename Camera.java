public class Camera {
    private double tx, ty;
    private final int worldSizeX, worldSizeY, screenSizeX,screenSizeY;
    public Camera(int worldSizeX, int worldSizeY, int screenSizeX, int screenSizeY){
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
    }
    public void follow(Player player){
        tx = player.getX() + (player.playerSizeX / 2) - (screenSizeX / 2); //tx är hur mycket du ska trycka allt åt sidan när du rör dig i X led
        ty = player.getY() + (player.playerSizeY / 2) - (screenSizeY / 2); //ty är hur mycket du ska trycka allt åt sidan när du rör dig i Y led

        if (ty <= 0) {
            ty = 0;
        }
        if (ty >= worldSizeY - screenSizeY) {
            ty = worldSizeY - screenSizeY;
        }
        if (tx <= 0) {
            tx = 0;
        }
        if (tx >= worldSizeX - screenSizeX) {
            tx = worldSizeX - screenSizeX ;
        }

    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }
}
