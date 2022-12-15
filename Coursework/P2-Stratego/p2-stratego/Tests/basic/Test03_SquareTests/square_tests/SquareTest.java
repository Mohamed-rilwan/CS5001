package square_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

public class SquareTest {

    private Square land, land2, land3, water, water2;
    private Piece lance, general;
    private Player michael;

    @Before
    public void setup() {
        land = new Square(null, 1, 2, false);  // null game, but should still work
        land2 = new Square(null, 1, 3, false);
        land3 = new Square(null, 0, 3, false);
        water = new Square(null, 3, 1, true);
        water2 = new Square(null, 4, 1, true);

        michael = new Player("Michael", 0);

        lance = new Scout(michael, land);
        general = new StepMover(michael, land3, 9);
    }

    @Test
    public void testExists() {
        assertNotNull(land);
    }

    @Test
    public void testGetRow() {
        assertEquals(1, land.getRow());
    }

    @Test
    public void testGetCol() {
        assertEquals(2, land.getCol());
    }

    @Test
    public void testGetGameNull() {
        assertNull(land.getGame());  // this one was created with a null game
    }

    @Test
    public void testGetPiece() {
        assertEquals(lance, land.getPiece());
    }

    @Test
    public void testCanBeEnteredOccupied() {
        assertFalse(land.canBeEntered());
    }

    @Test
    public void testCanBeEnteredUnoccupied() {
        assertTrue(land2.canBeEntered());
    }

    @Test
    public void testCanBeEnteredWater() {
        assertFalse(water.canBeEntered());
    }

    @Test
    public void testCanBeEnteredUnoccupiedWater() {
        assertFalse(water2.canBeEntered());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlacePieceFail() {
        land.placePiece(general);  // place general on square occupied by lance
        fail("the last line should have thrown an exception");
    }

    @Test
    public void testRemovePiece() {
        land.removePiece();
        assertNull(land.getPiece());
    }

    @Test
    public void testPlacePieceSuccess() {
        land.removePiece();
        land.placePiece(general);  // Note: not actually a legal move
        assertEquals(general, land.getPiece());
    }
}
