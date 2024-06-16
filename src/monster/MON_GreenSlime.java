package monster;

import entity.Entity;
import main.Panel;
import object.OBJ_Axe;
import object.OBJ_Bronze_Coin;
import object.OBJ_Dagger;
import object.OBJ_Shield_Blue;

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
        updateLockCounter++;

        if (updateLockCounter >= 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            } else if (i > 25 && i <= 50) {
                direction = "down";
            } else if (i > 50 && i <= 75) {
                direction = "left";
            } else if (i > 75 && i <= 100) {
                direction = "right";
            }
            updateLockCounter = 0;
        }

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
        int i = new Random().nextInt(100)+1;

        //SET THE MONSTER DROP
        if (i < 50) {
            dropItem(new OBJ_Bronze_Coin(panel));
        }
        if (i >= 50 && i < 80) {
            dropItem(new OBJ_Shield_Blue(panel));
        }
        if (i >= 80 && i < 100) {
            dropItem(new OBJ_Axe(panel));
        }
    }
}
