package object;

import entity.Entity;
import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(Panel panel) {
        super(panel);

        name = "Boots";
        down1 = setup("/object/boots", panel.tileSize, panel.tileSize);
    }
}
