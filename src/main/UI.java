package main;

import entity.Entity;
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
    BufferedImage heartFull, heartHalf, heartBlank, crystalFull, crystalBlank;
    public boolean messageOn = false;
    public ArrayList<String> messages = new ArrayList<>();
    public ArrayList<Integer> messageCounters = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int TitleCommandNum = 0;
    public int LevelUpCommandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0;

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
    }

    public void addMessage(String text) {
        messages.add(text);
        messageCounters.add(0);

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
            drawMessage();
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

        //Character state
        if (panel.gameState == panel.characterState) {
            drawPlayerHP();
            drawCharacterScreen();
            drawInventory();
        }

        //Level up state
        if (panel.gameState == panel.levelUpState) {
            drawPlayerHP();
            drawLevelUpScreen();
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

    public void drawPlayerHP() {
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
        if (TitleCommandNum == 0) {
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
        if (TitleCommandNum == 1) {
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
        if (TitleCommandNum == 2) {
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
        int frameWidth = panel.tileSize*12;
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
        int imageX = frameX + panel.tileSize;
        int imageY = frameY + panel.tileSize*2;
        graphics2D.drawImage(panel.player.strength1.down1, imageX, imageY,
                panel.tileSize*2, panel.tileSize*2, null);

        if (LevelUpCommandNum == 0) {
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString("<", imageX+panel.tileSize*2+12, imageY+panel.tileSize+panel.tileSize+2);
            graphics2D.setColor(new Color(255, 255, 255));
            graphics2D.drawString("<", imageX+panel.tileSize*2+10, imageY+panel.tileSize+panel.tileSize);
        }

        imageX += panel.tileSize*3+40;
        graphics2D.drawImage(heartFull, imageX, imageY,
                panel.tileSize*2, panel.tileSize*2, null);

        if (LevelUpCommandNum == 1) {
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString("<", imageX+panel.tileSize*2+12, imageY+panel.tileSize+panel.tileSize+2);
            graphics2D.setColor(new Color(255, 255, 255));
            graphics2D.drawString("<", imageX+panel.tileSize*2+10, imageY+panel.tileSize+panel.tileSize);
        }

        imageX += panel.tileSize*3+40;
        graphics2D.drawImage(panel.player.defenceImage.down1, imageX, imageY,
                panel.tileSize*2, panel.tileSize*2, null);

        if (LevelUpCommandNum == 2) {
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString("<", imageX+panel.tileSize*2+12, imageY+panel.tileSize+panel.tileSize+2);
            graphics2D.setColor(new Color(255, 255, 255));
            graphics2D.drawString("<", imageX+panel.tileSize*2+10, imageY+panel.tileSize+panel.tileSize);
        }

        //LEVEL UP TEXT
        graphics2D.setFont(MaruM);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        int LUtextX = frameX + 35;
        int LUtextY = frameY + panel.tileSize*5;

        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawString("+1 Strength", LUtextX+2, LUtextY+2);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.drawString("+1 Strength", LUtextX, LUtextY);

        LUtextX += panel.tileSize*4+32;
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawString("+2 HP", LUtextX+2, LUtextY+2);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.drawString("+2 HP", LUtextX, LUtextY);

        LUtextX += panel.tileSize*3;
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.drawString("+1 Defence", LUtextX+2, LUtextY+2);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.drawString("+1 Defence", LUtextX, LUtextY);

    }

    public void drawInventory() {

        //FRAME
        int frameX = panel.tileSize*9;
        int frameY = panel.tileSize+20;
        int frameWidth = panel.tileSize*6;
        int frameHeight = panel.tileSize*5;
        drawSubWindow(frameX, frameY ,frameWidth, frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = panel.tileSize+3;

        //DRAW PLAYER'S ITEMS
        for (int index = 0; index < panel.player.inventory.size(); index++) {

            //EQUIP CURSOR
            if (panel.player.inventory.get(index) == panel.player.currentWeapon ||
                    panel.player.inventory.get(index) == panel.player.currentShield) {
                graphics2D.setColor(new Color(240, 190, 90));
                graphics2D.fillRoundRect(slotX, slotY, panel.tileSize, panel.tileSize, 10, 10);
            }

            graphics2D.drawImage(panel.player.inventory.get(index).down1, slotX, slotY, null);

            slotX += slotSize;

            if (index == 4 || index == 9 || index == 14) {
                slotY += slotSize;
                slotX = slotXStart;
            }
        }

        //CURSOR
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

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < panel.player.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY ,dFrameWidth, dFrameHeight);

            for (String line : panel.player.inventory.get(itemIndex).description.split("\n")) {
                graphics2D.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow*5);
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