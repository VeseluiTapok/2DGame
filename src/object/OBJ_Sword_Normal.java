package object;

import entity.Entity;
import main.Panel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(Panel panel) {
        super(panel);

        type = typeSword;
        name = "Normal Sword";
        down1 = setup("/object/sword_normal", panel.tileSize, panel.tileSize);
        attackValue = 0;
        description = "[" + name + "]" + "\nAn old sword...";
        attackArea.width = 36;
        attackArea.height = 36;
        price = 50;
    }
}
