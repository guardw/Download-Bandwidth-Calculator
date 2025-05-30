package Utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

// quirky effects for user interface :V
public class Effects {

    private static final String[] rands_unix = {"|", "■", "■"}; // train like anim.... goated.
    private static final int max_count = rands_unix.length - 1;

    public static void labelRandomizeEffect(JLabel label, String finalText) {

        if (label.getText().equals(finalText)) {return;}

        StringBuilder train = new StringBuilder();
        StringBuilder builtText = new StringBuilder();

        int[] c_count = {0};

        Timer timer = new Timer(2, e -> {
            if (builtText.length() < finalText.length()) {
                if (c_count[0] < max_count) {
                    train.append(rands_unix[c_count[0]]);
                    c_count[0]++;
                }

                builtText.append(finalText.charAt(builtText.length()));

                if ((builtText.length() + train.length()) > finalText.length()) {
                    train.deleteCharAt(train.length() - 1);
                }

                label.setText("" + builtText + "" + train);
            } else {
                label.setText(finalText);
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    public static class AnimatedBarsPanel extends JPanel { // Bars 🔥
        private final int[] barHeights;
        private final int[] barMax;
        private final int[] barMin;
        private final boolean[] barUp;

        final int actual_max_height = 200;

        private final Random random = new Random();

        protected int rdMaxHt() {
            int value = random.nextInt(actual_max_height - actual_max_height/2) + 100;
            return Math.max(50, value);
        }

        protected int rdMinHt() {
            int value = random.nextInt((actual_max_height/2) - 0) + 0;
            return Math.max(0, value);
        }

        public AnimatedBarsPanel(int barCount) {
            barHeights = new int[barCount];
            barMax = new int[barCount];
            barMin = new int[barCount];
            barUp = new boolean[barCount];

            for (int i = 0; i < barCount; i++) {
                barMax[i] = this.rdMaxHt();
                barMin[i] = this.rdMinHt();

                barHeights[i] = random.nextInt(barMax[i] - 0) + 0;
                barUp[i] = random.nextBoolean();
            }

            Timer timer = new Timer(10, e -> {
                for (int i = 0; i < barHeights.length; i++) {

                    if (barUp[i]) {
                        barHeights[i] += random.nextInt(4 - 2) + 1;
                    } else {
                        barHeights[i] -= random.nextInt(4 - 2) + 1;
                    }

                    if (barHeights[i] >= barMax[i]) {
                        barMin[i] = this.rdMinHt();
                        barUp[i] = false;
                    } else if (barHeights[i] <= barMin[i]) {

                        barHeights[i] -= 1;

                        barMax[i] = this.rdMaxHt();
                        barUp[i] = true;
                    }

                }
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) { // render/animate the bars
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int barWidth = getWidth() / barHeights.length; 
            int spacing = 5; 
            int adjustedBarWidth = barWidth - spacing; 

            for (int i = 0; i < barHeights.length; i++) {
                int x = i * barWidth + spacing / 2;  // para ma balancy ang matrix brother
                int y = getHeight() - barHeights[i]; 
                int height = barHeights[i]; 

                g2d.setColor(GUI_Utils.BUTTON_SECONDARY);
                g2d.fillRoundRect(x, y-1, adjustedBarWidth, height, 4, 10); 
            }
        }
    }
}