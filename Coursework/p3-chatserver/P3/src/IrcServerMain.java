public class IrcServerMain {
    public static void main(String[] args) {
        try {
            if (args.length < 1 ) {
                throw new IllegalArgumentException("Usage: ClientMain <hostname>");
            } else {
                Server server = new Server(args[0],Integer.parseInt(args[1]));
                server.run();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}