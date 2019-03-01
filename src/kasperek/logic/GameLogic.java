package kasperek.logic;

import kasperek.gui.GamePane;

import java.awt.*;
import java.util.Random;

/**
 * @author Tomasz Kasperek
 * @version 1.2 03/01/2019
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

    public GameLogic() {
        board = new Cell[BOARD_WIDTH][BOARD_HEIGHT];

        createCells();
    }

    public void startGame(int numberOfAliveCells) {
        this.numberOfAliveCells = numberOfAliveCells;
        stepsCounter = 0;
        numberOfNotAliveCells = BOARD_SIZE - numberOfAliveCells;
        createAliveCells(numberOfAliveCells);
        willItBeAlive();
    }

    private void createCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
            }
        }
    }

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

    private void updateCells() {
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                cell.update();
            }
        }
    }

    public void paintBoard(Graphics g) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].paintCell(g, i, j);
            }
        }
    }

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

    void willItBeAlive() {
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

    private boolean isEndGame() {
        return numberOfAliveCells == 0;
    }

    public int getNumberOfAliveCells() {
        return numberOfAliveCells;
    }

    public int getNumberOfNotAliveCells() {
        return numberOfNotAliveCells;
    }

    public int getNumberOfWillBeAlive() {
        return numberOfWillBeAlive;
    }

    public int getNumberOfWillNotBeAliveCells() {
        return numberOfWillNotBeAliveCells;
    }

    public int getStepsCounter() {
        return stepsCounter;
    }

    public Cell[][] getBoard() {
        return board;
    }
}