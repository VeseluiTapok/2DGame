package object;

import entity.Entity;
import main.Panel;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(Panel panel) {
        super(panel);

        type = typeShield;
        name = "Wood Shield";
        down1 = setup("/object/shield_wood", panel.tileSize, panel.tileSize);
        defenceValue = 0;
        description = "[" + name + "]" + "\nMade by wood...";
        price = 50;
    }
}
