package kasperek.gui;

import kasperek.logic.GameLogic;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Tomasz Kasperek
 * @version 0.6 02/28/2019
 * @see GameWindow
 * @see GameLogic
 * @since 0.1
 */

public class GamePane extends JPanel {
    private GameLogic game;

    private JButton nextStepButton;
    private JButton allStepsButton;
    private JButton startGameButton;

    private JLabel warningText;

    private JFormattedTextField numbersOfNeighboursTextField;

    /**
     * Default pane constructor. It is responsibility for setter all needed parameters for window.
     */

    GamePane() {
        game = new GameLogic();

        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        initializeNumbersOfNeighboursLabel();
        initializeNumbersOfNeighboursTextField();
        initializeWarningLabelText();
        initializeStartGameButton();
        initializeResetGameButton();
        initializeNextStepButton();
        initializeAllStepButton();
        initializeLegendLabel();
    }

    private void initializeNumbersOfNeighboursLabel() {
        JLabel numbersOfNeighboursLabel = new JLabel("Numbers of neighbours:");
        numbersOfNeighboursLabel.setBounds(983, 25, 155, 20);
        numbersOfNeighboursLabel.setForeground(Color.LIGHT_GRAY);

        this.add(numbersOfNeighboursLabel);
        numbersOfNeighboursLabel.setVisible(true);
    }

    private void initializeNumbersOfNeighboursTextField() {
        NumberFormatter nf = new NumberFormatter();
        nf.setMinimum(20);
        nf.setMaximum(1000);

        numbersOfNeighboursTextField = new JFormattedTextField(nf);
        numbersOfNeighboursTextField.setBounds(980, 42, 200, 30);
        numbersOfNeighboursTextField.setHorizontalAlignment(JTextField.CENTER);
        numbersOfNeighboursTextField.setBackground(Color.GRAY);
        numbersOfNeighboursTextField.setForeground(Color.LIGHT_GRAY);

        numbersOfNeighboursTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char input = e.getKeyChar();

                if (input < '0' || input > '9') {
                    e.consume();
                    warningText.setText("You can type only numbers!");
                    warningText.setVisible(true);
                } else {
                    warningText.setVisible(false);
                }
            }
        });

        this.add(numbersOfNeighboursTextField);
        numbersOfNeighboursTextField.setVisible(true);
    }

    private void initializeWarningLabelText() {
        warningText = new JLabel();
        warningText.setBounds(983, 67, 200, 20);
        warningText.setForeground(Color.RED);

        this.add(warningText);
        warningText.setVisible(false);
    }

    private void initializeStartGameButton() {
        startGameButton = new JButton("Start game");
        startGameButton.setBounds(980, 100, 100, 40);

        startGameButton.addActionListener(e -> {
            game.startGame(Integer.valueOf(numbersOfNeighboursTextField.getText()));
            startGameButton.setEnabled(false);
            numbersOfNeighboursTextField.setEditable(false);
            nextStepButton.setEnabled(true);
            allStepsButton.setEnabled(true);
            this.repaint();
        });

        this.add(startGameButton);
        startGameButton.setVisible(true);
    }

    private void initializeResetGameButton() {
        JButton resetGameButton = new JButton("Reset game");
        resetGameButton.setBounds(1080, 100, 100, 40);

        resetGameButton.addActionListener(e -> {
            game = new GameLogic();
            numbersOfNeighboursTextField.setText("");
            numbersOfNeighboursTextField.setEditable(true);
            startGameButton.setEnabled(true);
            nextStepButton.setEnabled(false);
            allStepsButton.setEnabled(false);
            this.repaint();
        });

        this.add(resetGameButton);
        resetGameButton.setVisible(true);
    }

    private void initializeNextStepButton() {
        nextStepButton = new JButton("Next step");
        nextStepButton.setBounds(980, 550, 100, 40);

        nextStepButton.addActionListener(e -> {
            game.singleStep();
            this.repaint();
        });

        nextStepButton.setEnabled(false);

        this.add(nextStepButton);
        nextStepButton.setVisible(true);
    }

    private void initializeAllStepButton() {
        allStepsButton = new JButton("All steps");
        allStepsButton.setBounds(1080, 550, 100, 40);

        allStepsButton.addActionListener(e -> {
            game.allSteps(this);
            this.repaint();
            nextStepButton.setEnabled(false);
        });

        allStepsButton.setEnabled(false);

        this.add(allStepsButton);
        allStepsButton.setVisible(true);
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

        g.setColor(Color.getColor("aliveCell"));
        g.fillOval(965, 720, 20, 20);
        g.setColor(Color.GRAY);
        g.drawOval(965, 720, 20, 20);

        game.paintBoard(g);
    }
}