import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientMessageHandler {

    public void commandActions(String input, BufferedReader reader, PrintWriter writer) {
        try {
            if (!input.isBlank()) {
                String[] messages = splitMessage(input);
                System.out.println("Seperated message " + messages[0] + " S " + input);
                switch (messages[0].toUpperCase()) {
                    case Commands.PING: {
                        writer.println(messages.length >= 2 ? "PONG " + messages[1] : "PONG");
                        writer.flush();
                        break;
                    }
                    default:
                        writer.println("Reached default");
                        writer.flush();
                        System.out.println("Reached default");
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String[] splitMessage(String input) {
        return input.split(" ");
    }

}
