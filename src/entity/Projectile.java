package entity;

import main.Panel;
import object.OBJ_Fireball;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity{

    Panel panel;
    Entity user;
    public boolean haveStartAnimation = false;

    public Projectile(Panel panel) {
        super(panel);

        this.panel = panel;

    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        currentHP = maxHP;
    }

    public void update() {
        if (user == panel.player) {
            int monsterIndex = panel.checker.checkEntity(this, panel.monster);
            if (monsterIndex != 999) {
                panel.player.damageMonster(monsterIndex, attack);
                generateParticle(user.projectile, panel.monster[panel.currentMap][monsterIndex]);
                alive = false;
                startSpriteNum = 1;
                readyToShot = false;
            }
        }
        else {
            boolean contactPlayer = panel.checker.checkPlayer(this);
            if (panel.player.invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                generateParticle(user.projectile, panel.player);
                alive = false;
                startSpriteNum = 1;
                readyToShot = false;
            }
        }

        switch (direction) {
            case "up":
                if (readyToShot == true) {
                    worldY -= speed;
                }
                break;
            case "down":
                if (readyToShot == true) {
                    worldY += speed;
                }
                break;
            case "right":
                if (readyToShot == true) {
                    worldX += speed;
                }
                break;
            case "left":
                if (readyToShot == true) {
                    worldX -= speed;
                }
                break;
        }


        int counter = 5;
        if (readyToShot == false) {
            readyToShotCounter++;
            if (readyToShotCounter <= counter) {
                startSpriteNum = 1;
            }
            if (readyToShotCounter > counter && readyToShotCounter <= counter * 2) {
                startSpriteNum = 2;
            }
            if (readyToShotCounter > counter * 2 && readyToShotCounter <= counter * 3) {
                startSpriteNum = 3;
            }
            if (readyToShotCounter > counter * 3 && readyToShotCounter <= counter * 4) {
                startSpriteNum = 4;
            }
            if (readyToShotCounter > counter * 4 && readyToShotCounter <= counter * 5) {
                startSpriteNum = 5;
            }
            if (readyToShotCounter > counter * 5 && readyToShotCounter <= counter * 6) {
                startSpriteNum = 6;
            }
            if (readyToShotCounter > counter * 6 && readyToShotCounter <= counter * 7) {
                startSpriteNum = 7;
            }
            if (readyToShotCounter > counter * 7 && readyToShotCounter <= counter * 8) {
                startSpriteNum = 8;
            }
            if (readyToShotCounter > counter * 8 && readyToShotCounter <= counter * 9) {
                startSpriteNum = 9;
            }
            if (readyToShotCounter > counter * 9 && readyToShotCounter <= counter * 10) {
                startSpriteNum = 10;
            }
            if (readyToShotCounter > counter * 10 && readyToShotCounter <= counter * 11) {
                startSpriteNum = 11;
            }
            if (readyToShotCounter > counter * 11) {
                startSpriteNum = 12;
                readyToShotCounter = 0;
                readyToShot = true;
            }
        }

        currentHP--;
        if (currentHP == 0) {
            alive = false;
            readyToShot = false;
            startSpriteNum = 1;
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
        int screenX = worldX - panel.player.worldX + panel.player.screenX;
        int screenY = worldY - panel.player.worldY + panel.player.screenY;

        if (worldX + panel.tileSize > panel.player.worldX - panel.player.screenX &&
                worldX  - panel.tileSize < panel.player.worldX + panel.player.screenX &&
                worldY  + panel.tileSize > panel.player.worldY - panel.player.screenY &&
                worldY  - panel.tileSize < panel.player.worldY + panel.player.screenY) {

            switch (direction) {
                case "up":
                    screenY -= panel.tileSize;
                    if (readyToShot == true) {
                        if (spriteNum == 1) {
                            image = up1;
                        }
                        if (spriteNum == 2) {
                            image = up2;
                        }
                    }
                    break;
                case "down":
                    screenY += panel.tileSize;
                    if (readyToShot == true) {
                        if (spriteNum == 1) {
                            image = down1;
                        }
                        if (spriteNum == 2) {
                            image = down2;
                        }
                    }
                    break;
                case "right":
                    screenX += panel.tileSize;
                    if (readyToShot == true) {
                        if (spriteNum == 1) {
                            image = right1;
                        }
                        if (spriteNum == 2) {
                            image = right2;
                        }
                    }
                    break;
                case "left":
                    screenX -= panel.tileSize;
                    if (readyToShot == true) {
                        if (spriteNum == 1) {
                            image = left1;
                        }
                        if (spriteNum == 2) {
                            image = left2;
                        }
                    }
                    break;
            }

            if (readyToShot == false) {
                if (startSpriteNum == 1) {
                    image = start1;
                }
                if (startSpriteNum == 2) {
                    image = start2;
                }
                if (startSpriteNum == 3) {
                    image = start3;
                }
                if (startSpriteNum == 4) {
                    image = start4;
                }
                if (startSpriteNum == 5) {
                    image = start5;
                }
                if (startSpriteNum == 6) {
                    image = start6;
                }
                if (startSpriteNum == 7) {
                    image = start7;
                }
                if (startSpriteNum == 8) {
                    image = start8;
                }
                if (startSpriteNum == 9) {
                    image = start9;
                }
                if (startSpriteNum == 10) {
                    image = start10;
                }
                if (startSpriteNum == 11) {
                    image = start11;
                }
                if (startSpriteNum == 12) {
                    image = start12;
                }
            }

            graphics2D.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);

            changeAlpha(graphics2D, 1f);
        }
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        return haveResource;
    }

    public void subtractResource(Entity user) {}
}
