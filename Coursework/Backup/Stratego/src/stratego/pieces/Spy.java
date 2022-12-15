package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

public class Spy extends StepMover{
    public Spy(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece){
        //checking if the attacking piece is marshal
        if(targetPiece.getRank() == 10){
            return CombatResult.WIN;
        }

        //Implement game winning strategy when spy attacks a flag
//        else if(targetPiece instanceof flag){
//
//            return CombatResult.Win'
//        }
        else return CombatResult.LOSE;
    }
}
