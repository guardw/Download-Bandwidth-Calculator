package Frames;
import Utils.GUI_Utils;
import java.awt.*;
import javax.swing.*;

public class SimulateDownload extends JFrame {
    private JProgressBar progressBar;
    private JLabel timeRemainingLabel; 
    private Timer timer;
    private final int secondsTotal;
    public static boolean Active = false;
    public static boolean Finished = false;
   
    public SimulateDownload(int totalSeconds) {
        this.secondsTotal = totalSeconds;
        Active = true;
        Finished = false;

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/img/icon.png")));
        setTitle("Download Simulation");
        setSize(390, 120);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = GUI_Utils.createPanel(new BorderLayout(), GUI_Utils.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(GUI_Utils.INTER_BOLD.deriveFont(18f));
        progressBar.setForeground(GUI_Utils.BUTTON_PRIMARY);
        progressBar.setBackground(GUI_Utils.BUTTON_SECONDARY);
        progressBar.setBorder(BorderFactory.createLineBorder(GUI_Utils.BUTTON_PRIMARY));

        timeRemainingLabel = GUI_Utils.createLabel("Time Remaining: Calculating...", GUI_Utils.INTER_LIGHT.deriveFont(14f), GUI_Utils.TEXT_PRIMARY);
        timeRemainingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(progressBar, BorderLayout.CENTER);
        mainPanel.add(timeRemainingLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        startSimulation();
    }

    private void startSimulation() {
        final long startTime = System.currentTimeMillis();

        timer = new Timer(50, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            double progress = ((double) elapsed / (secondsTotal * 1000.0)) * 100.0;

            if (progress >= 100) {
                progressBar.setValue(100);
                progressBar.setString("Download Complete");
                timeRemainingLabel.setText("");
                timer.stop();
                Finished = true;
            } else {
                progressBar.setValue((int) progress);
                progressBar.setString(String.format("%.02f%%", progress));

                double remainingTime = secondsTotal - (elapsed / 1000.0);
                int h = (int) (remainingTime / 3600);
                int m = (int) ((remainingTime % 3600) / 60);
                int s = (int) (remainingTime % 60);

                StringBuilder timeText = new StringBuilder();
                if (h > 0) timeText.append(String.format("%02dh ", h));
                if (m > 0) timeText.append(String.format("%02dm ", m));
                if (s > 0 || timeText.length() == 0) timeText.append(String.format("%02ds", s));

                timeRemainingLabel.setText(timeText.toString().trim());
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