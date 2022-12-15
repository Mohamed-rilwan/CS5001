package stratego.pieces;

import java.util.ArrayList;
import java.util.List;

import stratego.Player;
import stratego.Square;

/**
 * Flag is subclass of class ImmobilePiece
 * that represents a Flag piece (Immovable Pieces).
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class Flag extends ImmobilePiece {

    /**
     * <b>Constructor</b>: Constructs a Flag piece that extends features of
     * ImmobilePiece class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     */
    public Flag(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * <b>Accessor(selector)</b>: returns the possible legal moves allowed for the
     * flag piece.
     * 
     * @return empty array of squares as flag cannot be moved.
     */
    @Override
    public List<Square> getLegalMoves() {
        return new ArrayList<>();
    }

    /**
     * <b>Accessor(selector)</b>: returns the possible legal attacks allowed for the
     * flag piece.
     * 
     * @return empty array of squares as flag cannot attack.
     */
    @Override
    public List<Square> getLegalAttacks() {
        return new ArrayList<>();
    }

    /**
     * <b>Accessor(selector)</b>: sets the player to lose the game if flag
     * is captured.
     */
    @Override
    public void beCaptured() {
        this.getSquare().getPiece().getOwner().loseGame();
    }
}
