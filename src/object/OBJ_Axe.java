package object;

import entity.Entity;
import main.Panel;

public class OBJ_Axe extends Entity {

    public OBJ_Axe(Panel panel) {
        super(panel);

        type = typeAxe;
        name = "Woodcutter's Axe";
        down1 = setup("/object/axe", panel.tileSize, panel.tileSize);
        attackValue = 1;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]" + "\nA bit rusty but still \ncan cut some trees...";
    }
}
