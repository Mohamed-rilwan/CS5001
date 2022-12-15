package piece_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

public class BombTest {

    private Player p0;
    private Player p1;
    private Game game;
    private Piece bomb, attacker;
    
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Ozgur", 1);
        game = new Game(p0, p1);
        bomb = new Bomb(p0, game.getSquare(0, 3));
        attacker = new StepMover(p1, game.getSquare(1, 3), 7);
    }

    @Test
    public void testDraw() {
        // Bomb should "draw" with everything, so both are destroyed.
        assertEquals(CombatResult.DRAW, attacker.resultWhenAttacking(bomb));
    }

    @Test
    public void testExplode() {
        attacker.attack(game.getSquare(0, 3));

        // Both pieces are now gone
        assertNull(bomb.getSquare());
        assertNull(attacker.getSquare());
    }
    
    @Test
    public void testNoMoves() {
        assertEquals(0, bomb.getLegalMoves().size());
    }

    @Test
    public void testNoAttacks() {
        assertEquals(0, bomb.getLegalAttacks().size());
    }

}
