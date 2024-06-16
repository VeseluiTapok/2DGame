package entity;

import main.KeyHandler;
import main.Panel;
import object.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public int ExpRemainder = 0;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    Entity crystal = new OBJ_ManaCrystal(panel);
    Entity heart = new OBJ_Heart(panel);
    public BufferedImage manaImage = crystal.image2;
    public BufferedImage heartImage = heart.image3;


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

        setAttacking();
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = panel.tileSize * 23;
        worldY = panel.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxHP = 10;
        currentHP = maxHP;
        maxMana = 5;
        currentMana = maxMana;
        ammo = 10;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(panel);
        currentShield = new OBJ_Shield_Wood(panel);
        strength1 = new OBJ_Strength(panel);
        defenceImage = new OBJ_Defence(panel);
        projectile = new OBJ_Fireball(panel);
        attack = getAttack();
        defence = getDefence();
    }

    public void setItems() {

        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return strength + currentWeapon.attackValue;
    }

    public int getDefence() {
        return dexterity + currentShield.defenceValue;
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
        if (currentWeapon.type == typeSword) {
            attackUp1 = setup("/player/boy_attack_up_1", panel.tileSize, panel.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", panel.tileSize, panel.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", panel.tileSize, panel.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_6", panel.tileSize, panel.tileSize * 2);
            attackRight1 = setup("/player/boy_attack_right_1", panel.tileSize * 2, panel.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", panel.tileSize * 2, panel.tileSize);
            attackLeft1 = setup("/player/boy_attack_left_1", panel.tileSize * 2, panel.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", panel.tileSize * 2, panel.tileSize);
        }
        if (currentWeapon.type == typeAxe) {
            attackUp1 = setup("/player/boy_axe_up_1", panel.tileSize, panel.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", panel.tileSize, panel.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", panel.tileSize, panel.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", panel.tileSize, panel.tileSize * 2);
            attackRight1 = setup("/player/boy_axe_right_1", panel.tileSize * 2, panel.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", panel.tileSize * 2, panel.tileSize);
            attackLeft1 = setup("/player/boy_axe_left_1", panel.tileSize * 2, panel.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", panel.tileSize * 2, panel.tileSize);
        }
    }

    public void update() {

        if (attacking == true) {
            attacking();
        }

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
                }
            }

            panel.keyHandler.enterPressed = false;

            if (attacking == false) {
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

        if (panel.keyHandler.shotKeyPressed == true && projectile.alive == false &&
                projectile.haveResource(this) == true) {

            //SET DEFAULT VALUES
            projectile.set(worldX, worldY, direction, true, this);

            //SUBTRACT THE COST
            projectile.subtractResource(this);

            //ADD IT TO LIST
            panel.projectileList.add(projectile);

            shotAvailableCounter = 0;
            readyToShot = false;
            startSpriteNum = 1;

            panel.playSoundEffect(12);
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        //HEAL HP, NEVER OPEN THIS
        if (currentMana < maxMana) {
            healManaCounter++;
            int i = 5;
            int a = 1;
            int b = 2;
            if (healManaCounter < i) {
                manaImage = crystal.image2;
            }
            if (healManaCounter >= i && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank1", panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank2", panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank3", panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            b++;
            if (healManaCounter >= i * a && healManaCounter < i * b) {
                manaImage = setup("/object/crystal/manacrystal_blank" + a, panel.tileSize, panel.tileSize);
            }
            a++;
            if (healManaCounter == i * a) {
                manaImage = crystal.image2;
                currentMana++;
                healManaCounter = 0;
            }
        }

        //HEAL MANA, NEVER OPEN THIS
        if (currentHP < maxHP) {
            healHPCounter++;
            int i = 13;
            int a = 1;
            int b = 2;
            if (healHPCounter < i) {
                if (currentHP % 2 == 0) {
                    heartImage = heart.image3;
                }
                if (currentHP % 2 != 0) {
                    heartImage = heart.image2;
                }
            }
            if (healHPCounter >= i && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            a++;
            b++;
            if (healHPCounter >= i * a && healHPCounter < i * b) {
                if (currentHP % 2 == 0) {
                    heartImage = setup("/object/Heart/heart_blank" + a, panel.tileSize, panel.tileSize);
                }
                if (currentHP % 2 != 0) {
                    heartImage = setup("/object/Heart/heart_blank" + (a + 44), panel.tileSize, panel.tileSize);
                }
            }
            if (healHPCounter == 597) {
                currentHP++;
                if (currentHP % 2 == 0) {
                    heartImage = heart.image3;
                }
                if (currentHP % 2 != 0) {
                    heartImage = heart.image2;
                }
                healHPCounter = 0;
            }
        }
    }

    public void attacking () {

        spriteCounter++;

        if (spriteCounter <= 10) {
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <= 25) {
            spriteNum = 2;

            // Save the current WorldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX, worldY for the attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
            }

            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //Check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = panel.checker.checkEntity(this, panel.monster);
            damageMonster(monsterIndex, attack);

            //restore to original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject (int index) {

        String text;

        if (index != 999) {
            // PICK UP ITEMS ONLY
            if (panel.object[index].type == typePickUpOnly) {

                panel.object[index].use(this);
                panel.object[index] = null;
            }

            //INVENTORY ITEMS
            else {
                if (inventory.size() != maxInventorySize) {

                    inventory.add(panel.object[index]);
                    panel.playSoundEffect(1);
                    text = "Got a " + panel.object[index].name + "!";

                } else {
                    text = "You cannot carry any more!";
                }
                panel.ui.addMessage(text);
                panel.object[index] = null;
            }
        }
    }

    public void setAttacking () {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    panel.playSoundEffect(7);
                    attacking = true;
                }
            }
        });
    }

    public void interactNPS ( int index) {
        if (index != 999) {
            if (panel.keyHandler.enterPressed == true) {
                panel.gameState = panel.dialogueState;
                panel.nps[index].speak();
            }
        }
    }

    public void contactMonster ( int index) {
        if (index != 999) {
            if (invincible == false && panel.monster[index].dying == false) {
                panel.playSoundEffect(6);

                int damage = panel.monster[index].attack - defence;
                if (damage < 0) {
                    damage = 0;
                }

                currentHP -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster ( int index, int attack) {

        if (index != 999) {

            if (panel.monster[index].invincible == false) {

                panel.playSoundEffect(5);

                int damage = attack - panel.monster[index].defence;
                if (damage < 0) {
                    damage = 0;
                }

                panel.monster[index].currentHP -= damage;

                panel.ui.addMessage(damage + " damage!");

                panel.monster[index].invincible = true;
                panel.monster[index].damageReaction();

                if (panel.monster[index].currentHP <= 0) {
                    panel.monster[index].dying = true;
                    panel.ui.addMessage("killed the " + panel.monster[index].name + "!");
                    exp += panel.monster[index].exp;
                    panel.ui.addMessage("+" + panel.monster[index].exp + " exp!");
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp () {

        if (exp >= nextLevelExp) {

            ExpRemainder++;
            level++;
            nextLevelExp += (5 + ExpRemainder);

            panel.playSoundEffect(10);
            panel.gameState = panel.levelUpState;

        }
    }

    public void selectItem () {

        int itemIndex = panel.ui.getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == typeSword || selectedItem.type == typeAxe) {

                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == typeShield) {

                currentShield = selectedItem;
                defence = getDefence();
            }
            if (selectedItem.type == typeConsumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void draw (Graphics2D graphics2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking == true) {
                    tempScreenY = screenY - panel.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }

                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking == true) {
                    tempScreenX = screenX - panel.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
        }

        if (invincible == true) {
            graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        graphics2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset alpha
        graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
