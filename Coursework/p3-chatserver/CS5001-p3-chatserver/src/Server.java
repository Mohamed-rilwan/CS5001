import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the server class of IRC Chat server
 * that maintains the client connections along with
 * channel connections informations.
 * 
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */

public class Server {

    private ArrayList<ConnectionHandler> clientConnections;
    private Map<String, ArrayList<ConnectionHandler>> channelConnections;
    private ServerSocket serverSocket;
    private boolean done;
    private String serverName;
    private int port;

    /**
     * <b>Constructor</b>: constructs a server class taking servername
     * and port number for instantiation.
     * 
     * @param serverName The name of the server passed as argument when starting
     *                   server.
     * @param port       The port on which the server needs to be run.
     */
    public Server(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        clientConnections = new ArrayList<>();
        channelConnections = new LinkedHashMap<>();
        done = false;
    }

    /**
     * The start server method starts the server socket and
     * waits for requests to come in over the network.
     * It performs some operation based on that request,
     * and then possibly returns a result to the requester.
     * 
     */
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
            /*
             * Calling the clean up function when the server needs to stop listening to
             * client
             */
            shutDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method is used to send message to all
     * connected clients synchronously.
     * 
     * @param message - sends this message to all clients.
     */
    public synchronized void broadcast(String message) {
        for (ConnectionHandler ch : clientConnections) {
            ch.sendMessage(message);
        }
    }

    /**
     * The method is used to send message to all
     * connected clients synchronously within a channel.
     * 
     * @param channel - name of the channel to send message to.
     * @param message - information to be transmitted to clients
     */
    public synchronized void broadcastToChannel(String channel, String message) {
        if (channelConnections.get(channel) != null) {
            for (ConnectionHandler ch : channelConnections.get(channel)) {
                ch.sendMessage(message);
            }
        }
    }

    /**
     * The method is used clean up all client
     * connections and close the socket.
     * 
     * @throws IOException - input/output exception thrown if flushing failed.
     */
    public void shutDown() throws IOException {
        try {
            done = true;
            /* Cleanup all connection */
            for (ConnectionHandler ch : clientConnections) {
                ch.cleanup();
            }
            /* close the socket */
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * The method is used to get the given server name.
     * 
     * @return servername given at the time of starting a server.
     */
    public String getServerName() {
        return this.serverName;
    }

    /**
     * The method is used to get all the connected client information.
     * 
     * @return list of all clients in the server.
     */
    public List<ConnectionHandler> getClientConnections() {
        return this.clientConnections;
    }

    /**
     * The method is used to get all the connected client in channel related
     * information.
     * 
     * @return dictionary of channel name and client connections in each channel.
     */
    public Map<String, ArrayList<ConnectionHandler>> getChannelClientConnection() {
        return this.channelConnections;
    }

    /**
     * The method is used to add client to a channel.
     * 
     * @param channelName             - channel to which the client is to be added.
     * @param clientConnectionHandler - client that needs to be added to the given
     *                                channel
     */
    public void addClientToChannel(String channelName, ConnectionHandler clientConnectionHandler) {
        ArrayList<ConnectionHandler> connections = new ArrayList<>();
        if (channelName != null && clientConnectionHandler != null) {
            connections = channelConnections.get(channelName);
            if (connections == null) {
                connections = new ArrayList<>();
            }
            connections.add(clientConnectionHandler);
            channelConnections.put(channelName, connections);
            broadcastToChannel(channelName, ":" + clientConnectionHandler.getNickName() + " JOIN " + channelName);
        }
    }

    /**
     * The method is used to remove client from a channel.
     * 
     * @param channelName             - channel from which the client is to be
     *                                removed.
     * @param clientConnectionHandler - client that needs to be removed from the
     *                                given channel.
     */
    public void removeClientFromChannel(String channelName, ConnectionHandler clientConnectionHandler) {
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
