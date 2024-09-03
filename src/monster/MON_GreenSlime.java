package monster;

import entity.Entity;
import main.Panel;
import object.OBJ_Axe;
import object.OBJ_Bronze_Coin;
import object.OBJ_Dagger;
import object.OBJ_Shield_Blue;

import java.awt.*;
import java.util.Random;

public class MON_GreenSlime extends Entity {

    Panel panel;

    public MON_GreenSlime(Panel panel) {
        super(panel);

        this.panel = panel;

        type = typeMonster;
        name = "Green Slime";
        speed = 1;
        maxHP = 5;
        currentHP = maxHP;
        attack = 5;
        defence = 0;
        exp = 2;
        projectile = new OBJ_Dagger(panel);

        solidArea.x = 0;
        solidArea.y = 10;
        solidArea.width = 48;
        solidArea.height = 38;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monsters/slime_default", panel.tileSize, panel.tileSize);
        up2 = setup("/monsters/slime_up", panel.tileSize, panel.tileSize);
        down1 = setup("/monsters/slime_default", panel.tileSize, panel.tileSize);
        down2 = setup("/monsters/slime_down", panel.tileSize, panel.tileSize);
        right1 = setup("/monsters/slime_default", panel.tileSize, panel.tileSize);
        right2 = setup("/monsters/slime_right", panel.tileSize, panel.tileSize);
        left1 = setup("/monsters/slime_default", panel.tileSize, panel.tileSize);
        left2 = setup("/monsters/slime_left", panel.tileSize, panel.tileSize);
    }

    public void setAction() {
        direction = toPlayerDirection();

//        int i = new Random().nextInt(100)+1;
//        if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
//            switch (direction) {
//                case "up":
//                    projectile.set(worldX, worldY-1, direction, true, this);
//                    break;
//                case "down":
//                    projectile.set(worldX, worldY+1, direction, true, this);
//                    break;
//                case "right":
//                    projectile.set(worldX+1, worldY, direction, true, this);
//                    break;
//                case "left":
//                    projectile.set(worldX-1, worldY, direction, true, this);
//                    break;
//            }
//            panel.projectileList.add(projectile);
//            shotAvailableCounter = 0;
//        }
    }

    public void damageReaction() {
        updateLockCounter = 0;
        direction = oppositeDirection(panel.player.direction);
    }

    public void checkDrop() {

        //CAST THE DIE
        int dropNum = new Random().nextInt(100)+1;

        //SET THE MONSTER DROP
        if (dropNum < 50) {
            dropItem(new OBJ_Bronze_Coin(panel));
        }
        if (dropNum >= 50 && dropNum < 80) {
            dropItem(new OBJ_Shield_Blue(panel));
        }
        if (dropNum >= 80 && dropNum <= 100) {
            dropItem(new OBJ_Axe(panel));
        }
    }

    public Color getParticleColor() {
        Color color = new Color(79, 198, 38);
        return color;
    }

    public int getParticleSize() {
        int size = 8;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxHP() {
        int maxHP = 20;
        return maxHP;
    }

    public String toPlayerDirection() {
        String direction = "";
//                if (worldX <= panel.player.worldX+8 && worldX >= panel.player.worldX-8) {
//                    if (worldY < panel.player.worldY) {
//                        direction = "down";
//                    } else if (worldY > panel.player.worldY) {
//                        direction = "up";
//                    }
//                }
//                else if (worldX < panel.player.worldX+8) {
//                    direction = "right";
//                }
//                else if (worldX > panel.player.worldX-8) {
//                    direction = "left";
//                }
        return direction;
    }
}
