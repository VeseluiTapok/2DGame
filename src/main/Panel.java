package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Panel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;

    //Screen settings
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCols = 16;
    public final int maxScreenRows = 12;
    public final int screenWidth = tileSize * maxScreenCols;
    public final int screenHeight = tileSize * maxScreenRows;

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;
    public long timer = 0;

    //System
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    //Entity and object
    public Player player = new Player(this, keyHandler);
    public Entity object[] = new Entity[10];
    public Entity nps[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPS();
        assetSetter.setMonsters();

        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        long lastTime = System.nanoTime();
        long currentTime;
        double delta = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

        }
    }
    public void update() {
        if (gameState == playState) {
            //PLAYER
            player.update();
            //NPS
            for (int i = 0;i < nps.length; i++) {
                if (nps[i] != null) {
                    nps[i].update();
                }
            }
            for (int i = 0;i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();
                }
            }
        }
        if (gameState == pauseState) {
            //nothing
        }
    }
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2 = (Graphics2D)graphics;

        //DEBUG
        long drawStart = 0;
        if (keyHandler.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if (gameState == titleState) {
            tileManager.draw(graphics2);
            ui.draw(graphics2);
        }
        //OTHERS
        else {
            //TILE
            tileManager.draw(graphics2);

            //ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < nps.length; i++) {
                if (nps[i] != null) {
                    entityList.add(nps[i]);
                }
            }

            for (int i = 0; i < object.length; i++) {
                if (object[i] != null) {
                    entityList.add(object[i]);
                }
            }

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(graphics2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();

            //UI
            ui.draw(graphics2);
        }

        //DEBUG
        if (keyHandler.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            graphics2.setColor(Color.white);
            graphics2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw time: " + passed);
        }

        graphics2.dispose();
    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }
}