package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.List;

public class Bomb extends ImmobilePiece{
    public Bomb(Player owner, Square square) {
        super(owner, square, 0);
    }

    /**
     * @return
     */
    @Override
    public List<Square> getLegalMoves() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<Square> getLegalAttack() {
        return null;
    }
}
