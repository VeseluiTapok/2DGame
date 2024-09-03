package entity;

import main.Panel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    Panel panel;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackDown3,
    attackRight1, attackRight2, attackLeft1, attackLeft2;
    public BufferedImage start1, start2, start3, start4, start5, start6,
            start7, start8, start9, start10, start11, start12;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultY, solidAreaDefaultX;
    public String[] dialogues = new String[20];
    public BufferedImage image1, image2, image3;
    public boolean collision = false;

    //STATE
    public int worldX, worldY;
    public int startSpriteNum = 1;
    public int spriteNum = 1;
    public String direction = "down";
    public boolean collisionOn = false;
    public boolean invincible = false;
    int dialogueIndex = 0;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean HpBarOn = false;
    public boolean readyToShot = false;
    public boolean onPath = false;

    //COUNTER
    public int spriteCounter = 0;
    public int updateLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public int readyToShotCounter = 0;
    public int healManaCounter = 0;
    public int healHPCounter = 0;
    int dyingCounter = 0;
    int HpBarCounter = 0;

    //CHARACTER ATTRIBUTES
    public String name;
    public int maxHP;
    public int currentHP;
    public int maxMana;
    public int currentMana;
    public int ammo;
    public int speed;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity strength1;
    public Entity defenceImage;
    public Projectile projectile;

    //ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenceValue;
    public String description = "";
    public int useCost;
    public int price;

    //TYPE
    public int type;
    public final int typePlayer = 0;
    public final int typeNPS = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickUpOnly = 7;

    public Entity(Panel panel) {
        this.panel = panel;

    }

    public void setAction() {}

    public void damageReaction() {}

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

    public void use(Entity entity) {}

    public void checkDrop() {}

    public void dropItem(Entity droppedItem) {
        for (int index = 0; index < panel.object[1].length; index++) {
            if (panel.object[panel.currentMap][index] != null) {
                panel.object[panel.currentMap][index] = droppedItem;
                panel.object[panel.currentMap][index].worldX = worldX;
                panel.object[panel.currentMap][index].worldY = worldY;
                break;
            }
        }
    }

    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxHP() {
        int maxHP = 0;
        return maxHP;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxHP = generator.getParticleMaxHP();

        Particle p1 = new Particle(panel, target, color, size, speed, maxHP, -2, -1);
        Particle p2 = new Particle(panel, target, color, size, speed, maxHP, 2, -1);
        Particle p3 = new Particle(panel, target, color, size, speed, maxHP, -2, 1);
        Particle p4 = new Particle(panel, target, color, size, speed, maxHP, 2, 1);
        panel.particleList.add(p1);
        panel.particleList.add(p2);
        panel.particleList.add(p3);
        panel.particleList.add(p4);
    }

    public void checkCollision() {
        collisionOn = false;
        panel.checker.checkTile(this);
        panel.checker.checkObject(this, false);
        panel.checker.checkEntity(this, panel.nps);
        panel.checker.checkEntity(this, panel.monster);
        panel.checker.checkEntity(this, panel.interactiveTile);
        boolean contactPlayer = panel.checker.checkPlayer(this);

        if (this.type == typeMonster && contactPlayer == true) {
            damagePlayer(attack);
        }
    }

    public void update() {

        setAction();
        checkCollision();

        //If collision is false, NPS can move
        if (collisionOn == false) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "right": worldX += speed; break;
                case "left": worldX -= speed; break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 24) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if (panel.player.invincible == false) {
            //we can give damage
            panel.playSoundEffect(6);

            int damage = attack - panel.player.defence;
            if (damage < 0) {
                damage = 0;
            }

            panel.player.currentHP -= damage;
            panel.player.invincible = true;
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
                    if (spriteNum == 1) { image = up1; }
                    if (spriteNum == 2) { image = up2; }
                    break;
                case "down":
                    if (spriteNum == 1) { image = down1; }
                    if (spriteNum == 2) { image = down2; }
                    break;
                case "right":
                    if (spriteNum == 1) { image = right1; }
                    if (spriteNum == 2) { image = right2; }
                    break;
                case "left":
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                    break;
            }

            //Monster HP bar
            if (type == 2 && HpBarOn == true) {
                double oneScale = (double) panel.tileSize/maxHP;
                double HpBarValue = oneScale*currentHP;

                graphics2D.setColor(new Color(35, 35, 35));
                graphics2D.fillRect(screenX-1, screenY - 16, panel.tileSize+2, 12);

                graphics2D.setColor(new Color(161, 30, 47));
                graphics2D.fillRect(screenX, screenY - 15, (int)HpBarValue, 10);

                HpBarCounter++;

                if (HpBarCounter > 600) {
                    HpBarCounter = 0;
                    HpBarOn = false;
                }
            }

            if (invincible == true) {
                HpBarOn = true;
                HpBarCounter = 0;
                changeAlpha(graphics2D, 0.4f);
            }
            if (dying == true) {
                dyingAnimation(graphics2D);
            }

            graphics2D.drawImage(image, screenX, screenY, null);

            changeAlpha(graphics2D, 1f);
        }
    }

    public void dyingAnimation(Graphics2D graphics2D) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) { changeAlpha(graphics2D, 0f); }
        if (dyingCounter > i && dyingCounter <= i*2) { changeAlpha(graphics2D, 1f); }
        if (dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(graphics2D, 0f); }
        if (dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(graphics2D, 1f); }
        if (dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(graphics2D, 0f); }
        if (dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(graphics2D, 1f); }
        if (dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(graphics2D, 0f); }
        if (dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(graphics2D, 1f); }
        if (dyingCounter > i*8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D graphics2D, float alphaValue) {

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int wight, int height) {

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream( imagePath +".png"));
            image = utilityTool.scaleImage(image, wight, height);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public String oppositeDirection(String direction) {

        if (direction == "up") {
            direction = "down";
        }
        else if (direction == "down") {
            direction = "up";
        }
        else if (direction == "right") {
            direction = "left";
        }
        else if (direction == "left") {
            direction = "right";
        }

        return direction;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x)/ panel.tileSize;
        int startRow = (worldY + solidArea.y)/ panel.tileSize;

        panel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if (panel.pathFinder.search()) {
            int nextX = panel.pathFinder.pathList.get(0).col* panel.tileSize;
            int nextY = panel.pathFinder.pathList.get(0).row* panel.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enButtonY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + panel.tileSize) {
                direction = "up";
            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + panel.tileSize) {
                direction = "down";
            }
            else if (enTopY >= nextY && enButtonY < nextY + panel.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            }
            else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }
            else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            }
            else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }

            int nextCol = panel.pathFinder.pathList.get(0).col;
            int nextRow = panel.pathFinder.pathList.get(0).row;
            if (nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }
        }
    }
}
