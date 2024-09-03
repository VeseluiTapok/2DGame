package entity;

import main.Panel;

import java.awt.*;

public class Particle extends Entity {

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(Panel panel, Entity generator, Color color, int size, int speed, int maxHP, int xd, int yd) {

        super(panel);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxHP = maxHP;
        this.xd = xd;
        this.yd = yd;

        this.currentHP = maxHP;
        int offset = (panel.tileSize/2) - (size/2);
        worldX = generator.worldX+offset;
        worldY = generator.worldY+offset;
    }

    public void update() {
        currentHP--;

        if (currentHP < maxHP/3) {
            yd++;
        }

        worldX += xd*speed;
        worldY += yd*speed;

        if (currentHP == 0) {
            alive = false;
        }
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - panel.player.worldX + panel.player.screenX;
        int screenY = worldY - panel.player.worldY + panel.player.screenY;

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screenY, size, size);
    }
}
