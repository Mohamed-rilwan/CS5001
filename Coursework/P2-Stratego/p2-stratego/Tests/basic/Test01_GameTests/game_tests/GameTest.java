package game_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

import java.util.List;

public class GameTest {

    private Game myGame;
    private Player p0;
    private Player p1;

    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Oz", 1);
        myGame = new Game(p0, p1);
    }

    @Test
    public void testGameExists() {
        assertNotNull(myGame);
    }

    @Test
    public void testGetPlayer() {
        assertEquals(p0, myGame.getPlayer(0));
        assertEquals(p1, myGame.getPlayer(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayerBad() {
        myGame.getPlayer(3);
        fail("the last line was supposed to throw an IllegalArgumentException");
    }

    @Test
    public void testGetWinnerNone() {
        assertNull(myGame.getWinner());
    }

    @Test
    public void testGetWinnerP0() {
        p1.loseGame();
        assertNotNull(myGame.getWinner());
        assertEquals(p0, myGame.getWinner());
    }

    @Test
    public void testGetSquareEmpty() {
        Square emptySquare = myGame.getSquare(1,0);
        assertNull(emptySquare.getPiece());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetSquareBad() {
        myGame.getSquare(23, 4);
        fail("the last line was supposed to throw an IndexOutOfBoundsException");
    }
    
    @Test
    public void testGetSquareColonel() {
        Square colonelSquare = myGame.getSquare(0, 2);
        Piece colonel = new StepMover(p0, colonelSquare, 8);  // rank 8 = colonel
        Piece presentPiece = colonelSquare.getPiece();
        assertNotNull(presentPiece);
        assertEquals(colonel, presentPiece);
    }

    @Test
    public void testLand() {
        assertTrue(myGame.getSquare(0, 0).canBeEntered());
        assertTrue(myGame.getSquare(8, 3).canBeEntered());
        assertTrue(myGame.getSquare(5, 4).canBeEntered());
    }

    @Test
    public void testWater() {
        assertFalse(myGame.getSquare(4, 2).canBeEntered());
        assertFalse(myGame.getSquare(4, 7).canBeEntered());
        assertFalse(myGame.getSquare(5, 3).canBeEntered());
    }

    @Test
    public void fullGame() {
        // TODO
    }
}
