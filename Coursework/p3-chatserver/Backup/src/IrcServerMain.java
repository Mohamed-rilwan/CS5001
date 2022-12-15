
public class IrcServerMain {
    private static final int MIN_ALLOWED_PORT = 1024;
    private static final int MAX_ALLOWED_PORT = 65535;


    public static void main(String[] args) {
        try {
            switch (args.length) {
                case 2: {
                    validatePort(args);
                    break;
                }
                default: {
                    throw new IllegalArgumentException(Commands.SERVER_ILLEGAL_ARGUMENT_RESPONSE);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void validatePort(String[] args){
        try {
            int port = Integer.parseInt(args[1]);
            if (port < MIN_ALLOWED_PORT || port > MAX_ALLOWED_PORT) {
                throw new IllegalArgumentException(Commands.SERVER_ILLEGAL_ARGUMENT_RESPONSE);
            }
            Server server = new Server(args[0], Integer.parseInt(args[1]));
            server.startServer();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Commands.SERVER_ILLEGAL_ARGUMENT_RESPONSE);
        }
    }
}
