package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tileInteractive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Panel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;

    //Screen settings
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCols = 20;
    public final int maxScreenRows = 12;
    public final int screenWidth = tileSize * maxScreenCols;
    public final int screenHeight = tileSize * maxScreenRows;

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    //For Full screen
    int fullScreenWidth = screenWidth;
    int fullScreenHeight = screenHeight;
    BufferedImage tempScreen;
    Graphics2D graphics2D;
    public boolean fullScreenOn = false;

    //FPS
    int FPS = 60;
    public long timer = 0;

    //System
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pathFinder = new PathFinder(this);
    Thread gameThread;

    //Entity and object
    public Player player = new Player(this, keyHandler);
    public Entity object[][] = new Entity[maxMap][10];
    public Entity nps[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile[][] interactiveTile = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int levelUpState = 5;
    public final int optionsState = 6;
    public final int gameOverState = 7;
    public final int transitionState = 8;
    public final int tradeState = 9;

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
        assetSetter.setInteractiveTile();

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void retry() {
        player.setDefaultPlayerPosition();
        player.restoreDefaultHpAndMana();
        assetSetter.setNPS();
        assetSetter.setMonsters();
    }

    public void restart() {
        player.setDefaultValues();
        player.setItems();
        assetSetter.setObject();
        assetSetter.setNPS();
        assetSetter.setMonsters();
        assetSetter.setInteractiveTile();
    }

    public void setFullScreen() {
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment graphicsE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsD = graphicsE.getDefaultScreenDevice();
        graphicsD.setFullScreenWindow(Main.window);

        //GET FULL SCREEN WIGHT AND HEIGHT
        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
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
                drawToTempScreen();
                drawToScreen();
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
            for (int i = 0;i < nps[1].length; i++) {
                if (nps[currentMap][i] != null) {
                    nps[currentMap][i].update();
                }
            }
            for (int i = 0;i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0;i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }
            for (int i = 0;i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }
            for (int i = 0;i < interactiveTile[1].length; i++) {
                if (interactiveTile[currentMap][i] != null) {
                    interactiveTile[currentMap][i].update();
                }
            }
        }
        if (gameState == pauseState) {
            //nothing
        }
    }

    public void drawToTempScreen() {
        //DEBUG
        long drawStart = 0;
        if (keyHandler.showDebugText == true) {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if (gameState == titleState) {
            tileManager.draw(graphics2D);
            ui.draw(graphics2D);
        }
        //OTHERS
        else {
            //TILE
            tileManager.draw(graphics2D);

            for (int i = 0; i < interactiveTile[1].length; i++) {
                if (interactiveTile[currentMap][i] != null) {
                    interactiveTile[currentMap][i].draw(graphics2D);
                }
            }

            //ADD ENTITIES TO THE LIST
            entityList.add(player);

            //NPS
            for (int i = 0; i < nps[1].length; i++) {
                if (nps[currentMap][i] != null) {
                    entityList.add(nps[currentMap][i]);
                }
            }

            //OBJECT
            for (int i = 0; i < object[1].length; i++) {
                if (object[currentMap][i] != null) {
                    entityList.add(object[currentMap][i]);
                }
            }

            //MONSTER
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            //PROJECTILE
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            //PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
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
                entityList.get(i).draw(graphics2D);
            }

            //EMPTY ENTITY LIST
            entityList.clear();

            //UI
            ui.draw(graphics2D);
        }

        //DEBUG
        if (keyHandler.showDebugText == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            graphics2D.setFont(new Font("Arial", Font.PLAIN,  20));
            graphics2D.setColor(Color.white);
            int x = 10;
            int y = 420;
            int lineHeight = 20;

            graphics2D.drawString("X " + player.worldX, x, y);
            y += lineHeight;
            graphics2D.drawString("Y "+ player.worldY, x, y);
            y += lineHeight;
            graphics2D.drawString("Col " + (player.worldX + player.solidArea.x)/tileSize, x, y);
            y += lineHeight;
            graphics2D.drawString("Row "+ (player.worldY + player.solidArea.y)/tileSize, x, y);

            graphics2D.setColor(Color.white);
            graphics2D.drawString("Draw time: " + passed, 10, 400);
        }
    }

    public void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        graphics.dispose();
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