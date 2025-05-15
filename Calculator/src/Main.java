import Frames.*;
import Utils.*;
import java.awt.*;
import javax.swing.*;

class Settings {
    // guys oa lagi ning vscode og variable naming :<

    public static final int HORIZONTAL_PADDING = 10;
    public static final int VERTICAL_PADDING = 10;

    public static final int MINIMUM_WIDTH = 400;
    public static final int MINIMUM_HEIGHT = 400;

    public static final int BORDER_SIZE = 10;
    public static final int GEN_BUTTON_WIDTH = 400;
    public static final int FIELD_HEIGHT = 60;
}

public class Main extends JFrame {

    public Main() {
        setTitle("Bandwidth Calculators");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/img/icon.png")));

        Effects.AnimatedBarsPanel animatedBars = new Effects.AnimatedBarsPanel(22);
        animatedBars.setOpaque(false);
        animatedBars.setBounds(0, 0, 800, 600);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.setBackground(GUI_Utils.BACKGROUND);
        layeredPane.setOpaque(true);
        layeredPane.add(animatedBars, Integer.valueOf(0));

        JPanel mainPanel = GUI_Utils.createPanel(new GridBagLayout(), GUI_Utils.BACKGROUND);
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(Settings.BORDER_SIZE, Settings.BORDER_SIZE, Settings.BORDER_SIZE, Settings.BORDER_SIZE));

        JPanel titlePanel = GUI_Utils.createPanel(new FlowLayout(FlowLayout.CENTER), null);
        titlePanel.setOpaque(false);

        JLabel titleLabel = GUI_Utils.createLabel(
            "Bandwidth Calculators",
            GUI_Utils.INTER_BOLD.deriveFont(32f),
            GUI_Utils.TEXT_PRIMARY
        );
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel,
            GUI_Utils.create_grid_constraint(0, 0, 4, 1, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 20, 0), 0, 0));

        Insets buttonInsets = new Insets(20, Settings.HORIZONTAL_PADDING, Settings.VERTICAL_PADDING, Settings.HORIZONTAL_PADDING);

        JButton timec = GUI_Utils.styledButton("Download/Upload Time Calculator");
        timec.setFont(GUI_Utils.INTER_BOLD.deriveFont(18f));
        timec.setPreferredSize(new Dimension(Settings.GEN_BUTTON_WIDTH, Settings.FIELD_HEIGHT));
        mainPanel.add(timec,
            GUI_Utils.create_grid_constraint(0, 2, 4, 1, GridBagConstraints.CENTER, buttonInsets, 0, 0));

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

        JButton unitconv = GUI_Utils.styledButton("Data Unit Converter");
        unitconv.setFont(GUI_Utils.INTER_BOLD.deriveFont(18f));
        unitconv.setPreferredSize(new Dimension(Settings.GEN_BUTTON_WIDTH, Settings.FIELD_HEIGHT));
        
        mainPanel.add(unitconv,
            GUI_Utils.create_grid_constraint(0, 3, 4, 1, GridBagConstraints.CENTER, buttonInsets, 0, 0));
        
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

        layeredPane.add(mainPanel, Integer.valueOf(1));

        setContentPane(layeredPane);

        pack();
        setMinimumSize(new Dimension(Settings.MINIMUM_WIDTH, Settings.MINIMUM_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());

    }
}