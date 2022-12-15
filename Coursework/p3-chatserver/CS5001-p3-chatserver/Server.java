import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(IrcServerMain.port);
            System.out.println("Server started at port " + IrcServerMain.port);

            while (true) {
                // Wait for a client to connect
                Socket connection = serverSocket.accept();

                // Create threads for each conneciton
                ClientThread clientThread = new ClientThread(connection);
                new Thread(clientThread).start();

                System.out.println("Server got new connection request from " + connection.getInetAddress());

            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
