package kasperek.test;

import kasperek.logic.GameLogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomasz Kasperek
 * @version 1.0 03/07/2019
 * @see GameLogic
 * @since 1.0
 */

public class GameLogicTest {
    private static GameLogic gameTest;

    /**
     * The method clears all data after each test.
     */

    @Before
    public void startUp() {
        gameTest = new GameLogic();
    }

    /**
     * Test <code>shouldCreateBoardAndObjects</code> checks that the board is not null.
     */

    @Test
    public void shouldCreateBoardAndObjects() {

        // Then
        Assert.assertEquals(45, gameTest.getBoardXSize());
        Assert.assertEquals(35, gameTest.getBoardYSize());
        Assert.assertNotNull(gameTest);
    }

    /**
     * Test <code>shouldGenerateAliveCells</code> checks that the program generate alive cells for the game.
     */

    @Test
    public void shouldGenerateAliveCells() {

        // When
        gameTest.startGame(10);

        // Then
        Assert.assertEquals(10, gameTest.getNumberOfAliveCells());
        Assert.assertNotEquals(12, gameTest.getNumberOfAliveCells());
        Assert.assertNotEquals(5, gameTest.getNumberOfAliveCells());
        Assert.assertNotEquals(896, gameTest.getNumberOfAliveCells());
    }

    /**
     * Test <code>shouldDoSingleStep</code> checks that the program do a single step.
     */

    @Test
    public void shouldDoSingleStep() {

        // Given
        gameTest.startGame(200);

        // When
        gameTest.singleStep();

        // Then
        Assert.assertNotEquals(200, gameTest.getNumberOfAliveCells());
    }

    /**
     * Test <code>shouldReturnCorrectAmountOfAliveCells</code> checks that the program counts dead cells correctly.
     */

    @Test
    public void shouldReturnCorrectAmountOfNotAliveCells() {

        // Given
        gameTest.startGame(100);

        // Then
        Assert.assertEquals(1475, gameTest.getNumberOfNotAliveCells());
        Assert.assertNotEquals(100, gameTest.getNumberOfNotAliveCells());
        Assert.assertNotEquals(521, gameTest.getNumberOfNotAliveCells());
        Assert.assertNotEquals(74, gameTest.getNumberOfNotAliveCells());
    }

    /**
     * Test <code>shouldReturnCorrectAmountOfWillAliveCells</code> checks the program correctly counts the cells
     * that will alive in the next round.
     */

    @Test
    public void shouldReturnCorrectAmountOfWillAliveCells() {

        // Given
        gameTest.startGame(1575);

        // Then
        Assert.assertEquals(4, gameTest.getNumberOfWillBeAlive());
        Assert.assertNotEquals(233, gameTest.getNumberOfWillBeAlive());
        Assert.assertNotEquals(12, gameTest.getNumberOfWillBeAlive());
        Assert.assertNotEquals(521, gameTest.getNumberOfWillBeAlive());
    }

    /**
     * Test <code>shouldReturnCorrectAmountOfWillAliveCells</code> checks the program correctly counts the cells that
     * won't alive in the next round.
     */

    @Test
    public void shouldReturnCorrectAmountOfWontAliveCells() {

        // Given
        gameTest.startGame(1575);

        // Then
        Assert.assertEquals(1571, gameTest.getNumberOfWillNotBeAliveCells());
        Assert.assertNotEquals(1221, gameTest.getNumberOfWillNotBeAliveCells());
        Assert.assertNotEquals(873, gameTest.getNumberOfWillNotBeAliveCells());
        Assert.assertNotEquals(352, gameTest.getNumberOfWillNotBeAliveCells());
    }

    /**
     * Test <code>shouldCountSteps</code> checks that the program counts steps correctly.
     */

    @Test
    public void shouldCountSteps() {
        // Given
        gameTest.startGame(100);

        // When
        for (int i = 0; i < 10; i++) {
            gameTest.singleStep();
        }

        // Then
        Assert.assertEquals(10, gameTest.getStepsCounter());
        Assert.assertNotEquals(100, gameTest.getNumberOfAliveCells());
    }

    /**
     * Test <code>shouldEndTheGame</code> checks that the program ends the game, when the number of alive cells equal 0.
     */

    @Test
    public void shouldEndTheGame() {

        // Given
        gameTest.startGame(1575);

        // When
        for (int i = 0; i < 2; i++)
            gameTest.singleStep();

        // When
        Assert.assertEquals(0, gameTest.getNumberOfAliveCells());
        Assert.assertEquals(1575, gameTest.getNumberOfNotAliveCells());
        Assert.assertEquals(0, gameTest.getNumberOfWillBeAlive());
        Assert.assertEquals(1575, gameTest.getNumberOfWillNotBeAliveCells());
        Assert.assertTrue(gameTest.isEndGame());
    }
}