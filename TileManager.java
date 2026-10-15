
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import javax.imageio.ImageIO;

public class TileManager {

    private final Tile[] tile;

    private final GamePanel gp;
    private BufferedImage noise;
    private final int mapTileNum[][];

    public TileManager(GamePanel gp, MapType mapType) {
        this.gp = gp;
        tile = new Tile[10];
        this.mapTileNum = new int[gp.worldCol][gp.worldRow];

        setTileImage();

        switch (mapType) {
            case GENERATED ->
                generativeMap();
            case MAP ->
                loadMap();
        }

    }

    private void setTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass_tile.png"));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water_tile.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/brick_tile.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand_tile.png"));
            tile[3].collision = false;
        } catch (IOException e) {
        }
    }

    public boolean isSolid(double worldX, double worldY) {
        //kollar bounds åt spelaren, sålänge den inte överskrider världstorleken
        if (worldX < 0 || worldY < 0 || worldX >= gp.worldSizeX || worldY >= gp.worldSizeY) {
            return true;
        }
        return tile[mapTileNum[(int) worldX / gp.tileSize][(int) worldY / gp.tileSize]].collision;
    }

    //kollar collision åt spelaren
    public boolean canStandAt(double x, double y, Rectangle Hitbox) {
        double leftX = x + Hitbox.x;
        double rightX = leftX + Hitbox.width - 1;
        double topY = y + Hitbox.y;
        double bottomY = topY + Hitbox.height - 1;

        //true om någon av hörnen inte nuddar en solid tile
        return !(isSolid(leftX, topY) || isSolid(rightX, topY) || isSolid(leftX, bottomY) || isSolid(rightX, bottomY));
    }

    public Point findFreePosition() {
        Random random = new Random();
        int worldPadding = 10;
        for (int i = 0; i < 100; i++) {
                int x = random.nextInt(worldPadding, gp.worldCol-worldPadding) * gp.tileSize;
                int y = random.nextInt(worldPadding, gp.worldRow-worldPadding) * gp.tileSize;
                if (!(isSolid(x, y))) {
                    return new Point(x, y);
                }
        }
        return null;
    }

    private void generativeMap() {
        Random random = new Random();

        int spacing = 10;
        int gridCols = (gp.worldCol / spacing) + 2; //dela upp världen i en mindre rutnät: var tioende tile har en random siffra, som "flaggor"
        int gridRows = (gp.worldRow / spacing) + 2;
        double grid[][] = new double[gridCols][gridRows];

        for (int a = 0; a < gridRows; a++) {
            for (int b = 0; b < gridCols; b++) {
                grid[b][a] = random.nextDouble(); //sätter en random 0-1 siffra på varje "flagga" i världen
            }
        }
        for (int i = 0; i < gp.worldRow; i++) {
            for (int j = 0; j < gp.worldCol; j++) {
                int a = j / spacing; //vilken ruta den aktuella tilen tillhör i det mindre 9x9
                int b = i / spacing;

                //detta räknar ut tilens nuvarande position till flaggan, från 0 till 1. 0 = vänster flagga, 0.9 = höger flagga
                double tx = (j % spacing) / (double) spacing;  //2 % 10 = 2 / 10 = 0.2, alltså värdet på tilen i korrelation mellan vänster och höger flagga. 
                double ty = (i % spacing) / (double) spacing;  // samma som ovan för Y

                //linear interpolation
                double top = lerp(grid[a][b], grid[a + 1][b], tx); //rutans övre kant, som blandar värde mellan vänster och höger flagga
                double bottom = lerp(grid[a][b + 1], grid[a + 1][b + 1], tx); //rutans undre kant, gör samma sak
                double value = lerp(top, bottom, ty); //blandar övre och undre kanten för att mjukt värde

                if (value < 0.32) {
                    mapTileNum[j][i] = 1; //vatten
                } else if (value < 0.44) {
                    mapTileNum[j][i] = 3;//sand
                } else {
                    mapTileNum[j][i] = 0;//gräs
                }

            }
        }
    }

    private double lerp(double a, double b, double t) {
        return a + (b - a) * t; //Linjär interpolation för att räkna ut ett värde mellan punkt A och punkt B.
        //T finns för att bestämma hur långt vi kommit mellan A och B. T:0 = A, T:0.5 = halvvägs till B, T:1 = B
    }

    private void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/Maps/tile_Map2.txt");) { //laddar in din textfil
            if (is == null) {
                throw new Exception("Map is null");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int i = 0; i < gp.worldRow; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new Exception("line är null");
                }
                String numbers[] = line.split(" ");
                for (int j = 0; j < gp.worldCol; j++) {
                    int num = Integer.parseInt(numbers[j]);
                    mapTileNum[j][i] = num;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void randomiseBackground() {
        noise = new BufferedImage(gp.screenSizeX, gp.screenSizeY, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < gp.screenSizeX; i += gp.tileSize) {
            for (int j = 0; j < gp.screenSizeY; j += gp.tileSize) {
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

        int startCol = Math.max(0, (int) camera.getTx() / gp.tileSize);
        int endCol = Math.min(gp.worldCol, ((int) camera.getTx() + gp.screenSizeX) / gp.tileSize + 1);
        int startRow = Math.max(0, (int) camera.getTy() / gp.tileSize);
        int endRow = Math.min(gp.worldRow, ((int) camera.getTy() + gp.screenSizeY) / gp.tileSize + 1);

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                g2.drawImage(tile[mapTileNum[j][i]].image, (j * (gp.tileSize) - (int) camera.getTx()), (i * (gp.tileSize) - (int) camera.getTy()), gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
