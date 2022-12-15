import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Clinet implements Runnable {

    private Socket socket;
    private BufferedReader reader = new BufferedReader(null);
    private PrintWriter writer;
    private String nickname;
    private boolean done = false;

    @Override
    public void run() {

        try{
            socket = new Socket("127.0.0.1", 12121);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           InputHandler handler = new InputHandler();
           Thread t = new Thread(handler);
           t.start();

           String incomingMessage;
           while((incomingMessage = reader.readLine()) !=null){
            System.out.println(incomingMessage);
           }

        } catch (IOException e) {
            shutDown();
            e.printStackTrace();
        }
    }

    // public void broadcast(String message) {
    // for (ConnectionHandler ch : clientConnections) {
    // ch.sendMessage(message);
    // }
    // }

    public void shutDown() {
        try {
            done = true;
            reader.close();
            writer.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }

    }

    class InputHandler implements Runnable {

    

        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inReader.readLine();
                    if (message.equals("/quit")) {
                        inReader.close();
                        shutDown();
                    } else {
                        writer.println(message);
                    }
                }
            } catch (IOException ioe) {
                shutDown();
            }

        }

        public void sendMessage(String message) {
            writer.println(message);
        }
    }

    public static void main(String[] args) {
        Clinet client = new Clinet();
        client.run();
    }
}