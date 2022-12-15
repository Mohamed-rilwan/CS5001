package piece_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

public class SpyTest {

    private Player p0;
    private Player p1;
    private Game game;
    private Piece marshal, sergeant, spy;
    
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Ozgur", 1);
        game = new Game(p0, p1);
        marshal = new StepMover(p0, game.getSquare(1, 4), 10);
        sergeant = new StepMover(p0, game.getSquare(2, 5), 4);
        spy = new Spy(p1, game.getSquare(2, 4));
    }

    @Test
    public void testLose() {
        // Spy loses to sergeant (rank 4)
        assertEquals(CombatResult.LOSE, spy.resultWhenAttacking(sergeant));
    }

    @Test
    public void testWinAgainstMarshal() {
        // Spy loses to marshal, even though it is rank 10
        assertEquals(CombatResult.WIN, spy.resultWhenAttacking(marshal));
    }

    @Test
    public void testMarshalBeatsSpy() {
        // If marshal is the one attacking, it still beats the spy
        assertEquals(CombatResult.WIN, marshal.resultWhenAttacking(spy));
    }

}
