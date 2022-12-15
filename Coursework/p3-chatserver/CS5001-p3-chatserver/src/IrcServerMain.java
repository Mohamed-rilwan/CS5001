/**
 * This is the main class of IRC Chat server.
 * It accepts mulitple client connections and sends
 * the appropriate updates to each one in real time.
 *
 * @author Matriculation Id : 220032472
 * @version 1.0
 */
public class IrcServerMain {
    private static final int MIN_ALLOWED_PORT = 1024;
    private static final int MAX_ALLOWED_PORT = 65535;

    /**
     * The main method of the IRC Chat server that takes
     * port number and server name as part of the input arguments
     * of the format java IrcServerMain <servername> <port>.
     *
     * @param args - input arguments taking server name and port number
     */
    public static void main(String[] args) {
        try {
            if (args.length == 2) {
                int port = validatePort(args);
                Server server = new Server(args[0], port);
                server.startServer();
            } else {
                throw new IllegalArgumentException(Commands.SERVER_ILLEGAL_ARGUMENT_RESPONSE);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The entered port number of the IRC chat server
     * needs to be of a valid number within the range
     * 1024 to 65535 as the number below 1024 are reserved
     * for specific purpose.
     *
     * @param args - input arguments taking server name and port number
     * @return int - if the port number is valid, it is passed to the main method
     */
    public static int validatePort(String[] args) {
        try {
            int port = Integer.parseInt(args[1]);
            if (port < MIN_ALLOWED_PORT || port > MAX_ALLOWED_PORT) {
                throw new IllegalArgumentException(Commands.SERVER_ILLEGAL_ARGUMENT_RESPONSE);
            } else {
                return port;
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Commands.SERVER_ILLEGAL_ARGUMENT_RESPONSE);
        }
    }
}
