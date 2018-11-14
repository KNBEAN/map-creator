import gui.MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ResourceBundle;


public class Main {


    private static String appName = "Map creator";

    private static JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> runGUI());
    }

    private static void runGUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new MainWindow(appName);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeApp();
            }
        });

        frame.setBounds(300,100,1100,600);
        frame.setVisible(true);
    }

    static void closeApp(){
        int selection = JOptionPane.showConfirmDialog(
                null,
                "Exit "+appName+"?",
                "Confirm exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if(selection == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }


}
