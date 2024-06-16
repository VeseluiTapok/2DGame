package object;

import entity.Entity;
import entity.Projectile;
import main.Panel;

public class OBJ_Dagger extends Projectile {

    Panel panel;

    public OBJ_Dagger(Panel panel) {
        super(panel);

        this.panel = panel;

        name = "Rock";
        speed = 8;
        maxHP = 100;
        currentHP = maxHP;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/daggerUp", panel.tileSize, panel.tileSize);
        up2 = setup("/projectile/daggerUp", panel.tileSize, panel.tileSize);
        down1 = setup("/projectile/daggerDown", panel.tileSize, panel.tileSize);
        down2 = setup("/projectile/daggerDown", panel.tileSize, panel.tileSize);
        right1 = setup("/projectile/daggerRight", panel.tileSize, panel.tileSize);
        right2 = setup("/projectile/daggerRight", panel.tileSize, panel.tileSize);
        left1 = setup("/projectile/daggerLeft", panel.tileSize, panel.tileSize);
        left2 = setup("/projectile/daggerLeft", panel.tileSize, panel.tileSize);

    }

    public boolean haveResource(Entity user) {

        boolean haveResource = false;
        if (user.ammo >= useCost) {
            haveResource = true;
        }

        return haveResource;
    }
    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }
}
