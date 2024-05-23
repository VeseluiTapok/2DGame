package entity;

import main.KeyHandler;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    Panel panel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    int hasKey = 0;

    public Player(Panel panel ,KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        this.panel = panel;

        screenX = panel.screenWidth/2 - (panel.tileSize/2);
        screenY = panel.screenHeight/2 - (panel.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = panel.tileSize * 24;
        worldY = panel.tileSize * 24;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boyUp1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boyUp2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boyDown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boyDown2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boyRight1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boyRight2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boyLeft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boyLeft2.png"));

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed == true || keyHandler.downPressed == true ||
                keyHandler.rightPressed == true || keyHandler.leftPressed == true) {
            if (keyHandler.upPressed == true) {
                direction = "up";

            } else if (keyHandler.downPressed == true) {
                direction = "down";

            } else if (keyHandler.rightPressed == true) {
                direction = "right";

            } else if (keyHandler.leftPressed == true) {
                direction = "left";

            }

            //Check tile collision
            collisionOn = false;
            panel.checker.CheckTile(this);

            //Check object collision
            int objectIndex = panel.checker.checkObject(this, true);
            pickUpObject(objectIndex);

            //If collision is false, player can move
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
    }
    public void pickUpObject(int index) {
        if (index != 999) {
            String objectName = panel.superObject[index].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    panel.superObject[index] = null;
                    System.out.println("Key: " + hasKey);
                    break;
                case "Door":
                    if (hasKey > 0) {
                        panel.superObject[index] = null;
                        hasKey--;
                        System.out.println("Key: " + hasKey);
                    }
                    break;
                case "GrassDoor":
                    if (hasKey > 0) {
                        panel.superObject[index] = null;
                        hasKey--;
                        System.out.println("Key: " + hasKey);
                    }
                    break;
                case "Chest":
                    break;
            }
        }
    }
    public void draw(Graphics2D graphics2) {
        BufferedImage image = null;

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
        graphics2.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);

    }
}
