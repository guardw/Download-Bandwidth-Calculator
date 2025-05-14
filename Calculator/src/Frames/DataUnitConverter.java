package Frames;

import Calculator_Classes.*;
import Utils.Effects;
import Utils.GUI_Utils;
import java.awt.*;
import javax.swing.*;

class Settings {
    public static final int HORIZONTAL_PADDING = 10;
    public static final int VERTICAL_PADDING = 10;

    public static final int MINIMUM_WIDTH = 100;
    public static final int MINIMUM_HEIGHT = 100;

    public static final int BORDER_SIZE = 10;
    public static final int GEN_BUTTON_WIDTH = 100;
    public static final int FIELD_HEIGHT = 40;
}


public class DataUnitConverter extends JFrame {
    private JTextField inputField;
    private JComboBox<String> comboFrom, comboTo;
    private JLabel resultLabel;

    public boolean Opened = false;

    public DataUnitConverter() {
        setTitle("Data Unit Converter");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/img/icon.png")));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setMinimumSize(new Dimension(Settings.MINIMUM_WIDTH, Settings.MINIMUM_HEIGHT));
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        Insets fieldInsets = new Insets(Settings.VERTICAL_PADDING, Settings.HORIZONTAL_PADDING, Settings.VERTICAL_PADDING, Settings.HORIZONTAL_PADDING);
        Insets buttonInsets = new Insets(20, Settings.HORIZONTAL_PADDING, Settings.VERTICAL_PADDING, Settings.HORIZONTAL_PADDING);

        JPanel mainPanel = GUI_Utils.createPanel(new GridBagLayout(), GUI_Utils.BACKGROUND);

        JLabel inputLabel = GUI_Utils.createLabel("Enter value:", GUI_Utils.INTER_BOLD.deriveFont(15f), GUI_Utils.TEXT_PRIMARY);
        mainPanel.add(inputLabel, 
            GUI_Utils.create_grid_constraint(0, 0, 1, 1, GridBagConstraints.HORIZONTAL, fieldInsets, 0, 0));

        inputField = GUI_Utils.createTextField(150, Settings.FIELD_HEIGHT, GUI_Utils.INTER_REGULAR);
        mainPanel.add(inputField, 
            GUI_Utils.create_grid_constraint(1, 0, 2, 1, GridBagConstraints.HORIZONTAL, fieldInsets, 1, 0));

        JLabel fromLabel = GUI_Utils.createLabel("From:", GUI_Utils.INTER_BOLD.deriveFont(15f), GUI_Utils.TEXT_PRIMARY);
        mainPanel.add(fromLabel, 
            GUI_Utils.create_grid_constraint(0, 1, 1, 1, GridBagConstraints.HORIZONTAL, fieldInsets, 0, 0));

        comboFrom = GUI_Utils.createComboBox(BitsClass.UNITS, GUI_Utils.INTER_REGULAR);
        mainPanel.add(comboFrom, GUI_Utils.create_grid_constraint(1, 1, 2, 1, GridBagConstraints.HORIZONTAL, fieldInsets, 1, 0));

        JLabel toLabel = GUI_Utils.createLabel("To:", GUI_Utils.INTER_BOLD.deriveFont(15f), GUI_Utils.TEXT_PRIMARY);
        mainPanel.add(toLabel, 
            GUI_Utils.create_grid_constraint(0, 2, 1, 1, GridBagConstraints.HORIZONTAL, fieldInsets, 0, 0));

        comboTo = GUI_Utils.createComboBox(BitsClass.UNITS, GUI_Utils.INTER_REGULAR);
        mainPanel.add(comboTo, 
            GUI_Utils.create_grid_constraint(1, 2, 2, 1, GridBagConstraints.HORIZONTAL, fieldInsets, 1, 0));

        JButton convertButton = GUI_Utils.styledButton("Convert");
        convertButton.setFont(GUI_Utils.INTER_BOLD.deriveFont(15f));
        convertButton.setPreferredSize(new Dimension(Settings.GEN_BUTTON_WIDTH, Settings.FIELD_HEIGHT));
        mainPanel.add(convertButton, 
            GUI_Utils.create_grid_constraint(1, 3, 1, 1, GridBagConstraints.CENTER, buttonInsets, 0, 0));

        JButton clearButton = GUI_Utils.styledSecondaryButton("Clear");
        clearButton.setFont(GUI_Utils.INTER_BOLD.deriveFont(15f));
        clearButton.setPreferredSize(new Dimension(Settings.GEN_BUTTON_WIDTH, Settings.FIELD_HEIGHT));
        mainPanel.add(clearButton, 
            GUI_Utils.create_grid_constraint(2, 3, 1, 1, GridBagConstraints.CENTER, buttonInsets, 0, 0));

        resultLabel = GUI_Utils.createLabel("Result: ", GUI_Utils.INTER_BOLD.deriveFont(18f), GUI_Utils.TEXT_PRIMARY);
        mainPanel.add(resultLabel, 
            GUI_Utils.create_grid_constraint(0, 4, 3, 1, GridBagConstraints.HORIZONTAL, fieldInsets, 0, 0));

        convertButton.addActionListener(e -> performConversion());
        clearButton.addActionListener(e -> clearFields());

        setContentPane(mainPanel);
        pack();
    }

    private void performConversion() {
        try {
            String inputText = inputField.getText().trim();
            
            if (inputText.isEmpty()) {
                throw new NumberFormatException("Please enter a value to convert.");
            }

            double inputValue = Double.parseDouble(inputText);
            if (inputValue <= 0) {
                throw new NumberFormatException("Value must be greater than 0.");
            }

            if (comboFrom.getSelectedItem() == null) {
                throw new IllegalArgumentException("Please select a valid 'From' unit.");
            }

            if (comboTo.getSelectedItem() == null) {
                throw new IllegalArgumentException("Please select a valid 'To' unit.");
            }

            String from = (String) comboFrom.getSelectedItem();
            String to = (String) comboTo.getSelectedItem();

            double result = BitsClass.convert(inputValue, from, to);

            Effects.labelRandomizeEffect(resultLabel, String.format("Result: %.6g %s", result, to));

        } catch (Exception ex) {
           JOptionPane.showMessageDialog(this, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        inputField.setText("");
        resultLabel.setText("Result: ");
    }

}