
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

    private final Tile[] tile;
    private final int screenCol = 25;
    private final int screenRow = 15;
    private final int tileSize = 32;
    private final GamePanel gp;
    private BufferedImage noise;
    private final int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        this.mapTileNum = new int[screenCol][screenRow];

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
        } catch (Exception e) {
        }
    }

    public void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/Tiles/tile_Map.txt");) { //laddar in din textfil
            if (is == null) {
                throw new Exception("Map is null");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col < screenCol && row < screenRow) {
                String line = br.readLine(); //läser den nuvarande raden i din textfil
                if (line == null) {
                    throw new Exception("Text in file is null");
                }
                while (col < screenCol) {
                    String numbers[] = line.split(" "); //delar raden vid varje mellanslag och stoppar siffran, som blir en string, i numbers
                    int num = Integer.parseInt(numbers[col]); //gör siffran tillbaka till int
                    mapTileNum[col][row] = num; //lägger in det numren i mapTileNum i indexen col, row. Så mapTileNum[0][0] = tile[0], mapTileNum[1][0] = tile[0]
                    col++;
                }
                if (col == screenCol) { //resettar till första kolumnen men nästa rad om man når sista kolumnen
                    col = 0;
                    row++;
                }

            }
        } catch (Exception e) {
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

    public void draw(Graphics2D g2) {
        // randomiseBackground();
        // g2.drawImage(noise, 0,0,null);

        for (int i = 0; i < screenRow; i++) {
            for (int j = 0; j < screenCol; j++) {
                g2.drawImage(tile[mapTileNum[j][i]].image, j*tileSize, i*tileSize, tileSize, tileSize, null);
            }
        }
        // int col = 0;
        // int row = 0;

        // for (int i = 0; i < gp.getHeight(); i+=tileSize) {
        //     for (int j = 0; j < gp.getWidth(); j+=tileSize) {
        //         int tileNum = mapTileNum[col][row];
        //         g2.drawImage(tile[tileNum].image, j, i, tileSize, tileSize, null);
        //         col ++;
        //         if (col == screenCol) {
        //             col = 0;
        //             row+=1;
        //         }
        //     }
        // }
    }
}
