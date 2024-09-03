package tileInteractive;

import entity.Entity;
import main.Panel;

import java.awt.*;

public class IT_DryTree extends InteractiveTile{
    Panel panel;

    public IT_DryTree(Panel panel, int col, int row) {

        super(panel, col, row);
        this.panel = panel;

        this.worldX = panel.tileSize * col;
        this.worldY = panel.tileSize * row;

        down1 = setup("/tilesInteractive/drytree", panel.tileSize, panel.tileSize);
        destructible = true;
        currentHP = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.currentWeapon.type == typeAxe) {
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    public void playSoundEffect() {
        panel.playSoundEffect(13);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(panel, worldX/panel.tileSize, worldY/panel.tileSize);
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);
        return color;
    }

    public int getParticleSize() {
        int size = 6;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxHP() {
        int maxHP = 20;
        return maxHP;
    }
}
