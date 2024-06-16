package main;

import java.awt.*;

public class EventHandler {
    Panel panel;
    EventRect[][] eventRect;

    public EventHandler(Panel panel) {
        this.panel = panel;

        eventRect = new EventRect[panel.maxWorldCol][panel.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < panel.maxWorldCol && row < panel.maxWorldRow) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if (col == panel.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        if (hit(27, 15, "right") == true) {
            damagePit(panel.dialogueState);
        }
        if (hit(23,12, "up") == true) {
            healingPool(panel.dialogueState);
        }
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        panel.player.solidArea.x = panel.player.worldX + panel.player.solidArea.x;
        panel.player.solidArea.y = panel.player.worldY + panel.player.solidArea.y;
        eventRect[col][row].x = col*panel.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*panel.tileSize + eventRect[col][row].y;

        if (panel.player.solidArea.intersects(eventRect[col][row])) {
            if (panel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }

        panel.player.solidArea.x = eventRect[col][row].eventRectDefaultX;
        panel.player.solidArea.y = eventRect[col][row].eventRectDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState) {
        if (panel.timer >= 2000000000) {
            panel.gameState = gameState;
            panel.ui.currentDialogue = "you fall into a hole!";
            panel.player.currentHP -= 1;
            panel.timer = 0;
        }
    }

    public void healingPool(int gameState) {

        if (panel.keyHandler.enterPressed == true) {
            panel.gameState = gameState;
            panel.ui.currentDialogue = "You respawn all monsters";
            panel.assetSetter.setMonsters();
        }
    }
}
