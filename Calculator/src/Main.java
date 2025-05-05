import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Settings {
    public static final String[] UNITS = {"MB", "GB"};
    public static final String[] SPEED_UNITS = {"Mbps", "Kbps"};

    public static final int[] GUI_Sizes = {
        15, // Horizontal padding
        10, // Vertical padding
        25, // Field height
        400 // Minimum width
    };
    public static final int Border_Size = 10; // Border size
}

public class Main extends JFrame {
    private JTextField sizeField;
    private JComboBox<String> unitBox;
    private JTextField speedField;
    private JLabel resultLabel;
    private JComboBox<String> speedUnitBox;

    private GridBagConstraints crt_conts(int x, int y, int width, int height, int fill, Insets insets) { // rewrote gridconstraint cuz typing always makes me pmo..
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        c.fill = fill;
        c.insets = insets;
        return c;
    }

    public Main() {
        setTitle("Bandwidth Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(Settings.Border_Size, Settings.Border_Size, Settings.Border_Size, Settings.Border_Size));

        Insets defaultInsets = new Insets(Settings.GUI_Sizes[1], Settings.GUI_Sizes[0], Settings.GUI_Sizes[1], Settings.GUI_Sizes[0]);

        // Title label
        JLabel titleLabel = new JLabel("Bandwidth Calculator", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, 
            crt_conts(0, 0, 4, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        // File size input
        mainPanel.add(new JLabel("File Size:"), 
            crt_conts(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        sizeField = new JTextField();
        sizeField.setPreferredSize(new Dimension(150, Settings.GUI_Sizes[2]));
        mainPanel.add(sizeField, 
            crt_conts(1, 1, 2, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        unitBox = new JComboBox<>(Settings.UNITS);
        unitBox.setPreferredSize(new Dimension(80, Settings.GUI_Sizes[2]));
        mainPanel.add(unitBox, 
            crt_conts(3, 1, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        // Speed input
        mainPanel.add(new JLabel("Download Speed:"), 
            crt_conts(0, 2, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        speedField = new JTextField();
        speedField.setPreferredSize(new Dimension(150, Settings.GUI_Sizes[2]));
        mainPanel.add(speedField, 
            crt_conts(1, 2, 2, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        speedUnitBox = new JComboBox<>(Settings.SPEED_UNITS);
        speedUnitBox.setPreferredSize(new Dimension(80, Settings.GUI_Sizes[2]));
        mainPanel.add(speedUnitBox, 
            crt_conts(3, 2, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        // Calculate button
        JButton calc = new JButton("Calculate Download Time");
        calc.setPreferredSize(new Dimension(200, Settings.GUI_Sizes[2] + 5));
        calc.setBackground(new Color(70, 130, 180));
        calc.setForeground(Color.white);
        calc.setFocusPainted(false);
        mainPanel.add(calc, 
            crt_conts(0, 3, 4, 1, GridBagConstraints.CENTER, defaultInsets));

        // Result panel
        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        resultPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        resultLabel = new JLabel("Time will be displayed here");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultPanel.add(resultLabel);
        mainPanel.add(resultPanel, 
            crt_conts(0, 4, 4, 1, GridBagConstraints.HORIZONTAL, defaultInsets));

        // Action listener for the calculate button
        calc.addActionListener(e -> {
            try {
                double size = Double.parseDouble(sizeField.getText());
                String unit = (String) unitBox.getSelectedItem();
                double speed = Double.parseDouble(speedField.getText());
                String speedUnit = (String) speedUnitBox.getSelectedItem();

                if (size <= 0 || speed <= 0) throw new NumberFormatException();

                // Convert to common units
                double mb = unit.equals("GB") ? size * 1024 : size;
                double mbps = speedUnit.equals("Kbps") ? speed / 1000 : speed;

                double secs = (mb * 8) / mbps; // Simple calculation

                int h = (int) (secs / 3600);
                int m = (int) ((secs % 3600) / 60);
                int s = (int) (secs % 60);

                resultLabel.setText(String.format("Estimated Time: %02dh %02dm %02ds", h, m, s));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Main.this,
                    "Please enter valid positive numbers",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Set the main panel as content pane
        setContentPane(mainPanel);

        pack();
        setMinimumSize(new Dimension(Settings.GUI_Sizes[3], getHeight()));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}