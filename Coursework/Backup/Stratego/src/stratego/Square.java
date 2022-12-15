package stratego;

import stratego.pieces.Piece;

import java.util.Arrays;

public class Square {
    private int row;
    private int col;
    private Game game;
    private boolean isWater;

    private Piece piece = null;

    public Square(Game game, int row, int col, boolean isWater) {
        this.row = row;
        this.col = col;
        this.game = game;
        this.isWater = isWater;
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    public Game getGame() {
        return this.game;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean canBeEntered() {
       return  !this.isWater;          
    }

}
