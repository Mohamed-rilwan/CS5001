import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWithGui {

    JTextField outgoing;
    JTextArea incoming;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    
    public static void main(String[] args){
        new ClientWithGui().Client("127.0.0.1",12348);
    }

    public void Client(java.lang.String host, int port){
        JFrame frame = new JFrame("Simple chat app");
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15,50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qScrollPane = new JScrollPane(incoming);
        qScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener((ActionListener) new SendButtonListener());
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);

        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
        
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400,500);
        frame.setVisible(true);
    } 

    public void setUpNetworking(){
        try {
            socket = new Socket("127.0.0.1",12348);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Networking established");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class SendButtonListener implements ActionListener{


        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
                try {
                    writer.println(outgoing.getText());
                    writer.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // TODO: handle exception
                }
                outgoing.setText("");
                outgoing.requestFocus();

            }

        }



    public class IncomingReader implements Runnable{
        public void run(){
            String message;
            try{
                while((message = reader.readLine()) != null){
                    System.out.println("read "+ message);
                    incoming.append(message + "\n");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
}
