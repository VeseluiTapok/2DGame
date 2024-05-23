package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_GrassDoor;
import object.OBJ_Key;

public class AssetSetter {
    Panel panel;

    public AssetSetter(Panel panel) {
        this.panel = panel;
    }

    public void setObject() {

        panel.superObject[0] = new OBJ_Key();
        panel.superObject[0].worldX = 4 * panel.tileSize;
        panel.superObject[0].worldY = 27 * panel.tileSize;

        panel.superObject[1] = new OBJ_Key();
        panel.superObject[1].worldX = 18 * panel.tileSize;
        panel.superObject[1].worldY = 7 * panel.tileSize;

        panel.superObject[2] = new OBJ_Key();
        panel.superObject[2].worldX = 24 * panel.tileSize;
        panel.superObject[2].worldY = 45 * panel.tileSize;

        panel.superObject[3] = new OBJ_Key();
        panel.superObject[3].worldX = 40 * panel.tileSize;
        panel.superObject[3].worldY = 32 * panel.tileSize;

        panel.superObject[4] = new OBJ_GrassDoor();
        panel.superObject[4].worldX = 48 * panel.tileSize;
        panel.superObject[4].worldY = 47 * panel.tileSize;

        panel.superObject[5] = new OBJ_GrassDoor();
        panel.superObject[5].worldX = 48 * panel.tileSize;
        panel.superObject[5].worldY = 30 * panel.tileSize;

        panel.superObject[6] = new OBJ_GrassDoor();
        panel.superObject[6].worldX = 48 * panel.tileSize;
        panel.superObject[6].worldY = 20 * panel.tileSize;

        panel.superObject[7] = new OBJ_Door();
        panel.superObject[7].worldX = 47 * panel.tileSize;
        panel.superObject[7].worldY = 13 * panel.tileSize;

        panel.superObject[8] = new OBJ_Chest();
        panel.superObject[8].worldX = 47 * panel.tileSize;
        panel.superObject[8].worldY = 10 * panel.tileSize;
    }
}
