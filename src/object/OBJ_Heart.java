package object;

import entity.Entity;
import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(Panel panel) {
        super(panel);

        name = "Heart";
        image1 = setup("/object/Heart/heart_full", panel.tileSize, panel.tileSize);
        image2 = setup("/object/Heart/heart_half", panel.tileSize, panel.tileSize);
        image3 = setup("/object/Heart/heart_blank", panel.tileSize, panel.tileSize);
    }
}
