package Frames;

import Calculator_Classes.BandwidthCalculator;
import Calculator_Classes.BitsClass;
import Calculator_Classes.ColorPalette;
import Calculator_Classes.Effects;
import custom_errors.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

class Settings {
    public static final String[] UNITS = {"KB", "MB", "GB", "TB"};
    public static final String[] SPEED_UNITS = {"Kbp/s", "Mbp/s", "Gbp/s", "Tbp/s"};

    public static final int[] GUI_Sizes = {
        9,  // Horizontal padding
        10, // Vertical padding
        25, // Field height
        300, // Minimum width
        400  // Minimum height
    };
    public static final int Border_Size = 10; // Border size
}

public class TimeCalc extends JFrame {
    private JTextField sizeField;
    private JComboBox<String> unitBox;
    private JTextField speedField;
    private JLabel resultLabel;
    private JComboBox<String> speedUnitBox;
    private String current_label_Text;
    
    public boolean Opened = false;

    BandwidthCalculator SpeedCalc = new BandwidthCalculator();

    private GridBagConstraints crt_conts(int x, int y, int width, int height, int fill, Insets insets, double spacex, double spacey) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        c.weightx = spacex;
        c.weighty = spacey;
        c.fill = fill;
        c.insets = insets;
        return c;
    }

    public TimeCalc() {
        setTitle("Download/Upload Time Calculator");

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/icon.png")));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(Settings.Border_Size, Settings.Border_Size, Settings.Border_Size, Settings.Border_Size));
        mainPanel.setBackground(ColorPalette.BACKGROUND);

        Insets defaultInsets = new Insets(Settings.GUI_Sizes[1], Settings.GUI_Sizes[0], Settings.GUI_Sizes[1], Settings.GUI_Sizes[0]);

        // title ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        JPanel titlePanel = new JPanel();
        int BorderThickness = 2;

        TitledBorder titleBord = BorderFactory.createTitledBorder(
            BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(BorderThickness, BorderThickness, BorderThickness, BorderThickness, ColorPalette.BORDER),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
            )
        );
        titleBord.setTitleColor(ColorPalette.TEXT_SECONDARY);

        titlePanel.setBorder(titleBord);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Download/Upload Time Calculator");
        titleLabel.setFont(ColorPalette.INTER_BOLD.deriveFont(18f));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel,
            crt_conts(0, -1, 4, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 1, 1));

        // file size ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        JLabel file_input = new JLabel("File Size:");
        file_input.setFont(ColorPalette.INTER_REGULAR.deriveFont(15f));
        mainPanel.add(file_input,
            crt_conts(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        sizeField = new JTextField();
        sizeField.setPreferredSize(new Dimension(150, Settings.GUI_Sizes[2]));
        mainPanel.add(sizeField,
            crt_conts(1, 1, 2, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        unitBox = new JComboBox<>(Settings.UNITS);
        unitBox.setPreferredSize(new Dimension(80, Settings.GUI_Sizes[2]));
        mainPanel.add(unitBox,
            crt_conts(3, 1, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        // speed input ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        JLabel speed_input = new JLabel("Bandwidth");
        speed_input.setFont(ColorPalette.INTER_REGULAR.deriveFont(15f));

        mainPanel.add(speed_input,
            crt_conts(0, 2, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        speedField = new JTextField();
        speedField.setPreferredSize(new Dimension(150, Settings.GUI_Sizes[2]));
        mainPanel.add(speedField,
            crt_conts(1, 2, 2, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        speedUnitBox = new JComboBox<>(Settings.SPEED_UNITS);
        speedUnitBox.setPreferredSize(new Dimension(80, Settings.GUI_Sizes[2]));
        mainPanel.add(speedUnitBox,
            crt_conts(3, 2, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        // calculate button -------------------------------------------------------------------------------------------------------------------------------------------------------------------
        JButton calc = ColorPalette.styledButton("Calculate Download Time");
        calc.setFont(ColorPalette.INTER_REGULAR.deriveFont(13f));
        calc.setPreferredSize(new Dimension(200, Settings.GUI_Sizes[2] + 5));
        calc.setBackground(ColorPalette.BUTTON_PRIMARY);
        calc.setForeground(ColorPalette.TEXT_SECONDARY);
        calc.setFocusPainted(true);
        mainPanel.add(calc,
            crt_conts(0, 3, 4, 1, GridBagConstraints.CENTER, defaultInsets, 0, 0));

        // result panel ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        resultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        resultLabel = new JLabel("Time will be displayed here");
        resultLabel.setFont(ColorPalette.INTER_BOLD.deriveFont(13f));
        resultPanel.add(resultLabel);
        mainPanel.add(resultPanel,
            crt_conts(0, 4, 4, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        calc.addActionListener(e -> {
            try {
                double size = Double.parseDouble(sizeField.getText());
                String unit = (String) unitBox.getSelectedItem();
                double speed = Double.parseDouble(speedField.getText());
                String speedUnit = (String) speedUnitBox.getSelectedItem();

                if (size <= 0 || speed <= 0) throw new NumberFormatException("Please enter valid positive numbers.");

                double mb = BitsClass.convertMB(size, unit);
                double mbps = BitsClass.convertMBPS(speed, speedUnit);

                System.out.println(size + " " + mb + " " + mbps);

                double secs = SpeedCalc.calculate(mb, mbps);

                int h = (int) (secs / 3600);
                int m = (int) ((secs % 3600) / 60);
                int s = (int) (secs % 60);

                current_label_Text = String.format("Estimated Time: %02dh %02dm %02ds", h, m, s);

                if (!resultLabel.getText().equals(current_label_Text)) {
                    Effects.labelRandomizeEffect(resultLabel, current_label_Text);
                }

                JButton simulate = ColorPalette.styledSecondaryButton("Simulate Download");
                simulate.setFont(ColorPalette.INTER_LIGHT.deriveFont(13f));
                simulate.setPreferredSize(new Dimension(200, Settings.GUI_Sizes[2]));
                simulate.setBackground(ColorPalette.BUTTON_SECONDARY);
                simulate.setForeground(ColorPalette.TEXT_SECONDARY);
                simulate.setFocusPainted(true);
                mainPanel.add(simulate,
                    crt_conts(0, 5, 5, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

                simulate.addActionListener(ey -> {
                    SimulateDownload window = new SimulateDownload();
                });

            } catch (NumberFormatException | InvalidUnitType ex) {
                JOptionPane.showMessageDialog(TimeCalc.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setContentPane(mainPanel);

        pack();
        setMinimumSize(new Dimension(Settings.GUI_Sizes[3], Settings.GUI_Sizes[4]));
    }
}
