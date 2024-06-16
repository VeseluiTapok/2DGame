package main;

import entity.NPS_OldMan;
import monster.MON_GreenSlime;
import object.*;

public class AssetSetter {
    Panel panel;

    public AssetSetter(Panel panel) {
        this.panel = panel;
    }

    public void setObject() {

        int i = 0;
        panel.object[i] = new OBJ_Bronze_Coin(panel);
        panel.object[i].worldX = panel.tileSize * 25;
        panel.object[i].worldY = panel.tileSize * 23;
        i++;
        panel.object[i] = new OBJ_Bronze_Coin(panel);
        panel.object[i].worldX = panel.tileSize * 21;
        panel.object[i].worldY = panel.tileSize * 19;
        i++;
        panel.object[i] = new OBJ_Axe(panel);
        panel.object[i].worldX = panel.tileSize * 33;
        panel.object[i].worldY = panel.tileSize * 21;
        i++;
        panel.object[i] = new OBJ_Shield_Blue(panel);
        panel.object[i].worldX = panel.tileSize * 35;
        panel.object[i].worldY = panel.tileSize * 21;
        i++;
        panel.object[i] = new OBJ_Potion_Red(panel);
        panel.object[i].worldX = panel.tileSize * 23;
        panel.object[i].worldY = panel.tileSize * 27;

    }
    public void setNPS() {
        panel.nps[0] = new NPS_OldMan(panel);
        panel.nps[0].worldX = panel.tileSize * 21;
        panel.nps[0].worldY = panel.tileSize * 21;
    }
    public void setMonsters() {
        int i = 0;

        panel.monster[i] = new MON_GreenSlime(panel);
        panel.monster[i].worldX = 26*panel.tileSize;
        panel.monster[i].worldY = 36*panel.tileSize;

        i++;
        panel.monster[i] = new MON_GreenSlime(panel);
        panel.monster[i].worldX = 23*panel.tileSize;
        panel.monster[i].worldY = 37*panel.tileSize;

        i++;
        panel.monster[i] = new MON_GreenSlime(panel);
        panel.monster[i].worldX = 24*panel.tileSize;
        panel.monster[i].worldY = 37*panel.tileSize;

        i++;
        panel.monster[i] = new MON_GreenSlime(panel);
        panel.monster[i].worldX = 34*panel.tileSize;
        panel.monster[i].worldY = 42*panel.tileSize;

        i++;
        panel.monster[i] = new MON_GreenSlime(panel);
        panel.monster[i].worldX = 38*panel.tileSize;
        panel.monster[i].worldY = 42*panel.tileSize;
    }
}
