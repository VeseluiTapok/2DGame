package object;

import entity.Entity;
import main.Panel;

public class OBJ_Potion_Red extends Entity {

    Panel panel;

    public OBJ_Potion_Red(Panel panel) {
        super(panel);
        value = 5;

        this.panel = panel;

        type = typeConsumable;
        name = "Heal Potion";
        down1 = setup("/object/potion_red", panel.tileSize, panel.tileSize);
        description = "[" + name + "]" + "\nHeals your HP by " + value + "...";
    }

    public void use(Entity entity) {

        panel.gameState = panel.dialogueState;
        panel.ui.currentDialogue = "You drink the " + name + "!\n" +
                "your life has been recorded \nto " + value + "!";

        entity.currentHP += value;
        if (entity.currentHP > entity.maxHP) {
            entity.currentHP = entity.maxHP;
        }

        panel.playSoundEffect(3);

    }
}
