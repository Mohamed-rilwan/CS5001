package view.commandline;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import controller.BookingController;
import controller.BuildingController;
import controller.UserController;
import model.Constants.PropertyToCheck;
import view.EventCloser;
import model.Constants;
import model.University;

enum Action {
    USER_ACTION,
    BUILDING_ACTION,
    ROOM_ACTION,
    BOOKIN_ACTION,
    MAIN_MENU
}

public class Helper extends RoomBookingCL {

    private final UserController userController;
    private final BuildingController buildingController;
    private final BookingController bookingController;
    PrintStream logger = System.out;
    private Scanner scanner;

    public UserOperation userOperation = new UserOperation();
    public BuildingOperation buildingOperation = new BuildingOperation();
    public RoomOperation roomOperation = new RoomOperation();
    public BookingOperation bookingOperation = new BookingOperation();

    public Helper(University university, UserController userController, BuildingController buildingRoomController,
            BookingController bookingController,EventCloser eventCloser) {
        super(university, userController, buildingRoomController, bookingController,eventCloser);
        this.userController = super.getUserController();
        this.buildingController = getBuildingController();
        this.bookingController = getBookingController();
    }

    public void rerunAction(Action actionType) {

        switch (actionType) {
            case USER_ACTION -> {
                logger.println(Constants.INVALID_OPTION);
                userOperation.userAction();
            }
            case BUILDING_ACTION -> {
                logger.println(Constants.INVALID_OPTION);
                buildingOperation.buildingAction();
            }
            case ROOM_ACTION -> {
                logger.println(Constants.INVALID_OPTION);
                roomOperation.roomAction();
            }
            case BOOKIN_ACTION -> {
                logger.println(Constants.INVALID_OPTION);
                bookingOperation.bookAction();
            }
            case MAIN_MENU -> {
                logger.println("Returning to Main Menu .....");
                run();
            }
            default -> run();
        }
    }

    // #region Building related Operation
    /**
     * Assumption that no 2 building can have same name
     */
    public class BuildingOperation {
        String input;
        String name;
        String address;

        public void buildingAction() {
            scanner = new Scanner(System.in);
            logger.println("""

                    ********************************************
                    Choose one the following actions\s
                    ********************************************
                        1. Add a New Building.
                        2. Remove Existing Building.
                        3. View all Building.
                        4. Return to main menu.
                    ********************************************

                    """);

            input = scanner.nextLine().trim();
            try {
                int chosenOption = Integer.parseInt(input);
                switch (chosenOption) {
                    case 1 -> buildingOperation.addBuilding();
                    case 2 -> buildingOperation.removeBuilding();
                    case 3 -> buildingOperation.viewAllBuilding();
                    case 4 -> rerunAction(Action.MAIN_MENU);
                    default -> rerunAction(Action.BUILDING_ACTION);
                }
                logger.println("Do you want to Add, Remove (or) view buildings? Y/N");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    buildingAction();
                } else {
                    rerunAction(Action.MAIN_MENU);
                }

            } catch (Exception ex) {
                logger.println(ex.getLocalizedMessage());
                buildingAction();
            }
            rerunAction(Action.MAIN_MENU);
        }

        public String addBuilding() {
            scanner = new Scanner(System.in);
            logger.println(Constants.ENTER_BUILDING_NAME);
            name = scanner.nextLine().trim();
            logger.println("Enter Address of the building");
            address = scanner.nextLine().trim();
            buildingController.addBuilding(name, address);
            logger.println();
            logger.println("************** Building Added to the University **************");
            return name;
        }

        public void removeBuilding() {
            logger.println(Constants.ENTER_BUILDING_NAME);
            name = scanner.nextLine().trim();
            buildingController.removeBuilding(name);
            logger.println();
            logger.println("************** Building Removed From the University **************");
        }

        public void viewAllBuilding() {
            List<String> allBuildingNames = buildingController.getAllBuilding();
            if (!allBuildingNames.isEmpty()) {
                logger.println("The following building exist in the university");
                for (String building : allBuildingNames) {
                    logger.println(building);
                }
            } else {
                logger.println("No Building Exists");
            }
        }

        public String checkIfBuildingsExistAndAdd(boolean showAddNew) {
            String buildingName = "";
            scanner = new Scanner(System.in);
            if (buildingController.getAllBuilding().isEmpty()) {
                logger.println(Constants.NO_REGISTERED_BUILDING);
            } else {
                logger.println(Constants.ENTER_BUILDING_NAME);
                buildingName = scanner.nextLine();
                if (!buildingController.checkIfBuildingExists(buildingName)) {
                    logger.println(Constants.NO_BUILDING_WITH_NAME + buildingName);
                    logger.println("The following Buildings are present in the University");
                    for (String building : buildingController.getAllBuilding()) {
                        logger.println(building);
                    }
                    logger.println();
                } else {
                    return buildingName;
                }
            }
            if (showAddNew) {
                logger.println("Add new Building? Y/N");
                input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("y")) {
                    buildingName = addBuilding();
                } else {
                    logger.println(
                            input.equalsIgnoreCase("n") ? "Exiting action... " : "Invalid Option. Exiting action...");
                    return "";
                }
            }
            return buildingName;
        }
    }

    // #endregion
    // #region Room related Operation
    public class RoomOperation {
        String input;
        String buildingName;
        String roomName;

        public void roomAction() {
            scanner = new Scanner(System.in);
            logger.println("""

                    ********************************************
                        Choose one the following options
                    ********************************************
                        1. Add New Room?
                        2. Remove Existing Room?
                        3. View all Room in a building
                        4. Return to main menu
                     ********************************************
                        Please select an option from the menu:

                        """);

            input = scanner.nextLine().trim();
            try {
                int chosenOption = Integer.parseInt(input);
                switch (chosenOption) {
                    case 1 -> {
                        buildingName = buildingOperation.checkIfBuildingsExistAndAdd(true);
                        roomOperation.addRoom(buildingName);
                    }
                    // Remove room from a building using building name
                    case 2 -> roomOperation.removeRoom();
                    // View Room under a building using the bulding name
                    case 3 -> roomOperation.viewAllRooms();
                    case 4 -> rerunAction(Action.MAIN_MENU);
                    default -> rerunAction(Action.ROOM_ACTION);
                }

                logger.println("Do you want to add,remove or view rooms? Y/N");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    roomAction();
                } else {
                    logger.println(
                            input.equalsIgnoreCase("n") ? "Exiting Operation..."
                                    : "Invalid option. Exiting Operation...");
                    rerunAction(Action.MAIN_MENU);
                }
            } catch (Exception ex) {
                logger.println(ex.getLocalizedMessage());
                roomAction();
            }
            rerunAction(Action.MAIN_MENU);
        }

        public String addRoom(String buildingName) {
            // Add a new room to the building
            if (!buildingName.isEmpty()) {
                logger.println("Enter name of the room");
                roomName = scanner.nextLine().trim();
                buildingController.addRoomToBuilding(buildingName, roomName);
                logger.println();
                logger.println("************** Room Added to the Building **************");
            } else {
                roomAction();
            }
            return roomName;
        }

        public void removeRoom() {
            logger.println(Constants.ENTER_BUILDING_NAME);
            buildingName = scanner.nextLine().trim();
            if (!buildingController.checkIfBuildingExists(buildingName)) {
                logger.println(
                        buildingController.getAllBuilding().isEmpty() ? Constants.NO_REGISTERED_BUILDING
                                : Constants.NO_BUILDING_WITH_NAME + buildingName);
                roomAction();
            } else {
                logger.println("Enter name of the room");
                roomName = scanner.nextLine().trim();
                buildingController.removeRoomFromBuilding(buildingName, roomName);
                logger.println("Removed room with name " + roomName + " from building " + buildingName);
            }
        }

        public void viewAllRooms() {
            logger.println(Constants.ENTER_BUILDING_NAME);
            List<String> allBuildings = buildingController.getAllBuilding();
            if (allBuildings.isEmpty()) {
                logger.println(Constants.NO_REGISTERED_BUILDING);
            } else {
                buildingName = scanner.nextLine().trim();
                if (buildingController.checkIfBuildingExists(buildingName)) {
                    List<String> allRooms = buildingController.getAllRoomFromBuilding(buildingName);
                    if (allRooms.isEmpty()) {
                        logger.println("No rooms available in the building");
                    } else {
                        logger.println("The following rooms exists in the building " + buildingName);
                        int index = 1;
                        for (String room : allRooms) {
                            logger.println(index + ".Room Name: " + room);
                            index++;
                        }
                    }
                } else {
                    logger.println(Constants.NO_BUILDING_WITH_NAME + buildingName);
                }
            }
        }

        public String checkIfRoomExistsAndAdd(String buildingName, boolean showAddNew) {
            List<String> rooms;
            scanner = new Scanner(System.in);
            rooms = buildingController.getAllRoomFromBuilding(buildingName);
            if (rooms.isEmpty()) {
                logger.println("Building " + buildingName + " does not contain any rooms");
                return addRoom(buildingName);
            } else {
                logger.println(Constants.ENTER_ROOM_NAME);
                input = scanner.nextLine().trim();
                if (!rooms.contains(input)) {
                    logger.println("Building " + buildingName + " doesn't contain the room " + input);
                    logger.println();
                    logger.println("Building " + buildingName + " contains the following rooms");
                    int index = 1;
                    for (String room : rooms) {
                        System.out.println(index + ".Room Name: " + room);
                        index++;
                    }
                    logger.println();
                    logger.println("Add new room? Y/N");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("y")) {
                        roomName = (new RoomOperation()).addRoom(buildingName);
                    } else {
                        logger.println(
                                input.equalsIgnoreCase("n") ? "Exiting Operation..."
                                        : "Invalid option. Exiting Operation...");
                        roomName = "";
                    }

                } else {
                    roomName = input;
                }
            }

            if (showAddNew) {
                logger.println("Add new room? Y/N");
                if (input.equalsIgnoreCase("y")) {
                    roomName = (new RoomOperation()).addRoom(buildingName);
                } else {
                    logger.println(
                            input.equalsIgnoreCase("n") ? "Exiting Operation..."
                                    : "Invalid option. Exiting Operation...");
                    roomName = "";
                }
                logger.println(Constants.ENTER_ROOM_NAME);
                roomName = scanner.nextLine();

            }
            return roomName;
        }
    }

    // #endregion
    // #region User related Operation
    public class UserOperation {
        String input;
        String email;
        String name;

        public void userAction() {
            scanner = new Scanner(System.in);
            logger.println("""

                    ********************************************
                        Choose one the following options
                    ********************************************

                        1. Add New user.
                        2. Remove Existing User.
                        3. View all users.
                        4. Return to main menu.

                    ********************************************
                        Please select an option from the menu:

                        """);
            input = scanner.nextLine().trim();
            try {
                while (!input.isEmpty() ||
                        !input.isBlank()) {
                    int chosenOption = Integer.parseInt(input);
                    switch (chosenOption) {
                        case 1 -> userOperation.addUser();
                        case 2 -> userOperation.removeUser();
                        case 3 -> userOperation.viewAllUser();
                        case 4 -> rerunAction(Action.MAIN_MENU);
                        default -> rerunAction(Action.USER_ACTION);
                    }
                    logger.println("Do you want to add,remove or view users? Y/N");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        userAction();
                    } else {
                        rerunAction(Action.MAIN_MENU);
                    }
                }
                userAction();
            } catch (Exception ex) {
                logger.println(ex.getMessage());
                userAction();
            }
            rerunAction(Action.MAIN_MENU);
        }

        public String addUser() {
            scanner = new Scanner(System.in);
            logger.println("Enter user email in the format name@domain.com.");
            email = scanner.nextLine().trim().trim();
            logger.println("Enter user name.");
            name = scanner.nextLine().trim();
            userController.addUsers(name, email);
            logger.println();
            logger.println("************** User Addded To The System **************");
            return email;
        }

        public void removeUser() {
            logger.println("Enter user email in the format name@domain.com.");
            email = scanner.nextLine().trim();
            userController.removeUser(email);

            logger.println();
            logger.println("************** User Removed From System **************");
        }

        public void viewAllUser() {
            List<String> allUsers = userController.getAllUserEmail();
            if (allUsers.isEmpty()) {
                System.out.println("No users found in the system");
            } else {
                logger.println("The following users exist in the system");
                int index = 1;
                for (String person : allUsers) {
                    logger.println(index + ".Email: " + person);
                }
            }
        }

        public String checkIfUserExistsOrAdd(boolean showAddNew) {
            String userEmail = "", input;
            scanner = new Scanner(System.in);
            if (userController.getAllUserEmail().isEmpty()) {
                logger.println("No registered user in the system");
            } else {
                logger.println("Enter user email");
                userEmail = scanner.nextLine();
                boolean existingUser = userController.checkUser(userEmail);
                if (!existingUser) {
                    logger.println("No user with the email " + userEmail + " registered");
                } else {
                    return userEmail;
                }
            }
            if (showAddNew) {
                logger.println("Add new User? Y/N");
                input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("y")) {
                    userEmail = addUser();
                } else {
                    logger.println(
                            input.equalsIgnoreCase("n") ? "Exiting action..." : "Invalid Option. Existing action...");
                    return "";
                }
            }
            return userEmail;
        }
    }

    // #endregion
    // #region Booking related Operation
    public class BookingOperation {
        String input;
        String buildingName;
        String roomName;
        String userEmail;
        String checkInTime;
        String checkOutTime;

        public void bookAction() {
            scanner = new Scanner(System.in);
            logger.println("""

                    **********************************************************
                        Choose one the following actions\s
                    **********************************************************

                        1. Add new Room Booking.
                        2. Remove Existing Room booking.
                        3. View all Booking for a Room in a Building.
                        4. View all Booking for a Building in the University.
                        5. View all Booking for a User in the University.
                        6. View all free period for a room.
                        7. View all Rooms available at the given time.
                        8. View all Rooms available at the given time period.
                        9. Return to main menu.

                    *********************************************************
                        Please select an option from the menu:

                        """);
            input = scanner.nextLine().trim();
            try {
                while (!input.isEmpty()) {
                    int chosenOption = Integer.parseInt(input);
                    switch (chosenOption) {
                        case 1 -> bookingOperation.addBooking();
                        case 2 -> bookingOperation.removeBooking();
                        case 3 -> bookingOperation.viewBookingByRoom();
                        case 4 -> bookingOperation.viewBookingByBuilding();
                        case 5 -> bookingOperation.viewBookingByUser();
                        case 6 -> bookingOperation.viewFreePeriod();
                        case 7 -> bookingOperation.viewBookingByTime();
                        case 8 -> bookingOperation.viewBookingByTimePeriod();
                        case 9 -> rerunAction(Action.MAIN_MENU);
                        default -> rerunAction(Action.BOOKIN_ACTION);
                    }

                    logger.println("Do you want to add,remove or view booking? Y/N");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("y")) {
                        bookAction();
                    } else {
                        rerunAction(Action.MAIN_MENU);
                    }
                }
                bookAction();
            } catch (Exception ex) {
                logger.println();
                logger.println("The following error was thrown when performing room booking.");
                logger.println(ex.getLocalizedMessage());
                bookAction();
            }
            rerunAction(Action.MAIN_MENU);
        }

        public void addBooking() {
            buildingName = buildingOperation.checkIfBuildingsExistAndAdd(true);
            if (!buildingName.isEmpty()) {
                roomName = roomOperation.checkIfRoomExistsAndAdd(buildingName, false);
                if (!roomName.isEmpty()) {
                    userEmail = userOperation.checkIfUserExistsOrAdd(true);
                    if (!userEmail.isEmpty()) {
                        logger.println("Enter check-in time in the format: yyyy-mm-dd HH:MM (24hours)");
                        checkInTime = getValidDate("Check-In time");
                        logger.println("Enter check-out time in the format: yyyy-mm-dd HH:MM (24hours)");
                        checkOutTime = getValidDate("Check-Out time");
                        bookingController.addBooking(buildingName, roomName, userEmail, checkInTime, checkOutTime);
                        logger.println();
                        logger.println("************** Booking Successfull **************");
                    }
                } else {
                    bookAction();
                }
            } else {
                bookAction();
            }
        }

        public void removeBooking() {
            try {
                scanner = new Scanner(System.in);
                String userEmail, bookingId;
                logger.println("Enter user email to view their bookings before removing (format: name@domain.com)");
                userEmail = scanner.nextLine().trim();
                List<String> bookings = bookingController.viewBooking(PropertyToCheck.PERSON, userEmail);
                if (!bookings.isEmpty()) {
                    logger.println("The following bookings are made under the email.");
                    for (String booking : bookings) {
                        logger.println(booking);
                    }
                    logger.println();
                    logger.println("Enter the Booking Id to delete the corresponding boooking");
                    bookingId = scanner.nextLine().trim();
                    logger.println("Removing booking with Id " + bookingId);
                    bookingController.removeBookingByIndex(Integer.parseInt(bookingId));
                    logger.println();
                    logger.println("************** Booking Removed **************");
                } else {
                    logger.println("No Booking Available for the User");
                    logger.println();
                }
            } catch (Exception e) {
                logger.println("Error occured when removing the booking. Please try again");
            }
        }

        public void viewBookingByRoom() {
            String room, building;
            List<String> bookings;
            logger.println("Enter building name to view all booking");
            building = scanner.nextLine();
            logger.println("Enter room name to view all booking");
            room = scanner.nextLine();

            if (!room.isEmpty()) {
                bookings = bookingController.viewBookingForRoom(building.trim(), room.trim());
                if (bookings.isEmpty()) {
                    logger.println("No bookings for " + room);
                } else {
                    logger.println("The following bookings were found for room " + room);
                    for (String booking : bookings) {
                        System.out.println(booking);
                    }
                }
            } else {
                logger.println("Enter a valid room name or number and building name.");
                viewBookingByRoom();
            }
        }

        public void viewBookingByBuilding() {
            String building;
            List<String> bookings;
            logger.println("Enter building name to view all booking");
            building = scanner.nextLine();
            if (!building.isEmpty()) {
                bookings = bookingController.viewBooking(PropertyToCheck.BUILDING, building.trim());
                if (bookings.isEmpty()) {
                    logger.println("No bookings for " + building);
                    return;
                } else {
                    logger.println("The following bookings were found for building " + building);
                    for (String booking : bookings) {
                        logger.println(booking);
                    }
                    return;
                }
            } else {
                logger.println("Enter a valid building name.");
                viewBookingByBuilding();
            }
        }

        public void viewBookingByUser() {
            String user;
            List<String> bookings;
            logger.println("Enter User Email to view all booking");
            user = scanner.nextLine();
            if (!user.isEmpty()) {
                bookings = bookingController.viewBooking(PropertyToCheck.PERSON, user.trim());
                if (bookings.isEmpty()) {
                    logger.println("No bookings for " + user);
                    return;
                } else {
                    logger.println("The following bookings were found for user " + user);
                    for (String booking : bookings) {
                        logger.println(booking);
                    }
                    return;
                }
            } else {
                logger.println("Enter a valid user email in the format name@domain.com.");
                viewBookingByUser();
            }
        }

        public void viewBookingByTime() {
            scanner = new Scanner(System.in);
            String date, time;
            List<String> bookings;
            logger.println("Enter date to check for available rooms. Format YYYY-mm-dd");
            date = scanner.nextLine();
            logger.println("Enter time to check for available rooms. Format HH:MM (24 hours)");
            time = scanner.nextLine();

            if (!date.isEmpty() && !time.isEmpty()) {
                bookings = bookingController.getAvailableRoomAtTime(date.trim(), time.trim());
                if (bookings.isEmpty()) {
                    logger.println("No Rooms available at the given time.");
                } else {
                    logger.println("The following rooms are available at the given time.");
                    for (String booking : bookings) {
                        logger.println(booking);
                    }
                }

            } else {
                logger.println("Enter a valid date and time to check for availability.");
                viewBookingByTime();
            }
        }

        public void viewBookingByTimePeriod() {
            scanner = new Scanner(System.in);
            String date;
            String time1;
            String time2;
            List<String> bookings;
            logger.println("Enter date to check for available rooms. Format YYYY-mm-dd");
            date = scanner.nextLine();
            logger.println("Enter time to check-in for available rooms. Format HH:MM (24 hours)");
            time1 = scanner.nextLine();
            logger.println("Enter time to check-out for available rooms. Format HH:MM (24 hours)");
            time2 = scanner.nextLine();

            if (!date.isEmpty() && !time1.isEmpty() && !time2.isEmpty()) {
                bookings = bookingController.getAvailableRoomDuringPeriod(date.trim(), time1.trim(), time2.trim());
                if (bookings.isEmpty()) {
                    logger.println("No Rooms available at the given time.");
                } else {
                    logger.println("The following rooms are available at the given time.");
                    for (String booking : bookings) {
                        logger.println(booking);
                    }
                }

            } else {
                logger.println("Enter a valid date and time to check for availability.");
                viewBookingByTime();
            }
        }

        public void viewFreePeriod() {
            scanner = new Scanner(System.in);
            String building, room, date;
            List<String[]> freePeriod;
            logger.println("Enter the building");
            building = scanner.nextLine();
            logger.println("Enter room in the building");
            room = scanner.nextLine();
            logger.println("Enter date for checking the schedule. Format (YYYY-mm-dd)");
            date = scanner.nextLine();

            freePeriod = bookingController.getFreePeriod(building, room, date);
            if (freePeriod.isEmpty()) {
                logger.println("No free slot found for the room " + room + " on date " + date);
            } else {
                logger.println("The following free slots are available for the room " + room + " in building '"
                        + building + "'");
                int index = 1;
                for (String[] freeSlot : freePeriod) {
                    logger.println("Time slot " + index + ": " + freeSlot[0] + " - " + freeSlot[1]);
                    index++;
                }
            }
        }

        public String getValidDate(String requestedTime) {
            String inputDateTime;
            inputDateTime = scanner.nextLine();
            while (true) {
                if (bookingController.checkIsValidDate(inputDateTime.trim())) {
                    return inputDateTime;
                }
                logger.println(
                        "Invalid date format for " + requestedTime + ".Please enter in the format yyyy-dd-mm HH:MM");
                inputDateTime = scanner.nextLine();
            }
        }
    }
    // #endregion
}
