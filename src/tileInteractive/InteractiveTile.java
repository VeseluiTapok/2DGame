package tileInteractive;

import entity.Entity;
import main.Panel;

public class InteractiveTile extends Entity {
    Panel panel;
    public boolean destructible = false;

    public InteractiveTile(Panel panel) {
        super(panel);
        this.panel = panel;
    }

    public void update() {

    }
}
