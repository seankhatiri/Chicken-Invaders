package game.swing;



import game.Menu.FirstMenu;
import game.Menu.PauseMenu;
import game.Menu.SecondMenu;
import game.Menu.SecondMenu_Setting;
import game.PaintLoop;
import game.engine.BombShoot;
import game.engine.Game;
import game.engine.Rocket;
import game.engine.Sound;
import game.logger.Logger;
import game.logger.Read;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;


public class MainPanel extends JPanel  {
    public JTextField jTextField0 = new JTextField();
    public JTextField jTextField1 = new JTextField();
    public JTextField jTextField2 = new JTextField();
    public JTextField jTextField3 = new JTextField();
    public JLabel jLabel = new JLabel();
    private boolean GameState=false;
    private boolean MenuState=false;
    public boolean runnng=true;
    private Game game;
    public static String NameOfPlayer ;
    SecondMenu SecondMenu = new SecondMenu();
    FirstMenu FirstMenu = new FirstMenu();
    public static int x;
    public static int y;
    public static long TimeOfShoot;
    public static int ShootCounter = 0;
    public static boolean WaitForShoot = true;
    public static long WaitForShootTime;
    public static int NumberOfShoot=10000;
    public static boolean BombState=false;
    public static boolean statePauseMenu = false;
    public static Logger log  ;
    public Read read;
    public Sound sound;


    private enum STATE {
        SEcondMenu ,
        FIrstMenu ,
        PauseMenu ,
        Game
    };
    private STATE state = STATE.FIrstMenu;


    public MainPanel() {
        setBounds(0, 0, 1000, 800);
        game = new Game(1000, 800);
        if(state == STATE.FIrstMenu){
            Jtextfieldinit(jTextField0,250,320,480,40,this);
            Jtextfieldinit(jTextField1,250,390,480,40,this);
            Jtextfieldinit(jTextField2,250,440,480,40,this);
            Jtextfieldinit(jTextField3,250,490,480,40,this);
        }

        System.out.println("hello");
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        if(state == STATE.Game) {
        game.paint((Graphics2D) g);
    }
        if(state == STATE.SEcondMenu) {
            this.remove(jTextField0);
            this.remove(jTextField1);
            this.remove(jTextField2);
            this.remove(jTextField3);
        }
        if(state == STATE.FIrstMenu){
            FirstMenu.paint((Graphics2D) g);
            MenuState = FirstMenu.MenuState();
            if(MenuState){
               state = STATE.SEcondMenu;
            }

        }
        if(state == STATE.SEcondMenu) {
            SecondMenu.paint((Graphics2D) g);
            GameState = SecondMenu.GameState();
            if (GameState) {
                state = STATE.Game;
            }
        }



            addMouseMotionListener(new MouseMotionListener() {

                @Override
                public void mouseDragged(MouseEvent e) {

                }

                @Override
                public void mouseMoved(MouseEvent e) {


                        x = e.getX();
                        y = e.getY();

                    if(state == STATE.Game) {
                        game.getRocket().setX(e.getX());
                        game.getRocket().setY(e.getY());
                    }


                }
            });



            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if(state == STATE.FIrstMenu){
                        FirstMenu.buttonClicked(x, y, jTextField0,jTextField1);

                        System.out.println( FirstMenu.counterOfPlayer);
                        /*switch (FirstMenu.CounterOfPlayer) {
                            case 1:
                            FirstMenu.buttonClicked(x, y, jTextField0,jTextField1);
                            System.out.println("1");
                            break;
                            case 2:
                                FirstMenu.buttonClicked(x, y, jTextField0,jTextField2);
                                System.out.println("2");
                                break;
                            case 3:
                                FirstMenu.buttonClicked(x, y, jTextField0,jTextField3);
                                break;
                        }*/
                    }

                    if(state == STATE.SEcondMenu) {
                        SecondMenu.buttonClicked(x,y);
                    }
                    if(state==STATE.Game & statePauseMenu==true){
                        PauseMenu.ButtonClicked(x,y);
                    }

                    if(state == STATE.Game & statePauseMenu==false){
                        if (ShootCounter%2==0) {
                            game.shelik();
                            NumberOfShoot--;

                            TimeOfShoot = System.currentTimeMillis();
                            ShootCounter++;
                        }
                        if((System.currentTimeMillis() - TimeOfShoot) >= 200 & WaitForShoot){
                            ShootCounter++;
                        }
                        if(ShootCounter == 20){
                            WaitForShoot = false;
                            WaitForShootTime = System.currentTimeMillis();

                        }
                        if((System.currentTimeMillis() - WaitForShootTime) >= 4000 & ShootCounter >= 19.5 ){
                            WaitForShoot = true;
                            ShootCounter=0;
                        }
                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if(SwingUtilities.isRightMouseButton(e)){
                        BombState = true;
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (state == STATE.Game) {
                        statePauseMenu = true;
                        Logger.getLogger().LocationOfRocket(Rocket.LastXRocket,Rocket.LastYRocket);
                        Logger.getLogger().TypeOfSpacecraft(SecondMenu_Setting.LastTypeOfSpacecraft);
                        Logger.getLogger().ColorOfShoot(SecondMenu_Setting.LastColorOfShoot);
                        Logger.getLogger().TypeOfShoot(SecondMenu_Setting.LastTypeOfShoot);
                        Logger.getLogger().NameOfPlayer(FirstMenu.NameOfPlayer);
                    }

                }

            });

    }

    public void moveGame() {
        game.move();
    }

    public boolean state(){
        if(state == STATE.Game) {
            return true;
        }
        else{
            return false;
        }
    }

    public static  int  ShootCounter(){

        return ShootCounter;
    }
    public  static int NumberOfShoot(){
        return NumberOfShoot;
    }

        public JTextField Jtextfieldinit(JTextField jTextField,int x,int y,int width,int height ,MainPanel Mainpanel){
            jTextField.setLocation(x,y);
            jTextField.setSize(width,height);
            //jTextField.setText("pleas input your name :)");
            setVisible(true);
            setLayout(null);
            Mainpanel.add(jTextField);
            return jTextField;

        }

}
