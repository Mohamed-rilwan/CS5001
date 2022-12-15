import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> clientConnections;
    private ServerSocket serverSocket;
    private boolean done;
    private ExecutorService threadpool;

    public Server() {
        clientConnections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(12321);
            threadpool = Executors.newCachedThreadPool();
            System.out.println("Starting socket");
            while (!done) {
                Socket connection = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(connection);
                clientConnections.add(handler);
                //Change it to run the threasservice instead of executor
                threadpool.execute(handler);
            }

        } catch (IOException e) {
            // TODO: handle
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : clientConnections) {
            ch.sendMessage(message);
        }
    }

    public void shutDown() throws IOException{
        try {
            done = true;
            if(!serverSocket.isClosed()){
                serverSocket.close();
            }
            for(ConnectionHandler ch: clientConnections){
                ch.shutDown();
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
                } else {
                    System.out.println(nickname + " connected");
                    broadcast(nickname + " joined the chat");
                    String inputMessage;
                    while ((inputMessage = reader.readLine()) != null) {
                        if (inputMessage.startsWith("/nick ")) {
                            String[] commandSplit = inputMessage.split(" ", 2);
                            if (commandSplit.length == 2) {
                                broadcast(nickname + " renamed themselves to " + commandSplit[1]);
                                nickname = commandSplit[1];
                                writer.println("Successfully renamed to " + nickname);
                            } else {
                                writer.println("Invalid command");
                            }
                        } else if (inputMessage.startsWith("/quit")) {
                            writer.println("quit command reciewved");
                            broadcast(nickname + " left the chat");
                            shutDown();

                        } else {
                            broadcast(nickname + " : " + inputMessage);
                        }
                    }
                }
            } catch (IOException ioe) {
                // TODO: handle exception
            }

        }

        public void sendMessage(String message) {
            writer.println(message);
        }

        public void shutDown() throws IOException{
            try {
                reader.close();
                writer.close();
                if(!socket.isClosed()){
                    socket.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: handle exception
            }
           
        }
    }

    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }
}