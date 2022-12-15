import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class ChatServer {

    String[] messagesToSend = { "Hello new connection", "How are you" };

    public void serverMethod() {
        try {

            try (ServerSocket serverSocket = new ServerSocket(Constants.DEFAULTPORT)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Accepting client connections on port " + Constants.DEFAULTPORT);
                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
                    // sample message being sent to client
                    String message = messagesToSend[1];
                    writer.println(message);
                    writer.close();
                }
            }

            // This accepts some request from client and then retuens a
            // socket on a anonymous port ot the client

        }

        catch (IOException ioe) {
            System.out.println("IO Exception thrown");
            ioe.printStackTrace();
            System.exit(0);
            // TODO: handle exception
        }
    }

}