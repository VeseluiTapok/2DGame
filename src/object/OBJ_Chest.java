package object;

import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {

    Panel panel;

    public OBJ_Chest(Panel panel) {
        this.panel = panel;

        name = "Chest";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/object/Chest.png"));
            utilityTool.scaleImage(image1, panel.tileSize, panel.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
