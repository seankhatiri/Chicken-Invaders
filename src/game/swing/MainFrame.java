package game.swing;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Chicken Invaders");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);//we can't resize it
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(null);
    }
}
