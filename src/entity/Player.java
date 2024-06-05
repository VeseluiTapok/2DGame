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
        getPlayerAttackImage();
    }
    public void setDefaultValues() {
        worldX = panel.tileSize * 23;
        worldY = panel.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxHP = 10;
        currentHP = maxHP;
    }

    public void getPlayerImage() {
        up1 = setup("/player/boyUp1", panel.tileSize, panel.tileSize);
        up2 = setup("/player/boyUp2", panel.tileSize, panel.tileSize);
        down1 = setup("/player/boyDown1", panel.tileSize, panel.tileSize);
        down2 = setup("/player/boyDown2", panel.tileSize, panel.tileSize);
        right1 = setup("/player/boyRight1", panel.tileSize, panel.tileSize);
        right2 = setup("/player/boyRight2", panel.tileSize, panel.tileSize);
        left1 = setup("/player/boyLeft1", panel.tileSize, panel.tileSize);
        left2 = setup("/player/boyLeft2", panel.tileSize, panel.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/boy_attack_up_1", panel.tileSize, panel.tileSize*2);
        attackUp2 = setup("/player/boy_attack_up_2", panel.tileSize, panel.tileSize*2);
        attackDown1 = setup("/player/boy_attack_down_1", panel.tileSize, panel.tileSize*2);
        attackDown2 = setup("/player/boy_attack_down_2", panel.tileSize, panel.tileSize*2);
        attackRight1 = setup("/player/boy_attack_right_1", panel.tileSize*2, panel.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2", panel.tileSize*2, panel.tileSize);
        attackLeft1 = setup("/player/boy_attack_left_1", panel.tileSize*2, panel.tileSize);
        attackLeft2 = setup("/player/boy_attack_left_2", panel.tileSize*2, panel.tileSize);
    }

    public void update() {
        if (keyHandler.upPressed == true || keyHandler.downPressed == true ||
                keyHandler.rightPressed == true || keyHandler.leftPressed == true ||
                keyHandler.enterPressed == true) {

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

            //Check monster collision
            int monsterIndex = panel.checker.checkEntity(this, panel.monster);
            contactMonster(monsterIndex);

            //Check event
            panel.eventHandler.checkEvent();

            //If collision is false, player can move
            if (collisionOn == false && keyHandler.enterPressed == false) {
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

            panel.keyHandler.enterPressed = false;

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
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
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
    }

    public void contactMonster(int index) {
        if (index != 999) {
            if (invincible == false) {
                currentHP -= 1;
                invincible = true;
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

        if (invincible == true) {
                graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        graphics2.drawImage(image, screenX, screenY, null);

        //Reset alpha
        graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
