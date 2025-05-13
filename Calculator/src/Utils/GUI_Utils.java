package Utils;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class GUI_Utils {
    public static final Color BACKGROUND = new Color(255, 255, 255);

    public static final Color BUTTON_PRIMARY = new Color(229, 56, 59);
    public static final Color BUTTON_SECONDARY = new Color(255, 166, 167);

    public static final Color TEXT_PRIMARY = new Color(11, 9, 10);
    public static final Color TEXT_SECONDARY = new Color(22, 26, 29);

    public static final Color PANEL_BACKGROUND = new Color(245, 243, 244);

    public static final Color BORDER = new Color(211, 211, 211);
    public static final Color BORDER_COLOR = new Color(177, 167, 166);

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

    static JButton createButton(String text) {
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
                button.setBackground(BUTTON_PRIMARY);
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
                button.setBackground(BUTTON_SECONDARY);
            }
        });

        button.setUI(new RoundedButtonUI());
        return button;
    }
}

class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
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