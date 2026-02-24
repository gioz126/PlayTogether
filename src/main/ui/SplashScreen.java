package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

// Represents image startup for PlayTogether graphical user interface
public class SplashScreen extends JWindow {
    public static final String LOGO_STORE = "./data/PlayTogetherLogo.png";

    public SplashScreen() {
        ImageIcon icon = new ImageIcon(LOGO_STORE);

        JLabel imageLabel = new JLabel(icon);

        imageLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        this.getContentPane().add(imageLabel, BorderLayout.CENTER);

        // automatic size to fit screen
        this.pack();

        this.setLocationRelativeTo(null);
    }

    public void showSplash(int milliseconds) {
        this.setVisible(true);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            //
        }
        this.setVisible(false);
        this.dispose();
    }
}
