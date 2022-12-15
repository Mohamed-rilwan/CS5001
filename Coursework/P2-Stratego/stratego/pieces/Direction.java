package stratego.pieces;

/**
 * Direction adjacent to sqaures and not diagonal.
 */
public enum Direction {
    /**
     * Indicates a direction above a piece in a 2D board starting from top to down
     * aproach.
     */
    TOP,
    /**
     * Indicates a direction bellow a piece in a 2D board starting from top to down
     * aproach.
     */
    BOTTOM,
    /**
     * Indicates a direction right of a piece in a 2D board starting from right to
     * left aproach.
     */
    RIGHT,
    /**
     * Indicates a direction left of a piece in a 2D board starting from right to
     * left aproach.
     */
    LEFT
}
