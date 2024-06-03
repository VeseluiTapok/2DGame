package object;

import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {

    Panel panel;

    public OBJ_Key(Panel panel) {
        this.panel = panel;

        name = "Key";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/object/key.png"));
            utilityTool.scaleImage(image1, panel.tileSize, panel.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
