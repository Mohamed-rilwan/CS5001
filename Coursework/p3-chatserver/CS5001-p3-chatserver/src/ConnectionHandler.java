import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the connection handler class of IRC Chat server
 * It is used to maintain connected client information.
 * 
 * @version 1.0
 * @author Matriculation Id : 220032472
 */
class ConnectionHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private String nickName;
    private String userName;
    private String realName;
    private String channel;
    private static final String VALID_NICK_NAME = "^[A-Za-z_][\\w]{0,8}$";
    private static final String VALID_CHANNEL_NAME = "^#[\\w][\\w]*$";
    private static final int MINIMUM_USER_COMMAND_LENGHT = 5;
    private static final int MINIMUM_PRIVMSG_COMMAND_LENGHT = 3;
    private static final int USER_ZERO_INDEX = 2;
    private static final int USER_ASTERISKS_INDEX = 3;

    private Server server;

    ConnectionHandler(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
        // Assingning * as a defualt nickname until it is set
        nickName = "*";
    }

    /**
     * The method overrides the run method of Runnable class
     * It performs the actions as per the command sent from the user.
     * 
     */
    @Override
    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String[] commandInput = null;
            String input;
            while ((input = reader.readLine()) != null || !reader.readLine().isBlank()) {

                if (input != null) {
                    commandInput = input.trim().split(" ");
                }
                commandActions(input, commandInput);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method is used to set nickname of the client.
     * 
     * @param name - nickname given by the client
     */
    public void setNickName(String name) {
        this.nickName = name;
    }

    /**
     * The method is used to get nickname of the client.
     * 
     * @return nickname of the client
     */
    public String getNickName() {
        return this.nickName;
    }

    /**
     * The method is used to get user name of the client.
     * 
     * @return username of the client
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * The method is used to set user name of the client.
     * 
     * @param name - user name given by the client
     */
    public void setUserName(String name) {
        this.userName = name;
    }

    /**
     * The method is used to set channel name that client belongs to.
     * 
     * @param channelName - name of the channel given by the client
     */
    public void setChannel(String channelName) {
        this.channel = channelName;
    }

    /**
     * The method is used to set real name of the client.
     * 
     * @param realName - real name given by the client
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * The method is used to send message to the client.
     * 
     * @param message - information to send to client.
     */
    public void sendMessage(String message) {
        writer.println(message);
    }

    /**
     * The method is used clean up client connections.
     * 
     * @throws IOException - input/output exception thrown if closing stream failed.
     */
    public void cleanup() throws IOException {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method is used to remove client from a server connection information.
     */
    public void removeClient() {
        List<ConnectionHandler> allConnections = server.getClientConnections();
        for (int i = 0; i < allConnections.size(); ++i) {
            ConnectionHandler ch = server.getClientConnections().get(i);
            if (ch == this) {
                allConnections.remove(i);
                break;
            }
        }
    }

    /**
     * The method is used to send private message to given target.
     * 
     * @param target  - client or channel to whom the message is to be sent to.
     * @param message - information to be sent to the target user.
     */
    public void privateMessage(String target, String message) {
        if (target.matches(VALID_CHANNEL_NAME)) {
            // Here it is assumes that even if the user is not part of another chanel, he
            // can send message to it
            ArrayList<ConnectionHandler> channelConnections = server.getChannelClientConnection().get(target);
            if (channelConnections == null || channelConnections.isEmpty()) {
                writer.println(
                        ":" + server.getServerName() + " 400 " + this.nickName + " :No channel exists with that name");
            } else {
                for (ConnectionHandler connectionHandler : channelConnections) {
                    connectionHandler.sendMessage(":" + nickName + " PRIVMSG " + target + " :" + message);
                }
            }
        }

        else {
            boolean userExistis = false;
            List<ConnectionHandler> userConnections = server.getClientConnections();
            for (ConnectionHandler connection : userConnections) {
                if (connection.getNickName().equals(target)) {
                    userExistis = true;
                    connection.sendMessage(":" + nickName + " PRIVMSG " + target + " :" + message);
                }
            }
            if (!userExistis) {
                writer.println(":" + server.getServerName() + " 400 * :No user exists with that name");
            }
        }
    }

    /**
     * The method is used to respond to nick command.
     * 
     * @param commandInput - The input commands passed to the server from client.
     */
    public void nickCommandAction(String[] commandInput) {
        if (commandInput.length >= 1 && commandInput[1].matches(VALID_NICK_NAME)) {
            setNickName(commandInput[1]);
        } else {
            writer.println(":" + server.getServerName() + " 400 * :Invalid nickname");
        }
    }

    /**
     * The method is used to respond to user command.
     * 
     * @param commandInput - The input commands passed to the server from client.
     */
    public void userCommandAction(String[] commandInput) {
        if (commandInput.length < MINIMUM_USER_COMMAND_LENGHT) {
            sendMessage(":" + server.getServerName() + " 400 * :Not enough arguments");
        } else if (commandInput.length >= MINIMUM_USER_COMMAND_LENGHT && (!"0".equals(commandInput[USER_ZERO_INDEX])
                || !"*".equals(commandInput[USER_ASTERISKS_INDEX])
                || !commandInput[USER_ASTERISKS_INDEX + 1].startsWith(":"))) {
            sendMessage(":" + server.getServerName() + " 400 * :Invalid arguments to USER command");
        } else if (nickName == null) {
            sendMessage(":" + server.getServerName() + " 400 * :No nick name found");
        } else if (userName != null) {
            sendMessage(":" + server.getServerName() + " 400 * :You are already registered");
        } else {
            setUserName(commandInput[1]);
            setRealName(commandInput[USER_ASTERISKS_INDEX + 1].replace(":", ""));
            sendMessage(
                    ":" + server.getServerName() + " 001 " + nickName + " :Welcome to the IRC network, "
                            + nickName);
        }
    }

    /**
     * The method is used to respond to join command.
     * 
     * @param commandInput - The input commands passed to the server from client.
     */
    public void joinCommandAction(String[] commandInput) {
        if (userName == null) {
            sendUnregisteredMessage();
        } else if (!commandInput[1].matches(VALID_CHANNEL_NAME)) {
            writer.println(":" + server.getServerName() + " 400 * :Invalid channel name");
        } else {
            server.addClientToChannel(commandInput[1], this);
            setChannel(commandInput[1]);
        }
    }

    /**
     * The method is used to respond to part command.
     * 
     * @param commandInput - The input commands passed to the server from client.
     */
    public void partCommandAction(String[] commandInput) {
        if (userName == null) {
            sendUnregisteredMessage();
        } else if (!commandInput[1].matches(VALID_CHANNEL_NAME)) {
            writer.println(":" + server.getServerName() + " 400 * :Invalid channel name");
        } else {
            server.removeClientFromChannel(commandInput[1], this);
        }
    }

    /**
     * The method is used to respond to list command.
     * 
     */
    public void listCommandAction() {
        if (userName == null) {
            sendUnregisteredMessage();
        } else {
            for (Map.Entry<String, ArrayList<ConnectionHandler>> eachChannelClient : server
                    .getChannelClientConnection().entrySet()) {
                writer.println(
                        ":" + server.getServerName() + " 322 " + nickName + " "
                                + eachChannelClient.getKey());
            }
            writer.println(":" + server.getServerName() + " 323 " + nickName + " :End of LIST");
        }
    }

    /**
     * The method is used to respond to name command.
     * 
     * @param commandInput - The input commands passed to the server from client.
     */
    public void namesCommandAction(String[] commandInput) {
        StringBuilder channelNickNames = new StringBuilder();
        if (userName == null) {
            sendUnregisteredMessage();
        } else if (commandInput.length == 2 && !commandInput[1].matches(VALID_CHANNEL_NAME)) {
            writer.println(":" + server.getServerName() + " 400 :No channel exists with that name");
        } else {
            ArrayList<ConnectionHandler> channelConnections = server.getChannelClientConnection()
                    .get(commandInput[1]);
            for (ConnectionHandler channelConnection : channelConnections) {
                channelNickNames.append(channelConnection.nickName + " ");

            }
            writer.println(
                    ":" + server.getServerName() + " 353 " + nickName + " = " + commandInput[1] + " :"
                            + channelNickNames);

        }
    }

    /**
     * The method is used to send message to client
     * if they perform command without registering.
     */
    public void sendUnregisteredMessage() {
        writer.println(":" + server.getServerName() + " 400 * :You need to register first");
    }

    /**
     * The method is used to handle command passed from the client to server.
     * 
     * @param input        - The input string passed to the server from client.
     * @param commandInput - The splitted commands from inout passed to the server
     *                     from client.
     */
    public void commandActions(String input, String[] commandInput) throws IOException {
        switch (commandInput[0].toUpperCase()) {
            case Commands.NICK:
                nickCommandAction(commandInput);
                break;

            case Commands.USER:
                userCommandAction(commandInput);
                break;

            case Commands.QUIT:
                // Assuming we cannot add username without a nick name
                if (userName != null) {
                    server.broadcast(":" + nickName + " QUIT");
                }
                cleanup();
                removeClient();
                server.removeClientFromChannel(this.channel, this);
                break;

            case Commands.JOIN:
                joinCommandAction(commandInput);
                break;

            case Commands.PART:
                partCommandAction(commandInput);
                break;

            case Commands.PRIVMSG:
                if (commandInput.length < MINIMUM_PRIVMSG_COMMAND_LENGHT) {
                    writer.println(
                            ":" + server.getServerName() + " 400 * :Invalid arguments to PRIVMSG command");
                } else if (userName == null) {
                    sendUnregisteredMessage();
                } else {
                    int messageIndex = input.indexOf(":");
                    privateMessage(commandInput[1], input.substring(messageIndex + 1));
                }
                break;

            case Commands.NAMES:
                namesCommandAction(commandInput);
                break;

            case Commands.LIST:
                listCommandAction();
                break;

            case Commands.PING:
                writer.println("PONG " + input.replace("PING ", ""));
                writer.flush();
                break;

            case Commands.TIME:
                LocalDateTime now = LocalDateTime.now();
                writer.println(":" + server.getServerName() + " 391 * :" + now);
                break;

            case Commands.INFO:
                writer.println(
                        ":" + server.getServerName()
                                + " 371 * :This is a example IRC chat server implementation using Java.");
                break;

            default:
                writer.println("Invalid command");
                writer.flush();
                break;
        }
    }
}
