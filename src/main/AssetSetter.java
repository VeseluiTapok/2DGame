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

    }
    public void setNPS() {
        panel.nps[0] = new NPS_OldMan(panel);
        panel.nps[0].worldX = panel.tileSize * 21;
        panel.nps[0].worldY = panel.tileSize * 21;
    }
    public void setMonsters() {
        panel.monster[0] = new MON_GreenSlime(panel);
        panel.monster[0].worldX = 26*panel.tileSize;
        panel.monster[0].worldY = 36*panel.tileSize;

        panel.monster[1] = new MON_GreenSlime(panel);
        panel.monster[1].worldX = 23*panel.tileSize;
        panel.monster[1].worldY = 37*panel.tileSize;
    }
}
