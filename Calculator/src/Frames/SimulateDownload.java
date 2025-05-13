package Frames;

import javax.swing.*;
//ysn's job
public class SimulateDownload extends JFrame {

    static boolean Active = false;
    
    public SimulateDownload() {
        if (Active == true) {return;}
        Active = true;

        setTitle("Simulation Window");
        setSize(400, 300);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/assets/teto.jpg"));
        JLabel imageLabel = new JLabel(imageIcon);

        add(imageLabel);
        setVisible(true);
    }
}