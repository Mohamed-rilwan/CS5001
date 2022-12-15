package view.graphicalinterface;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.BookingController;
import controller.BuildingController;
import controller.UserController;
import model.University;

public class GUIMain extends Thread {
    private UserController userController;
    private BuildingController buildingController;
    private BookingController bookingController;
    private University university;
    private boolean isGuiOpen;
    private boolean isCLIOpen;

    public void setUniversity(University university) {
        this.university = university;
    }

    ActionEvent actionEvent;

    public GUIMain(University university, UserController userController,
            BuildingController buildingRoomController,
            BookingController bookingController, boolean isGuiOpen, boolean isCLIOpen) {
        this.university = university;
        this.userController = userController;
        this.bookingController = bookingController;
        this.buildingController = buildingRoomController;
        this.isGuiOpen = isGuiOpen;
        this.isCLIOpen = isCLIOpen;
        this.actionEvent = new ActionEvent(university, userController,
                buildingController, bookingController);
    }

    /**
     * This getter method returns the user controller.
     * 
     * @return UserController initiated from main.
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * This getter method returns the building controller.
     * 
     * @return BuildingController initiated from main
     */
    public BuildingController getBuildingController() {
        return buildingController;
    }

    /**
     * This getter method returns the building controller.
     * 
     * @return BuildingController initiated from main
     */
    public BookingController getBookingController() {
        return bookingController;
    }

    @Override
    public void run() {

        JLabel label = new JLabel();
        JFrame frame = new JFrame("University Room Booking");

        label.setText("Please select an option from the menu option:");

        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(40, 40, 40, 40, Color.white));
        frame.getContentPane().setBackground(new Color(255, 255, 255));
    

        // frame.getContentPane().setBackground(new Color(0,0,0));

        label.setText(" Please select an option from the list below the menu option:");
        label.setBackground(new Color(255, 255, 255));

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.add(label);

        frame.getContentPane().add(containerPanel);
        displayButton(frame);

        frame.setLayout(new GridLayout(10, 1, 15, 10));// using no layout managers

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setVisible(true);

    }

    /**
     * This method defined the input buttons that the frame must show on initial
     * load
     * 
     * @param frame - Framw from the main method
     */
    public void displayButton(JFrame frame) {
        JButton users = new JButton("Add, Remove (or) View All Users");
        
        users.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actionEvent.userAction(frame, false);
            }
        });
        frame.add(users);

        JButton buildings = new JButton("Add, Remove (or) View All Buildings");
        buildings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actionEvent.buildingAction(frame, false);
            }
        });
        frame.add(buildings);

        JButton rooms = new JButton("Add, Remove (or) View All Rooms");
        rooms.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actionEvent.roomAction(frame, false);
            }
        });
        frame.add(rooms);

        JButton bookings = new JButton("Add, Remove (or) View All booking");
        bookings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actionEvent.bookAction(frame);
            }
        });
        frame.add(bookings);

        JButton saveToFile = new JButton("Save to File");
        saveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                bookingController.saveToFile();
                System.out.println("Saved successfully to file.");
                run();
                JOptionPane.showMessageDialog(frame,
                        "Saved values to storage.");
            }
        });
        frame.add(saveToFile);

        JButton load = new JButton("Load from a File");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                setUniversity(bookingController.readFromFile());
                System.out.println("Loaded values from file.");
                JOptionPane.showMessageDialog(frame,
                        "Retrieved values from storage.");

            }
        });
        frame.add(load);
        
        JButton exit = new JButton("Exit System");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(!isCLIOpen){
                    System.exit(0);
                }
                else{
                    isGuiOpen = false;
                    frame.setVisible(false);
                }

            }
        });
        frame.add(exit);
    }

}
