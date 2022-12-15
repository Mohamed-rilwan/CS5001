public class IrcServerMain {
    public static String serverName;
    public static int port;

    public static void main(String[] args) {
        try {
            if (args.length < 1
                    || ((args.length >= 1) && Integer.parseInt(args[1]) < 1024 || Integer.parseInt(args[1]) > 65535)) {
                throw new IllegalArgumentException(" Usage: java IrcServerMain <server_name> <port>");
            } else {
                serverName = args[0];
                port = Integer.parseInt(args[1]);
                Server server = new Server();
            }

        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}