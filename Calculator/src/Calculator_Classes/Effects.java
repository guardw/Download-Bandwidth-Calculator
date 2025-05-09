package Calculator_Classes;

import javax.swing.*;

public class Effects {

    private static final String[] rands_unix = {"|","■","■"};
    //private static final String[] rands_unix = {"@", "#", "$", "%", "&", "*", "!"};
    private static final int max_count = rands_unix.length - 1;

    public static void labelRandomizeEffect(JLabel label, String finalText) {
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
                    train.deleteCharAt(train.length()-1);
                }

                label.setText(""+builtText+""+train);
            } else {
                label.setText(finalText);
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }
}