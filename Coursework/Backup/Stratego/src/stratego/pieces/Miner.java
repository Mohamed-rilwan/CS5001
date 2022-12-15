package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

import java.util.List;

public class Miner extends Piece{
    public Miner(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    @Override
    public List<Square> getLegalMoves() {
        return null;
    }

    @Override
    public List<Square> getLegalAttack() {
        return null;
    }
    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece){
        //checking if the attacking piece is bomb
        if(targetPiece instanceof Bomb){
            return CombatResult.WIN;
        }
        else return CombatResult.LOSE;
    }
}
