package kasperek.logic;

import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 0.2 02/19/2019
 * @see GameLogic
 * @since 0.1
 */

public class Cell {
    private boolean isAlive;
    private boolean willBeAlive;

    /**
     * Default constructor with variables implementation.
     */

    public Cell() {
        isAlive = false;
        willBeAlive = false;
    }

    /**
     * The method sets the cell state from alive to dead.
     */

    void killCell() {
        willBeAlive = false;
    }

    /**
     * The method sets the cell state from dead to alive.
     */

    void reviveCell() {
        willBeAlive = true;
    }

    /**
     * The method change state of cell on the negation.
     */

    void changeState() {
        this.isAlive = !this.isAlive;
    }

    /**
     * The method change state of cells for the next round.
     */

    void update() {
        this.isAlive = this.willBeAlive;
    }

    /**
     * The method return, whether the cell is alive.
     */

    boolean isAlive() {
        return isAlive;
    }

    /**
     * The method return, whether the cell will be alive.
     */

    boolean WillBeAlive() {
        return willBeAlive;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     */

    public void paintCell(Graphics g, int x, int y) {
        if (isAlive) {
            g.setColor(Color.DARK_GRAY);
            g.fillOval(x * 20, y * 20, 20, 20);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillOval(x * 20, y * 20, 20, 20);
        }
    }
}