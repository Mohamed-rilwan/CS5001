package stratego;

import stratego.pieces.Piece;

/**
 * Square defines the individual elements of Stratego board.
 * It is a 10*10 board (100 squares) and indicates the water and land
 * squares in the board.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class Square {
    private int row;
    private int col;
    private boolean isWater;
    private Game game;

    private Piece piece;

    /**
     * <b>Constructor</b>: Constructs a new square.
     * 
     * @param game    The current game being placed.
     * @param row     The row in which this square resides.
     * @param col     The column in which this square resides.
     * @param isWater indicates if a square is a water.
     */
    public Square(Game game, int row, int col, boolean isWater) {
        this.row = row;
        this.col = col;
        this.game = game;
        this.isWater = isWater;
    }

    /**
     * <b>Transformer</b>: places the given piece in athis square.
     * 
     * @param piece - The piece to be placed in the square.
     * @throws IllegalArgumentException if the given square is water
     *                                  or contains a peice already.
     */
    public void placePiece(Piece piece) throws IllegalArgumentException {
        if (this.getPiece() == null && !isWater) {
            this.piece = piece;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * <b>Transformer</b>: removes the piece from the square.
     */
    public void removePiece() {
        this.piece = null;
    }

    /**
     * <b>Accessor(selector)</b>: Used to get information about the game being
     * played.
     * 
     * @return information about the current game.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * <b>Accessor(selector)</b>: used to get information of the piece in a given
     * sqaure.
     * 
     * @return piece in the given square.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Gets the current row number of the square.
     * 
     * @return row number of the given square.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the current column number of the square.
     * 
     * @return column number of the given square.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Checks if the given sqaure has water in it or not.
     * 
     * @return true if the square contains water.
     */
    public boolean isWaterSquare() {
        return this.isWater;
    }

    /**
     * Checks if the given sqaure can be entered by other pieces.
     * 
     * @return true if the square does not contains water or any pieces.
     */
    public boolean canBeEntered() {
        return !this.isWater && this.piece == null;
    }
}
