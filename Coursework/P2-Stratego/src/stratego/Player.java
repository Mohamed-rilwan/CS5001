package stratego;

/**
 * Player class defines the individual player of Stratego game.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class Player {

    private String name;
    private boolean isLost = false;
    private int playerNumber;

    /**
     * <b>Constructor</b>: Constructs a new player.
     * 
     * @param name         The name of the player.
     * @param playerNumber The number associated to the player.
     */
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    /**
     * <b>Accessor(selector)</b>: used to get name of the player.
     * 
     * @return string name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <b>Accessor(selector)</b>: used to get number of the player.
     * 
     * @return int number of the player.
     */
    public int getPlayerNumber() {
        return this.playerNumber;
    }

    /**
     * <b>Transformer</b>: sets the player islost status to true.
     */
    public void loseGame() {
        this.isLost = true;
    }

    /**
     * <b>Accessor(selector)</b>: used to get winning status of the player.
     * 
     * @return true if the player has won the game.
     */
    public boolean hasLost() {
        return isLost;
    }

}
