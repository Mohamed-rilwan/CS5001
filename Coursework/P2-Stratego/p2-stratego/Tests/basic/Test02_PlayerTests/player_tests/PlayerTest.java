package player_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

import java.util.ArrayList;

public class PlayerTest {

    private Player michael;
    private Player oz;

    private Piece captain;
    private Square land;
    private Square water;

    @Before
    public void setup() {
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);

        // This stuff should work even without a Game object!
        land = new Square(null, 0, 0, false);
        water = new Square(null, 0, 2, true);
        captain = new StepMover(michael, land, 6);
    }

    @Test
    public void testExists() {
        assertNotNull(michael);
    }

    @Test
    public void testGetName() {
        assertEquals("Michael", michael.getName());
        assertEquals("Ozgur", oz.getName());
    }

    @Test
    public void testGetPlayerNumber() {
        assertEquals(0, michael.getPlayerNumber());
        assertEquals(1, oz.getPlayerNumber());
    }

    @Test
    public void testHasLostFalse() {
        assertFalse(michael.hasLost());
    }

    @Test
    public void testHasLostTrue() {
        michael.loseGame();
        assertTrue(michael.hasLost());
    }

}
