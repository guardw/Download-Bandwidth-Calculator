package Frames;

import javax.swing.*;
//elie's job
public class DataUnitConverter extends JFrame {
    public boolean Opened = false; // Set to false initially

    public DataUnitConverter() {
        setTitle("Data Unit Converter");
        setSize(400, 300);

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/assets/img/teto.jpg"));
        JLabel imageLabel = new JLabel(imageIcon);

        add(imageLabel);
    }
}