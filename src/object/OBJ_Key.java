package object;

import entity.Entity;
import main.Panel;

public class OBJ_Key extends Entity {


    public OBJ_Key(Panel panel) {
        super(panel);

        name = "Key";
        down1 = setup("/object/key", panel.tileSize, panel.tileSize);
        description = "[" + name + "]" + "\nIt open a door...";
        price = 10;
    }
}
