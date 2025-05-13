package Calculator_Classes;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class AnimatedBarsPanel extends JPanel {
    private final int[] barHeights;
    private final int[] barMax;
    private final int[] barMin;
    private final boolean[] barUp;

    private final Random random = new Random();

    protected int rdMaxHt() {
        int value = random.nextInt(150 - 100) + 100; 
        return Math.max(50, value);
    }

    protected int rdMinHt() {
        int value = random.nextInt(99 - 0) + 0; 
        return Math.max(0, value);
    }

    public AnimatedBarsPanel(int barCount) {
        barHeights = new int[barCount];
        barMax     = new int[barCount];
        barMin     = new int[barCount];

        barUp      = new boolean[barCount];

        for (int i = 0; i < barCount; i++) {
            barMax[i] = this.rdMaxHt();
            barMin[i] = this.rdMinHt(); 

            barHeights[i] = random.nextInt(barMax[i] - 0) + 0;
            barUp[i] = random.nextBoolean();
        }

        Timer timer = new Timer(10, e -> {
            for (int i = 0; i < barHeights.length; i++) {
                
                if (barUp[i]) {
                    barHeights[i] += random.nextInt(3-1) + 1; 
                } else {barHeights[i] -= random.nextInt(3-1) + 1; }

                if (barHeights[i] >= barMax[i]) {
                    barMin[i] = this.rdMinHt(); 
                    barUp[i] = false;
                } else if(barHeights[i] <= barMin[i]) {

                    barHeights[i] -= 1; // para murag quad animation idk

                    barMax[i] = this.rdMaxHt(); 
                    barUp[i] = true;
                }

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
            int x = i * barWidth + 5; // the damn space pmos always keep above 2 if mag adjust pls
            int y = getHeight() - barHeights[i];
            int height = barHeights[i];
            g2d.setColor(ColorPalette.BUTTON_SECONDARY); 
            g2d.fillRoundRect(x, y, barWidth - 5, height,5,10); 
        }
    }
}