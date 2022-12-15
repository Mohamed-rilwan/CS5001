public interface Commands
{
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
    String PART = "PART";
    String NAMES = "NAMES";
    String LIST = "LIST";
    String PRIVMSG = "PRIVMSG";
    String TIME = "TIME";
    String INFO = "INFO";
    String PING = "PING";

    String SERVER_ILLEGAL_ARGUMENT_RESPONSE = "Usage: java IrcServerMain <server_name> <port>";
    String REGISTER_FIRST_ERROR =  " 400 * :You need to register first";
    String INVALID_CHANNEL_ERROR = " 400 * :Invalid channel name";
}
