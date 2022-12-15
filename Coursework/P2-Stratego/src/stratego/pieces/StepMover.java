package stratego.pieces;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.NullCipher;

import stratego.Game;
import stratego.Player;
import stratego.Square;

/**
 * StepMover is subclass of class Piece
 * that represents all single step pieces.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class StepMover extends Piece {

    /**
     * <b>Constructor</b>: Constructs a StepMover piece that extends features of
     * Piece class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     * @param rank   The rank of the stepmover piece.
     */
    public StepMover(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    /**
     * <b>Constructor</b>: Constructs a StepMover piece that extends features of
     * Piece class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     */
    public StepMover(Player owner, Square square) {
        super(owner, square);
    }

    private int currentRow = getRow();
    private int currentCol = getCol();

     /**
     * Gets the current row number of the piece.
     * 
     * @return row number of the given stepmover.
     */
    public int getRow() {
        return this.getSquare().getRow();
    }

     /**
     * Gets the current column number of the piece.
     * 
     * @return column number of the given stepmover.
     */
    public int getCol() {
        return this.getSquare().getCol();
    }

    /**
     * <b>Accessor(selector)</b>: returns the possible legal moves allowed for the
     * step mover piece, by checking if the resultant square is not a water square
     * and
     * that it does not already contain a piece.
     *
     * @return array of squares that a step mover can be moved into.
     */
    @Override
    public List<Square> getLegalMoves() {
        List<Square> allowedSquares = new ArrayList<>();

        Square top = getAdjacentPeice(Direction.TOP);
        Square bottom = getAdjacentPeice(Direction.BOTTOM);
        Square right = getAdjacentPeice(Direction.RIGHT);
        Square left = getAdjacentPeice(Direction.LEFT);

        if (top != null && !top.isWaterSquare() && top.getPiece() == null) {
            allowedSquares.add(top);
        }

        if (bottom != null && !bottom.isWaterSquare()
                && (bottom.getPiece() == null)) {
            allowedSquares.add(bottom);
        }

        if (right != null && !right.isWaterSquare()
                && right.getPiece() == null) {
            allowedSquares.add(this.getSquare().getGame().getSquare(currentRow, currentCol + 1));
        }

        if (left != null && !left.isWaterSquare()
                && left.getPiece() == null) {
            allowedSquares.add(left);
        }

        return allowedSquares;
    }

    /**
     * <b>Accessor(selector)</b>: returns the possible legal attacks allowed for the
     * step mover piece, by checking if the resultant square is not a water square
     * and
     * that it contains a piece and its not of the same player.
     *
     * @return array of squares that a step mover can be attack.
     */
    @Override
    public List<Square> getLegalAttacks() {
        List<Square> allowedAttacks = new ArrayList<>();

        Square top = getAdjacentPeice(Direction.TOP);
        Square bottom = getAdjacentPeice(Direction.BOTTOM);
        Square right = getAdjacentPeice(Direction.RIGHT);
        Square left = getAdjacentPeice(Direction.LEFT);

        if (top != null && !top.isWaterSquare()
                && top.getPiece() != null
                && top.getPiece().getOwner() != getOwner()) {
            allowedAttacks.add(top);
        }

        if (bottom != null && !bottom.isWaterSquare()
                && bottom.getPiece() != null
                && bottom.getPiece()
                        .getOwner() != getOwner()) {
            allowedAttacks.add(bottom);
        }

        if (right != null && !right.isWaterSquare()
                && right.getPiece() != null
                && right.getPiece()
                        .getOwner() != getOwner()) {
            allowedAttacks.add(right);
        }

        if (left != null && !left.isWaterSquare()
                && left.getPiece() != null
                && left.getPiece()
                        .getOwner() != getOwner()) {
            allowedAttacks.add(left);
        }

        return allowedAttacks;
    }

    /**
     * <b>Accessor(selector)</b>: returns the square information in the adjacent
     * square that is not diagonal.
     *
     * @param direction in which the square information needs to be fetched.
     * @return square information in the given direction.
     */
    public Square getAdjacentPeice(Direction direction) {
        switch (direction) {
            case TOP:
                return currentRow < Game.HEIGHT - 1 ? this.getSquare().getGame().getSquare(currentRow + 1, currentCol)
                        : null;

            case BOTTOM:
                return currentRow > 0 ? this.getSquare().getGame().getSquare(currentRow - 1, currentCol) : null;

            case RIGHT:
                return currentCol < Game.WIDTH - 1 ? this.getSquare().getGame().getSquare(currentRow, currentCol + 1)
                        : null;

            case LEFT:
                return currentCol > 0 ? this.getSquare().getGame().getSquare(currentRow, currentCol - 1) : null;

            default:
                return null;

        }
    }
}
