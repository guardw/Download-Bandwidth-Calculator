import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

interface Calculator {
    double calculate(double fileSizeMb, double speedMbps);
}

class BandwidthCalculator implements Calculator {
    public double calculate(double fileSizeMb, double speedMbps) {
        return (fileSizeMb * 8) / speedMbps;
    }
}

public class Main extends JFrame {
    private JTextField sizeField;
    private JComboBox<String> unitBox;
    private JTextField speedField;
    private JLabel resultLabel;
    private Calculator calculator = new BandwidthCalculator();

    public Main() {
        setTitle("Bandwidth Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; add(new JLabel("File size:"), c);
        c.gridx = 1; sizeField = new JTextField(); add(sizeField, c);
        c.gridx = 2; unitBox = new JComboBox<>(new String[]{"MB","GB"}); add(unitBox, c);

        c.gridx = 0; c.gridy = 1; add(new JLabel("Speed (Mbps):"), c);
        c.gridx = 1; c.gridwidth = 2; speedField = new JTextField(); add(speedField, c);
        c.gridwidth = 1;

        c.gridx = 0; c.gridy = 2; JButton calc = new JButton("Calculate"); add(calc, c);
        c.gridx = 1; c.gridwidth = 2; resultLabel = new JLabel("Time: "); add(resultLabel, c);

        calc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double size = Double.parseDouble(sizeField.getText());
                    String unit = (String)unitBox.getSelectedItem();
                    double speed = Double.parseDouble(speedField.getText());
                    if(size <= 0 || speed <= 0) throw new NumberFormatException();
                    double mb = unit.equals("GB") ? size * 1024 : size;
                    double secs = calculator.calculate(mb, speed);
                    int h = (int)(secs/3600);
                    int m = (int)((secs%3600)/60);
                    int s = (int)(secs%60);
                    resultLabel.setText(String.format("Time: %02dh %02dm %02ds", h, m, s));
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(Main.this, "Enter valid positive numbers");
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
