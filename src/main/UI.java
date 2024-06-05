package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    Panel panel;
    Graphics2D graphics2D;
    Font purisaB, MaruM;
    BufferedImage heartFull, heartHalf, heartBlank, Hole;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;

    public UI(Panel panel) {
        this.panel = panel;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            inputStream = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            MaruM = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }

        //Create hud object
        Entity heart = new OBJ_Heart(panel);
        heartFull = heart.image1;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(MaruM);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.white);

        //Title state
        if (panel.gameState == panel.titleState) {
            drawTitleScreen();
        }
        //Play state
        if (panel.gameState == panel.playState) {
            drawPlayerHP();
        }

        //Pause state
        if (panel.gameState == panel.pauseState) {
            drawPlayerHP();
            drawPauseScreen();
        }

        //Dialogue state
        if (panel.gameState == panel.dialogueState) {
            drawPlayerHP();
            drawDialogueScreen();
        }
    }

    public void drawPlayerHP() {
        int x = panel.tileSize/4;
        int y = panel.tileSize/4;
        int i = 0;

        //DRAW MAX HP
        while (i < panel.player.maxHP/2) {
            graphics2D.drawImage(heartBlank, x, y, null);
            i++;
            x += panel.tileSize;
        }

        //RESET
        x = panel.tileSize/4;
        y = panel.tileSize/4;
        i = 0;

        //DRAW CURRENT HP
        while (i < panel.player.currentHP) {
            graphics2D.drawImage(heartHalf, x, y, null);
            i++;
            if (i < panel.player.currentHP) {
                graphics2D.drawImage(heartFull, x, y, null);
            }
            i++;
            x += panel.tileSize;
        }
    }

    public void drawTitleScreen() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, panel.screenWidth, panel.screenHeight);

        //TITLE NAME
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 120F));
        String text = "2D Adventure";
        int x = getXforCenteredText(text);
        int y = panel.tileSize * 3;

        //SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x+5, y+5);

        //MAIN COLOR
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x, y);

        //GREEN BOY IMAGE
        x = panel.screenWidth/2 - (panel.tileSize*3)/2;
        y += panel.tileSize*2 - (panel.tileSize*3)/2;
        graphics2D.drawImage(panel.player.down1, x, y, panel.tileSize*3, panel.tileSize*3, null);

        //MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 52F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += panel.tileSize * 4;

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x+3, y+3);

        graphics2D.setColor(Color.white);
        graphics2D.drawString(text,x, y);
        if (commandNum == 0) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x - (panel.tileSize-3), y);

            graphics2D.setColor(Color.white);
            graphics2D.drawString(">", x-panel.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += panel.tileSize*1.3;

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x+3, y+3);

        graphics2D.setColor(Color.white);
        graphics2D.drawString(text,x, y);
        if (commandNum == 1) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x - (panel.tileSize-3), y);

            graphics2D.setColor(Color.white);
            graphics2D.drawString(">", x-panel.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += panel.tileSize*1.3;

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x+3, y+3);

        graphics2D.setColor(Color.white);
        graphics2D.drawString(text,x, y);
        if (commandNum == 2) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x - (panel.tileSize-3), y);

            graphics2D.setColor(Color.white);
            graphics2D.drawString(">", x-panel.tileSize, y);
        }
    }

    public void drawPauseScreen() {

        graphics2D.getFont().deriveFont(Font.PLAIN, 80F);
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = panel.screenHeight/2;

        graphics2D.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        //Window
        int x = panel.tileSize * 2;
        int y = panel.tileSize / 2;
        int width = panel.screenWidth - (panel.tileSize * 4);
        int height = panel.tileSize * 5;
        drawSubWindow(x, y, width, height);

        graphics2D.setFont(purisaB);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN , 28F));
        x += panel.tileSize;
        y += panel.tileSize;

        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 200);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXforCenteredText(String text) {

        int length = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = panel.screenWidth/2 - length/2;
        return x;
    }
}
