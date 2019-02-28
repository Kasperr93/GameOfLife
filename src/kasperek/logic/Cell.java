package kasperek.logic;

import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 0.5 02/27/2019
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

    boolean willBeAlive() {
        return willBeAlive;
    }

    /**
     * The method paint cells on the user gui.
     *
     * @param g graphics object.
     * @param x position on the x-axis.
     * @param y position on the y-axis.
     */

    public void paintCell(Graphics g, int x, int y) {
        int marginX = 50;
        int marginY = 40;

        if (isAlive) {
            g.setColor(Color.GREEN);
            g.fillOval(x * 20 + marginX, y * 20 + marginY, 20, 20);
            g.setColor(Color.GRAY);
            g.drawOval(x * 20 + marginX, y * 20 + marginY, 20, 20);
        } else {
            g.setColor(Color.GRAY);
            g.drawOval(x * 20 + marginX, y * 20 + marginY, 20, 20);
        }
    }
}