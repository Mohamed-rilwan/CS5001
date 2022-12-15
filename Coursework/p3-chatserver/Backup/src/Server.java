import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Server {

    private ArrayList<ConnectionHandler> clientConnections;
    private Map<String, ArrayList<ConnectionHandler>> channelConnections;
    private ServerSocket serverSocket;
    private boolean done;
    public String serverName;
    private int port;

    public Server(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        clientConnections = new ArrayList<>();
        channelConnections = new LinkedHashMap<>();
        done = false;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Starting server with name " + serverName + " on port " + port);
            while (!done) {
                Socket connection = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(this, connection);
                clientConnections.add(handler);
                Thread t = new Thread(handler);
                t.start();
            }
            // If the server is to be closed we can call the cleanup
            shutDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Synchronizing all broadcast to clients
    public synchronized void broadcast(String message) {
        for (ConnectionHandler ch : clientConnections) {
            ch.sendMessage(message);
        }
    }

    // Synchronizing all broadcast to clients
    public synchronized void broadcastToChannel(String channel, String message) {
        if (channelConnections.get(channel) != null) {
            System.out.println("broadcasr found");

            for (ConnectionHandler ch : channelConnections.get(channel)) {
                ch.sendMessage(message);
            }
        }
    }

    public void shutDown() throws IOException {
        try {
            done = true;
            // Closing server
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
            // Cleanup all connection
            for (ConnectionHandler ch : clientConnections) {
                ch.cleanup();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    public String getServerName() {
        return this.serverName;
    }

    public ArrayList<ConnectionHandler> getClientConnections() {
        return this.clientConnections;
    }

    public Map<String, ArrayList<ConnectionHandler>> getChannelClientConnection() {
        return this.channelConnections;
    }

    public void addClientToChannel(String channelName, ConnectionHandler clientConnectionHandler) {
        ArrayList<ConnectionHandler> connections = new ArrayList<>();
        if (channelName != null && clientConnectionHandler != null) {
            // check if channel exist and add the client to exisitig;
            connections = channelConnections.get(channelName);
            if (connections == null) {
                connections = new ArrayList<>();
            }
            connections.add(clientConnectionHandler);
            channelConnections.put(channelName, connections);
            broadcastToChannel(channelName, ":" + clientConnectionHandler.getNickName() + " JOIN " + channelName);
        }
    }

    public void removeClientFromChannel(String channelName, ConnectionHandler clientConnectionHandler) {
        System.out.println("reached remove");
        ArrayList<ConnectionHandler> connections;
        if (channelName != null && clientConnectionHandler != null) {
            connections = channelConnections.get(channelName);
            if (connections == null) {
                String response = ":" + serverName + " 400 " + clientConnectionHandler.getNickName()
                        + " :No channel exists with that name";
                clientConnectionHandler
                        .sendMessage(response);
            } else {
                broadcastToChannel(channelName, ":" + clientConnectionHandler.getNickName() + " PART " + channelName);
                connections.remove(clientConnectionHandler);
            }
        }
    }
}
