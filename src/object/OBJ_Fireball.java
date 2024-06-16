package object;

import entity.Entity;
import entity.Projectile;
import main.Panel;

public class OBJ_Fireball extends Projectile {

    Panel panel;

    public OBJ_Fireball(Panel panel) {
        super(panel);

        this.panel = panel;

        name = "Fireball";

        speed = 10;
        maxHP = 110;
        currentHP = maxHP;
        attack = 2;
        useCost = 1;
        alive = false;
        haveStartAnimation = true;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/fireball_up_1", panel.tileSize, panel.tileSize);
        up2 = setup("/projectile/fireball_up_2", panel.tileSize, panel.tileSize);
        down1 = setup("/projectile/fireball_down_1", panel.tileSize, panel.tileSize);
        down2 = setup("/projectile/fireball_down_2", panel.tileSize, panel.tileSize);
        right1 = setup("/projectile/fireball_right_1", panel.tileSize, panel.tileSize);
        right2 = setup("/projectile/fireball_right_2", panel.tileSize, panel.tileSize);
        left1 = setup("/projectile/fireball_left_1", panel.tileSize, panel.tileSize);
        left2 = setup("/projectile/fireball_left_2", panel.tileSize, panel.tileSize);

        start1 = setup("/projectile/fireball_start_1", panel.tileSize, panel.tileSize);
        start2 = setup("/projectile/fireball_start_2", panel.tileSize, panel.tileSize);
        start3 = setup("/projectile/fireball_start_3", panel.tileSize, panel.tileSize);
        start4 = setup("/projectile/fireball_start_4", panel.tileSize, panel.tileSize);
        start5 = setup("/projectile/fireball_start_5", panel.tileSize, panel.tileSize);
        start6 = setup("/projectile/fireball_start_6", panel.tileSize, panel.tileSize);
        start7 = setup("/projectile/fireball_start_7", panel.tileSize, panel.tileSize);
        start8 = setup("/projectile/fireball_start_8", panel.tileSize, panel.tileSize);
        start9 = setup("/projectile/fireball_start_9", panel.tileSize, panel.tileSize);
        start10 = setup("/projectile/fireball_start_10", panel.tileSize, panel.tileSize);
        start11 = setup("/projectile/fireball_start_11", panel.tileSize, panel.tileSize);
        start12 = setup("/projectile/fireball_start_12", panel.tileSize, panel.tileSize);
    }

    public boolean haveResource(Entity user) {

        boolean haveResource = false;
        if (user.currentMana >= useCost) {
            haveResource = true;
        }

        return haveResource;
    }
    public void subtractResource(Entity user) {
        user.currentMana -= useCost;
    }
}
