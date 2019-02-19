package kasperek.logic;

import java.util.Random;

/**
 * @author Tomasz Kasperek
 * @version 0.1 02/19/2019
 * @see Cell
 * @since 0.1
 */

public class GameLogic {
    private Cell[][] board;

    private final int boardWidth = 10;
    private final int boardHeight = 10;

    public GameLogic() {
        board = new Cell[boardWidth][boardHeight];
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
        int x = random.nextInt(boardWidth);
        int y = random.nextInt(boardHeight);

        for (int i = 0; i < numberOfAliveCells; i++) {
            while (board[x][y].willBeAlive()) {
                x = random.nextInt(boardWidth);
                y = random.nextInt(boardHeight);
            }
        }
    }

    private void updateCells() {
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                cell.update();
            }
        }
    }
}