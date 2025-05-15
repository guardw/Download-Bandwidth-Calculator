package Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.io.IOException;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

// Whole purpose of this class is to make making swing objects more compact to create and reduce text floods sa classes that uses it.

public class GUI_Utils {
    public static final Color BACKGROUND       = new Color(255, 255, 255);

    public static final Color BUTTON_PRIMARY   = new Color(229, 56, 59);
    public static final Color BUTTON_SECONDARY = new Color(255, 166, 167);
    public static final Color BUTTON_ACTIVE    = new Color(255, 166, 167);

    public static final Color TEXT_PRIMARY     = new Color(11, 9, 10);
    public static final Color TEXT_SECONDARY   = new Color(22, 26, 29);

    public static final Color PANEL_BACKGROUND = new Color(245, 243, 244);

    public static final Color BORDER           = new Color(211, 211, 211);
    public static final Color BORDER_COLOR     = new Color(177, 167, 166);

    public static Font INTER_LIGHT;
    public static Font INTER_BOLD;
    public static Font INTER_REGULAR;

    static {
        try {
            INTER_LIGHT   = Font.createFont(Font.TRUETYPE_FONT, GUI_Utils.class.getResourceAsStream("/assets/fonts/inter_light.ttf")).deriveFont(18f);
            INTER_REGULAR = Font.createFont(Font.TRUETYPE_FONT, GUI_Utils.class.getResourceAsStream("/assets/fonts/inter_regular.ttf")).deriveFont(18f);
            INTER_BOLD    = Font.createFont(Font.TRUETYPE_FONT, GUI_Utils.class.getResourceAsStream("/assets/fonts/inter_bold.ttf")).deriveFont(18f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(INTER_LIGHT);
            ge.registerFont(INTER_REGULAR);
            ge.registerFont(INTER_BOLD);

        } catch (IOException | FontFormatException e) {
            System.err.println("failed to load inter fonts.. switched to default :<");
        }
    }

    public static GridBagConstraints create_grid_constraint(int x, int y, int width, int height, int fill, Insets insets, double spacex, double spacey) {
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

    public static JTextField createTextField(int width, int height, Font font) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(width, height));
        textField.setFont(font);
        return textField;
    }

    public static <T> JComboBox<T> createComboBox(T[] items, Font font) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(font);
        return comboBox;
    }

    public static JPanel createPanel(LayoutManager layout, Color bgColor) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(bgColor);
        return panel;
    }
    
    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, BUTTON_PRIMARY.brighter(), BUTTON_PRIMARY.darker()));

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    public static JButton styledButton(String text) {
        JButton button = createButton(text);
        button.setBackground(BUTTON_PRIMARY);
       
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_PRIMARY.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_PRIMARY);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_ACTIVE);
            }
        });

        button.setUI(new RoundedButtonUI());
        return button;
    }

    public static JButton styledSecondaryButton(String text) {
        JButton button = createButton(text);
        button.setBackground(BUTTON_SECONDARY);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_SECONDARY.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_SECONDARY);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_SECONDARY.brighter());
            }
        });

        button.setUI(new RoundedButtonUI());
        return button;
    }

    public static void ErrorHasText(String x) throws NumberFormatException { // Method version for text boxes if it has non number (it was getting bloated)
        StringBuilder temp = new StringBuilder();
            for (char c : x.toCharArray()) {
                if (!Character.isDigit(c) && c != '.' && c != '-') {
                    temp.append(c);
                }
            }
        if (!temp.toString().isEmpty()) {
            throw new NumberFormatException("Input contains: " + temp.toString());
        }
    }
}

class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI { // This is the amazing hidden jujutsu technique where i make it look good
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        AbstractButton b = (AbstractButton) c;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(b.getBackground());
        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), 20, 20);

        super.paint(g, c);
    }
}