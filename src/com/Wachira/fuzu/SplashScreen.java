//Michael Wahome Wachira ICS 135360 28/03/2021
package com.Wachira.fuzu;

import javax.swing.*;

public class SplashScreen extends JFrame{
    private JPanel pnlSplash;
    private JLabel lblSplash1;
    private JLabel lblcopyright;
    private JLabel lblSplash3;
    private JLabel lblSplash2;

    public SplashScreen() {
        super("Loading Screen");
        this.setContentPane(pnlSplash);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setVisible(true);
        this.pack();

        try
        {
            Thread.sleep(2500);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        dispose();
        Index frame = new Index();
    }

    public static void main(String[] args) {
        SplashScreen frame = new SplashScreen();
    }
}
