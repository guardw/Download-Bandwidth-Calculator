package Frames;

import Calculator_Classes.BandwidthCalculator;
import Calculator_Classes.BitsClass;
import Utils.Effects;
import Utils.GUI_Utils;
import custom_errors.InvalidUnitType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Settings {
    public static final int HORIZONTAL_PADDING = 9;
    public static final int VERTICAL_PADDING = 10;

    public static final int MINIMUM_WIDTH = 300;
    public static final int MINIMUM_HEIGHT = 400;

    public static final int BORDER_SIZE = 10;
    public static final int GEN_BUTTON_WIDTH = 400;
    public static final int FIELD_HEIGHT = 30;
}

public class TimeCalc extends JFrame {
    private JTextField sizeField;
    private JComboBox<String> unitBox;
    private JTextField speedField;
    private JLabel resultLabel;
    private JComboBox<String> speedUnitBox;

    private String current_label_Text;
    private double current_secs;
    private JButton simulate;
    private SimulateDownload currentSimWindow;

    public boolean Opened = false;

    BandwidthCalculator SpeedCalc = new BandwidthCalculator();

    public TimeCalc() {
        setTitle("Download/Upload Time Calculator");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/img/icon.png")));

        JPanel mainPanel = GUI_Utils.createPanel(new GridBagLayout(), GUI_Utils.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(Settings.BORDER_SIZE, Settings.BORDER_SIZE, Settings.BORDER_SIZE, Settings.BORDER_SIZE));

        Insets defaultInsets = new Insets(Settings.VERTICAL_PADDING, Settings.HORIZONTAL_PADDING, Settings.VERTICAL_PADDING, Settings.HORIZONTAL_PADDING);

        JPanel titlePanel = GUI_Utils.createPanel(new FlowLayout(FlowLayout.CENTER), null);
        JLabel titleLabel = GUI_Utils.createLabel("Download/Upload Time Calculator", GUI_Utils.INTER_BOLD.deriveFont(18f), GUI_Utils.TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, 
        GUI_Utils.create_grid_constraint(0, 0, 4, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 1, 1));
        
        // ------- File Size -------
        JLabel fileInputLabel = GUI_Utils.createLabel("File Size:", GUI_Utils.INTER_REGULAR.deriveFont(15f), GUI_Utils.TEXT_PRIMARY);
        mainPanel.add(fileInputLabel, 
            GUI_Utils.create_grid_constraint(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        sizeField = GUI_Utils.createTextField(150, Settings.FIELD_HEIGHT, GUI_Utils.INTER_REGULAR.deriveFont(13f));
        mainPanel.add(sizeField, 
            GUI_Utils.create_grid_constraint(1, 1, 2, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        unitBox = GUI_Utils.createComboBox(BitsClass.UNITS, GUI_Utils.INTER_REGULAR.deriveFont(15f));
        unitBox.setPreferredSize(new Dimension(80, Settings.FIELD_HEIGHT));
        mainPanel.add(unitBox, 
            GUI_Utils.create_grid_constraint(3, 1, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));


        // ------- Bandwidth -------
        JLabel speedInputLabel = GUI_Utils.createLabel("Bandwidth:", GUI_Utils.INTER_REGULAR.deriveFont(15f), GUI_Utils.TEXT_PRIMARY);
        mainPanel.add(speedInputLabel, 
            GUI_Utils.create_grid_constraint(0, 2, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        speedField = GUI_Utils.createTextField(150, Settings.FIELD_HEIGHT, GUI_Utils.INTER_REGULAR.deriveFont(13f));
        mainPanel.add(speedField, 
            GUI_Utils.create_grid_constraint(1, 2, 2, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        speedUnitBox = GUI_Utils.createComboBox(BitsClass.SPEED_UNITS, GUI_Utils.INTER_REGULAR.deriveFont(15f));
        speedUnitBox.setPreferredSize(new Dimension(80, Settings.FIELD_HEIGHT));
        mainPanel.add(speedUnitBox, 
            GUI_Utils.create_grid_constraint(3, 2, 1, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));
        
        // ------- Simulate -------
        simulate = GUI_Utils.styledSecondaryButton("Simulate Download");
        simulate.setFont(GUI_Utils.INTER_LIGHT.deriveFont(13f));
        simulate.setForeground(GUI_Utils.TEXT_SECONDARY);
        simulate.setPreferredSize(new Dimension(200, Settings.FIELD_HEIGHT));
        mainPanel.add(simulate, 
            GUI_Utils.create_grid_constraint(0, 5, 5, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        // Handling simulated window properties
        simulate.addActionListener(ey -> {
            if (SimulateDownload.Finished && SimulateDownload.Active) {currentSimWindow.dispose();}
            if (SimulateDownload.Active) {return;}

            try {
                if (resultLabel.getText().equals("Time will be displayed here")) {throw new ArithmeticException("Result time is empty!");}

                double totalSeconds = Double.parseDouble(simulate.getClientProperty("totalSeconds").toString());

                if (Math.round(totalSeconds) > 0) {
                    currentSimWindow = new SimulateDownload((int) Math.round(totalSeconds));
                    currentSimWindow.setVisible(true);

                    int mainX = getLocation().x;
                    int mainY = getLocation().y;
                    int mainHeight = getHeight();

                    currentSimWindow.setLocation(mainX, mainY + mainHeight);
                } else {
                    throw new ArithmeticException("Cannot simulate. Time must be greater than 0");
                }
            } catch (ArithmeticException ex) {
                JOptionPane.showMessageDialog(TimeCalc.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton calc = GUI_Utils.styledButton("Calculate Download Time");
        calc.setFont(GUI_Utils.INTER_REGULAR.deriveFont(13f));
        calc.setPreferredSize(new Dimension(200, Settings.FIELD_HEIGHT + 5));
        mainPanel.add(calc, GUI_Utils.create_grid_constraint(0, 3, 4, 1, GridBagConstraints.CENTER, defaultInsets, 0, 0));

        // where the magic for estimated time calc
        calc.addActionListener(e -> {
            try {
                double size;
                String unit;
                double speed;
                String speedUnit;

                GUI_Utils.ErrorHasText(sizeField.getText().trim());
                GUI_Utils.ErrorHasText(speedField.getText().trim());

                if (sizeField.getText().isEmpty()) {
                    throw new NumberFormatException("File size cannot be empty.");
                } else {
                    size = Double.parseDouble(sizeField.getText());
                    if (size <= 0) {
                        throw new NumberFormatException("File size must be greater than 0.");
                    }
                }

                if (unitBox.getSelectedItem() == null) {
                    throw new IllegalArgumentException("Please select a valid file size unit.");
                } else {
                    unit = (String) unitBox.getSelectedItem();
                }

                if (speedField.getText().isEmpty()) {
                    throw new NumberFormatException("Bandwidth cannot be empty.");
                } else {
                    speed = Double.parseDouble(speedField.getText());
                    if (speed <= 0) {
                        throw new NumberFormatException("Bandwidth must be greater than 0.");
                    }
                }

                if (speedUnitBox.getSelectedItem() == null) {
                    throw new IllegalArgumentException("Please select a valid bandwidth unit.");
                } else {
                    speedUnit = (String) speedUnitBox.getSelectedItem();
                }

                double mb = BitsClass.convertMB(size, unit);
                double mbps = BitsClass.convertMBPS(speed, speedUnit);

                current_secs = SpeedCalc.calculate(mb, mbps);

                int h = (int) (current_secs / 3600);
                int m = (int) ((current_secs % 3600) / 60);
                int s = (int) (current_secs % 60);

                current_label_Text = String.format("Estimated Time: %02dh %02dm %02ds", h, m, s);

                Effects.labelRandomizeEffect(resultLabel, current_label_Text);

                simulate.putClientProperty("totalSeconds", (double) Math.round(current_secs));
                simulate.setVisible(true);

            } catch (NumberFormatException | InvalidUnitType ex) {
                JOptionPane.showMessageDialog(TimeCalc.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel resultPanel = GUI_Utils.createPanel(new FlowLayout(FlowLayout.CENTER), null);
        resultLabel = GUI_Utils.createLabel("Time will be displayed here", GUI_Utils.INTER_BOLD.deriveFont(13f), GUI_Utils.TEXT_PRIMARY);
        resultPanel.add(resultLabel);
        mainPanel.add(resultPanel, GUI_Utils.create_grid_constraint(0, 4, 4, 1, GridBagConstraints.HORIZONTAL, defaultInsets, 0, 0));

        setContentPane(mainPanel);
        pack();
        setMinimumSize(new Dimension(Settings.MINIMUM_WIDTH, Settings.MINIMUM_HEIGHT));
        setResizable(false);
    }
}
