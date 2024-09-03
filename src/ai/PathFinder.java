package ai;

import entity.Entity;
import main.Panel;

import java.util.ArrayList;

public class PathFinder {
    Panel panel;
    public Node[][] node;
    public ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    public Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(Panel panel) {
        this.panel = panel;

        instantiateNodes();
    }

    public void instantiateNodes() {
        node = new Node[panel.maxWorldCol][panel.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < panel.maxWorldCol && row < panel.maxWorldRow) {
            node[col][row] = new Node(col, row);

            col++;
            if (col == panel.maxWorldCol) {
                row++;
                col = 0;
            }
        }
    }

    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < panel.maxWorldCol && row < panel.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checker = false;
            node[col][row].solid = false;

            col++;
            if (col == panel.maxWorldCol) {
                row++;
                col = 0;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < panel.maxWorldCol && row < panel.maxWorldRow) {
            int tileNum = panel.tileManager.mapTaleNum[panel.currentMap][col][row];
            if (panel.tileManager.tile[tileNum].collision) {
                node[col][row].solid = true;
            }

            for (int i = 0; i < panel.interactiveTile[1].length; i++) {
                if (panel.interactiveTile[panel.currentMap][i] != null
                        && panel.interactiveTile[panel.currentMap][i].destructible) {
                    int itCol = panel.interactiveTile[panel.currentMap][i].worldX/panel.tileSize;
                    int itRow = panel.interactiveTile[panel.currentMap][i].worldY/panel.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }

            getCost(node[col][row]);

            col++;
            if (col == panel.maxWorldCol) {
                row++;
                col = 0;
            }
        }
    }

    public void getCost(Node node) {
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checker = true;
            openList.remove(currentNode);

            if (row - 1 >= 0) {
                openNode(node[col][row-1]);
            }
            if (col - 1 >= 0) {
                openNode(node[col-1][row]);
            }
            if (row + 1 < panel.maxWorldRow) {
                openNode(node[col][row+1]);
            }
            if (col + 1 < panel.maxWorldCol) {
                openNode(node[col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost == bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }

                else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.size() == 0) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    public void openNode(Node node) {
        if (!node.open && !node.checker && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
