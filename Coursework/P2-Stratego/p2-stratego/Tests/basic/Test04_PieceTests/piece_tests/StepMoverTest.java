package piece_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

import java.util.List;

public class StepMoverTest {

    private Player p0;
    private Player p1;
    private Game game;
    private Piece marshal, sergeant, captain;
    
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Ozgur", 1);
        game = new Game(p0, p1);
        marshal = new StepMover(p0, game.getSquare(0, 3), 10);
        sergeant = new StepMover(p1, game.getSquare(0, 2), 4);
        captain = new StepMover(p1, game.getSquare(1, 2), 6);
    }

    @Test
    public void testWin() {
        // Marshal (10) beats Sergeant (4)
        assertEquals(CombatResult.WIN, marshal.resultWhenAttacking(sergeant));
    }

    @Test
    public void testRank() {
        assertEquals(10, marshal.getRank());
    }

    @Test
    public void testGetLegalMoves() {
        // From this position it can only make two moves - down and right
        List<Square> moves = marshal.getLegalMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains(game.getSquare(0, 4)));
        assertTrue(moves.contains(game.getSquare(1, 3)));
        assertFalse(moves.contains(game.getSquare(0, 2)));  // contains sergeant
    }

    @Test
    public void testGetLegalAttacks() {
        // It can attack the sergeant next to it
        List<Square> attacks = marshal.getLegalAttacks();
        assertEquals(1, attacks.size());
        assertTrue(attacks.contains(game.getSquare(0, 2)));  // contains sergeant
        assertFalse(attacks.contains(game.getSquare(1, 2)));  // captain is diagonal
    }

}
