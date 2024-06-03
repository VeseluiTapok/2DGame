package entity;

import main.KeyHandler;
import main.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(Panel panel ,KeyHandler keyHandler) {
        super(panel);

        this.keyHandler = keyHandler;

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
        worldX = panel.tileSize * 23;
        worldY = panel.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("/player/boyUp1");
        up2 = setup("/player/boyUp2");
        down1 = setup("/player/boyDown1");
        down2 = setup("/player/boyDown2");
        right1 = setup("/player/boyRight1");
        right2 = setup("/player/boyRight2");
        left1 = setup("/player/boyLeft1");
        left2 = setup("/player/boyLeft2");
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
            panel.checker.checkTile(this);

            //Check object collision
            int objectIndex = panel.checker.checkObject(this, true);
            pickUpObject(objectIndex);

            //Check NPS collision
            int npsIndex = panel.checker.checkEntity(this, panel.nps);
            interactNPS(npsIndex);

            //If collision is false, player can move
            if (collisionOn == false) {
                long time = System.nanoTime();
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
    }
    public void pickUpObject(int index) {
        if (index != 999) {

        }
    }

    public void interactNPS(int index) {
        if (index != 999) {
            if (panel.keyHandler.enterPressed == true) {
                panel.gameState = panel.dialogueState;
                panel.nps[index].speak();
            }
        }
        panel.keyHandler.enterPressed = false;
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
        graphics2.drawImage(image, screenX, screenY, null);

    }
}
