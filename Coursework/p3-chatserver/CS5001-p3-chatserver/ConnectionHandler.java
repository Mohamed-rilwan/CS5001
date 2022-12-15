import java.io.*;
import java.net.Socket;

public class ConnectionHandler {

    private Socket connection; // socket representing TCP/IP connection to Client
    private InputStream inputStream; // get data from client on this input stream
    private OutputStream outputStream; // can send data back to the client on this output stream
    BufferedReader reader; // use buffered reader to read client data
    PrintWriter writer;

    public ConnectionHandler(Socket conn) {
        this.connection = conn;
        try {
            inputStream = conn.getInputStream(); // get data from client on this input stream
            outputStream = conn.getOutputStream(); // to send data back to the client on this stream
            reader = new BufferedReader(new InputStreamReader(inputStream)); // use buffered reader to read data
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    public void handleClientRequest() {
        System.out.println("new ConnectionHandler constructed .... ");
        try {
            printClientData();
        } catch (Exception e) { // exit cleanly for any Exception
            System.out.println("ConnectionHandler.handleClientRequest: " + e.getMessage());
            cleanup(); // cleanup and exit
        }
    }

    private void printClientData() throws IOException {
        while (true) {
            try{
            String line = reader.readLine(); // get data from client over socket
            String[] input  = line.split(" ");

            // if readLine fails we can deduce here that the connection to the client is
            // broken
            // and shut down the connection on this side cleanly by throwing a
            // DisconnectedException
            // which will be passed up the call stack to the nearest handler (catch block)
            // in the run method
            // if (line == null || line.equals("null") ||
            // line.equals(Configuration.exitString)) {
            // throw new DisconnectedException(" ... client has closed the connection ...
            // ");
            // }
            // assuming no exception, print out line received from client
            System.out.println(input[0]);
            switch(input[0]){
                case Commands.PING:
                writer.println("PONG");
            }
            System.out.println("ConnectionHandler: " + line);

            // hanlde what to do for a specific command from user

            // PrintWriter outToClient = new PrintWriter(
            // connected.getOutputStream(), true);

            }
            catch(Exception ex){
                //remove client from list
            }
        }
    }

    private void cleanup() {
        System.out.println("ConnectionHandler: ... cleaning up and exiting ... ");
        try {
            reader.close();
            inputStream.close();
            connection.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }
}
