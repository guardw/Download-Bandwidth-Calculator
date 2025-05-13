package Frames;
import javax.swing.*;
import Calculator_Classes.ColorPalette;
import java.awt.*;

//ysn's job
public class SimulateDownload extends JFrame {
    private JProgressBar progressBar;
    private Timer timer;
    private final int secondsTotal;
    static boolean Active = false;
    
    public SimulateDownload(int totalSeconds) {
        this.secondsTotal = totalSeconds;
        if (Active) return;
        Active = true;
        setTitle("Download Simulation");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(ColorPalette.BACKGROUND);

        // Progress bar setup
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.BOLD, 14));
        progressBar.setForeground(ColorPalette.BUTTON_PRIMARY);
        progressBar.setBackground(ColorPalette.BUTTON_SECONDARY);
        progressBar.setBorder(BorderFactory.createLineBorder(ColorPalette.BUTTON_PRIMARY));

        mainPanel.add(progressBar, BorderLayout.CENTER);

        setContentPane(mainPanel);
        startSimulation();
    }

    private void startSimulation() {
        final long startTime = System.currentTimeMillis();

        timer = new Timer(50, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = ((double) elapsed / (secondsTotal * 1000.0)) * 100.0; // Simulate a 10-second download
            
            if (progress >= 100) {
                progressBar.setValue(100);
                progressBar.setString("Download Complete");
                timer.stop();
                Active = false;
            } else {
                progressBar.setValue((int) progress);
                progressBar.setString(String.format("%.02f%%", progress));
            }
        });
        timer.start();

    }

    @Override
    public void dispose() {
        Active = false;
        if (timer != null) timer.stop();
        super.dispose();
    }
}