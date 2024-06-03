package entity;

import main.Panel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    Panel panel;

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    public int spriteNum = 1;
    public int spriteCounter = 0;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultY, solidAreaDefaultX;
    public boolean collisionOn = false;
    public int updateLockCounter = 0;
    public String[] dialogues = new String[20];
    int dialogueIndex = 0;

    //CHARACTER STATUS

    public Entity(Panel panel) {
        this.panel = panel;

    }

    public void setAction() {}
    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        panel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (panel.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
    }
    public void update() {

        setAction();

        collisionOn = false;
        panel.checker.checkTile(this);
        panel.checker.checkObject(this, false);
        panel.checker.checkPlayer(this);

        //If collision is false, NPS can move
        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "boost":
                    worldX -= speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;
        int screenX = worldX - panel.player.worldX +panel.player.screenX;
        int screenY = worldY - panel.player.worldY +panel.player.screenY;

        if (worldX + panel.tileSize > panel.player.worldX - panel.player.screenX &&
                worldX  - panel.tileSize < panel.player.worldX + panel.player.screenX &&
                worldY  + panel.tileSize > panel.player.worldY - panel.player.screenY &&
                worldY  - panel.tileSize < panel.player.worldY + panel.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
            }

            graphics2D.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);
        }
    }

    public BufferedImage setup(String imagePath) {

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream( imagePath +".png"));
            image = utilityTool.scaleImage(image, panel.tileSize, panel.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
