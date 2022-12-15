package stratego.pieces;

import java.util.List;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

/**
 * Piece is superclass of all the pieces on the Stratego board.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public abstract class Piece {
    private Player owner;
    private Square square;
    private int rank;

    /**
     * <b>Constructor</b>: Constructs a piece ot be placed in a square
     * class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     * @param rank   The rank that the piece holds.
     * 
     */
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
        this.square.placePiece(getPiece());
    }

    /**
     * <b>Constructor</b>: Constructs a piece ot be placed in a square
     * class.
     * 
     * @param owner  The player who owns the piece.
     * @param square The square in the board that the piece must reside.
     * 
     */
    public Piece(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
        this.square.placePiece(getPiece());
    }

    /**
     * <b>Accessor(selector)</b>: interface that defines the possible legal moves
     * allowed for the piece.
     * 
     * @return array of squares a that a piece can move and can be overridden on
     *         need.
     */
    public abstract List<Square> getLegalMoves();

    /**
     * <b>Accessor(selector)</b>: interface that defines the possible legal attacks
     * allowed for the piece.
     * 
     * @return array of squares a that a piece can attack and can be overridden on
     *         need.
     */

    public abstract List<Square> getLegalAttacks();

    /**
     * <b>Transformer</b>: moves the piece to given square.
     * 
     * @param toSquare the target square to pass into
     */
    public void move(Square toSquare) {
        square.removePiece();
        square = toSquare;
        toSquare.placePiece(this);
    }

    /**
     * This methods is used to perform the result of attacks on the
     * adjacent legal attack squares allowed for the piece.
     *
     * @param targetSquare The resultant sqaure that needs to be attacked.
     * @throws IllegalArgumentException when the result of attacking is not one of
     *                                  combatResult.
     * @link #package.stratego#combatResult.
     */
    public void attack(Square targetSquare) throws IllegalArgumentException {
        switch (resultWhenAttacking(targetSquare.getPiece())) {
            case WIN:
                winResultAction(targetSquare);
                break;
            case LOSE:
                loseResultAction();
                break;
            case DRAW:
                drawResultAction(targetSquare);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * This methods is used to get the result of attacks that is perfomed on the
     * adjacent legal squares allowed for the piece.
     *
     * @param targetPiece The resultant piece that needs to be attacked.
     * @return Win(CombatResult) if the target piece is lower rank,
     *         Lose(CombatResult) if the target piece is of higher rank.
     *         Draw(CombatResult) if the target is of same rank or a bomb
     * @link #package.stratego#combatResult.
     */
    public CombatResult resultWhenAttacking(Piece targetPiece) {
        if (targetPiece.rank > this.rank) {
            return CombatResult.LOSE;
        } else if (targetPiece.rank == this.rank || targetPiece instanceof Bomb) {
            return CombatResult.DRAW;
        } else {
            return CombatResult.WIN;
        }
    }

    /**
     * <b>Transformer</b>: sets a piece to a captured.
     */
    public void beCaptured() {
        this.getSquare().removePiece();
        setSquare(null);
    }

    /**
     * <b>Accessor(selector)</b>: Used to get information about the square.
     * 
     * @return information about the current square of the piece.
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * <b>Transformer</b>: sets a piece to a square.
     * 
     * @param square that needs to be set
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * <b>Accessor(selector)</b>: Used to get owner inforamtion of a piece.
     * 
     * @return information about the pieces's owner(player).
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * <b>Accessor(selector)</b>: Used to get inforamtion of a piece.
     * 
     * @return information about the pieces.
     */
    public Piece getPiece() {
        return this;
    }

    /**
     * <b>Accessor(selector)</b>: Used to get rank inforamtion of a piece.
     * 
     * @return information about the pieces's rank.
     */
    public int getRank() {
        return rank;
    }

    /**
     * <b>Transformer</b>: Used to perform actions of winning an attack.
     * 
     * @param targetSquare - the actions to be performed to target square after
     *                     winning.
     */
    public void winResultAction(Square targetSquare) {
        if (targetSquare.getPiece() instanceof Flag) {
            targetSquare.getPiece().getOwner().loseGame();
            targetSquare.getPiece().setSquare(null);
            targetSquare.removePiece();
            move(targetSquare);
        } else {
            targetSquare.getPiece().setSquare(null);
            targetSquare.removePiece();
            move(targetSquare);
        }
    }

    /**
     * <b>Transformer</b>: Used to perform actions of losing an attack.
     * 
     */
    public void loseResultAction() {
        this.getSquare().removePiece();
        this.setSquare(null);
    }

    /**
     * <b>Transformer</b>: Used to perform actions of drawing an attack.
     * 
     * @param targetSquare - the actions to be performed to target square after
     *                     making a draw.
     * 
     */
    public void drawResultAction(Square targetSquare) {
        this.getSquare().removePiece();
        this.setSquare(null);
        targetSquare.getPiece().setSquare(null);
    }
}
