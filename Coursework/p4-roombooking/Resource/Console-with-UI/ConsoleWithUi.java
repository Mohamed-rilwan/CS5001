import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConsoleWithUi extends JFrame {

    // JPanel
    JPanel pnlButton = new JPanel();
    // Buttons
    JButton btnAddFlight = new JButton("Click me");

    public ConsoleWithUi() {

        initUI();

    }

    private void initUI() {

        btnAddFlight.setBounds(60, 400, 220, 30);

        // JPanel bounds
        pnlButton.setBounds(800, 800, 200, 100);

        // Adding to JFrame
        pnlButton.add(btnAddFlight);
        add(pnlButton);

        btnAddFlight.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("OUtput to console");

            }
        });

        setTitle("Simple example");
        setSize(300, 200);

        setLocationRelativeTo(null);

        // chnge to exit on close to exit program.
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {

        ConsoleWithUi ex = new ConsoleWithUi();
        ex.setVisible(true);

        System.out.println("Press Enter to  exit");
        

        System.in.read();
        System.out.println("Exiting....");
        System.exit(0);

    }
}