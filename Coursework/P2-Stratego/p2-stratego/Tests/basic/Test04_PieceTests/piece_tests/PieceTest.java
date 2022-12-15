package piece_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stratego.*;
import stratego.pieces.*;

public class PieceTest {
    
    private Square land, land2, land3, land4, water, water2;
    private Piece sergeant, general, general2;
    private Player michael, oz;

    @Before
    public void setup() {
        land = new Square(null, 1, 2, false);  // null game, but should still work
        land2 = new Square(null, 1, 3, false);
        land3 = new Square(null, 4, 3, false);
        land4 = new Square(null, 4, 9, false);
        water = new Square(null, 3, 1, true);
        water2 = new Square(null, 4, 1, true);

        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);

        sergeant = new StepMover(michael, land, 4);  // weaker
        general = new StepMover(oz, land3, 9);  // stronger
        general2 = new StepMover(michael, land4, 9);
    }

    @Test
    public void testGetSquare() {
        assertEquals(land, sergeant.getSquare());
    }

    @Test
    public void testGetOwner() {
        assertEquals(michael, sergeant.getOwner());
    }

    @Test
    public void testGetRank() {
        assertEquals(4, sergeant.getRank());
    }

    @Test
    public void testBeCaptured() {
        sergeant.beCaptured();
        assertNull(sergeant.getSquare());
        assertNull(land.getPiece());
    }

    @Test
    public void testMove() {
        sergeant.move(land2);

        assertEquals(land2, sergeant.getSquare());
        assertEquals(sergeant, land2.getPiece());

        assertNotEquals(land, sergeant.getSquare());
        assertNull(land.getPiece());
    }

    @Test
    public void testResultWhenAttackingWin() {
        assertEquals(CombatResult.WIN, general.resultWhenAttacking(sergeant));
    }

    @Test
    public void testResultWhenAttackingLose() {
        assertEquals(CombatResult.LOSE, sergeant.resultWhenAttacking(general));
    }

    @Test
    public void testResultWhenAttackingDraw() {
        assertEquals(CombatResult.DRAW, general.resultWhenAttacking(general2));
    }

    @Test
    public void testUnsuccessfulAttack() {
        assertEquals(land, sergeant.getSquare());
        assertEquals(land3, general.getSquare());

        // sergeant attacks general (legality not checked)
        sergeant.attack(land3);  

        // the sergeant loses
        assertNull(sergeant.getSquare());
        assertNull(land.getPiece());

        // the general wasn't hurt
        assertEquals(land3, general.getSquare());
        assertEquals(general, land3.getPiece());
    }

    @Test
    public void testSuccessfulAttack() {
        assertEquals(land3, general.getSquare());
        assertEquals(land, sergeant.getSquare());

        // general attacks sergeant (legality not checked)
        general.attack(land);  

        // the general defeats the sergeant
        assertNull(sergeant.getSquare());

        // the general has moved to the sergeant's square
        assertNull(land3.getPiece());
        assertEquals(land, general.getSquare());
        assertEquals(general, land.getPiece());
    }

}
