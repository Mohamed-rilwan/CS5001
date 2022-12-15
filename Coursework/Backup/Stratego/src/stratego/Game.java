package stratego;

import java.util.Arrays;
import stratego.Player;
import stratego.Square;

public class Game {
    public static final int height = 10;
    public static final int width = 10;
    public final int[] water_rows = {4, 5};
    public final int[] water_cols = {2, 3, 6, 7};
    public final int[] land_rows = {0, 1, 2, 3, 6, 7, 8, 9};
    public final int[] land_cols = {0, 1, 4, 5, 8, 9};

    private Player p0;
    private Player p1;

    private Player winner = null;

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Game(Player p0,Player p1){
        this.p0 = p0;
        this.p1 = p1;
    }

    public Player getPlayer(int playerNumber) throws IllegalArgumentException{
        switch(playerNumber){
            case 0:
                return this.p0 ;
            case 1:
                return this.p1;
            default:
                throw new IllegalArgumentException("Invalid player number entered");
        }
    }

    public Player getWinner(){
        if(p0.hasLost() && !p1.hasLost()) { return p1;}
        else if (!p0.hasLost() && p1.hasLost()) { return p0;}
        else return null;
    }

    public Square getSquare(int row,int col) throws IndexOutOfBoundsException {
        if (row < 9 && col < 9) {
            for (int water_row = 0; water_row < this.water_rows.length; water_row++) {
                for (int water_col = 0; water_col< this.water_cols.length;water_col++) {
                    if (row == this.water_rows[water_row] && col == this.water_cols[water_col]) {
                        return new Square(this, row, col, true);
                    } else {
                        return new Square(this, row, col, false);
                    }
                }
            }
        }
        else{
            throw new IndexOutOfBoundsException();
        }
        return new Square(this, row, col, true);
    }
}
