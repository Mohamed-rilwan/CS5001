import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiClientThread implements Runnable {
    Socket socket;
    PrintWriter writer;
    BufferedReader buffReader;
    InputStreamReader isr;

    public MultiClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
            buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println("Connection to server has been accepted");

            while (true) {
                String command = buffReader.readLine();
                System.out.println(command);
            }
        }

        catch (IOException ioe) {
            System.out.println("IO Exception caught");
        }
    }

}
