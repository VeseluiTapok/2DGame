package tile;

import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
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
        tile = new Tile[10];
        mapTaleNum = new int[panel.maxWorldCol][panel.maxWorldRow];

        getTileImage();
        loadMaps("/maps/map01.txt");
    }
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pidloga.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stena.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/doroga.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[7].collision = true;

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
                graphics2.drawImage(tile[tileNum].image, screenX, screenY, panel.tileSize, panel.tileSize, null);
            }

            worldCols++;
            if (worldCols == panel.maxWorldCol) {
                worldCols = 0;
                worldRows++;
            }
        }
    }
}
