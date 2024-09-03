package main;

import entity.Entity;

import java.awt.*;

public class EventHandler {
    Panel panel;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(Panel panel) {
        this.panel = panel;

        eventRect = new EventRect[panel.maxMap][panel.maxWorldCol][panel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < panel.maxMap && col < panel.maxWorldCol && row < panel.maxWorldRow) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if (col == panel.maxWorldCol) {
                col = 0;
                row++;

                if (row == panel.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(panel.player.worldX - previousEventX);
        int yDistance = Math.abs(panel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > panel.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent == true) {
            if (hit(0, 27, 15, "right") == true) {
                damagePit(panel.dialogueState);
            } else if (hit(0, 23, 12, "up") == true) {
                healingPool(panel.dialogueState);
            } else if (hit(0, 12, 39, "any") == true) {
                teleport(1, 12, 12);
            } else if (hit(1, 12, 12, "any") == true) {
                teleport(0, 12, 39);
            } else if (hit(1, 20, 8, "up") == true) {
                speak(panel.nps[1][0]);
            }
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        panel.player.solidArea.x = panel.player.worldX + panel.player.solidArea.x;
        panel.player.solidArea.y = panel.player.worldY + panel.player.solidArea.y;
        eventRect[map][col][row].x = col*panel.tileSize + eventRect[map][col][row].x;
        eventRect[map][col][row].y = row*panel.tileSize + eventRect[map][col][row].y;

        if (panel.player.solidArea.intersects(eventRect[map][col][row])) {
            if (panel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = panel.player.worldX;
                previousEventY = panel.player.worldY;
            }
        }

        panel.player.solidArea.x = eventRect[map][col][row].eventRectDefaultX;
        panel.player.solidArea.y = eventRect[map][col][row].eventRectDefaultY;
        eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
        eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState) {
        if (panel.timer >= 2000000000) {
            panel.gameState = gameState;
            panel.ui.currentDialogue = "you fall into a hole!";
            panel.player.currentHP -= 1;
            panel.timer = 0;
            canTouchEvent = false;
        }
    }

    public void healingPool(int gameState) {

        if (panel.keyHandler.enterPressed == true) {
            panel.gameState = gameState;
            panel.ui.currentDialogue = "You respawn all monsters";
            panel.assetSetter.setMonsters();
        }
    }

    public void teleport(int map, int col, int row) {
        panel.gameState = panel.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        panel.playSoundEffect(15);
    }

    public void speak(Entity entity) {
        if (panel.keyHandler.enterPressed) {
            panel.gameState = panel.dialogueState;
            entity.speak();
        }
    }
}
