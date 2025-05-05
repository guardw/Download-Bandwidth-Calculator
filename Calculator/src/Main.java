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

    public Main() {
        setTitle("Bandwidth Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Main panel with proper layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(Settings.Border_Size,Settings.Border_Size,Settings.Border_Size,Settings.Border_Size));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(Settings.GUI_Sizes[1], Settings.GUI_Sizes[0], 
                             Settings.GUI_Sizes[1], Settings.GUI_Sizes[0]);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("Bandwidth Calculator", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 4;
        mainPanel.add(titleLabel, c);

        // File size input
        c.gridx = 0; c.gridy = 1; c.gridwidth = 1;
        mainPanel.add(new JLabel("File Size:"), c);

        c.gridx = 1; c.gridwidth = 2;
        sizeField = new JTextField();
        sizeField.setPreferredSize(new Dimension(150, Settings.GUI_Sizes[2]));
        mainPanel.add(sizeField, c);

        c.gridx = 3; c.gridwidth = 1;
        unitBox = new JComboBox<>(Settings.UNITS);
        unitBox.setPreferredSize(new Dimension(80, Settings.GUI_Sizes[2]));
        mainPanel.add(unitBox, c);

        // Speed input
        c.gridx = 0; c.gridy = 2;
        mainPanel.add(new JLabel("Download Speed:"), c);

        c.gridx = 1; c.gridwidth = 2;
        speedField = new JTextField();
        speedField.setPreferredSize(new Dimension(150, Settings.GUI_Sizes[2]));
        mainPanel.add(speedField, c);

        c.gridx = 3; c.gridwidth = 1;
        speedUnitBox = new JComboBox<>(Settings.SPEED_UNITS);
        speedUnitBox.setPreferredSize(new Dimension(80, Settings.GUI_Sizes[2]));
        mainPanel.add(speedUnitBox, c);

        // Calculate button
        c.gridx = 0; c.gridy = 3; c.gridwidth = 4;
        c.fill = GridBagConstraints.CENTER;
        JButton calc = new JButton("Calculate Download Time");
        calc.setPreferredSize(new Dimension(200, Settings.GUI_Sizes[2] + 5));
        calc.setBackground(new Color(70, 130, 180));
        calc.setForeground(Color.WHITE);
        calc.setFocusPainted(false);
        mainPanel.add(calc, c);

        // Result panel
        c.gridx = 0; c.gridy = 4; c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        resultPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        resultLabel = new JLabel("Time will be displayed here");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultPanel.add(resultLabel);
        mainPanel.add(resultPanel, c);

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