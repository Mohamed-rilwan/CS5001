package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

/**
 * Spy is subclass of class StepMover
 * that represents a spy piece (one step mover Pieces) whose ranks is not
 * defined.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class Spy extends StepMover {

    /**
     * <b>Constructor</b>: Constructs a Spy piece that extends features of StepMover
     * and contains no rank.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     */
    public Spy(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * This methods is used to get the result of attacks that is perfomed on the
     * adjacent legal squares allowed for the spy piece.
     *
     * @param targetPiece The resultant piece that needs to be attacked by the spy.
     * @return Win(CombatResult) if the target piece is a Marshal(Rank 10) or Flag,
     *         Lose(CombatResult) if
     *         another is piece is present in the target square.
     * @link #package.stratego#combatResult.
     */
    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {
        if (targetPiece.getRank() == 10 || targetPiece instanceof Flag && targetPiece.getOwner() != this.getOwner()) {
            targetPiece.getOwner().loseGame();
            return CombatResult.WIN;
        } else {
            return CombatResult.LOSE;
        }
    }
}
