package tile;

import main.Panel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    Panel panel;
    public Tile[] tile;
    public int mapTaleNum[][];

    public TileManager(Panel panel) {
        this.panel = panel;
        tile = new Tile[50];
        mapTaleNum = new int[panel.maxWorldCol][panel.maxWorldRow];

        getTileImage();
        loadMaps("/maps/map01.txt");
    }
    public void getTileImage() {
        //Forest
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(43, "grass00", false);


        setup(34, "grass01", false);
        setup(35, "tree", true);

        //House
        setup(36, "earth", false);
        setup(37, "wall", true);

        //Road
        setup(38, "road01", false);
        setup(39, "road02", false);
        setup(40, "road03", false);
        setup(41, "road04", false);
        setup(42, "road05", false);
        setup(10, "road06", false);
        setup(11, "road07", false);
        setup(12, "road08", false);
        setup(13, "road09", false);
        setup(14, "road10", false);
        setup(15, "road11", false);
        setup(16, "road12", false);
        setup(33, "road00", false);

        //Water
        setup(17, "water00", true);
        setup(18, "water01", true);
        setup(19, "water02", true);
        setup(20, "water03", true);
        setup(21, "water04", true);
        setup(22, "water05", true);
        setup(23, "water06", true);
        setup(24, "water07", true);
        setup(25, "water08", true);
        setup(26, "water09", true);
        setup(27, "water10", true);
        setup(28, "water11", true);
        setup(29, "water12", true);
        setup(30, "water10", true);
        setup(31, "water14", true);
        setup(32, "water15", true);

    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = utilityTool.scaleImage(tile[index].image, panel.tileSize, panel.tileSize);
            tile[index].collision = collision;

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMaps(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            int cols = 0;
            int rows = 0;

            while (cols < panel.maxWorldCol && rows < panel.maxWorldRow) {
                String line = bufferedReader.readLine();
                while (cols < panel.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[cols]);

                    mapTaleNum[cols][rows] = num;
                    cols++;
                }
                if (cols == panel.maxWorldCol) {
                    cols = 0;
                    rows++;
                }
            }
        bufferedReader.close();
        }catch (Exception e) {

        }
    }
    public void draw(Graphics2D graphics2) {
        int worldCols = 0;
        int worldRows = 0;

        while (worldCols < panel.maxWorldCol && worldRows < panel.maxWorldRow) {
            int tileNum = mapTaleNum[worldCols][worldRows];

            int worldX = worldCols * panel.tileSize;
            int worldY = worldRows * panel.tileSize;
            int screenX = worldX - panel.player.worldX +panel.player.screenX;
            int screenY = worldY - panel.player.worldY +panel.player.screenY;

            if (worldX + panel.tileSize > panel.player.worldX - panel.player.screenX &&
                worldX  - panel.tileSize < panel.player.worldX + panel.player.screenX &&
                worldY  + panel.tileSize > panel.player.worldY - panel.player.screenY &&
                worldY  - panel.tileSize < panel.player.worldY + panel.player.screenY) {
                graphics2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCols++;
            if (worldCols == panel.maxWorldCol) {
                worldCols = 0;
                worldRows++;
            }
        }
    }
}
