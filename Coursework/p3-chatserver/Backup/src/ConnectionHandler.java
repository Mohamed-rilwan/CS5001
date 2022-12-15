import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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

    private Server server;

    public ConnectionHandler(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;

        // Assingning * as a defualt nickname until it is set
        nickName = "*";
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String[] commandInput = null;
            String input;
            while ((input = reader.readLine()) != null || !reader.readLine().isBlank()) {
                // Added trim in the beginning and then removed it later so as to check for
                // "PING "
                if (input != null) {
                    commandInput = input.split(" ");
                }

                switch (commandInput[0].toUpperCase()) {
                    case Commands.NICK: {
                        if (commandInput == null || !commandInput[1].matches(VALID_NICK_NAME)) {
                            writer.println(":" + server.serverName + " 400 * :Invalid nickname");
                        } else {
                            setNickName(commandInput[1]);
                        }
                        break;
                    }

                    case Commands.USER: {
                        if (commandInput.length < 5) {
                            sendMessage(":" + server.serverName + " 400 * :Not enough arguments");
                        } else if (commandInput.length >= 5 && (!"0".equals(commandInput[2])
                                || !"*".equals(commandInput[3]) || !commandInput[4].startsWith(":"))) {
                            sendMessage(":" + server.serverName + " 400 * :Invalid arguments to USER command");
                        } else if (nickName == null) {
                            sendMessage(":" + server.serverName + " 400 * :No nick name found");
                        } else if (userName != null) {
                            sendMessage(":" + server.serverName + " 400 * :You are already registered");
                        } else {
                            setUserName(commandInput[1]);
                            setRealName(commandInput[4].replace(":",""));
                            setRealName(commandInput[4].replace(":", ""));
                            sendMessage(":" + server.serverName + " 001 " + nickName + " :Welcome to the IRC network, "
                                    + nickName);
                        }
                        break;
                    }

                    case Commands.QUIT: {
                        // Assuming we cannot add username without a nick name
                        if (userName != null) {
                            server.broadcast(":" + nickName + " QUIT");
                        }
                        cleanup();
                        removeClient();
                        server.removeClientFromChannel(this.channel, this);
                        break;
                    }

                    case Commands.JOIN: {
                        if (userName == null) {
                            writer.println(":" + server.serverName + " 400 * :You need to register first");
                        } else if (!commandInput[1].matches(VALID_CHANNEL_NAME)) {
                            writer.println(":" + server.serverName + " 400 * :Invalid channel name");
                        } else {
                            server.addClientToChannel(commandInput[1], this);
                            setChannel(commandInput[1]);
                        }
                        break;
                    }

                    case Commands.PART: {
                        if (userName == null) {
                            System.out.println("user  null part");
                            writer.println(":" + server.serverName + " 400 * :You need to register first");
                        } else if (!commandInput[1].matches(VALID_CHANNEL_NAME)) {
                            writer.println(":" + server.serverName + " 400 * :Invalid channel name");
                        } else {
                            System.out.println("reached eflse partz");
                            server.removeClientFromChannel(commandInput[1], this);
                        }
                        break;
                    }

                    case Commands.PRIVMSG: {
                        if (commandInput.length < 3) {
                            writer.println(":" + server.serverName + " 400 * :Invalid arguments to PRIVMSG command");
                        } else if (userName == null) {
                            writer.println(":" + server.serverName + " 400 * :You need to register first");
                        } else {
                            int messageIndex = input.indexOf(":");
                            privateMessage(commandInput[1], input.substring(messageIndex + 1));
                        }
                        break;
                    }

                    case Commands.NAMES: {
                        StringBuilder channelNickNames = new StringBuilder();
                        if (userName == null) {
                            writer.println(":" + server.serverName + " 400 * :You need to register first");
                        } else if (commandInput.length < 2) {
                            writer.println(":" + server.serverName + " 400 * :Enter a valid channel name");
                        } else {
                            ArrayList<ConnectionHandler> channelConnections = server.getChannelClientConnection()
                                    .get(commandInput[1]);
                            for (ConnectionHandler channelConnection : channelConnections) {
                                channelNickNames.append(channelConnection.nickName + " ");

                            }
                            writer.println(":" + server.serverName + " 353 " + nickName + " = " + commandInput[1] + " :"
                                    + channelNickNames);

                        }
                        break;
                    }

                    case Commands.LIST: {
                        if (userName == null) {
                            writer.println(":" + server.serverName + " 400 * :You need to register first");
                        } else {
                            for (Map.Entry<String, ArrayList<ConnectionHandler>> eachChannelClient : server
                                    .getChannelClientConnection().entrySet()) {
                                writer.println(
                                        ":" + server.serverName + " 322 " + nickName + " "
                                                + eachChannelClient.getKey());
                            }
                            writer.println(":" + server.serverName + " 323 " + nickName + " :End of LIST");
                        }
                        break;
                    }

                    case Commands.PING: {
                        String[] text = Arrays.copyOfRange(commandInput, 1, commandInput.length);
                        StringBuilder remainingText = new StringBuilder();
                        for (int i = 0; i < text.length; i++) {
                            remainingText.append(text[i] + " ");
                        }
                        writer.println(commandInput.length >= 2 ? "PONG " + remainingText : "PONG");
                        writer.flush();
                        break;
                    }

                    case Commands.TIME: {
                        LocalDateTime now = LocalDateTime.now();
                        writer.println(":" + server.serverName + " 391 * :" + now);
                        break;
                    }

                    case Commands.INFO: {
                        writer.println(":" + server.serverName + " 371 * :This is a exampke of server   information.");
                        break;
                    }

                    default:
                        writer.println("Invalid command");
                        writer.flush();
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setNickName(String name) {
        this.nickName = name;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setChannel(String channelName) {
        this.channel = channelName;
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void cleanup() throws IOException {
        try {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeClient() {

        ArrayList<ConnectionHandler> allConnections = server.getClientConnections();
        for (int i = 0; i < allConnections.size(); ++i) {
            ConnectionHandler ch = server.getClientConnections().get(i);
            // if found remove it
            if (ch == this) {

                allConnections.remove(i);
                break;
            }
        }
    }

    public void privateMessage(String target, String message) {
        if (target.matches(VALID_CHANNEL_NAME)) {
            // Here it is assumes that even if the user is not part of another chanel, he
            // can send message to it
            ArrayList<ConnectionHandler> channelConnections = server.getChannelClientConnection().get(target);
            if (channelConnections == null || channelConnections.isEmpty()) {
                writer.println(
                        ":" + server.serverName + " 400 " + this.nickName + " :No channel exists with that name");
            } else {
                for (ConnectionHandler connectionHandler : channelConnections) {
                    connectionHandler.sendMessage(":" + nickName + " PRIVMSG " + target + " :" + message);
                }
            }
        }

        else {
            boolean userExistis = false;
            ArrayList<ConnectionHandler> userConnections = server.getClientConnections();
            for (ConnectionHandler connection : userConnections) {
                if (connection.getNickName().equals(target)) {
                    userExistis = true;
                    connection.sendMessage(":" + nickName + " PRIVMSG " + target + " :" + message);
                }
            }
            if (!userExistis) {
                writer.println(":" + server.serverName + " 400 * :No user exists with that name");
            }
        }
    }
}
