package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    Panel panel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //DEBUG
    public boolean checkDrawTime = false;

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
            if (code == KeyEvent.VK_W) {
                panel.ui.commandNum--;
                if (panel.ui.commandNum < 0) {
                    panel.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                panel.ui.commandNum++;
                if (panel.ui.commandNum > 2) {
                    panel.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (panel.ui.commandNum == 0) {
                    panel.gameState = panel.playState;
                    panel.playMusic(0);
                }
                if (panel.ui.commandNum == 1) {
                    //NOTHING
                }
                if (panel.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }

        //Play state
        if (panel.gameState == panel.playState) {
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
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            //DEBUG
            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;
                }
                else if (checkDrawTime == true) {
                    checkDrawTime = false;
                }
            }
        }
        //Pause state
        else if (panel.gameState == panel.pauseState) {
            if (code == KeyEvent.VK_P) {
                panel.gameState = panel.playState;
            }
        }

        //Dialogue state
        else if (panel.gameState == panel.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                panel.gameState = panel.playState;
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
    }
}
