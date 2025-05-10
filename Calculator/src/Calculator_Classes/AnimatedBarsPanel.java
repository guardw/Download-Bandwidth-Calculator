package Calculator_Classes;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class AnimatedBarsPanel extends JPanel {
    private final int[] barHeights;
    private final Random random = new Random();

    public AnimatedBarsPanel(int barCount) {
        barHeights = new int[barCount];
        for (int i = 0; i < barCount; i++) {
            int maxHeight = random.nextInt(110 - 100 + 1) + 100; 
            barHeights[i] = random.nextInt(maxHeight - 50) + 50; 
        }

        Timer timer = new Timer(30, e -> {
            for (int i = 0; i < barHeights.length; i++) {
                barHeights[i] += random.nextInt(21) - 10; 
                barHeights[i] = Math.max(50, Math.min(150, barHeights[i])); //amp randomizer lowk aint working
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int barWidth = getWidth() / barHeights.length;
        for (int i = 0; i < barHeights.length; i++) {
            int x = i * barWidth + 5; // the damn space pmos always keep above +2 if mag adjust pls
            int y = getHeight() - barHeights[i];
            int height = barHeights[i];
            g2d.setColor(ColorPalette.BUTTON_SECONDARY); 
            g2d.fillRoundRect(x, y, barWidth - 5, height,5,10); 
        }
    }
}