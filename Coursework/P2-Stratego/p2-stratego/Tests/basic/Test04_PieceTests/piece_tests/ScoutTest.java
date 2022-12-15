package piece_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

import java.util.List;

public class ScoutTest {

    private Player p0;
    private Player p1;
    private Game game;
    private Piece marshal, sergeant, flag, scout;
    
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Ozgur", 1);
        game = new Game(p0, p1);
        marshal = new StepMover(p0, game.getSquare(1, 4), 10);
        sergeant = new StepMover(p0, game.getSquare(0, 2), 4);
        flag = new Flag(p0, game.getSquare(2, 5));
        scout = new Scout(p1, game.getSquare(5, 4));
    }

    @Test
    public void testLose() {
        // Scout's rank is 2, which loses to nearly everything
        assertEquals(CombatResult.LOSE, scout.resultWhenAttacking(sergeant));
        assertEquals(CombatResult.LOSE, scout.resultWhenAttacking(marshal));
    }

    @Test
    public void testWin() {
        assertEquals(CombatResult.WIN, scout.resultWhenAttacking(flag));
    }
    
    @Test
    public void testRank() {
        assertEquals(2, scout.getRank());
    }

    @Test
    public void testGetLegalMoves() {
        // Moves like a rook
        List<Square> moves = scout.getLegalMoves();
        assertEquals(8, moves.size());
        assertTrue(moves.contains(game.getSquare(2, 4)));  // can move next to marshal
        assertFalse(moves.contains(game.getSquare(1, 4)));  // cannot move onto marshal
        assertFalse(moves.contains(game.getSquare(0, 4)));  // cannot jump over marshal
        assertTrue(moves.contains(game.getSquare(5, 5)));  // can move right onto land
        assertFalse(moves.contains(game.getSquare(5, 3)));  // cannot move left onto water
        assertFalse(moves.contains(game.getSquare(6, 5)));  // cannot move diagonally
    }

    @Test
    public void testGetLegalAttacks() {
        // Not next to anything, so no attacks
        // It can attack the sergeant next to it
        List<Square> attacks = scout.getLegalAttacks();
        assertEquals(0, attacks.size());
    }

    @Test
    public void testAttackMarshal() {
        scout.move(game.getSquare(2, 4));  // move next to marshal

        List<Square> attacks = scout.getLegalAttacks();
        assertTrue(attacks.contains(marshal.getSquare()));
        scout.attack(marshal.getSquare());

        // scout is killed
        assertNull(scout.getSquare());
        assertNotNull(marshal.getSquare());
    }        

    @Test
    public void testAttackFlag() {
        scout.move(game.getSquare(2, 4));  // move next to flag

        List<Square> attacks = scout.getLegalAttacks();
        assertTrue(attacks.contains(flag.getSquare()));
        scout.attack(flag.getSquare());

        // scout captures the flag
        assertEquals(game.getSquare(2, 5), scout.getSquare());
        assertEquals(p1, game.getWinner());
    }
}
