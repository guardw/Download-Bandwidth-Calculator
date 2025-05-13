import Calculator_Classes.*;
import Frames.*;
import java.awt.*;
import javax.swing.*;

class Settings {
   
    public static final int[] GUI_Sizes = {
        9, // Horizontal padding
        10, // Vertical padding

        50, // Field height
        400, // Minimum width
        400 // Minimum Height
    };
    public static final int Border_Size = 10; // Border size
}

public class Main extends JFrame {

    public Main() {
        setTitle("Bandwidth Calculators");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/icon.png")));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.setBackground(ColorPalette.BACKGROUND);
        layeredPane.setOpaque(true); 

        AnimatedBarsPanel animatedBars = new AnimatedBarsPanel(21);
        animatedBars.setOpaque(false);
        animatedBars.setBounds(0, 0, 800, 600);
        layeredPane.add(animatedBars, Integer.valueOf(0)); 

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(Settings.Border_Size, Settings.Border_Size, Settings.Border_Size, Settings.Border_Size));
        mainPanel.setBackground(ColorPalette.BACKGROUND);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false); // Keep transparent to show parent's background

        JLabel titleLabel = new JLabel("Bandwidth Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(ColorPalette.TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel,
            crt_conts(0, 0, 4, 1, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 20, 0), 0, 0));

        Insets buttonInsets = new Insets(20, Settings.GUI_Sizes[0], Settings.GUI_Sizes[1], Settings.GUI_Sizes[0]);

        JButton timec = ColorPalette.styledButton("Download/Upload Time Calculator");
        timec.setFont(new Font("Arial", Font.BOLD, 15));
        timec.setPreferredSize(new Dimension(300, Settings.GUI_Sizes[2]));
        timec.setBackground(ColorPalette.BUTTON_PRIMARY);
        timec.setForeground(ColorPalette.TEXT_SECONDARY);
        timec.setFocusPainted(false);

        mainPanel.add(timec,
            crt_conts(0, 2, 4, 1, GridBagConstraints.CENTER, buttonInsets, 0, 0));

        TimeCalc timeCalc_Window = new TimeCalc();
        timec.addActionListener(ey -> {
            if (timeCalc_Window.isVisible()) {
                timeCalc_Window.setVisible(false);
            } else if (timeCalc_Window.Opened) {
                timeCalc_Window.setVisible(true);
            } else {
                int mainX = getLocation().x;
                int mainY = getLocation().y;
                int mainWidth = getWidth();

                timeCalc_Window.setLocation(mainX + mainWidth, mainY);
                timeCalc_Window.setVisible(true);
                timeCalc_Window.Opened = true;
            }
        });

        JButton unitconv = ColorPalette.styledButton("Data Unit Converter");
        unitconv.setFont(new Font("Arial", Font.BOLD, 15));
        unitconv.setPreferredSize(new Dimension(300, Settings.GUI_Sizes[2]));
        unitconv.setBackground(ColorPalette.BUTTON_PRIMARY);
        unitconv.setForeground(ColorPalette.TEXT_SECONDARY);
        unitconv.setFocusPainted(false);

        DataUnitConverter unitConv_Window = new DataUnitConverter();
         unitconv.addActionListener(ey -> {
             if (unitConv_Window.isVisible()) {
                unitConv_Window.setVisible(false);
            } else if (unitConv_Window.Opened) {
                unitConv_Window.setVisible(true);
            } else {
                int mainX = getLocation().x;
                int mainY = getLocation().y;
                int unitConvWidth = unitConv_Window.getWidth(); 

                unitConv_Window.setLocation(mainX - unitConvWidth, mainY);

                unitConv_Window.setVisible(true);
                unitConv_Window.Opened = true;
            }
        });

        mainPanel.add(unitconv,
            crt_conts(0, 3, 4, 1, GridBagConstraints.CENTER, buttonInsets, 0, 0));

        layeredPane.add(mainPanel, Integer.valueOf(1));

        setContentPane(layeredPane);

        pack();
        setMinimumSize(new Dimension(Settings.GUI_Sizes[3], Settings.GUI_Sizes[4]));
        setLocationRelativeTo(null);
        setVisible(true);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}