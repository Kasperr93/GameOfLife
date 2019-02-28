package kasperek.logic;

import java.awt.*;

/**
 * @author Tomasz Kasperek
 * @version 1.0 02/28/2019
 * @see GameLogic
 * @since 0.1
 */

@SuppressWarnings("WeakerAccess")
public class Cell {
    private boolean isAlive;
    private boolean willBeAlive;

    /**
     * Default constructor with variables implementation.
     */

    Cell() {
        isAlive = false;
        willBeAlive = false;

        System.setProperty("aliveCell", "#228b22");
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

    void paintCell(Graphics g, int x, int y) {
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