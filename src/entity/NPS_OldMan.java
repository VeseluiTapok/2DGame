package entity;

import main.Panel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPS_OldMan extends Entity {

    public NPS_OldMan(Panel panel) {
        super(panel);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/NPS/oldman_up_1", panel.tileSize, panel.tileSize);
        up2 = setup("/NPS/oldman_up_2", panel.tileSize, panel.tileSize);
        down1 = setup("/NPS/oldman_down_1", panel.tileSize, panel.tileSize);
        down2 = setup("/NPS/oldman_down_2", panel.tileSize, panel.tileSize);
        right1 = setup("/NPS/oldman_right_1", panel.tileSize, panel.tileSize);
        right2 = setup("/NPS/oldman_right_2", panel.tileSize, panel.tileSize);
        left1 = setup("/NPS/oldman_left_1", panel.tileSize, panel.tileSize);
        left2 = setup("/NPS/oldman_left_2", panel.tileSize, panel.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Hello, lad!";
        dialogues[1] = "So you've come to this island \nto find the treasure?";
        dialogues[2] = "I used to be a great wizard \nbut now... I'm a bit too old \nfor talking an adventure.";
        dialogues[3] = "Well, good luck on your.";
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

    public void speak() {

        super.speak();
    }
}
