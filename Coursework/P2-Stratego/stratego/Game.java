package stratego;

/**
 * Game defines the base class for Stratego board
 * This intialises a 10*10 board and sets the water and land
 * squares in the board, along with the players playing it.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public class Game {
    /**
     * This constant value indicates the height of the stratego board.
     */
    public static final int HEIGHT = 10;
    /**
     * This constant value indicates the width of the stratego board.
     */
    public static final int WIDTH = 10;
    private static final int[] WATER_ROWS = {4, 5};
    private static final int[] WATER_COLS = {2, 3, 6, 7};
    private Square[][] board = new Square[HEIGHT][WIDTH];
    private Player p0;
    private Player p1;

    /**
     * <b>Constructor</b>: constructs a new game, with 2 new players,
     * new instances of board with HEIGHT*WIDTH dimensions.It is responsible for
     * creating a new game and initializing it.
     * 
     * @param p0 The first player in the game
     * @param p1 The second player in the game
     */
    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
        this.board = setBoard();
    }

    /**
     * Gets the player information based on the given player number.
     * 
     * @param playerNumber - A integer value that indicated the number used to fetch
     *                     player.
     * @return A Player object containing player name and
     *         player number.
     * @throws IllegalArgumentException when the given paramter is not a valid
     *                                  player number.
     * 
     */
    public Player getPlayer(int playerNumber) throws IllegalArgumentException {
        switch (playerNumber) {
            case 0:
                return this.p0;
            case 1:
                return this.p1;
            default:
                throw new IllegalArgumentException("Invalid player number entered");
        }
    }

    /**
     * Gets the player who won the game.
     * 
     * @return A Player object containing player name and
     *         player number.
     */
    public Player getWinner() {
        if (p0.hasLost() && !p1.hasLost()) {
            return p1;
        } else if (!p0.hasLost() && p1.hasLost()) {
            return p0;
        } else {
            return null;
        }
    }

    /**
     * Gets the square information indicating if its a water square
     * or the piece that holds if any.
     * 
     * @param row The row in which the square information needs to be fetched.
     * @param col The column in which the square information needs to be fetched.
     * @return The information about the square that is present in the
     *         given row and column number.
     * @throws IndexOutOfBoundsException when the given row or column number is
     *                                   not within the limits of board HEIGHT and
     *                                   WIDTH
     */
    public Square getSquare(int row, int col) throws IndexOutOfBoundsException {
        if (row > HEIGHT - 1 || col > WIDTH - 1) {
            throw new IndexOutOfBoundsException();
        }
        return board[row][col];
    }

    /**
     * Checks if the given square at the row and a column is a water square or not.
     * 
     * @param row - The row in which the square information needs to be fetched.
     * @param col - The column in which the square information needs to be fetched.
     * @return A true if the square is water square and false if not.
     */
    public boolean checkIfWaterSquare(int row, int col) {
        boolean isWater = false;
        for (int water_row : WATER_ROWS) {
            for (int water_col : WATER_COLS) {
                if (row == water_row && col == water_col) {
                    isWater = true;
                }
            }
        }
        return isWater;
    }

    /**
     * Sets the initial layout of the board in a two dimensional array of squares.
     * The land and the water squares are defined in this methods.
     * 
     * @return A teo dimensional array of squares named board which of size HEIGHT *
     *         WIDTH
     *         and value indicating if the square is water square or land squre.
     */
    public Square[][] setBoard() {
        Square[][] initialBoard = new Square[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (checkIfWaterSquare(row, col)) {
                    initialBoard[row][col] = new Square(this, row, col, true);
                } else {
                    initialBoard[row][col] = new Square(this, row, col, false);
                }
            }
        }
        return initialBoard;
    }
}
