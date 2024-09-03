package tileInteractive;

import entity.Entity;
import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends Entity {
    Panel panel;
    public boolean destructible = false;

    public InteractiveTile(Panel panel, int col, int row) {
        super(panel);
        this.panel = panel;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        return isCorrectItem;
    }

    public void playSoundEffect() {}

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public void update() {

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - panel.player.worldX +panel.player.screenX;
        int screenY = worldY - panel.player.worldY +panel.player.screenY;

        if (worldX + panel.tileSize > panel.player.worldX - panel.player.screenX &&
                worldX  - panel.tileSize < panel.player.worldX + panel.player.screenX &&
                worldY  + panel.tileSize > panel.player.worldY - panel.player.screenY &&
                worldY  - panel.tileSize < panel.player.worldY + panel.player.screenY) {

            graphics2D.drawImage(down1, screenX, screenY, null);
        }
    }
}
