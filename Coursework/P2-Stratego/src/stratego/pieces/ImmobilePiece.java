package stratego.pieces;

import stratego.Player;
import stratego.Square;

/**
 * ImmobilePiece is subclass of class Piece
 * that represents all Immovable Pieces.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */

public abstract class ImmobilePiece extends Piece {

    /**
     * <b>Constructor</b>: Constructs a immovable piece that extends features of
     * piece class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     * @param rank   The rank of the given piece
     */
    public ImmobilePiece(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    /**
     * <b>Constructor</b>: Constructs a immovable piece that extends features of
     * piece class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     */
    public ImmobilePiece(Player owner, Square square) {
        super(owner, square);
    }
}

