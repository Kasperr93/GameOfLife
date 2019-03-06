package kasperek.logic;

import kasperek.gui.GamePane;

import java.awt.*;
import java.util.Random;

/**
 * @author Tomasz Kasperek
 * @version 1.3 03/06/2019
 * @see Cell
 * @since 0.1
 */

public class GameLogic {
    private Cell[][] board;

    private int numberOfAliveCells;
    private int numberOfNotAliveCells;
    private int numberOfWillBeAlive;
    private int numberOfWillNotBeAliveCells;
    private int stepsCounter;

    private static final int BOARD_WIDTH = 45;
    private static final int BOARD_HEIGHT = 35;
    private static final int BOARD_SIZE = BOARD_WIDTH * BOARD_HEIGHT;

    /**
     *
     */

    public GameLogic() {
        board = new Cell[BOARD_WIDTH][BOARD_HEIGHT];

        createCells();
    }

    /**
     * @param numberOfAliveCells
     */

    public void startGame(int numberOfAliveCells) {
        this.numberOfAliveCells = numberOfAliveCells;
        stepsCounter = 0;
        numberOfNotAliveCells = BOARD_SIZE - numberOfAliveCells;
        createAliveCells(numberOfAliveCells);
        willItBeAlive();
    }

    /**
     *
     */

    private void createCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    /**
     * @param numberOfAliveCells
     */

    private void createAliveCells(int numberOfAliveCells) {
        Random random = new Random();
        int x = random.nextInt(BOARD_WIDTH);
        int y = random.nextInt(BOARD_HEIGHT);

        for (int i = 0; i < numberOfAliveCells; i++) {
            while (board[x][y].willBeAlive()) {
                x = random.nextInt(BOARD_WIDTH);
                y = random.nextInt(BOARD_HEIGHT);
            }

            board[x][y].reviveCell();
        }

        this.updateCells();
    }

    /**
     *
     */

    private void updateCells() {
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                cell.update();
            }
        }
    }

    /**
     * @param g
     */

    public void paintBoard(Graphics g) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].paintCell(g, i, j);
            }
        }
    }

    /**
     * @param x
     * @param y
     * @return
     */

    private int checkNeighbours(int x, int y) {
        int aliveNeighbours = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            if (i < 0 || i >= BOARD_WIDTH)
                continue;

            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || j >= BOARD_HEIGHT || (i == x && j == y))
                    continue;

                boolean isAlive = board[i][j].isAlive();

                if (isAlive)
                    aliveNeighbours++;
            }
        }

        return aliveNeighbours;
    }

    /**
     *
     */

    public void singleStep() {
        int aliveNeighbours;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                aliveNeighbours = checkNeighbours(i, j);

                if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                    if (board[i][j].isAlive()) {
                        board[i][j].killCell();
                        numberOfAliveCells--;
                    }
                } else {
                    if (!board[i][j].isAlive()) {
                        board[i][j].reviveCell();
                        numberOfAliveCells++;
                    }
                }
            }
        }

        this.updateCells();
        numberOfNotAliveCells = BOARD_SIZE - numberOfAliveCells;
        stepsCounter++;

        willItBeAlive();
    }

    /**
     *
     */

    private void willItBeAlive() {
        int aliveNeighbours;
        numberOfWillBeAlive = numberOfAliveCells;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                aliveNeighbours = checkNeighbours(i, j);

                if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                    if (board[i][j].isAlive()) {
                        numberOfWillBeAlive--;
                    }
                } else {
                    if (!board[i][j].isAlive()) {
                        numberOfWillBeAlive++;
                    }
                }
            }
        }

        numberOfWillNotBeAliveCells = BOARD_SIZE - numberOfWillBeAlive;
    }

    /**
     * @param panel
     */

    public void allSteps(GamePane panel) {
        new Thread(() -> {

            while (!isEndGame()) {
                try {
                    Thread.sleep(2000);
                    singleStep();
                    panel.updateStatistic(getNumberOfAliveCells(), getNumberOfNotAliveCells(),
                            getNumberOfWillBeAlive(), getNumberOfWillNotBeAliveCells(), getStepsCounter());
                    panel.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @return
     */

    private boolean isEndGame() {
        return numberOfAliveCells == 0;
    }

    /**
     * @return
     */

    public int getNumberOfAliveCells() {
        return numberOfAliveCells;
    }

    /**
     * @return
     */

    public int getNumberOfNotAliveCells() {
        return numberOfNotAliveCells;
    }

    /**
     * @return
     */

    public int getNumberOfWillBeAlive() {
        return numberOfWillBeAlive;
    }

    /**
     * @return
     */

    public int getNumberOfWillNotBeAliveCells() {
        return numberOfWillNotBeAliveCells;
    }

    /**
     * @return
     */

    public int getStepsCounter() {
        return stepsCounter;
    }

    /**
     * @return
     */

    public int getBoardXSize() {
        return board.length;
    }

    /**
     * @return
     */

    public int getBoardYSize() {
        return board[0].length;
    }

    /**
     * @author Tomasz Kasperek
     * @version 1.0 02/28/2019
     * @see GameLogic
     * @since 0.1
     */

    private class Cell {
        private boolean isAlive;
        private boolean willBeAlive;

        /**
         * Default constructor with variables implementation.
         */

        private Cell() {
            isAlive = false;
            willBeAlive = false;

            System.setProperty("aliveCell", "#228b22");
        }

        /**
         * The method sets the cell state from alive to dead.
         */

        private void killCell() {
            willBeAlive = false;
        }

        /**
         * The method sets the cell state from dead to alive.
         */

        private void reviveCell() {
            willBeAlive = true;
        }

        /**
         * The method change state of cells for the next round.
         */

        private void update() {
            this.isAlive = this.willBeAlive;
        }

        /**
         * The method return, whether the cell is alive.
         */

        private boolean isAlive() {
            return isAlive;
        }

        /**
         * The method return, whether the cell will be alive.
         */

        private boolean willBeAlive() {
            return willBeAlive;
        }

        /**
         * The method paint cells on the user gui.
         *
         * @param g graphics object.
         * @param x position on the x-axis.
         * @param y position on the y-axis.
         */

        private void paintCell(Graphics g, int x, int y) {
            int marginX = 50;
            int marginY = 40;

            if (isAlive) {
                g.setColor(Color.getColor("aliveCell"));
                g.fillOval(x * 20 + marginX, y * 20 + marginY, 20, 20);
                g.setColor(Color.GRAY);
                g.drawOval(x * 20 + marginX, y * 20 + marginY, 20, 20);
            } else {
                g.setColor(Color.GRAY);
                g.drawOval(x * 20 + marginX, y * 20 + marginY, 20, 20);
            }
        }
    }
}