package kasperek.logic;

import java.awt.*;
import java.util.Random;

/**
 * @author Tomasz Kasperek
 * @version 0.3 02/19/2019
 * @see Cell
 * @since 0.1
 */

public class GameLogic {
    private Cell[][] board;

    private final int BOARD_WIDTH = 45;
    private final int BOARD_HEIGHT = 35;

    public GameLogic(int numberOfAliveCells) {
        board = new Cell[BOARD_WIDTH][BOARD_HEIGHT];

        createCells();
        createAliveCells(numberOfAliveCells);
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

    public Cell[][] getBoard() {
        return board;
    }
}