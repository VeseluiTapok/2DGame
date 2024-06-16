package object;

import entity.Entity;
import main.Panel;

public class OBJ_Bronze_Coin extends Entity {

    Panel panel;

    public OBJ_Bronze_Coin(Panel panel) {
        super(panel);
        this.panel = panel;

        name = "Bronze Coin";
        type = typePickUpOnly;
        value = 1;
        down1 = setup("/object/coin_bronze" ,panel.tileSize, panel.tileSize);
    }

    public void use(Entity entity) {

        panel.playSoundEffect(1);
        panel.ui.addMessage("+ " + value + " Coin");
        panel.player.coin += value;
    }

}
