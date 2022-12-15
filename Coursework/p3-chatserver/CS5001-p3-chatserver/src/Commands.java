/**
 * This is the command class that stores the names of all the commands
 * and other constants needed by the IRC chat server.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
public final class Commands {

    private Commands() {

    }
    /**
     * Identifies a Nick name command.
     * This message is sent by the client in order to
     * declare what nickname the user wants to be known by.
     */
    public static final String NICK = "NICK";

    /**
     * Identifies a user command to register a user.
     * This command allows a client to specify username and realname
     */
    public static final String USER = "USER";

    /**
     * Identifies a quit command
     * Allow user to end their chat connection to server.
     */
    public static final String QUIT = "QUIT";

    /**
     * Identifies as join command
     * Allow user to join a chat channel.
     */
    public static final String JOIN = "JOIN";

    /**
     * Identifies as part command
     * Allow user to leave a channel.
     */
    public static final String PART = "PART";

    /**
     * This command is sent by a registered client
     * to request the nicknames of all users in a given channel.
     */
    public static final String NAMES = "NAMES";

    /**
     * This command is sent by a registered client
     * to request the names of all channels on server.
     */
    public static final String LIST = "LIST";

    /**
     * This command is sent by a registered client
     * to send private message to other connnected clients.
     */
    public static final String PRIVMSG = "PRIVMSG";

    /**
     * This command is sent to request current date and time.
     */
    public static final String TIME = "TIME";

    /**
     * This command is sent to request basic information about the server.
     */
    public static final String INFO = "INFO";

    /**
     * This command is sent to test if the client connnection
     * is still active with the server.
     */
    public static final String PING = "PING";

    /**
     * This is the response to send when the input argument
     * to start the server is not of the desired format.
     */
    public static final String SERVER_ILLEGAL_ARGUMENT_RESPONSE = "Usage: java IrcServerMain <server_name> <port>";

    /**
     * This is the response to send when an unregistered user
     * performs actions that they are not allowed to do
     * and ask them to register before passing those commands.
     */
    public static final String REGISTER_FIRST_ERROR = " 400 * :You need to register first";

    /**
     * This is the response to send when an command
     * to be performed on a channel is passed, but the channel doesn't exists.
     */
    public static final String INVALID_CHANNEL_ERROR = " 400 * :Invalid channel name";
}
