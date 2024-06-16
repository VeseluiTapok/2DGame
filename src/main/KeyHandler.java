package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    Panel panel;
    public boolean upPressed, downPressed, leftPressed,
    rightPressed, enterPressed, shotKeyPressed;
    //DEBUG
    public boolean showDebugText = false;

    public KeyHandler(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //Title state
        if (panel.gameState == panel.titleState) {
           titleState(code);
        }

        //Play state
        if (panel.gameState == panel.playState) {
            playState(code);
        }
        //Pause state
        else if (panel.gameState == panel.pauseState) {
            pauseState(code);
        }

        //Dialogue state
        else if (panel.gameState == panel.dialogueState) {
            dialogueState(code);
        }

        //Character state
        else if (panel.gameState == panel.characterState) {
            characterState(code);
        }

        //LevelUp state
        else if (panel.gameState == panel.levelUpState) {
            levelUpState(code);
        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            panel.playSoundEffect(8);
            panel.ui.TitleCommandNum--;
            if (panel.ui.TitleCommandNum < 0) {
                panel.ui.TitleCommandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            panel.playSoundEffect(8);
            panel.ui.TitleCommandNum++;
            if (panel.ui.TitleCommandNum > 2) {
                panel.ui.TitleCommandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (panel.ui.TitleCommandNum == 0) {
                panel.playSoundEffect(9);
                panel.gameState = panel.playState;
                panel.playMusic(0);
            }
            if (panel.ui.TitleCommandNum == 1) {
                //NOTHING
            }
            if (panel.ui.TitleCommandNum == 2) {
                panel.playSoundEffect(9);
                System.exit(0);
            }
        }
    }
    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            panel.gameState = panel.pauseState;
        }
        if (code == KeyEvent.VK_E) {
            panel.gameState = panel.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        //DEBUG
        if (code == KeyEvent.VK_T) {
            if (showDebugText == false) {
                showDebugText = true;
            }
            else if (showDebugText == true) {
                showDebugText = false;
            }
        }
        if (code == KeyEvent.VK_R) {
            panel.tileManager.loadMaps("/maps/map01.txt");
        }
    }
    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            panel.gameState = panel.playState;
        }
    }
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            panel.gameState = panel.playState;
        }
    }
    public void characterState(int code) {
        if (code == KeyEvent.VK_E) {
            panel.gameState = panel.playState;
        }
        if (code == KeyEvent.VK_W) {
            if (panel.ui.slotRow != 0) {
                panel.playSoundEffect(11);
                panel.ui.slotRow--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if (panel.ui.slotRow != 3) {
                panel.playSoundEffect(11);
                panel.ui.slotRow++;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (panel.ui.slotCol != 0) {
                panel.playSoundEffect(11);
                panel.ui.slotCol--;
            }
        }
        if (code == KeyEvent.VK_D) {
            if (panel.ui.slotCol != 4) {
                panel.playSoundEffect(11);
                panel.ui.slotCol++;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            panel.player.selectItem();
        }
    }
    public void levelUpState(int code) {
        if (code == KeyEvent.VK_A) {
            panel.playSoundEffect(8);
            panel.ui.LevelUpCommandNum--;
            if (panel.ui.LevelUpCommandNum < 0) {
                panel.ui.LevelUpCommandNum = 2;
            }
        }
        if (code == KeyEvent.VK_D) {
            panel.playSoundEffect(8);
            panel.ui.LevelUpCommandNum++;
            if (panel.ui.LevelUpCommandNum > 2) {
                panel.ui.LevelUpCommandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (panel.ui.LevelUpCommandNum == 0) {
                panel.playSoundEffect(9);
                panel.player.strength++;
                panel.gameState = panel.playState;
            }
            if (panel.ui.LevelUpCommandNum == 1) {
                panel.playSoundEffect(9);
                panel.player.maxHP += 2;
                panel.gameState = panel.playState;
            }
            if (panel.ui.LevelUpCommandNum == 2) {
                panel.playSoundEffect(9);
                panel.player.dexterity++;
                panel.gameState = panel.playState;
            }
        }
        panel.player.attack = panel.player.getAttack();
        panel.player.defence = panel.player.getDefence();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}
