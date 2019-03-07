package kasperek.gui;

import kasperek.logic.GameLogic;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Tomasz Kasperek
 * @version 1.4.1 03/07/2019
 * @see GameWindow
 * @see GameLogic
 * @since 0.1
 */

public class GamePane extends JPanel {
    private GameLogic game;

    private JButton nextStepButton;
    private JButton allStepsButton;
    private JButton startGameButton;

    private JLabel warningTextLabel;
    private JLabel aliveCellsCounterLabel;
    private JLabel notAliveCellsCounterLabel;
    private JLabel willBeAliveCellsCounterLabel;
    private JLabel willNotBeAliveCellsCounterLabel;
    private JLabel stepsCounterLabel;
    private JLabel initialValueOfAliveCellsLabel;

    private JFormattedTextField numberOfAliveCellsTextField;

    /**
     * Default pane constructor. It is responsibility for setter all needed parameters for window.
     */

    GamePane() {
        game = new GameLogic();
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        initialization();
    }

    /**
     * The method is responsibility for calling another methods needed to working the program.
     */

    private void initialization() {
        // Text field
        initializeNumberOfAliveCellsLabel();
        initializeNumberOfAliveCellsTextField();
        initializeWarningLabel();

        // Start/Reset buttons
        initializeStartGameButton();
        initializeResetGameButton();

        // Statistics
        initializeStatisticsLabel();
        initializeAliveCellsCounterLabel();
        initializeNotAliveCellsCounterLabel();
        initializeWillBeAliveCellsCounterLabel();
        initializeWillNotBeAliveCellsCounterLabel();
        initializeStepsCounterLabel();
        initializeInitialValueOfAliveCellsLabel();

        // Next/All step buttons
        initializeNextStepButton();
        initializeAllStepButton();

        // Legend
        initializeLegendLabel();
        initializeAliveTextLabel();
        initializeNotAliveTextLabel();
    }

    /**
     * The method create the label up to the text field.
     */

    private void initializeNumberOfAliveCellsLabel() {
        JLabel numberOfAliveCellsLabel = new JLabel("Number of alive cells:");
        numberOfAliveCellsLabel.setBounds(983, 25, 155, 20);
        numberOfAliveCellsLabel.setForeground(Color.LIGHT_GRAY);

        this.add(numberOfAliveCellsLabel);
        numberOfAliveCellsLabel.setVisible(true);
    }

    /**
     * The method create the text field, where the user put a number of alive cells.
     */

    private void initializeNumberOfAliveCellsTextField() {
        numberOfAliveCellsTextField = new JFormattedTextField();
        numberOfAliveCellsTextField.setBounds(980, 42, 200, 30);
        numberOfAliveCellsTextField.setHorizontalAlignment(JTextField.CENTER);
        numberOfAliveCellsTextField.setBackground(Color.GRAY);
        numberOfAliveCellsTextField.setForeground(Color.LIGHT_GRAY);

        numberOfAliveCellsTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char input = e.getKeyChar();

                if ((input < '0' || input > '9') && input != '\b') {
                    e.consume();
                    warningTextLabel.setText("You can type only numbers!");
                    warningTextLabel.setVisible(true);
                } else {
                    warningTextLabel.setVisible(false);
                }
            }
        });

        numberOfAliveCellsTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateDocumentListener(numberOfAliveCellsTextField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateDocumentListener(numberOfAliveCellsTextField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateDocumentListener(numberOfAliveCellsTextField.getText());
            }
        });

        this.add(numberOfAliveCellsTextField);
        numberOfAliveCellsTextField.setVisible(true);
    }

    /**
     * The method create a warning label, when the user put a wrong value on the text field.
     * The warnings show up and hide up, when a value is correct or incorrect.
     */

    private void initializeWarningLabel() {
        warningTextLabel = new JLabel();
        warningTextLabel.setBounds(983, 67, 220, 20);
        warningTextLabel.setForeground(Color.RED);

        this.add(warningTextLabel);
        warningTextLabel.setVisible(false);
    }

    /**
     * The method create the start game button.
     * After pushing the button, the game will be start.
     */

    private void initializeStartGameButton() {
        startGameButton = new JButton("Start game");
        startGameButton.setBounds(980, 90, 100, 40);

        startGameButton.addActionListener(e -> {
            String numberOfNeighbours = numberOfAliveCellsTextField.getText().replaceAll("\\W", "");
            initialValueOfAliveCellsLabel.setText("Initial value of alive cells: " + numberOfNeighbours);

            game.startGame(Integer.valueOf(numberOfNeighbours));
            setValuesAfterStart();
        });

        startGameButton.setEnabled(false);

        this.add(startGameButton);
        startGameButton.setVisible(true);
    }

    /**
     * The method create the reset game button.
     * After pushing all values will be restore to the default values.
     */

    private void initializeResetGameButton() {
        JButton resetGameButton = new JButton("Reset game");
        resetGameButton.setBounds(1080, 90, 100, 40);

        resetGameButton.addActionListener(e -> {
            game = new GameLogic();
            setDefaultDataAfterReset();
        });

        this.add(resetGameButton);
        resetGameButton.setVisible(true);
    }

    /**
     * The method create the label up to the statistics section.
     */

    private void initializeStatisticsLabel() {
        JLabel statisticsTitleLabel = new JLabel("Statistics:");
        statisticsTitleLabel.setBounds(965, 140, 80, 20);
        statisticsTitleLabel.setForeground(Color.LIGHT_GRAY);

        this.add(statisticsTitleLabel);
        statisticsTitleLabel.setVisible(true);
    }

    /**
     * The method create the label which will count the alive cells.
     */

    private void initializeAliveCellsCounterLabel() {
        aliveCellsCounterLabel = new JLabel("Alive: -");
        aliveCellsCounterLabel.setBounds(990, 164, 200, 20);
        aliveCellsCounterLabel.setForeground(Color.LIGHT_GRAY);

        this.add(aliveCellsCounterLabel);
        aliveCellsCounterLabel.setVisible(true);
    }

    /**
     * The method create the label which will count the not alive cells.
     */

    private void initializeNotAliveCellsCounterLabel() {
        notAliveCellsCounterLabel = new JLabel("Not alive: -");
        notAliveCellsCounterLabel.setBounds(990, 194, 200, 20);
        notAliveCellsCounterLabel.setForeground(Color.LIGHT_GRAY);

        this.add(notAliveCellsCounterLabel);
        notAliveCellsCounterLabel.setVisible(true);
    }

    /**
     * The method create the label which will count the will alive cells.
     */

    private void initializeWillBeAliveCellsCounterLabel() {
        willBeAliveCellsCounterLabel = new JLabel("Will be alive: -");
        willBeAliveCellsCounterLabel.setBounds(970, 224, 200, 20);
        willBeAliveCellsCounterLabel.setForeground(Color.LIGHT_GRAY);

        this.add(willBeAliveCellsCounterLabel);
        willBeAliveCellsCounterLabel.setVisible(true);
    }

    /**
     * The method create the label which will count the will not alive cells.
     */

    private void initializeWillNotBeAliveCellsCounterLabel() {
        willNotBeAliveCellsCounterLabel = new JLabel("Won't be alive: -");
        willNotBeAliveCellsCounterLabel.setBounds(970, 254, 200, 20);
        willNotBeAliveCellsCounterLabel.setForeground(Color.LIGHT_GRAY);

        this.add(willNotBeAliveCellsCounterLabel);
        willNotBeAliveCellsCounterLabel.setVisible(true);
    }

    /**
     * The method create the label with a step counter.
     */

    private void initializeStepsCounterLabel() {
        stepsCounterLabel = new JLabel("Steps counter: -");
        stepsCounterLabel.setBounds(970, 284, 200, 20);
        stepsCounterLabel.setForeground(Color.LIGHT_GRAY);

        this.add(stepsCounterLabel);
        stepsCounterLabel.setVisible(true);
    }

    /**
     * The method create the label with a initial value of alive cells.
     */

    private void initializeInitialValueOfAliveCellsLabel() {
        initialValueOfAliveCellsLabel = new JLabel("Initial value of alive cells: -");
        initialValueOfAliveCellsLabel.setBounds(970, 344, 200, 20);
        initialValueOfAliveCellsLabel.setForeground(Color.LIGHT_GRAY);

        this.add(initialValueOfAliveCellsLabel);
        initialValueOfAliveCellsLabel.setVisible(true);
    }

    /**
     * The method create the next step button.
     * After pushing, the program doing one step.
     */

    private void initializeNextStepButton() {
        nextStepButton = new JButton("Next step");
        nextStepButton.setBounds(980, 390, 100, 40);

        nextStepButton.addActionListener(e -> {
            game.singleStep();
            updateStatistic(game.getNumberOfAliveCells(), game.getNumberOfNotAliveCells(),
                    game.getNumberOfWillBeAlive(), game.getNumberOfWillNotBeAliveCells(), game.getStepsCounter());
            this.repaint();
        });

        nextStepButton.setEnabled(false);

        this.add(nextStepButton);
        nextStepButton.setVisible(true);
    }

    /**
     * The method create the all steps button.
     * After pushing the program doing step by step and ending, when the alive cells are equal 0.
     */

    private void initializeAllStepButton() {
        allStepsButton = new JButton("All steps");
        allStepsButton.setBounds(1080, 390, 100, 40);

        allStepsButton.addActionListener(e -> {
            game.allSteps(this);
            this.repaint();
            nextStepButton.setEnabled(false);
            allStepsButton.setEnabled(false);
        });

        allStepsButton.setEnabled(false);

        this.add(allStepsButton);
        allStepsButton.setVisible(true);
    }

    /**
     * The method create the label up to the legend section.
     */

    private void initializeLegendLabel() {
        JLabel legendText = new JLabel("Legend:");
        legendText.setBounds(965, 670, 80, 20);
        legendText.setForeground(Color.LIGHT_GRAY);

        this.add(legendText);
        legendText.setVisible(true);
    }

    /**
     * The method create the label next to the alive cell with the explain.
     */

    private void initializeAliveTextLabel() {
        JLabel aliveText = new JLabel("- Alive");
        aliveText.setBounds(990, 694, 45, 20);
        aliveText.setForeground(Color.LIGHT_GRAY);

        this.add(aliveText);
        aliveText.setVisible(true);
    }

    /**
     * The method create the label next to the not alive cell with the explain.
     */

    private void initializeNotAliveTextLabel() {
        JLabel notAliveText = new JLabel("- Not alive");
        notAliveText.setBounds(990, 719, 70, 20);
        notAliveText.setForeground(Color.LIGHT_GRAY);

        this.add(notAliveText);
        notAliveText.setVisible(true);
    }

    /**
     * The method updates all statistics. The statistics are updated after each round.
     *
     * @param aliveCells       the number of alive cells.
     * @param notAliveCells    the number of not alive cells.
     * @param willBeAliveCells the number of cells, which will alive.
     * @param willNotBeAlive   the number of cells, which not will alive.
     * @param steps            the number of steps.
     */

    public void updateStatistic(int aliveCells, int notAliveCells, int willBeAliveCells, int willNotBeAlive, int steps) {
        aliveCellsCounterLabel.setText("Alive: " + aliveCells);
        notAliveCellsCounterLabel.setText("Not alive: " + notAliveCells);
        willBeAliveCellsCounterLabel.setText("Will be alive: " + willBeAliveCells);
        willNotBeAliveCellsCounterLabel.setText("Won't be alive: " + willNotBeAlive);
        stepsCounterLabel.setText("Steps counter: " + steps);

        if (game.isEndGame())
            initializeEndGamePopUp(steps);
    }

    /**
     * The method shows a pop up with the results of the game.
     *
     * @param steps the number of steps.
     */

    private void initializeEndGamePopUp(int steps) {
        String title = "The game is over";
        String message = "All cells died after " + steps + " steps. If you want, you can try again with a different number of alive cells. :)";

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);

        nextStepButton.setEnabled(false);
        allStepsButton.setEnabled(false);
    }

    /**
     * The method enabled or disabled some buttons and update the statistics after the start.
     */

    private void setValuesAfterStart() {
        updateStatistic(game.getNumberOfAliveCells(), game.getNumberOfNotAliveCells(),
                game.getNumberOfWillBeAlive(), game.getNumberOfWillNotBeAliveCells(), game.getStepsCounter());
        startGameButton.setEnabled(false);
        numberOfAliveCellsTextField.setEditable(false);
        nextStepButton.setEnabled(true);
        allStepsButton.setEnabled(true);
        this.repaint();
    }

    /**
     * The method sets all statistics and buttons to default values.
     */

    private void setDefaultDataAfterReset() {
        numberOfAliveCellsTextField.setText("");
        aliveCellsCounterLabel.setText("Alive: -");
        notAliveCellsCounterLabel.setText("Not alive: -");
        willBeAliveCellsCounterLabel.setText("Will be alive: -");
        willNotBeAliveCellsCounterLabel.setText("Won't be alive: -");
        stepsCounterLabel.setText("Steps counter: -");
        initialValueOfAliveCellsLabel.setText("Initial value of alive cells: -");

        numberOfAliveCellsTextField.setEditable(true);
        startGameButton.setEnabled(false);
        nextStepButton.setEnabled(false);
        allStepsButton.setEnabled(false);

        this.repaint();
    }

    /**
     * The method validates a number entered by the user. When the value is correct, the program will continue.
     *
     * @param validateText a number entered by the user.
     */

    private void validateDocumentListener(String validateText) {
        int validateNumber;

        if (validateText.isEmpty()) {
            startGameButton.setEnabled(false);
            return;
        } else {
            validateNumber = Integer.valueOf(validateText);
        }

        if (validateNumber < 20) {
            warningTextLabel.setText("Minimum number of cells: 20");
            warningTextLabel.setVisible(true);
            startGameButton.setEnabled(false);
        } else if (validateNumber > 1575) {
            warningTextLabel.setText("Maximum number of cells: 1575");
            warningTextLabel.setVisible(true);
            startGameButton.setEnabled(false);
        } else {
            warningTextLabel.setText("");
            warningTextLabel.setVisible(false);
            startGameButton.setEnabled(true);
        }
    }

    /**
     * The method paints cells, frames and the board lines.
     *
     * @param g graphics object.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        int x = game.getBoardXSize();
        int y = game.getBoardYSize();
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

        // Statistics
        g.fillRect(960, 160, 2, 205);
        g.fillRect(1195, 160, 2, 205);
        g.fillRect(960, 160, 235, 2);
        g.fillRect(960, 365, 237, 2);

        g.drawOval(965, 165, 20, 20);
        g.drawOval(965, 195, 20, 20);

        // Legend
        g.fillRect(960, 690, 2, 55);
        g.fillRect(1195, 690, 2, 55);
        g.fillRect(960, 690, 235, 2);
        g.fillRect(960, 743, 237, 2);

        g.drawOval(965, 695, 20, 20);
        g.drawOval(965, 720, 20, 20);

        // Statistics
        g.setColor(Color.getColor("aliveCell"));
        g.fillOval(965, 165, 20, 20);

        // Legend
        g.fillOval(965, 695, 20, 20);

        game.paintBoard(g);
    }
}