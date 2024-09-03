package main;

import entity.Entity;

public class CollisionChecker {
    Panel panel;
    public CollisionChecker(Panel panel) {
        this.panel = panel;

    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / panel.tileSize;
        int entityRightCol = entityRightWorldX / panel.tileSize;
        int entityTopRow = entityTopWorldY / panel.tileSize;
        int entityBottomRow = entityBottomWorldY / panel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / panel.tileSize;
                tileNum1 = panel.tileManager.mapTaleNum[panel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = panel.tileManager.mapTaleNum[panel.currentMap][entityRightCol][entityTopRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / panel.tileSize;
                tileNum1 = panel.tileManager.mapTaleNum[panel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = panel.tileManager.mapTaleNum[panel.currentMap][entityRightCol][entityBottomRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / panel.tileSize;
                tileNum1 = panel.tileManager.mapTaleNum[panel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = panel.tileManager.mapTaleNum[panel.currentMap][entityLeftCol][entityBottomRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / panel.tileSize;
                tileNum1 = panel.tileManager.mapTaleNum[panel.currentMap][entityRightCol][entityTopRow];
                tileNum2 = panel.tileManager.mapTaleNum[panel.currentMap][entityRightCol][entityBottomRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < panel.object[1].length; i++) {
            if (panel.object[panel.currentMap][i] != null) {
                //Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                panel.object[panel.currentMap][i].solidArea.x = panel.object[panel.currentMap][i].worldX + panel.object[panel.currentMap][i].solidArea.x;
                panel.object[panel.currentMap][i].solidArea.y = panel.object[panel.currentMap][i].worldY + panel.object[panel.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                }

                if (entity.solidArea.intersects(panel.object[panel.currentMap][i].solidArea)) {
                    if (panel.object[panel.currentMap][i].collision == true) {
                        entity.collisionOn = true;
                    }
                    if (player == true) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                panel.object[panel.currentMap][i].solidArea.x = panel.object[panel.currentMap][i].solidAreaDefaultX;
                panel.object[panel.currentMap][i].solidArea.y = panel.object[panel.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }

    //NPS or Monster collision check
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;
        for (int i = 0; i < target[1].length; i++) {
            if (target[panel.currentMap][i] != null) {
                //Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                target[panel.currentMap][i].solidArea.x = target[panel.currentMap][i].worldX + target[panel.currentMap][i].solidArea.x;
                target[panel.currentMap][i].solidArea.y = target[panel.currentMap][i].worldY + target[panel.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                }

                if (entity.solidArea.intersects(target[panel.currentMap][i].solidArea)) {
                    if (target[panel.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[panel.currentMap][i].solidArea.x = target[panel.currentMap][i].solidAreaDefaultX;
                target[panel.currentMap][i].solidArea.y = target[panel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        //Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        //Get the object's solid area position
         panel.player.solidArea.x =  panel.player.worldX +  panel.player.solidArea.x;
         panel.player.solidArea.y =  panel.player.worldY +  panel.player.solidArea.y;

        switch (entity.direction) {
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
        }

        if (entity.solidArea.intersects(panel.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        panel.player.solidArea.x =  panel.player.solidAreaDefaultX;
        panel.player.solidArea.y =  panel.player.solidAreaDefaultY;

        return contactPlayer;
    }
}