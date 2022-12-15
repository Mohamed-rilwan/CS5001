package view.commandline;

import java.util.Scanner;

import controller.BookingController;
import controller.BuildingController;
import controller.UserController;
import model.University;

public class RoomBookingCL extends Thread {
    private UserController userController;
    private BuildingController buildingController;
    private BookingController bookingController;
    private University university;
    private boolean isGuiOpen;
    private boolean isCLIOpen;

    public void setUniversity(University university) {
        this.university = university;
    }

    public RoomBookingCL(University university, UserController userController,
            BuildingController buildingRoomController,
            BookingController bookingController, boolean isGuiOpen, boolean isCLIOpen) {
        this.university = university;
        this.userController = userController;
        this.bookingController = bookingController;
        this.buildingController = buildingRoomController;
        this.isCLIOpen = isCLIOpen;
        this.isGuiOpen = isGuiOpen;
    }

    public UserController getUserController() {
        return userController;
    }

    public BuildingController getBuildingController() {
        return buildingController;
    }

    public BookingController getBookingController() {
        return bookingController;
    }

    private boolean done = false;

    public void setDone(boolean action) {
        this.done = action;
    }

    @Override
    public void run() {
        while (!done) {
            Helper helper = new Helper(this.university, this.userController, this.buildingController,
                    this.bookingController,isGuiOpen,isCLIOpen);

            displayOptions();
            String input;
            Scanner scanner = new Scanner(System.in);
            try {
                do {
                    input = scanner.nextLine();
                    switch (input) {
                        case "1" -> helper.userOperation.userAction();
                        case "2" -> helper.buildingOperation.buildingAction();
                        case "3" -> helper.roomOperation.roomAction();
                        case "4" -> helper.bookingOperation.bookAction();
                        case "5" -> {
                            bookingController.saveToFile();
                            System.out.println("Saved successfully to file.");
                            run();
                        }
                        case "6" -> {
                            bookingController.readFromFile();
                            System.out.println("Loaded values from file.");
                            run();
                        }

                        case "7" -> {
                            setDone(true);
                            this.isCLIOpen = false;
                            if (!isGuiOpen) {
                                System.exit(0);
                            }
                        }

                        default -> {
                            System.out.println("Unknown action\n");
                            run();
                        }
                    }
                } while (Integer.parseInt(input) > 7);
            } catch (StringIndexOutOfBoundsException ex) {
                System.out.println("Empty input received. Please enter a valid input.");
                run();
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid Input entered.");
            }
        }
        System.out.println("*************** CommandLine Interface Closed ****************");
    }

    public void displayOptions() {

        System.out.print("""

                ***********************************************************
                     Welcome to the University Room Booking Application
                ***********************************************************
                        1. Add, Remove (or) View all User.
                        2. Add, Remove (or) View all Buildings.
                        3. Add, Remove (or) View all Rooms.
                        4. Add, Remove (or) View all Bookings.
                        5. Save to file
                        6. Load from a specified file
                        7. Exit system
                ***********************************************************
                        Please select an option from the menu:

                """);

    }
}
