package kasperek.tests;

import kasperek.logic.GameLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomasz Kasperek
 * @version 0.1 03/07/2019
 * @see GameLogic
 * @since 1.0
 */


public class GameLogicTest {
    private static GameLogic gameTest;

    private int numberOfAliveCells;
    private int numberOfNotAliveCells;
    private int numberOfWillBeAlive;
    private int numberOfWillNotBeAliveCells;
    private int stepsCounter;

    /**
     * The method clears all data after each test.
     */

    @Before
    public void startUp() {
        gameTest = new GameLogic();

        numberOfAliveCells = 0;
        numberOfNotAliveCells = 0;
        numberOfWillBeAlive = 0;
        numberOfWillNotBeAliveCells = 0;
        stepsCounter = 0;
    }

    /**
     * Test <code>shouldCreateBoardAndObjects</code> be a fine checks, that the board is not null.
     */

    @Test
    public void shouldCreateBoardAndObjects() {

        // Then
        Assert.assertEquals(45, gameTest.getBoardXSize());
        Assert.assertEquals(35, gameTest.getBoardYSize());
        Assert.assertNotNull(gameTest);
    }
}