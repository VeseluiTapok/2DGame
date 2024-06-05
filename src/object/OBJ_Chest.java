package object;

import entity.Entity;
import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {

    public OBJ_Chest(Panel panel) {
        super(panel);

        name = "Chest";
        down1 = setup("/object/Chest", panel.tileSize, panel.tileSize);
    }
}
