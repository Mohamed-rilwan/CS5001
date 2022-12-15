package stratego.pieces;

import java.util.ArrayList;
import java.util.List;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

/**
 * Miner is subclass of class Piece
 * that represents a miner piece in the board with rank 3.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class Miner extends Piece {

    /**
     * <b>Constructor</b>: Constructs a Miner piece that extends features of Piece
     * class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     */
    public Miner(Player owner, Square square) {
        super(owner, square, 3);
    }

    /**
     * <b>Accessor(selector)</b>: returns the possible legal moves allowed for the
     * bomb piece.
     * 
     * @return array of squares a miner can move and can be extended on need.
     */
    @Override
    public List<Square> getLegalMoves() {
        return new ArrayList<>();
    }

    /**
     * <b>Accessor(selector)</b>: returns the possible legal attacks allowed for the
     * miner piece.
     * 
     * @return array of squares a miner can attack and can be extended on need.
     */
    @Override
    public List<Square> getLegalAttacks() {
        return new ArrayList<>();
    }

    /**
     * This methods is used to get the result of attacks that is perfomed on the
     * adjacent legal squares allowed for the miner piece.
     *
     * @param targetPiece The resultant piece that needs to be attacked by the
     *                    miner.
     * @return Win(CombatResult) if the target piece is a bomb or of lower rank,
     *         Lose(CombatResult) if the target piece is of higher rank.
     * @link #package.stratego#combatResult.
     */
    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {
        if (targetPiece instanceof Bomb || targetPiece.getRank() < this.getRank()) {
            return CombatResult.WIN;
        } else {
            return CombatResult.LOSE;
        }
    }
}
