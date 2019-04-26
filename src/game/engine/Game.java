package game.engine;

import game.Main;
import game.Menu.PauseMenu;
import game.Menu.SecondMenu;
import game.Menu.SecondMenu_Setting;
import game.swing.MainFrame;
import game.swing.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.io.IOException;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Game implements Animatable  {

    public int LastXBomb;
    public int LastYBomb;
    private int width;
    private int height;
    private int j=0;
    private int f=0;
    private int a=0;
    private int b=0;
    private int velocity;
    private Sound sound;
    private int HeightOfBomb=3000000;
    private int WidthOfBomb;
    private boolean BombIsExploded = false;
    public BufferedImage bufferedImageHeart;
    public BufferedImage bufferedImagebackground;
    private BufferedImage bufferedImageBomb;
    public static long TimeOfBackgrounddMove = 0;
    String NumberOfShoot = "" + MainPanel.NumberOfShoot();
    private Rocket rocket;
    public BombShoot bombShoot;
    private BufferedImage bufferedImage;
    private final ArrayList<Tir> tirs = new ArrayList<>();
    static PauseMenu pauseMenu = new PauseMenu();


    public Game(int width, int height) {
        this.width = width;
        this.height = height;

        rocket = new Rocket(width / 2 - 50, height - 200);
        int Xrocket = rocket.getX();

    }

    @Override
    public void paint(Graphics2D g2) {
        try {bufferedImageHeart = ImageIO.read(new File("resources/heart.png"));
        bufferedImagebackground = ImageIO.read(new File("resources/gamebackground.png"));
        bufferedImageBomb = ImageIO.read(new File("resources/bomb.png"));

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }



        //moving the background
        if(MainPanel.statePauseMenu == false) {
            velocity = 25;
            if (a == 0) {
                j++;
                g2.drawImage(bufferedImagebackground, 0, -400 + velocity * j, 1500, 1200, null);
            }
            if ((-400 + velocity * j) >= 0) {
                b = 1;
                if ((-1200 + velocity * f) >= 800 & (-400 + velocity * j) >= 0) {
                    b = 0;
                    f = 0;
                }
                if ((-400 + velocity * j) >= 800 & (-1200 + velocity * f) >= 0) {
                    a = 1;
                    j = 0;
                }
            }
            if (b == 1) {
                g2.drawImage(bufferedImagebackground, 0, -1200 + velocity * f, 1500, 1200, null);
                f++;
            }
            if (a == 1) {
                g2.drawImage(bufferedImagebackground, 0, -1200 + velocity * j, 1500, 1200, null);
                j++;
            }
        }
        if(MainPanel.statePauseMenu==true){
            g2.drawImage(bufferedImagebackground, 0, 0, 1500, 1200, null);
        }




        for (int i=0 ; i<20 ; i++){
            g2.setColor(new Color(253, 255, 255));
            g2.fillRect(15*i+150,20,8,17);
        }
        for (int i = 0; i< 20 - MainPanel.ShootCounter(); i++){
            g2.setColor(new Color(73, 230, 27));
            g2.fillRect(15*i+150,20,8,17);
        }
        g2.drawImage(bufferedImageHeart,25,700,15,15,null);
            g2.setColor(new Color(0, 200, 200));
            g2.drawString("5",50,716);

        g2.setColor(new Color(236, 231, 57));
        g2.drawString(NumberOfShoot,20,20);
        if(MainPanel.statePauseMenu == false) {
            if (MainPanel.BombState & rocket.getY() > 400) {
           /* bombShoot=new BombShoot(rocket.getX(), rocket.getY());
            bombShoot.paint(g2);*/
                HeightOfBomb = rocket.getY();
                WidthOfBomb = rocket.getX();
                MainPanel.BombState = false;
                BombIsExploded = false;
            }
            if (BombIsExploded == false) {

                g2.drawImage(bufferedImageBomb, WidthOfBomb, HeightOfBomb, 40, 40, null);
                HeightOfBomb = HeightOfBomb - 10;
            }
            if (HeightOfBomb < 400) {
                System.out.println(HeightOfBomb);

                BombIsExploded = true;

            }
            LastXBomb=WidthOfBomb;
            LastYBomb=HeightOfBomb;
        }
        else{
            g2.drawImage(bufferedImageBomb, LastXBomb, LastYBomb, 40, 40, null);
        }


        for (Tir tir : tirs) {
            tir.paint(g2);
        }

        rocket.paint(g2);
        if( MainPanel.statePauseMenu==true){

            pauseMenu.paint((Graphics2D) g2);


        }
    }

    @Override
    public void move() {
        rocket.move();
        synchronized (tirs) {
            for (Tir tir : tirs) {
                tir.move();
            }
        }
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void shelik() {
        if(SecondMenu_Setting.typeOfShoot == false) {
            synchronized (tirs) {
                int r = 25;
                for (int i = 0; i < 5; i++) {
                    double degree = (70 + i * 10) / 180.0 * Math.PI;

                    tirs.add(new Tir(rocket.getX() + r * Math.cos(degree),
                            rocket.getY() + -r * Math.sin(degree),
                            10 * Math.cos(degree),
                            -10 * Math.sin(degree)));
                   // System.out.println("multi");
                }
            }
        }
        if(SecondMenu_Setting.typeOfShoot == true){
            synchronized (tirs) {
                int r = 25;

                    double degree = (90) / 180.0 * Math.PI;
                    tirs.add(new Tir(rocket.getX(),
                            rocket.getY() + -r * Math.sin(degree),
                            15 * Math.cos(degree),
                            -15 * Math.sin(degree)));
                   // System.out.println("single");

            }
        }

            //sound.playSound();
    }
    public static void AnimatedBackground(){

    }



}
