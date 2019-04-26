package game.engine;

import javax.sound.sampled.Clip;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

public class Sound implements Runnable  {
    private boolean running = false;
    private Thread thread;

    public Sound()
    {
        this.start();
    }

    public void start()
    {
        if(running)
            return;
        this.thread = new Thread(this);
        this.running = true;
        this.thread.start();
    }

    //
    private boolean playSong = false;
    private AudioInputStream inputStream;
    private String url ;//= "resources/shootSound.mp3";
    private Clip clip;


    @Override
    public void run()
    {
        while(running)
        {
            if(inputStream == null && playSong)
            {
                this.playSong = false;
                try
                {
                    this.inputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(url));
                    this.clip.open(inputStream);
                    this.clip.loop(10);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /*private Clip clip;
    public static void playSound() throws NullPointerException{
        File file = new File("resources/shootSound.mp3");
        URL url = null;
        try {
            if (file.canRead()) {
                url = file.toURI().toURL();
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        AudioClip clip = Applet.newAudioClip(url);
        clip.play();

        System.out.println("should've played by now");
    }*/
    public void playBackGround(String string) // call to play .wav file
    {
        if(this.clip != null)
        {
            this.clip.stop();
            this.clip.close();
        }
        try
        {
            this.clip = AudioSystem.getClip();
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
        url = string ;
        this.playSong = true;
        this.inputStream = null;
    }

    public void disposeSound()
    {
        if(this.clip != null)
        {
            this.clip.stop();
            this.clip.close();
        }
        this.clip = null;
        this.playSong = false;
        this.inputStream = null;
    }


}
