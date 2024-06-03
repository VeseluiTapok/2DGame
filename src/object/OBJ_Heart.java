package object;

import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{

    Panel panel;

    public OBJ_Heart(Panel panel) {
        this.panel = panel;

        name = "Heart";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/object/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/object/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/object/heart_blank.png"));
            utilityTool.scaleImage(image1, panel.tileSize, panel.tileSize);
            utilityTool.scaleImage(image2, panel.tileSize, panel.tileSize);
            utilityTool.scaleImage(image3, panel.tileSize, panel.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
