import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<ConnectionHandler> clientConnections;
    private ServerSocket serverSocket;
    private boolean done;
    private String serverName;
    private int port;

    public Server(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        clientConnections = new ArrayList<>();
        done = false;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Starting server with name " + serverName );
            while (!done) {
                Socket connection = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(connection);
                clientConnections.add(handler);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : clientConnections) {
            ch.sendMessage(message);
        }
    }

    public void shutDown() throws IOException {
        try {
            done = true;
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
            for (ConnectionHandler ch : clientConnections) {
                ch.cleanup();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    class ConnectionHandler implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private String nickname;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer.println("Please enter a nickname");

                nickname = reader.readLine();
                if (nickname.isBlank()) {
                    writer.println("Invalid nickname");
                    run();

                    /* You can clean up the code to perform close the connection */
                    // writer.println("Invalid nickname");
                    // cleanup();
                } else {
                    ClientMessageHandler messageHandler = new ClientMessageHandler();
                    broadcast(nickname + " joined the chat");
                    String inputMessage;
                    while ((inputMessage = reader.readLine()) != null) {
                        messageHandler.commandActions(inputMessage,reader,writer);
                    }

                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }

        public void sendMessage(String message) {
            writer.println(message);
        }

        public void cleanup() throws IOException {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (socket !=null && !socket.isClosed()) {
                    socket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}