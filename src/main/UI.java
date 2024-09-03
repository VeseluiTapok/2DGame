package main;

import entity.Entity;
import object.OBJ_Bronze_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    Panel panel;
    Graphics2D graphics2D;
    Font purisaB, MaruM;
    BufferedImage heartFull, heartHalf, heartBlank, crystalFull, crystalBlank, coin;
    public boolean messageOn = false;
    public ArrayList<String> messages = new ArrayList<>();
    public ArrayList<String> headLines = new ArrayList<>();
    public ArrayList<Integer> messageCounters = new ArrayList<>();
    public boolean canDelete = false;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int counter = 0;
    public Entity nps;
    public int commandNum = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npsSlotCol = 0;
    public int npsSlotRow = 0;
    public int subState = 0;

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

        Entity crystal = new OBJ_ManaCrystal(panel);
        crystalFull = crystal.image1;
        crystalBlank = crystal.image2;

        Entity bronzeCoin = new OBJ_Bronze_Coin(panel);
        coin = bronzeCoin.down1;
    }

    public void addMessage(String text) {
        messages.add(text);
        messageCounters.add(0);

    }

    public void addHeadLines(String text) {
        headLines.add(text);

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
            drawPlayerCharacteristics();
            drawMessage();
            drawHeadLines();
        }

        //Pause state
        if (panel.gameState == panel.pauseState) {
            drawPlayerCharacteristics();
            drawPauseScreen();
        }

        //Dialogue state
        if (panel.gameState == panel.dialogueState) {
            drawPlayerCharacteristics();
            drawDialogueScreen();
            drawHeadLines();
        }

        //Character state
        if (panel.gameState == panel.characterState) {
            drawPlayerCharacteristics();
            drawCharacterScreen();
            drawInventory(panel.player, true);
        }

        //Level up state
        if (panel.gameState == panel.levelUpState) {
            drawPlayerCharacteristics();
            drawLevelUpScreen();
        }
        
        //Options state
        if (panel.gameState == panel.optionsState) {
            drawPlayerCharacteristics();
            drawOptionsScreen();
        }

        //Game over state
        if (panel.gameState == panel.gameOverState) {
            drawGameOverScreen();
        }

        //Transition state
        if (panel.gameState == panel.transitionState) {
            drawTransitionScreen();
        }

        //Trade state
        if (panel.gameState == panel.tradeState) {
            drawTradeScreen();
        }
    }

    public void drawMessage() {
        int messageX = panel.tileSize;
        int messageY = panel.tileSize*4;
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i) != null) {
                graphics2D.setColor(Color.black);
                graphics2D.drawString(messages.get(i), messageX+2, messageY+2);

                graphics2D.setColor(Color.white);
                graphics2D.drawString(messages.get(i), messageX, messageY);

                int counter = messageCounters.get(i) + 1;
                messageCounters.set(i, counter);
                messageY += 50;

                if (messageCounters.get(i) > 180) {
                    messages.remove(i);
                    messageCounters.remove(i);
                }
            }
        }
    }

    public void drawHeadLines() {
        int messageX;
        int messageY = panel.tileSize*12 - 40;
        graphics2D.setFont(purisaB);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 20F));

        for (int i = 0; i < headLines.size(); i++) {
            if (headLines.get(i) != null) {
                messageX = getXForCenteredText(headLines.get(i));
                graphics2D.setColor(Color.white);
                graphics2D.drawString("<" + headLines.get(i) + ">", messageX+2, messageY+2);

                graphics2D.setColor(Color.black);
                graphics2D.drawString("<" + headLines.get(i) + ">", messageX, messageY);

                if (canDelete == true) {
                    headLines.remove(i);
                    canDelete = false;
                }
            }
        }
        graphics2D.setFont(MaruM);
    }

    public void drawPlayerCharacteristics() {
        int x = panel.tileSize/4;
        int y = panel.tileSize/4;
        boolean zeroHp = false;
        int i = 0;

        //DRAW MAX HP
        while (i < panel.player.maxHP/2) {
            if (i == panel.player.currentHP/2) {
                graphics2D.drawImage(panel.player.heartImage, x, y, null);
            }
            else {
                graphics2D.drawImage(heartBlank, x, y, null);
            }
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
            else {
                graphics2D.drawImage(panel.player.heartImage, x, y, null);
            }
            i++;
            x += panel.tileSize;
        }

        //DRAW MAX MANA
        x = (panel.tileSize/2)-12;
        y = panel.tileSize+7;
        i = 0;
        while (i < panel.player.maxMana) {
            if (i == panel.player.currentMana) {
                graphics2D.drawImage(panel.player.manaImage, x, y, null);
            }
            else {
                graphics2D.drawImage(crystalBlank, x, y, null);
            }
            i++;
            x += 35;
        }

        //RESET
        x = (panel.tileSize/2)-12;
        y = panel.tileSize+7;
        i = 0;

        //DRAW MANA
        while (i < panel.player.currentMana) {
            graphics2D.drawImage(crystalFull, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawTitleScreen() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, panel.screenWidth, panel.screenHeight);

        //TITLE NAME
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 120F));
        String text = "2D Adventure";
        int x = getXForCenteredText(text);
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
        x = getXForCenteredText(text);
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
        x = getXForCenteredText(text);
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
        x = getXForCenteredText(text);
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
        int x = getXForCenteredText(text);
        int y = panel.screenHeight/2;

        graphics2D.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        //Window
        int x = panel.tileSize * 3;
        int y = panel.tileSize / 2;
        int width = panel.screenWidth - (panel.tileSize * 6);
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

    public void drawCharacterScreen() {
        //Create a frame
        final int frameX = panel.tileSize;
        final int frameY = panel.tileSize + 20;
        final int frameWight = panel.tileSize*5;
        final int frameHeight = panel.tileSize*10;
        drawSubWindow(frameX, frameY, frameWight, frameHeight);

        //Text
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.setFont(MaruM);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + panel.tileSize;
        final int lineHeight = 35;

        //Names
        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("HP", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Mana", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Strength", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Attack", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Defence", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Exp", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Next Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        graphics2D.drawString("Weapon", textX, textY);
        textY += lineHeight + 5;
        graphics2D.drawString("Shield", textX, textY);

        //Values
        int tailX = (frameX + frameWight) - 30;

        //Reset textY
        textY = frameY + panel.tileSize;
        String value;

        value = String.valueOf(panel.player.level);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.currentHP + "/" + panel.player.maxHP);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.currentMana + "/" + panel.player.maxMana);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.defence);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(panel.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);

        textY += lineHeight;
        graphics2D.drawImage(panel.player.currentWeapon.down1, tailX - panel.tileSize, textY-25, null);

        textY += panel.tileSize;
        graphics2D.drawImage(panel.player.currentShield.down1, tailX - panel.tileSize, textY-25, null);
    }

    public void drawLevelUpScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(96F));

        //FRAME
        int frameX = panel.tileSize*2;
        int frameY = panel.tileSize+20;
        int frameWidth = panel.tileSize*16;
        int frameHeight = panel.tileSize*6;
        drawSubWindow(frameX, frameY ,frameWidth, frameHeight);

        //TEXT
        int textX = getXForCenteredText("LEVEL UP") - 32;
        int textY = frameY + panel.tileSize+24;
        graphics2D.setFont(purisaB);
        graphics2D.setFont(graphics2D.getFont().deriveFont(60F));
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawString("LEVEL UP", textX+2, textY+2);
        graphics2D.setColor(new Color(243, 201, 76));
        graphics2D.drawString("LEVEL UP", textX, textY);

        //IMAGE
        int imageX = frameX + panel.tileSize*2;
        int imageY = frameY + panel.tileSize*2;
        graphics2D.drawImage(panel.player.strength1.down1, imageX, imageY,
                panel.tileSize*2, panel.tileSize*2, null);

        if (commandNum == 0) {
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString("<", imageX+panel.tileSize*2+12, imageY+panel.tileSize+panel.tileSize+2);
            graphics2D.setColor(new Color(255, 255, 255));
            graphics2D.drawString("<", imageX+panel.tileSize*2+10, imageY+panel.tileSize+panel.tileSize);
        }

        imageX += panel.tileSize*5;
        graphics2D.drawImage(heartFull, imageX, imageY,
                panel.tileSize*2, panel.tileSize*2, null);

        if (commandNum == 1) {
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString("<", imageX+panel.tileSize*2+12, imageY+panel.tileSize+panel.tileSize+2);
            graphics2D.setColor(new Color(255, 255, 255));
            graphics2D.drawString("<", imageX+panel.tileSize*2+10, imageY+panel.tileSize+panel.tileSize);
        }

        imageX += panel.tileSize*5;
        graphics2D.drawImage(panel.player.defenceImage.down1, imageX, imageY,
                panel.tileSize*2, panel.tileSize*2, null);

        if (commandNum == 2) {
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString("<", imageX+panel.tileSize*2+12, imageY+panel.tileSize+panel.tileSize+2);
            graphics2D.setColor(new Color(255, 255, 255));
            graphics2D.drawString("<", imageX+panel.tileSize*2+10, imageY+panel.tileSize+panel.tileSize);
        }

        //LEVEL UP TEXT
        graphics2D.setFont(MaruM);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        int LUtextX = frameX + (panel.tileSize+35);
        int LUtextY = frameY + panel.tileSize*5;

        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawString("+1 Strength", LUtextX+2, LUtextY+2);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.drawString("+1 Strength", LUtextX, LUtextY);

        LUtextX += panel.tileSize*5+32;
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawString("+2 HP", LUtextX+2, LUtextY+2);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.drawString("+2 HP", LUtextX, LUtextY);

        LUtextX += panel.tileSize*4+20;
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawString("+1 Defence", LUtextX+2, LUtextY+2);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.drawString("+1 Defence", LUtextX, LUtextY);

    }

    public void drawInventory(Entity entity, Boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == panel.player) {
            frameX = panel.tileSize*12;
            frameY = panel.tileSize;
            frameWidth = panel.tileSize*6;
            frameHeight = panel.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = panel.tileSize*2;
            frameY = panel.tileSize;
            frameWidth = panel.tileSize*6;
            frameHeight = panel.tileSize*5;
            slotCol = npsSlotCol;
            slotRow = npsSlotRow;
        }

        //FRAME
        drawSubWindow(frameX, frameY ,frameWidth, frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = panel.tileSize+3;

        //DRAW ENTITY'S ITEMS
        for (int index = 0; index < entity.inventory.size(); index++) {

            //EQUIP CURSOR
            if (entity.inventory.get(index) == entity.currentWeapon ||
                    entity.inventory.get(index) == entity.currentShield) {
                graphics2D.setColor(new Color(240, 190, 90));
                graphics2D.fillRoundRect(slotX, slotY, panel.tileSize, panel.tileSize, 10, 10);
            }

            graphics2D.drawImage(entity.inventory.get(index).down1, slotX, slotY, null);

            slotX += slotSize;

            if (index == 4 || index == 9 || index == 14) {
                slotY += slotSize;
                slotX = slotXStart;
            }
        }

        //CURSOR
        if (cursor) {
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = panel.tileSize;
            int cursorHeight = panel.tileSize;

            //DRAW CURSOR
            graphics2D.setColor(Color.white);
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = panel.tileSize*3;

            //DRAW DESCRIPTION FRAME
            int textX = dFrameX + 20;
            int textY = dFrameY + panel.tileSize;
            graphics2D.setFont(purisaB);
            graphics2D.setFont(graphics2D.getFont().deriveFont(18F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY ,dFrameWidth, dFrameHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    graphics2D.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawGameOverScreen() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, panel.screenWidth, panel.screenHeight);

        String text;
        int x;
        int y;
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 110F));

        //SHADOW
        text = "Game over";
        x = getXForCenteredText(text);
        y = panel.tileSize*4;
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x, y);

        //MAIN
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x-4, y-4);

        //RETRY
        graphics2D.setFont(graphics2D.getFont().deriveFont(50F));
        text = "Retry";
        x = getXForCenteredText(text);
        y += panel.tileSize*4;
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x, y);
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x-4, y-4);
        if (commandNum == 0) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x-25 ,y);
            graphics2D.setColor(Color.white);
            graphics2D.drawString(">", x-29 ,y);
        }

        //QUIT
        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x, y);
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x-4, y-4);
        if (commandNum == 1) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x-25 ,y);
            graphics2D.setColor(Color.white);
            graphics2D.drawString(">", x-29 ,y);
        }

    }

    public void drawOptionsScreen() {
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));

        //SUB WINDOW
        int frameX = panel.tileSize*6;
        int frameY = panel.tileSize;
        int frameWidth = panel.tileSize*8;
        int frameHeight = panel.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_endGame(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_fullScreen(frameX, frameY); break;
        }

        panel.keyHandler.enterPressed = false;
    }

    public void drawTransitionScreen() {
        if (counter < 50) {
            counter++;
            graphics2D.setColor(new Color(0, 0, 0, counter * 5));
            graphics2D.fillRect(0, 0, panel.screenWidth, panel.screenHeight);
        }
        else if (counter >= 50) {
            counter = 0;
            panel.gameState = panel.playState;
            panel.currentMap = panel.eventHandler.tempMap;
            panel.player.worldX = panel.eventHandler.tempCol*panel.tileSize;
            panel.player.worldY = panel.eventHandler.tempRow*panel.tileSize;
            panel.eventHandler.previousEventX = panel.player.worldX;
            panel.eventHandler.previousEventY = panel.player.worldY;
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0: tradeSelect(); break;
            case 1: tradeBuy(); break;
            case 2: tradeSell(); break;
        }

        panel.keyHandler.enterPressed = false;
    }

    public void tradeSelect() {
        drawDialogueScreen();

        //DRAW WINDOW
        int x = panel.tileSize*15;
        int y = panel.tileSize*4;
        int width = (int) (panel.tileSize*3.5);
        int height = (int) (panel.tileSize*3.5);
        drawSubWindow(x, y, width, height);

        //DRAW TEXT
        x += panel.tileSize;
        y += panel.tileSize;
        graphics2D.drawString("Buy", x, y);
        if (commandNum == 0) {
            graphics2D.drawString(">", x-24, y);
            if (panel.keyHandler.enterPressed) {
                subState = 1;
            }
        }
        y += panel.tileSize;
        graphics2D.drawString("Sell", x, y);
        if (commandNum == 1) {
            graphics2D.drawString(">", x-24, y);
            if (panel.keyHandler.enterPressed) {
                subState = 2;
            }
        }
        y += panel.tileSize;
        graphics2D.drawString("Leave", x, y);
        if (commandNum == 2) {
            graphics2D.drawString(">", x-24, y);
            if (panel.keyHandler.enterPressed) {
                commandNum = 0;
                panel.gameState = panel.dialogueState;
                currentDialogue = "Come again, hehe!";
            }
        }
    }

    public void tradeBuy() {
        //DRAW PLAYER INVENTORY
        drawInventory(panel.player, false);

        //DRAW NPS INVENTORY
        drawInventory(nps, true);

        //DRAW HINT WINDOW
        int x = panel.tileSize*2;
        int y = panel.tileSize*9;
        int width = panel.tileSize*6;
        int height = panel.tileSize*2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[ESC] Back", x+24, y+60);

        //DRAW PLAYER COIN WINDOW
        x = panel.tileSize*12;
        y = panel.tileSize*9;
        width = panel.tileSize*6;
        height = panel.tileSize*2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Your coins: " + panel.player.coin, x+24, y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npsSlotCol, npsSlotRow);
        if (itemIndex < nps.inventory.size()) {
            x = (int) (panel.tileSize*5.5);
            y = (int) (panel.tileSize*5.5);
            width = (int) (panel.tileSize*2.5);
            height = panel.tileSize;
            drawSubWindow(x, y, width, height);
            graphics2D.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = nps.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForAlignToRightText(text, panel.tileSize*8-20);
            graphics2D.drawString(text, x, y+32);

            //BUY AN ITEM
            if (panel.keyHandler.enterPressed) {
                if (nps.inventory.get(itemIndex).price > panel.player.coin) {
                    subState = 0;
                    panel.gameState = panel.dialogueState;
                    currentDialogue = "You need more coin to buy that!";
                    drawDialogueScreen();
                }
                if (panel.player.inventory.size() == panel.player.maxInventorySize) {
                    subState = 0;
                    panel.gameState = panel.dialogueState;
                    currentDialogue = "You cannot carry any more!";
                }
                else {
                    panel.player.coin -= nps.inventory.get(itemIndex).price;
                    panel.player.inventory.add(nps.inventory.get(itemIndex));
                }
            }
        }
    }

    public void tradeSell() {
        //DRAW PLAYER INVENTORY
        drawInventory(panel.player, true);

        int x;
        int y;
        int width;
        int height;

        //DRAW HINT WINDOW
        x = panel.tileSize*2;
        y = panel.tileSize*9;
        width = panel.tileSize*6;
        height = panel.tileSize*2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[ESC] Back", x+24, y+60);

        //DRAW PLAYER COIN WINDOW
        x = panel.tileSize*12;
        y = panel.tileSize*9;
        width = panel.tileSize*6;
        height = panel.tileSize*2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Your coins: " + panel.player.coin, x+24, y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < panel.player.inventory.size()) {
            x = (int) (panel.tileSize*15.5);
            y = (int) (panel.tileSize*5.5);
            width = (int) (panel.tileSize*2.5);
            height = panel.tileSize;
            drawSubWindow(x, y, width, height);
            graphics2D.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = panel.player.inventory.get(itemIndex).price/2;
            String text = "" + price;
            x = getXForAlignToRightText(text, panel.tileSize*18-20);
            graphics2D.drawString(text, x, y+32);

            //SELL AN ITEM
            if (panel.keyHandler.enterPressed) {
                if (panel.player.inventory.get(itemIndex) == panel.player.currentWeapon ||
                        panel.player.inventory.get(itemIndex) == panel.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    panel.gameState = panel.dialogueState;
                    currentDialogue = "you cannot sell equipped item!";
                }
                else {
                    panel.player.inventory.remove(itemIndex);
                    panel.player.coin += price;
                }
            }
        }
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        graphics2D.setFont(graphics2D.getFont().deriveFont(54F));
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + (panel.tileSize+20);
        graphics2D.drawString(text, textX, textY);

        //FULL SCREEN ON/OFF
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        textX = frameX + panel.tileSize;
        textY += panel.tileSize*2-20;
        graphics2D.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            graphics2D.drawString(">", textX-25, textY);
            if (panel.keyHandler.enterPressed) {
                if (!panel.fullScreenOn) {
                    panel.fullScreenOn = true;
                }
                else {
                    panel.fullScreenOn = false;
                }
                subState = 3;
            }
        }

        //MUSIC
        textY += panel.tileSize;
        graphics2D.drawString("Music", textX, textY);
        if (commandNum == 1) {
            graphics2D.drawString(">", textX-25, textY);
        }

        //SOUND EFFECT
        textY += panel.tileSize;
        graphics2D.drawString("Sound Effects", textX, textY);
        if (commandNum == 2) {
            graphics2D.drawString(">", textX-25, textY);
        }

        //CONTROL
        textY += panel.tileSize;
        graphics2D.drawString("Control", textX, textY);
        if (commandNum == 3) {
            graphics2D.drawString(">", textX-25, textY);
            if (panel.keyHandler.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        //END GAME
        textY += panel.tileSize;
        graphics2D.drawString("End game", textX, textY);
        if (commandNum == 4) {
            graphics2D.drawString(">", textX-25, textY);
            if (panel.keyHandler.enterPressed) {
                commandNum = 0;
                subState = 1;
            }
        }

        //BACK
        textY += panel.tileSize*2;
        graphics2D.drawString("Back", textX, textY);
        if (commandNum == 5) {
            graphics2D.drawString(">", textX-25, textY);
            if (panel.keyHandler.enterPressed) {
                panel.gameState = panel.playState;
                commandNum = 0;
            }
        }

        //FULL SCREEN CHECK BOX
        textX = frameX + (int) (panel.tileSize*4.5);
        textY = frameY + panel.tileSize*2 + 24;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRect(textX, textY, 24, 24);
        if (panel.fullScreenOn) {
            graphics2D.fillRect(textX, textY, 24, 24);
        }

        //MUSIC VOLUME
        textY += panel.tileSize;
        graphics2D.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24*panel.music.volumeScale;
        graphics2D.fillRect(textX, textY, volumeWidth, 24);

        //SOUND EFFECT VOLUME
        textY += panel.tileSize;
        graphics2D.drawRect(textX, textY, 120, 24);
        volumeWidth = 24*panel.soundEffect.volumeScale;
        graphics2D.fillRect(textX, textY, volumeWidth, 24);

        panel.config.saveConfig();
    }

    public void options_fullScreen(int frameX, int frameY) {
        int textX = frameX + panel.tileSize;
        int textY = frameY + panel.tileSize*3;

        currentDialogue = "The change will take \neffect after restarting \nthe game";

        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, textX, textY);
            textY += 40;
        }

        //Back
        textY = frameY + panel.tileSize*9;
        graphics2D.drawString("Back", textX, textY);
        if (commandNum == 0) {
            graphics2D.drawString(">", textX, textY);
            if (panel.keyHandler.enterPressed) {
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + panel.tileSize;
        graphics2D.drawString(text, textX, textY);

        textX = frameX + panel.tileSize;
        textY += panel.tileSize;
        graphics2D.drawString("Move", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("Config/Attack", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("Shoot/Cast", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("Character Screen", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("Pause", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("Options", textX, textY);

        graphics2D.setColor(Color.red);
        textX = frameX + panel.tileSize*6;
        textY = frameY + panel.tileSize*2;
        graphics2D.drawString("WASD", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("LMB", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("F", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("C", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("P", textX, textY);
        textY += panel.tileSize;
        graphics2D.drawString("ESC", textX, textY);

        //BACK
        graphics2D.setColor(Color.white);
        textX = frameX + panel.tileSize;
        textY = frameY + panel.tileSize*9;
        graphics2D.drawString("Back", textX, textY);
        if (commandNum == 0) {
            graphics2D.drawString(">", textX-25, textY);
            if (panel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGame(int frameX, int frameY) {
        int textX = frameX + panel.tileSize;
        int textY = frameY + panel.tileSize*3;

        currentDialogue = "Quit the game and \nreturn to the title screen";

        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, textX, textY);
            textY += 40;
        }

        //YES
        String text = "Yes";
        textX = getXForCenteredText(text);
        textY += panel.tileSize*3;
        graphics2D.drawString(text, textX, textY);
        if (commandNum == 0) {
            graphics2D.drawString(">", textX-25, textY);
            if (panel.keyHandler.enterPressed) {
                subState = 0;
                panel.music.stop();
                panel.gameState = panel.titleState;
            }
        }

        //NO
        text = "No";
        textX = getXForCenteredText(text);
        textY += panel.tileSize;
        graphics2D.drawString(text, textX, textY);
        if (commandNum == 1) {
            graphics2D.drawString(">", textX-25, textY);
            if (panel.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow *5);
        return itemIndex;
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

    public int getXForCenteredText(String text) {

        int length = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = panel.screenWidth/2 - length/2;
        return x;
    }

    public int getXForAlignToRightText(String text, int tailX) {

        int length = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = tailX - length;
        return x;
    }
}