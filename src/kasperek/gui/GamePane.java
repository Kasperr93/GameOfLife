package kasperek.gui;

import kasperek.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 0.4 02/28/2019
 * @see GameWindow
 * @see GameLogic
 * @since 0.1
 */

public class GamePane extends JPanel {
    private GameLogic game;

    private JButton nextStep;
    private JButton allSteps;

    /**
     * Default pane constructor. It is responsibility for setter all needed parameters for window.
     */

    public GamePane() {
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        initializeNextStepButton();
        initializeAllStepButton();
        initializeLegendLabel();
    }

    private void initializeNextStepButton() {
        nextStep = new JButton("Next step");
        nextStep.setBounds(980, 32, 100, 40);

        nextStep.addActionListener(e -> {
            game.singleStep();
            this.repaint();
        });

        this.add(nextStep);
        nextStep.setVisible(true);
    }

    private void initializeAllStepButton() {
        allSteps = new JButton("All steps");
        allSteps.setBounds(1080, 32, 100, 40);

        allSteps.addActionListener(e -> {
            game.allSteps(this);
            this.repaint();
            nextStep.setEnabled(false);
        });

        nextStep.setEnabled(true);

        this.add(allSteps);
        allSteps.setVisible(true);
    }

    private void initializeLegendLabel() {
        JLabel legendText = new JLabel("Legend:");
        JLabel aliveText = new JLabel("- Alive");
        JLabel notAliveText = new JLabel("- Not alive");

        legendText.setBounds(965, 665, 80, 30);
        legendText.setForeground(Color.LIGHT_GRAY);

        aliveText.setBounds(990, 688, 45, 30);
        aliveText.setForeground(Color.LIGHT_GRAY);

        notAliveText.setBounds(990, 713, 70, 30);
        notAliveText.setForeground(Color.LIGHT_GRAY);

        this.add(legendText);
        this.add(aliveText);
        this.add(notAliveText);
        legendText.setVisible(true);
        aliveText.setVisible(true);
        notAliveText.setVisible(true);
    }

    void setGameLogic(GameLogic game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = game.getBoard().length;
        int y = game.getBoard()[0].length;
        int cellSize = 20;
        int marginX = 50;
        int marginY = 40;

        g.setColor(Color.GRAY);

        // Board
        g.fillRect(45, 40, 5, 700);
        g.fillRect(950, 40, 5, 700);
        g.fillRect(45, 35, 910, 5);
        g.fillRect(45, 740, 910, 5);

        for (int i = 0; i <= x; i++) {
            g.drawLine(i * cellSize + marginX, marginY, i * cellSize + marginX, y * cellSize + marginY);
        }

        for (int i = 0; i <= y; i++) {
            g.drawLine(marginX, i * cellSize + marginY, x * cellSize + marginX, i * cellSize + marginY);
        }

        // Legend
        g.fillRect(960, 668, 2, 75);
        g.fillRect(1195, 668, 2, 75);
        g.fillRect(960, 668, 235, 2);
        g.fillRect(960, 743, 237, 2);

        g.drawOval(965, 695, 20, 20);

        System.setProperty("aliveCell", "#228b22");
        g.setColor(Color.getColor("aliveCell"));
        g.fillOval(965, 720, 20, 20);
        g.setColor(Color.GRAY);
        g.drawOval(965, 720, 20, 20);

        game.paintBoard(g);
    }
}