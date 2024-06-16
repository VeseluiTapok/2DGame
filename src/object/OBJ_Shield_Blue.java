package object;

import entity.Entity;
import main.Panel;

public class OBJ_Shield_Blue extends Entity {

    public OBJ_Shield_Blue(Panel panel) {
        super(panel);

        type = typeShield;
        down1 = setup("/object/shield_blue", panel.tileSize, panel.tileSize);
        name = "Blue Shield";
        defenceValue = 1;
        description = "[" + name + "]\nA shiny blue shield";
    }
}
