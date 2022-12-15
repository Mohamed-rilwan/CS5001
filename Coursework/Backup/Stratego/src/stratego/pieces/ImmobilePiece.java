package stratego.pieces;

import stratego.Player;
import stratego.Square;

public abstract class ImmobilePiece extends Piece{
        public ImmobilePiece(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }
}
