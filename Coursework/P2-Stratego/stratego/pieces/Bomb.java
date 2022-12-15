package stratego.pieces;

import java.util.ArrayList;
import java.util.List;

import stratego.Player;
import stratego.Square;

/**
 * Bomb is subclass of class ImmobilePiece
 * that represents a Bombs piece (Immovable Pieces).
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */

public class Bomb extends ImmobilePiece {

    /**
     * <b>Constructor</b>: Constructs a Bomb piece that extends features of ImmobilePiece class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     */
    public Bomb(Player owner, Square square) {
        super(owner, square);
    }

    /**
    * <b>Accessor(selector)</b>: returns the possible legal moves allowed for the bomb piece.
    * @return empty array of squares as bomb cannot be moved.
    */
    @Override
    public List<Square> getLegalMoves() {
        return new ArrayList<>();
    }

    /**
    * <b>Accessor(selector)</b>: returns the possible legal attacks allowed for the bomb piece.
    * @return empty array of squares as bomb cannot attack.
    */
    @Override
    public List<Square> getLegalAttacks() {
        return new ArrayList<>();
    }
}
