package monster;

import entity.Entity;
import main.Panel;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    Panel panel;

    public MON_GreenSlime(Panel panel) {
        super(panel);

        this.panel = panel;

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxHP = 5;
        currentHP = maxHP;

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
    }
}
