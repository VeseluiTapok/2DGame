package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_GrassDoor extends SuperObject{
    public OBJ_GrassDoor() {
        name = "GrassDoor";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/graasDoor.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
