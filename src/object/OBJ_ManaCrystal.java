package object;

import entity.Entity;
import main.Panel;

public class OBJ_ManaCrystal extends Entity {

    Panel panel;

    public OBJ_ManaCrystal(Panel panel) {
        super(panel);
        this.panel = panel;

        name = "Mana Crystal";

        image1 = setup("/object/crystal/manacrystal_full", panel.tileSize, panel.tileSize);
        image2 = setup("/object/crystal/manacrystal_blank", panel.tileSize, panel.tileSize);
    }
}
