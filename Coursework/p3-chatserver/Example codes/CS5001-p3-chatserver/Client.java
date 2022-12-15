import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.Socket;

public class Client {

    // Use printwriter in case of one string at a time,
    // rather than bufferwriter

    // writer.println(adds a new line at the end of string that the socket will be
    // listening to)

    public void sampleClient() {
        try {
            try (Socket s = new Socket(Constants.localhost, Constants.DEFAULTPORT)) {
                System.out.println("Made connection to the server on port " + Constants.DEFAULTPORT);

                InputStreamReader isr = new InputStreamReader(s.getInputStream());
                BufferedReader reader = new BufferedReader(isr);

                // read message from client
                String message = reader.readLine();
                System.out.println(message);
                System.out.println("Closing reader connection to the server on port " + Constants.DEFAULTPORT);
                reader.close();
            }
        } catch (IOException e) {
            System.exit(0);
            // TODO: handle exception
        } finally {
            // close socket

        }

    }

    public static void main(String[] args) {
        System.out.println("Calling client method");
        Client client = new Client();
        client.sampleClient();

    }
}
