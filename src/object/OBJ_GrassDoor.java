package object;

import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_GrassDoor extends SuperObject{

    Panel panel;

    public OBJ_GrassDoor(Panel panel) {
        this.panel = panel;

        name = "GrassDoor";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/object/graasDoor.png"));
            utilityTool.scaleImage(image1, panel.tileSize, panel.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
