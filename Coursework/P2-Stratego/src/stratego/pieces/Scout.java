package stratego.pieces;

import java.util.ArrayList;
import java.util.List;

import stratego.Game;
import stratego.Player;
import stratego.Square;

/**
 * Scout is subclass of class Piece
 * that represents a scout piece (multi step mover Pieces) whose ranks is 2.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class Scout extends Piece {

    /**
     * <b>Constructor</b>: Constructs a Scout piece that extends features of Piece
     * class with a rank of 2.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     */
    public Scout(Player owner, Square square) {
        super(owner, square, 2);
    }

    private int currentRow = this.getSquare().getRow();
    private int currentCol = this.getSquare().getCol();

    /**
     * <b>Accessor(selector)</b>: returns the possible legal moves allowed for the
     * scout piece.
     * 
     * @return array of squares that a scout can move into.
     */
    @Override
    public List<Square> getLegalMoves() {
        List<Square> allowedSquares = new ArrayList<>();

        if (currentRow < Game.HEIGHT) {
            for (int row = currentRow + 1; row < Game.HEIGHT; row++) {
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() != null
                        || this.getSquare().getGame().checkIfWaterSquare(row, currentCol)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() == null) {
                    allowedSquares.add(this.getSquare().getGame().getSquare(row, currentCol));
                }
            }
        }

        if (currentRow > 0) {
            for (int row = currentRow - 1; row > 0; row--) {
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() != null
                        || this.getSquare().getGame().checkIfWaterSquare(row, currentCol)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() == null) {

                    allowedSquares.add(this.getSquare().getGame().getSquare(row, currentCol));
                }
            }
        }

        if (currentCol < Game.WIDTH) {
            for (int col = currentCol + 1; col < Game.WIDTH; col++) {
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() != null
                        || this.getSquare().getGame().checkIfWaterSquare(currentRow, col)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() == null) {
                    allowedSquares.add(this.getSquare().getGame().getSquare(currentRow, col));
                }
            }
        }

        if (currentCol > 0) {
            for (int col = currentCol - 1; col > 0; col--) {
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() != null
                        || this.getSquare().getGame().checkIfWaterSquare(currentRow, col)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() == null) {
                    allowedSquares.add(this.getSquare().getGame().getSquare(currentRow, col));
                }
            }
        }
        return allowedSquares;
    }

    /**
     * <b>Accessor(selector)</b>: returns the possible legal attacks allowed for the
     * scout piece.
     * 
     * @return array of squares that a scout can attack.
     */
    @Override
    public List<Square> getLegalAttacks() {
        List<Square> allowedAttacks = new ArrayList<>();
        int currentRow = this.getSquare().getRow();
        int currentCol = this.getSquare().getCol();

        if (currentRow < Game.HEIGHT) {
            for (int row = currentRow + 1; row < Game.HEIGHT; row++) {
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() == null
                        || this.getSquare().getGame().checkIfWaterSquare(row, currentCol)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() != null) {
                    allowedAttacks.add(this.getSquare().getGame().getSquare(row, currentCol));
                }
            }
        }

        if (currentRow > 0) {
            for (int row = currentRow - 1; row > 0; row--) {
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() == null
                        || this.getSquare().getGame().checkIfWaterSquare(row, currentCol)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(row, currentCol).getPiece() != null) {

                    allowedAttacks.add(this.getSquare().getGame().getSquare(row, currentCol));
                }
            }
        }

        if (currentCol < Game.WIDTH) {
            for (int col = currentCol + 1; col < Game.WIDTH; col++) {
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() == null
                        || this.getSquare().getGame().checkIfWaterSquare(currentRow, col)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() != null) {
                    allowedAttacks.add(this.getSquare().getGame().getSquare(currentRow, col));
                }
            }
        }

        if (currentCol > 0) {
            for (int col = currentCol - 1; col > 0; col--) {
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() == null
                        || this.getSquare().getGame().checkIfWaterSquare(currentRow, col)) {
                    break;
                }
                if (this.getSquare().getGame().getSquare(currentRow, col).getPiece() != null) {
                    allowedAttacks.add(this.getSquare().getGame().getSquare(currentRow, col));
                }
            }
        }
        return allowedAttacks;
    }
}
