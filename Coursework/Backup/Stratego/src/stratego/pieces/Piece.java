package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

import java.util.List;

public abstract class Piece {
    private Player owner;
    private Square square;
    private int rank;

    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
    }

    public Piece getPiece(){
        return this;
    }

    public abstract List<Square> getLegalMoves();
    public abstract List<Square> getLegalAttack();

    public void move(Square toSquare){
        this.square.placePiece(this);
    }

    public void attack(Square targetSquare){

    }

    public CombatResult resultWhenAttacking(Piece targetPiece){
        return CombatResult.WIN;
    }

    public void beCaptured(){

    }

    public Square getSquare(){
        return square;
    }

    public Player getOwner() {
        return owner;
    }

    public int getRank() {
        return rank;
    }
}
