package stratego;

import stratego.pieces.Piece;

public class Player {
    
    private String name;
    private boolean isLost = false;
    private int playerNumber;
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return this.name;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public void loseGame(){
        this.isLost = true;
    }

    public boolean hasLost(){
       return isLost;
    }


}
