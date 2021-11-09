package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Main extends JFrame {
    private static int frameWidth = 400;
    private static int frameLength = 400;

    public static void main(String[] args) {
//        JFrame frame = new JFrame(); // create frame
//        frame.setSize(frameWidth, frameLength); // set frame width and length
//        frame.setTitle("Emma's Travel Guide to Airlines");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit on application by default
//        frame.setVisible(true); // make frame visible
//
//        frame.getContentPane().setBackground(new Color(75, 130, 150));

        JFrame frame = new JFrame("Emma's Travel Guide to Airlines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(75, 130, 150));

        JComponent newContentPane = new AirlineGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);



//            javax.swing.SwingUtilities.invokeLater(new Runnable() {
//                public void run() {
//                    createAndShowGUI();
//                }
//            });
//        }
//        AirlineGUI newFrame = new AirlineGUI(); // can change to just new AirlineFrame();
//        try {
//            new AirlineApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file is not found");
//        }
    }
}


