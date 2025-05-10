package Calculator_Classes;

import java.awt.*;

import javax.swing.JButton;

public class ColorPalette {
    // Light Theme (Default)
    public static final Color BACKGROUND = new Color(240, 240, 253);
    public static final Color BUTTON_PRIMARY = new Color(147, 26, 30);
    public static final Color BUTTON_SECONDARY = new Color(238, 135, 138);
    public static final Color TEXT_PRIMARY = new Color(4, 4, 17);
    public static final Color TEXT_SECONDARY = new Color(240, 240, 253);
    public static final Color PANEL_BACKGROUND = new Color(240, 240, 253);
    public static final Color BORDER = new Color(232, 168, 84);
    public static final Color BORDER_COLOR = new Color(0, 0, 255, 128);
    
    // Helper method for buttons
    public static JButton styledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true); // Force background rendering
        // Override default button state effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_PRIMARY.darker()); // slightly darker on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_PRIMARY);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_PRIMARY);
            }
        });
        return button;
    }
    public static JButton styledSecondaryButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(BUTTON_SECONDARY); // Use secondary color
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setOpaque(true); 
    
    // Lock color during interactions
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(BUTTON_SECONDARY.darker()); // hover effect
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(BUTTON_SECONDARY);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            button.setBackground(BUTTON_SECONDARY);
        }
    });
    
    return button;
}
}