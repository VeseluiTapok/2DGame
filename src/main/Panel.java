package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

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

    //System
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //Entity and object
    public Player player = new Player(this, keyHandler);
    public SuperObject superObject[] = new SuperObject[10];
    public Entity nps[] = new Entity[10];

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

        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
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

            if (timer >= 1000000000) {
                timer = 0;
                drawCount = 0;
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

            //OBJECT
            for (int i = 0; i < superObject.length; i++) {
                if (superObject[i] != null) {
                    superObject[i].draw(graphics2, this);
                }
            }

            //NPS
            for (int i = 0; i < nps.length; i++) {
                if (nps[i] != null) {
                    nps[i].draw(graphics2);
                }
            }

            //PLAYER
            player.draw(graphics2);

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