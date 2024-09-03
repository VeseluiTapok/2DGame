package entity;

import main.Panel;
import object.OBJ_Key;
import object.OBJ_Potion_Red;

public class NPS_Merchant extends Entity{

    public NPS_Merchant(Panel panel) {
        super(panel);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        up1 = setup("/object/NPS/merchant_down_1", panel.tileSize, panel.tileSize);
        up2 = setup("/object/NPS/merchant_down_2", panel.tileSize, panel.tileSize);
        down1 = setup("/object/NPS/merchant_down_1", panel.tileSize, panel.tileSize);
        down2 = setup("/object/NPS/merchant_down_2", panel.tileSize, panel.tileSize);
        right1 = setup("/object/NPS/merchant_down_1", panel.tileSize, panel.tileSize);
        right2 = setup("/object/NPS/merchant_down_2", panel.tileSize, panel.tileSize);
        left1 = setup("/object/NPS/merchant_down_1", panel.tileSize, panel.tileSize);
        left2 = setup("/object/NPS/merchant_down_2", panel.tileSize, panel.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "He he, so you found me.\nI have some good staff.\nDo you want to trade?";
    }

    public void setItems() {
        inventory.add(new OBJ_Potion_Red(panel));
        inventory.add(new OBJ_Key(panel));
    }

    public void speak() {
        super.speak();
        panel.gameState = panel.tradeState;
        panel.ui.nps = this;
    }
}
