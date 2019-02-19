package kasperek.gui;

import kasperek.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 0.2 02/18/2019
 * @see GamePane
 * @see GameLogic
 * @since 0.1
 */

public class GameWindow {
    private JFrame frame;

    /**
     * Default window constructor. It is responsibility for setter all needed parameters for window.
     */

    public GameWindow() {
        frame = new JFrame("Game of Life");
        GamePane pane = new GamePane();
        GameLogic game = new GameLogic(10);

        pane.repaint();
        pane.setGameLogic(game);

        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.DARK_GRAY);
        frame.setContentPane(pane);

        windowCenter();
        frame.setVisible(true);
    }

    /**
     * The method is responsibility for the auto centering the window.
     */

    private void windowCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}