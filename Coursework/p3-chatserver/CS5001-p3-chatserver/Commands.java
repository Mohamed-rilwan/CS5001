


/**
 * Constants used with the client commands.
 *

 * @since 1.2
 */
public interface Commands
{
    /**
     * Identifies a "viewport" or display area, within which
     * scrolled contents are visible.
     */
    String NICK = "NICK";
    /** Identifies a vertical scrollbar. */
    String USER = "USER";
    /** Identifies a horizontal scrollbar. */
    String QUIT = "QUIT";
    /**
     * Identifies the area along the left side of the viewport between the
     * upper left corner and the lower left corner.
     */
    String JOIN = "JOIN";
    /**
     * Identifies the area at the top the viewport between the
     * upper left corner and the upper right corner.
     */
    String PART = "PART";
    /** Identifies the lower left corner of the viewport. */
    String NAMES = "NAMES";
    /** Identifies the lower right corner of the viewport. */
    String LIST = "LIST";
    /** Identifies the upper left corner of the viewport. */
    String PRIVMSG = "PRIVMSG";
    /** Identifies the upper right corner of the viewport. */
    String TIME = "TIME";

    /** Identifies the lower leading edge corner of the viewport. The leading edge
     * is determined relative to the Scroll Pane's ComponentOrientation property.
     */
    String INFO = "INFO";
    /** Identifies the lower trailing edge corner of the viewport. The trailing edge
     * is determined relative to the Scroll Pane's ComponentOrientation property.
     */
    String PING = "PING";
}
