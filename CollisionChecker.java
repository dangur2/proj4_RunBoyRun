public class CollisionChecker {
    private GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        double entityLeftX = entity.x + entity.collArea.x;
        double entityRightX = entity.x + entity.collArea.x + entity.collArea.width;
        double entityTopY = entity.y + entity.collArea.y;
        double entityBottomY = entity.y + entity.collArea.y + entity.collArea.height;

        double entityLeftCol = entityLeftX/gp.tileSize;
        double entityRightCol = entityRightX/gp.tileSize;
        double entityTopCol = entityTopY/gp.tileSize;
        double entityBottomCol = entityBottomY/gp.tileSize;

        int tileNum1,tileNum2;

        switch (entity.direction) {
            case UP -> {

            }
            case DOWN -> {

            }
            case LEFT -> {

            }
            case RIGHT -> {

            }
            case IDLE -> {

            }  
        }
    }
}
