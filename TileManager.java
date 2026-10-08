import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class TileManager {
    Tile[] tile;
    private final int tileSize = 32;
    private final GamePanel gp;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        setTileImage();
    }
    public void setTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass_tile.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water_tile.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/brick_tile.png"));
        } catch (Exception e) {
        }
    }
    public void draw(Graphics2D g2){
        for(int i = 0; i < (gp.getWidth()); i+=32){
            for(int j = 0; j < gp.getHeight(); j+=32){
                g2.drawImage(tile[0].image,i,j,tileSize,tileSize,null);
            }
        }
    }
}
