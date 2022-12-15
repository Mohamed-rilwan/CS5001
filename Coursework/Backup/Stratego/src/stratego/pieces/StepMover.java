package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.List;

public class StepMover extends Piece{

    public StepMover(Player owner, Square square, int rank) {
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
}
