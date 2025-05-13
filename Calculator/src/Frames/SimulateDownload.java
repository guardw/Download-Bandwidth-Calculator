package Frames;

import javax.swing.*;
import java.awt.*;
import Calculator_Classes.ColorPalette;
//ysn's job
public class SimulateDownload extends JFrame {
    private JProgressBar progressBar;
    private Timer timer;
    private int secondsTotal;
    static boolean Active = false;
    
    public SimulateDownload(int totalSeconds) {
        if (Active == true) {return;}
        Active = true;

        this.secondsTotal = totalSeconds;
        setTitle("Simulation Window");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Progress bar setup
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.BOLD, 14));
        progressBar.setForeground(ColorPalette.BUTTON_SECONDARY); // Match theme

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(progressBar, BorderLayout.CENTER);
        
        add(panel);
        
        startSimulation();
    }

    private void startSimulation() {
        final long startTime = System.currentTimeMillis();

        timer = new Timer(50, e -> { // Updates every 50ms for smooth animation
            long elapsedTime = System.currentTimeMillis() - startTime;
            int progress = (int) ((elapsedTime * 100) / (secondsTotal * 1000L)));
            
            if (progress >= 100) {
                progressBar.setValue(100);
                progressBar.setString("Download Complete!");
                timer.stop();
                Active = false;
            }
            else {
                progressBar.setValue(progress);
                progressBar.setString(progress + "%");
            }
        });
        timer.start();

        @Override
        public void dispose() {
            super.dispose();
            if (timer != null) timer.stop();
           super.dispose();
        }

    }
}