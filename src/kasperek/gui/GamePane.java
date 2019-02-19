package kasperek.gui;

import kasperek.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 0.2 02/18/2019
 * @see GameWindow
 * @see GameLogic
 * @since 0.1
 */

public class GamePane extends JPanel {
    private GameLogic game;

    private JButton nextStep;
    private JButton allSteps;

    private final int CELL_SIZE = 20;

    /**
     * Default pane constructor. It is responsibility for setter all needed parameters for window.
     */

    public GamePane() {
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        initializeNextStepButton();
        initializeAllStepButton();
    }

    private void initializeNextStepButton() {
        nextStep = new JButton("Next step");
        nextStep.setBounds(995, 10, 100, 40);

        this.add(nextStep);
        nextStep.setVisible(true);
    }

    private void initializeAllStepButton() {
        allSteps = new JButton("All steps");
        allSteps.setBounds(1095, 10, 100, 40);

        this.add(allSteps);
        allSteps.setVisible(true);
    }

    void setGameLogic(GameLogic game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = game.getBoard().length;
        int y = game.getBoard()[0].length;

        g.setColor(Color.DARK_GRAY);

        for (int i = 0; i <= x; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, y * CELL_SIZE);
        }

        g.fillRect(x * CELL_SIZE, 0, 5, y * CELL_SIZE);

        for (int i = 0; i <= y; i++) {
            g.drawLine(0, i * CELL_SIZE, x * CELL_SIZE, i * CELL_SIZE);
        }

        game.paintBoard(g);
    }
}