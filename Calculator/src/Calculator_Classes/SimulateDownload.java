package Calculator_Classes;

import javax.swing.*;

public class SimulateDownload extends JFrame {

    public SimulateDownload() {
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