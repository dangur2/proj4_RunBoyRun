
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

    private final Tile[] tile;

    private final GamePanel gp;
    private BufferedImage noise;
    private final int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        this.mapTileNum = new int[gp.worldCol][gp.worldRow];

        setTileImage();
        loadMap();
    }

    public void setTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass_tile.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water_tile.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/brick_tile.png"));
        } catch (IOException e) {
        }
    }

    public void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/Tiles/tile_Map.txt");) { //laddar in din textfil
            if (is == null) {
                throw new Exception("Map is null");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 0; i < gp.screenRow; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new Exception("line är null");
                }
                String numbers[] = line.split(" ");
                for (int j = 0; j < gp.screenCol; j++) {

                    int num = Integer.parseInt(numbers[j]);
                    mapTileNum[j][i] = num;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randomiseBackground() {
        int screenX = 800;
        int screenY = 480;
        noise = new BufferedImage(screenX, screenY, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < screenX; i++) {
            for (int j = 0; j < screenY; j++) {
                int red = (int) (Math.random() * 256);
                int green = (int) (Math.random() * 256);
                int blue = (int) (Math.random() * 256);
                int rgb = (red << 16) | (green << 8) | blue;
                //bitwise operators, << är en bit shift och | är en bitwise OR. Alltså shiftar bitsen av red 16 bits till vänster, bitsen av green 8 bits till vänster och blå är kvar.
                //Detta betyder att första 8 bitsen, vänstra channeln blir bitsen motsvarande rödas färg, mitten channeln blir motsvarande gröna färgens bits och blå stannar i högra channeln.
                // | sätter ihop channelsen och memorerar deras bits separat, så varje channel inte sätts ihop som hade gjorts om man adderade dom.
                noise.setRGB(i, j, rgb);
            }
        }
    }

    public void draw(Graphics2D g2, Camera camera) {
        for (int i = 0; i < gp.screenRow; i++) {
            for (int j = 0; j < gp.screenCol; j++) {
                g2.drawImage(tile[mapTileNum[j][i]].image, (j * (gp.tileSize) - (int) camera.getTx()), (i * (gp.tileSize)  - (int) camera.getTy()), gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
