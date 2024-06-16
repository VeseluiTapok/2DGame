package object;

import entity.Entity;
import main.Panel;

public class OBJ_Strength extends Entity {

    public OBJ_Strength(Panel panel) {
        super(panel);

        name = "Strength";

        down1 = setup("/object/strenght", panel.tileSize, panel.tileSize);
    }
}
