package piece_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

public class MinerTest {

    private Player p0;
    private Player p1;
    private Game game;
    private Piece miner, bomb, sergeant;
    
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Ozgur", 1);
        game = new Game(p0, p1);
        miner = new Miner(p0, game.getSquare(0, 3));
        bomb = new Bomb(p1, game.getSquare(1, 3));
        sergeant = new StepMover(p1, game.getSquare(0, 2), 4);
    }

    @Test
    public void testLose() {
        // Miner can't beat a sergeant
        assertEquals(CombatResult.LOSE, miner.resultWhenAttacking(sergeant));
    }

    @Test
    public void testWinAgainstBomb() {
        // Miner beats a bomb
        assertEquals(CombatResult.WIN, miner.resultWhenAttacking(bomb));
    }

    @Test
    public void testAttackBomb() {
        miner.attack(game.getSquare(1, 3));

        // Bomb is safely defused and miner is safe
        assertNull(bomb.getSquare());
        assertEquals(game.getSquare(1, 3), miner.getSquare());
    }

    @Test
    public void testRank() {
        assertEquals(3, miner.getRank());
    }

}
