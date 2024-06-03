package object;

import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject{

    Panel panel;

    public OBJ_Door(Panel panel) {
        this.panel = panel;

        name = "Door";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/object/door.png"));
            utilityTool.scaleImage(image1, panel.tileSize, panel.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
