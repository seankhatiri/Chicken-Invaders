package game;

import game.Menu.PauseMenu;
import game.engine.Sound;
import game.swing.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PushbackInputStream;

public class PaintLoop extends Thread {
    private MainPanel mainPanel;
    public boolean runnng = true;

    PauseMenu pauseMenu = new PauseMenu();

    public PaintLoop(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    @Override
    public void run() {

        while (true) {

            runnng=mainPanel.state();

               if (runnng & MainPanel.statePauseMenu==false) {

                   mainPanel.moveGame();
               }


                mainPanel.repaint();



            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
