package main;

import entity.Entity;

public class CollisionChecker {
    Panel panel;
    public CollisionChecker(Panel panel) {
        this.panel = panel;

    }

    public void CheckTile(Entity entity) {
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
                tileNum1 = panel.tileManager.mapTaleNum[entityLeftCol][entityTopRow];
                tileNum2 = panel.tileManager.mapTaleNum[entityRightCol][entityTopRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / panel.tileSize;
                tileNum1 = panel.tileManager.mapTaleNum[entityLeftCol][entityBottomRow];
                tileNum2 = panel.tileManager.mapTaleNum[entityRightCol][entityBottomRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / panel.tileSize;
                tileNum1 = panel.tileManager.mapTaleNum[entityLeftCol][entityTopRow];
                tileNum2 = panel.tileManager.mapTaleNum[entityLeftCol][entityBottomRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / panel.tileSize;
                tileNum1 = panel.tileManager.mapTaleNum[entityRightCol][entityTopRow];
                tileNum2 = panel.tileManager.mapTaleNum[entityRightCol][entityBottomRow];
                if (panel.tileManager.tile[tileNum1].collision == true ||
                        panel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < panel.superObject.length; i++) {
            if (panel.superObject[i] != null) {
                //Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                panel.superObject[i].solidArea.x = panel.superObject[i].worldX + panel.superObject[i].solidArea.x;
                panel.superObject[i].solidArea.y = panel.superObject[i].worldY + panel.superObject[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(panel.superObject[i].solidArea)) {
                            if (panel.superObject[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(panel.superObject[i].solidArea)) {
                            if (panel.superObject[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(panel.superObject[i].solidArea)) {
                            if (panel.superObject[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(panel.superObject[i].solidArea)) {
                            if (panel.superObject[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                panel.superObject[i].solidArea.x = panel.superObject[i].solidAreaDefaultX;
                panel.superObject[i].solidArea.y = panel.superObject[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
