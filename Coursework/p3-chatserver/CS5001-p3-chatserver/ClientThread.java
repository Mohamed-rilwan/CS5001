import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Here we create the ClientThread inner class and have it implement Runnable
//This means that it can be used as a thread
public class ClientThread implements Runnable {
    Socket threadSocket;

    // This constructor will be passed the socket
    public ClientThread(Socket socket) {
        // Here we set the socket to a local variable so we can use it later
        threadSocket = socket;
    }

    public void run() {
        // All this should look familiar
        try {
            // Create the streams
            PrintWriter output = new PrintWriter(threadSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(threadSocket.getInputStream()));

            // Tell the client that he/she has connected
        
            while (true) {
                // This will wait until a line of text has been sent
                String chatInput = input.readLine();
                String[] commands = chatInput.split(" ");
                System.out.println(chatInput);
                switch (commands[0]) {
                    case Commands.PING:
                        System.out.println("Reached ping");
                        output.println("PONG");
                        break;
                    default:
                        System.out.println("Not a valid command");
                        break;
                }

            }
        } catch (IOException exception) {
            System.out.println("Error: " + exception);
        }
    }
}