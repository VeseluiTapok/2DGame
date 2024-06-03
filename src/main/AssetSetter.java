package main;

import entity.NPS_OldMan;
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
}
