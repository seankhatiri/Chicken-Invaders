package game.engine;

import game.Main;
import game.swing.MainPanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;

public class background extends Thread implements Animatable {

    private BufferedImage bufferedImage1;
    private MainPanel mainPanel;

    public void background (MainPanel mainPanel) {
        this.mainPanel=mainPanel;


    }

    @Override
    public void run() {
        while (true) {


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /*public void paint (Graphics2D g2 ){

        g2.drawImage(bufferedImage1, 1000, 800, null);
    }
        try {
        bufferedImage1 = ImageIO.read(new File("resources/rocket.png"));
    } catch (IOException ex) {
        ex.printStackTrace();*/



    @Override
    public void paint(Graphics2D g2) {

    }

    @Override
    public void move() {

    }

}
