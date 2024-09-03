package main;

import entity.NPS_Merchant;
import entity.NPS_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tileInteractive.IT_DryTree;

public class AssetSetter {
    Panel panel;

    public AssetSetter(Panel panel) {
        this.panel = panel;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        panel.object[mapNum][i] = new OBJ_Bronze_Coin(panel);
        panel.object[mapNum][i].worldX = panel.tileSize * 25;
        panel.object[mapNum][i].worldY = panel.tileSize * 23;
        i++;
        panel.object[mapNum][i] = new OBJ_Bronze_Coin(panel);
        panel.object[mapNum][i].worldX = panel.tileSize * 21;
        panel.object[mapNum][i].worldY = panel.tileSize * 19;
        i++;
        panel.object[mapNum][i] = new OBJ_Axe(panel);
        panel.object[mapNum][i].worldX = panel.tileSize * 33;
        panel.object[mapNum][i].worldY = panel.tileSize * 21;
        i++;
        panel.object[mapNum][i] = new OBJ_Shield_Blue(panel);
        panel.object[mapNum][i].worldX = panel.tileSize * 35;
        panel.object[mapNum][i].worldY = panel.tileSize * 21;
        i++;

        mapNum = 1;
        i = 0;
    }
    public void setNPS() {
        int mapNum = 0;
        panel.nps[mapNum][0] = new NPS_OldMan(panel);
        panel.nps[mapNum][0].worldX = panel.tileSize * 21;
        panel.nps[mapNum][0].worldY = panel.tileSize * 21;

        mapNum = 1;
        panel.nps[mapNum][0] = new NPS_Merchant(panel);
        panel.nps[mapNum][0].worldX = panel.tileSize * 20;
        panel.nps[mapNum][0].worldY = panel.tileSize * 6;
    }
    public void setMonsters() {
        int mapNum = 0;
        int i = 0;

        panel.monster[mapNum][i] = new MON_GreenSlime(panel);
        panel.monster[mapNum][i].worldX = 26*panel.tileSize;
        panel.monster[mapNum][i].worldY = 36*panel.tileSize;

        i++;
        panel.monster[mapNum][i] = new MON_GreenSlime(panel);
        panel.monster[mapNum][i].worldX = 23*panel.tileSize;
        panel.monster[mapNum][i].worldY = 37*panel.tileSize;

        i++;
        panel.monster[mapNum][i] = new MON_GreenSlime(panel);
        panel.monster[mapNum][i].worldX = 24*panel.tileSize;
        panel.monster[mapNum][i].worldY = 37*panel.tileSize;

        i++;
        panel.monster[mapNum][i] = new MON_GreenSlime(panel);
        panel.monster[mapNum][i].worldX = 34*panel.tileSize;
        panel.monster[mapNum][i].worldY = 42*panel.tileSize;

        i++;
        panel.monster[mapNum][i] = new MON_GreenSlime(panel);
        panel.monster[mapNum][i].worldX = 38*panel.tileSize;
        panel.monster[mapNum][i].worldY = 42*panel.tileSize;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 27 ,12); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 28 ,12); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 29 ,12); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 30 ,12); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 31 ,12); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 32 ,12); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 33 ,12); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 17 ,39); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 16 ,39); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 15 ,39); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 15 ,40); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 14 ,40); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 13 ,40); i++;
        panel.interactiveTile[mapNum][i] = new IT_DryTree(panel, 12 ,40); i++;
    }
}
