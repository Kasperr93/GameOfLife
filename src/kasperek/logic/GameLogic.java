package kasperek.logic;

import kasperek.gui.GamePane;

import java.awt.*;
import java.util.Random;

/**
 * @author Tomasz Kasperek
 * @version 1.4 03/07/2019
 * @see Cell
 * @see GamePane
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
     * Default game logic constructor. It create a board and cells.
     */

    public GameLogic() {
        board = new Cell[BOARD_WIDTH][BOARD_HEIGHT];

        createCells();
    }

    /**
     * The method starts the game. It sets number of alive cells, step counter and calls other methods.
     *
     * @param numberOfAliveCells the number of cells that will be alive.
     */

    public void startGame(int numberOfAliveCells) {
        this.numberOfAliveCells = numberOfAliveCells;
        stepsCounter = 0;
        numberOfNotAliveCells = BOARD_SIZE - numberOfAliveCells;
        createAliveCells(numberOfAliveCells);
        willItBeAlive();
    }

    /**
     * The method fills the board the cells objects.
     */

    private void createCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    /**
     * The method create alive cells by the random. When a cell is currently alive, the method will random next object
     * from the board.
     *
     * @param numberOfAliveCells the number of alive cells.
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
     * The method updates the condition of the cells.
     */

    private void updateCells() {
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                cell.update();
            }
        }
    }

    /**
     * The method paints cells on the board given coordinates.
     *
     * @param g graphics object.
     */

    public void paintBoard(Graphics g) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].paintCell(g, i, j);
            }
        }
    }

    /**
     * The method checks how many neighbours have a cell.
     *
     * @param x coordinates of the first array.
     * @param y coordinates of the second array.
     * @return the number of neighbours.
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
     * The method performs a single step in the game. After the step, it updates some data and calls some methods.
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
     * The method checks, that a cell will alive (for statistic only).
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
     * The method performs the game step by step using the <code>singleStep</code> method.
     *
     * @param panel The GamePane object to update statistics and repaint the board.
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
     * The method checks, that the game is over. The game is over, when the number of alive cells equals 0.
     *
     * @return is the game over.
     */

    private boolean isEndGame() {
        return numberOfAliveCells == 0;
    }

    /**
     * The method returns the number of alive cells.
     *
     * @return the number of alive cells.
     */

    public int getNumberOfAliveCells() {
        return numberOfAliveCells;
    }

    /**
     * The method returns the number of not alive cells.
     *
     * @return the number of not alive cells.
     */

    public int getNumberOfNotAliveCells() {
        return numberOfNotAliveCells;
    }

    /**
     * The method returns the number of cells will alive.
     *
     * @return the number of cells which will alive.
     */

    public int getNumberOfWillBeAlive() {
        return numberOfWillBeAlive;
    }

    /**
     * The method returns the number of won't alive cells.
     *
     * @return the number of won't alive cells.
     */

    public int getNumberOfWillNotBeAliveCells() {
        return numberOfWillNotBeAliveCells;
    }

    /**
     * The method returns the number of steps performed in the game.
     *
     * @return the number of steps performed in the game.
     */

    public int getStepsCounter() {
        return stepsCounter;
    }

    /**
     * The method returns length on the the x-axis.
     *
     * @return the length of x-axis.
     */

    public int getBoardXSize() {
        return board.length;
    }

    /**
     * The method returns length on the the y-axis.
     *
     * @return the length of y-axis.
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