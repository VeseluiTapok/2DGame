package object;

import main.Panel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject{

    Panel panel;

    public OBJ_Boots(Panel panel) {
        this.panel = panel;

        name = "Boots";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/object/boots.png"));
            utilityTool.scaleImage(image1, panel.tileSize, panel.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
