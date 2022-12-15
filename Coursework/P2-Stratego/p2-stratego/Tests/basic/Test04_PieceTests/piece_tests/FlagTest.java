package piece_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

public class FlagTest {

    private Player p0;
    private Player p1;
    private Game game;
    private Piece flag, attacker;
    
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Ozgur", 1);
        game = new Game(p0, p1);
        flag = new Flag(p0, game.getSquare(0, 3));
        attacker = new StepMover(p1, game.getSquare(1, 3), 5);
    }

    @Test
    public void testWinAgainst() {
        // Anything beats a flag
        assertEquals(CombatResult.WIN, attacker.resultWhenAttacking(flag));
    }

    @Test
    public void testLoseGame() {
        attacker.attack(game.getSquare(0, 3));

        // Flag is gone
        assertNull(flag.getSquare());

        // Attacker takes flag's place
        assertEquals(game.getSquare(0, 3), attacker.getSquare());

        // Flag is captured and its owner loses the game
        assertTrue(flag.getOwner().hasLost());
        assertEquals(p1, game.getWinner());
    }
    
    @Test
    public void testNoMoves() {
        assertEquals(0, flag.getLegalMoves().size());
    }

    @Test
    public void testNoAttacks() {
        assertEquals(0, flag.getLegalAttacks().size());
    }

}
