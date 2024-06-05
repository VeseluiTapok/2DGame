package object;

import entity.Entity;
import main.Panel;

public class OBJ_Door extends Entity {

    public OBJ_Door(Panel panel) {
        super(panel);

        name = "Door";
        down1 = setup("/object/door", panel.tileSize, panel.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
