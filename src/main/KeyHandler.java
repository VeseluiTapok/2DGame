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

        //Options state
        else if (panel.gameState == panel.optionsState) {
            optionsState(code);
        }

        //Game over state
        else if (panel.gameState == panel.gameOverState) {
            gameOverState(code);
        }

        //Trade state
        else if (panel.gameState == panel.tradeState) {
            tradeState(code);
        }

        //Level Up state
        else if (panel.gameState == panel.levelUpState) {
            levelUpState(code);
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (panel.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                panel.playSoundEffect(8);
                panel.ui.commandNum--;
                if (panel.ui.commandNum < 0) {
                    panel.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                panel.playSoundEffect(8);
                panel.ui.commandNum++;
                if (panel.ui.commandNum > 2) {
                    panel.ui.commandNum = 0;
                }
            }
        }
        else if (panel.ui.subState == 1) {
            npsInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                panel.ui.subState = 0;
            }
        }
        else if (panel.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                panel.ui.subState = 0;
            }
        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            panel.playSoundEffect(8);
            panel.ui.commandNum--;
            if (panel.ui.commandNum < 0) {
                panel.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            panel.playSoundEffect(8);
            panel.ui.commandNum++;
            if (panel.ui.commandNum > 2) {
                panel.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (panel.ui.commandNum == 0) {
                panel.playSoundEffect(9);
                panel.gameState = panel.playState;
                panel.playMusic(0);
            }
            if (panel.ui.commandNum == 1) {
                //NOTHING
            }
            if (panel.ui.commandNum == 2) {
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
        if (code == KeyEvent.VK_ESCAPE) {
            panel.gameState = panel.optionsState;
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
        if (code == KeyEvent.VK_ENTER) {
            panel.player.selectItem();
        }
        playerInventory(code);
    }

    public void levelUpState(int code) {
        if (code == KeyEvent.VK_A) {
            panel.playSoundEffect(8);
            panel.ui.commandNum--;
            if (panel.ui.commandNum < 0) {
                panel.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_D) {
            panel.playSoundEffect(8);
            panel.ui.commandNum++;
            if (panel.ui.commandNum > 2) {
                panel.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (panel.ui.commandNum == 0) {
                panel.playSoundEffect(9);
                panel.player.strength++;
                panel.gameState = panel.playState;
            }
            if (panel.ui.commandNum == 1) {
                panel.playSoundEffect(9);
                panel.player.maxHP += 2;
                panel.gameState = panel.playState;
            }
            if (panel.ui.commandNum == 2) {
                panel.playSoundEffect(9);
                panel.player.dexterity++;
                panel.gameState = panel.playState;
            }
        }
        panel.player.attack = panel.player.getAttack();
        panel.player.defence = panel.player.getDefence();
    }

    public void optionsState(int code) {

        if (code == KeyEvent.VK_ESCAPE) {
            panel.gameState = panel.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (panel.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 1: maxCommandNum = 1;break;
        }

        if (code == KeyEvent.VK_W) {
            panel.playSoundEffect(8);
            panel.ui.commandNum--;
            if (panel.ui.commandNum < 0) {
                panel.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S) {
            panel.playSoundEffect(8);
            panel.ui.commandNum++;
            if (panel.ui.commandNum > maxCommandNum) {
                panel.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (panel.ui.subState == 0) {

                //MUSIC
                if (panel.ui.commandNum == 1 && panel.music.volumeScale > 0) {
                    panel.music.volumeScale--;
                    panel.music.checkVolume();
                    panel.playSoundEffect(8);
                }

                //SOUND EFFECT
                if (panel.ui.commandNum == 2 && panel.soundEffect.volumeScale > 0) {
                    panel.soundEffect.volumeScale--;
                    panel.playSoundEffect(8);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (panel.ui.subState == 0) {

                //MUSIC
                if (panel.ui.commandNum == 1 && panel.music.volumeScale < 5) {
                    panel.music.volumeScale++;
                    panel.music.checkVolume();
                    panel.playSoundEffect(8);
                }

                //SOUND EFFECT
                if (panel.ui.commandNum == 2 && panel.soundEffect.volumeScale < 5) {
                    panel.soundEffect.volumeScale++;
                    panel.playSoundEffect(8);
                }
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            if (panel.ui.commandNum == 0) {
                panel.gameState = panel.playState;
                panel.retry();
            }
            else if (panel.ui.commandNum == 1) {
                panel.music.stop();
                panel.gameState = panel.titleState;
                panel.restart();
            }
        }

        if (code == KeyEvent.VK_W) {
            panel.playSoundEffect(8);
            panel.ui.commandNum--;
            if (panel.ui.commandNum < 0) {
                panel.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S) {
            panel.playSoundEffect(8);
            panel.ui.commandNum++;
            if (panel.ui.commandNum > 1) {
                panel.ui.commandNum = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (panel.ui.playerSlotRow != 0) {
                panel.playSoundEffect(11);
                panel.ui.playerSlotRow--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if (panel.ui.playerSlotRow != 3) {
                panel.playSoundEffect(11);
                panel.ui.playerSlotRow++;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (panel.ui.playerSlotCol != 0) {
                panel.playSoundEffect(11);
                panel.ui.playerSlotCol--;
            }
        }
        if (code == KeyEvent.VK_D) {
            if (panel.ui.playerSlotCol != 4) {
                panel.playSoundEffect(11);
                panel.ui.playerSlotCol++;
            }
        }
    }

    public void npsInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (panel.ui.npsSlotRow != 0) {
                panel.playSoundEffect(11);
                panel.ui.npsSlotRow--;
            }
        }
        if (code == KeyEvent.VK_S) {
            if (panel.ui.npsSlotRow != 3) {
                panel.playSoundEffect(11);
                panel.ui.npsSlotRow++;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (panel.ui.npsSlotCol != 0) {
                panel.playSoundEffect(11);
                panel.ui.npsSlotCol--;
            }
        }
        if (code == KeyEvent.VK_D) {
            if (panel.ui.npsSlotCol != 4) {
                panel.playSoundEffect(11);
                panel.ui.npsSlotCol++;
            }
        }
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
