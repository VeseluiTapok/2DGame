package object;

import main.Panel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image1, image2, image3;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultY = 0;
    public int solidAreaDefaultX = 0;
    UtilityTool utilityTool = new UtilityTool();

    public void draw(Graphics2D graphics2D, Panel panel) {

        int screenX = worldX - panel.player.worldX +panel.player.screenX;
        int screenY = worldY - panel.player.worldY +panel.player.screenY;

        if (worldX + panel.tileSize > panel.player.worldX - panel.player.screenX &&
                worldX  - panel.tileSize < panel.player.worldX + panel.player.screenX &&
                worldY  + panel.tileSize > panel.player.worldY - panel.player.screenY &&
                worldY  - panel.tileSize < panel.player.worldY + panel.player.screenY) {
            graphics2D.drawImage(image1, screenX, screenY, panel.tileSize, panel.tileSize, null);
        }
    }
}
