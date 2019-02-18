package kasperek.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 0.1 02/18/2019
 * @see GamePane
 * @since 0.1
 */

public class GameWindow {
    private JFrame frame;
    private GamePane pane;

    /**
     * Default constructor. It is responsibility for setter all needed parameters for window.
     */

    public GameWindow() {
        frame = new JFrame("Game of Life");
        pane = new GamePane();

        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.DARK_GRAY);
        frame.setContentPane(pane);
        frame.setVisible(true);
    }
}