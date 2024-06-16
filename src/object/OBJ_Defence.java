package object;

import entity.Entity;
import main.Panel;

public class OBJ_Defence extends Entity {

    public OBJ_Defence(Panel panel) {
        super(panel);

        name = "Defence";

        down1 = setup("/object/defence", panel.tileSize, panel.tileSize);
    }
}
