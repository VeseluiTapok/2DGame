package tileInteractive;

import main.Panel;

public class IT_DryTree extends InteractiveTile{
    Panel panel;

    public IT_DryTree(Panel panel) {

        super(panel);
        this.panel = panel;

        down1 = setup("/tileInteractive/drytree", panel.tileSize, panel.tileSize);
        destructible = true;
    }
}
