package view.graphicalinterface;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.BookingController;
import controller.BuildingController;
import controller.UserController;
import model.Constants;
import model.Constants.Interaction;
import model.Constants.PropertyToCheck;
import model.University;

public class ActionEvent {
        private UserController userController;
        private BuildingController buildingController;
        private BookingController bookingController;
        private University university;

        public ActionEvent(University university, UserController userController,
                        BuildingController buildingRoomController,
                        BookingController bookingController) {
                this.university = university;
                this.userController = userController;
                this.bookingController = bookingController;
                this.buildingController = buildingRoomController;
        }

        public String userAction(JFrame frame, boolean returnToMenu) {
                String userEmail = "";
                String title = "User Actions";
                try {
                        String[] possibilities = (String[]) Constants.userAction.values().toArray(new String[0]);
                        String input = (String) JOptionPane.showInputDialog(
                                        frame,
                                        Constants.CHOOSE_OPTION,
                                        title,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        possibilities,
                                        possibilities[0]);

                        if (input != null && input.equals(Constants.userAction.get(Interaction.ADD))) {
                                String email = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Add User Email",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                if (email == null) {
                                        return "";
                                }
                                String name = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Add User Name",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                if (name == null) {
                                        return "";
                                }
                                userEmail = email;
                                userController.addUsers(name, email);
                                JOptionPane.showMessageDialog(frame,
                                                "Registered User succesfully.");

                        } else if (input != null && input.equals(Constants.userAction.get(Interaction.REMOVE))) {
                                String email = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Enter User Email To Remove",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                if (email == null) {
                                        return "";
                                }
                                userController.removeUser(email);
                                JOptionPane.showMessageDialog(frame,
                                                "Removed User succesfully.");

                        } else if (input != null && input.equals(Constants.userAction.get(Interaction.VIEW))) {
                                List<String> allUsers = userController.getAllUserEmail();
                                if (allUsers.isEmpty()) {
                                        JOptionPane.showMessageDialog(frame,
                                                        "No Users Found In The System.",
                                                        "No Users",
                                                        JOptionPane.WARNING_MESSAGE);

                                } else {
                                        StringBuilder message = new StringBuilder();
                                        message.append("The following users exist in the system\n");
                                        int index = 1;
                                        for (String person : allUsers) {
                                                message.append(index + "." + person + "\n");

                                        }
                                        JOptionPane.showMessageDialog(frame,
                                                        message,
                                                        "Message",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                }
                        } else {
                                return "";
                        }

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame,
                                        ex.getLocalizedMessage(),
                                        "Error When Registering User",
                                        JOptionPane.WARNING_MESSAGE);
                        userAction(frame, returnToMenu);
                        userEmail = "";

                }
                if (!returnToMenu) {
                        userAction(frame, false);
                }
                return userEmail;
        }

        public void buildingAction(JFrame frame, boolean returnToMenu) {
                try {
                        String[] possibilities = (String[]) Constants.buildingAction.values().toArray(new String[0]);
                        String input = (String) JOptionPane.showInputDialog(
                                        frame,
                                        Constants.CHOOSE_OPTION,
                                        "Building Actions",
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        possibilities,
                                        null);
                        if (input != null && input.equals(Constants.buildingAction.get(Interaction.ADD))) {
                                String name = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Add Building Name",
                                                "Building Actions",
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                if (name == null) {
                                        return;
                                }
                                String address = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Add Bulding Address",
                                                "Building Actions",
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                if (address == null) {
                                        return;
                                }
                                buildingController.addBuilding(name, address);
                                JOptionPane.showMessageDialog(frame,
                                                "Registered Building succesfully.");

                        } else if (input != null && input.equals(Constants.buildingAction.get(Interaction.REMOVE))) {
                                List<String> allBuildings = buildingController.getAllBuilding();
                                if (allBuildings.isEmpty()) {
                                        JOptionPane.showMessageDialog(frame,
                                                        "No Buildings Found In The System To Remove.",
                                                        "No Building",
                                                        JOptionPane.WARNING_MESSAGE);

                                } else {
                                        String name = (String) JOptionPane.showInputDialog(
                                                        frame,
                                                        "Enter Building Name To Remove",
                                                        "Building Actions",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, null, null);
                                        if (name == null) {
                                                return;
                                        } else {
                                                buildingController.removeBuilding(name);
                                                JOptionPane.showMessageDialog(frame,
                                                                "Removed Building succesfully.");
                                        }
                                }

                        } else if (input != null && input.equals(Constants.buildingAction.get(Interaction.VIEW))) {
                                List<String> allBuildings = buildingController.getAllBuilding();
                                if (allBuildings.isEmpty()) {
                                        JOptionPane.showMessageDialog(frame,
                                                        "No Buildings Found In The System.",
                                                        "No Building",
                                                        JOptionPane.WARNING_MESSAGE);

                                } else {
                                        StringBuilder message = new StringBuilder();
                                        message.append("The Following Building Exist In The System\n");

                                        for (String building : allBuildings) {
                                                message.append(building + "\n");

                                        }
                                        JOptionPane.showMessageDialog(frame,
                                                        message,
                                                        "Message",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                }

                        } else {
                                return;
                        }

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame,
                                        ex.getLocalizedMessage(),
                                        "Error When Adding Building",
                                        JOptionPane.WARNING_MESSAGE);

                }
                if (!returnToMenu) {
                        buildingAction(frame, false);
                }

        }

        public String roomAction(JFrame frame, boolean returnToMenu) {
                String room = "";
                try {
                        String title = "Room Actions";
                        String[] possibilities = Constants.roomAction.values().toArray(new String[0]);

                        List<String> allBuildingList = buildingController.getAllBuilding();

                        List<String> allBuildingNamesList = buildingController.getAllBuildingNames();
                        String[] allBuildingName = new String[allBuildingNamesList.size()];
                        allBuildingNamesList.toArray(allBuildingName);

                        String buildingName;

                        String input = (String) JOptionPane.showInputDialog(
                                        frame,
                                        Constants.CHOOSE_OPTION,
                                        title,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        possibilities,
                                        null);

                        if (input != null && input.equals(Constants.roomAction.get(Interaction.ADD))) {
                                if (allBuildingList.isEmpty()) {
                                        checkIfBuildingExists(frame, title);
                                } else {
                                        buildingName = (String) JOptionPane.showInputDialog(
                                                        frame,
                                                        "Choose Building Name",
                                                        title,
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, allBuildingName, null);
                                        if (buildingName == null) {
                                                return "";
                                        } else {
                                                String roomName = (String) JOptionPane.showInputDialog(
                                                                frame,
                                                                "Enter Room Name",
                                                                title,
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null, null, null);
                                                if (roomName == null) {
                                                        return "";
                                                } else {
                                                        room = roomName;
                                                        buildingController.addRoomToBuilding(buildingName, roomName);
                                                        JOptionPane.showMessageDialog(frame,
                                                                        "Added Room To Building succesfully.");
                                                }
                                        }
                                }
                        }

                        else if (input != null && input.equals(Constants.roomAction.get(Interaction.REMOVE))) {
                                if (allBuildingList.isEmpty()) {
                                        checkIfBuildingExists(frame, title);
                                } else {
                                        buildingName = (String) JOptionPane.showInputDialog(
                                                        frame,
                                                        Constants.CHOOSE_BUILDING,
                                                        title,
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, allBuildingName,
                                                        null);
                                        if (buildingName == null) {
                                                return "";
                                        } else {
                                                String roomName = (String) JOptionPane.showInputDialog(
                                                                frame,
                                                                "Enter Room Name",
                                                                title,
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null, null, null);
                                                if (roomName == null) {
                                                        return "";
                                                } else {
                                                        buildingController.removeRoomFromBuilding(buildingName,
                                                                        roomName);
                                                        JOptionPane.showMessageDialog(frame,
                                                                        "Removed Room From Building succesfully.");
                                                }
                                        }
                                }
                        } else if (input != null && input.equals(Constants.roomAction.get(Interaction.VIEW))) {
                                if (allBuildingList.isEmpty()) {
                                        checkIfBuildingExists(frame, title);
                                } else {
                                        buildingName = (String) JOptionPane.showInputDialog(
                                                        frame,
                                                        Constants.CHOOSE_BUILDING,
                                                        title,
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, allBuildingName,
                                                        null);
                                        if (buildingName == null) {
                                                return "";
                                        } else {
                                                List<String> allBuildingsRoom = buildingController
                                                                .getAllRoomFromBuilding(buildingName);
                                                StringBuilder message = new StringBuilder();
                                                if (allBuildingsRoom.isEmpty()) {
                                                        JOptionPane.showMessageDialog(frame,
                                                                        "No Rooms assigned in the Building "
                                                                                        + buildingName,
                                                                        "Message",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                        message.append("The Following Rooms Exists In The Building: "
                                                                        + buildingName
                                                                        + "\n");
                                                        int index = 1;
                                                        for (String rooms : allBuildingsRoom) {
                                                                message.append(index + ". Room Name: " + rooms + "\n");
                                                                index++;

                                                        }
                                                        JOptionPane.showMessageDialog(frame,
                                                                        message,
                                                                        "Message",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                }
                                        }
                                }
                        } else {
                                return "";
                        }

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame,
                                        ex.getLocalizedMessage(),
                                        "Error while performing Room Action",
                                        JOptionPane.WARNING_MESSAGE);
                        room = "";
                }
                if (!returnToMenu) {
                        roomAction(frame, false);
                }
                return room;
        }

        public void bookAction(JFrame frame) {
                try {
                        String title = "Book Actions";
                        String[] possibilities = Constants.bookingAction.values().toArray(new String[0]);

                        List<String> allBuildingList = buildingController.getAllBuilding();

                        List<String> allBuildingNamesList = buildingController.getAllBuildingNames();
                        String[] allBuildingName = new String[allBuildingNamesList.size()];
                        allBuildingNamesList.toArray(allBuildingName);

                        String buildingName;

                        String input = (String) JOptionPane.showInputDialog(
                                        frame,
                                        Constants.CHOOSE_OPTION,
                                        title,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        possibilities,
                                        null);

                        if (input != null && input.equals(Constants.bookingAction.get(Interaction.ADD))) {
                                if (allBuildingList.isEmpty()) {
                                        checkIfBuildingExists(frame, title);
                                } else {
                                        buildingName = getBuildingInput(frame, title);
                                        if (buildingName == null || buildingName.isEmpty()) {
                                                return;
                                        } else {
                                                String roomName = getRoomInputFromBuilding(frame, title, buildingName);
                                                if (roomName == null || roomName.isEmpty()) {
                                                        return;
                                                } else {
                                                        String userEmail = getUserIfNotExist(frame, title);
                                                        if (userEmail == null || userEmail.isEmpty()) {
                                                                return;
                                                        } else {
                                                                String checkIntime = getValidDateTime(frame, title,
                                                                                "Check-In Date Time");
                                                                if (checkIntime.equals("") || checkIntime == null) {
                                                                        return;
                                                                }
                                                                String checkOutTime = getValidDateTime(frame, title,
                                                                                "Check-Out Date Time");
                                                                if (checkOutTime.equals("") || checkOutTime == null) {
                                                                        return;
                                                                }

                                                                bookingController.addBooking(buildingName,
                                                                                roomName, userEmail, checkIntime,
                                                                                checkOutTime);
                                                                JOptionPane.showMessageDialog(frame,
                                                                                "Successfully Booked Room.");
                                                        }
                                                }
                                        }
                                }
                        }

                        else if (input != null && input.equals(Constants.bookingAction.get(Interaction.REMOVE))) {
                                String userEmail = getUserIfNotExist(frame, title);
                                if (userEmail == null) {
                                        return;
                                } else {
                                        List<String> bookings = bookingController.viewBooking(PropertyToCheck.PERSON,
                                                        userEmail);
                                        String[] allBookingsForUser = new String[bookings.size()];
                                        bookings.toArray(allBookingsForUser);
                                        if (bookings.isEmpty()) {
                                                JOptionPane.showMessageDialog(frame,
                                                                "No Bookings available for the user" + userEmail,
                                                                "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                                String option = (String) JOptionPane.showInputDialog(
                                                                frame,
                                                                "Select Booking Id to delete\n",
                                                                title,
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null, allBookingsForUser, allBookingsForUser[0]);
                                                String bookingId = ((option.split(" "))[1]);
                                                bookingController.removeBookingByIndex(Integer.parseInt(bookingId));
                                                JOptionPane.showMessageDialog(frame,
                                                                "Removed Booking with booking Id: " + bookingId,
                                                                "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);

                                        }

                                }

                        } else if (input != null && input
                                        .equals(Constants.bookingAction.get(Interaction.VIEW_BOOKING_BY_ROOM))) {
                                if (allBuildingList.isEmpty()) {
                                        checkIfBuildingExists(frame, title);
                                } else {
                                        buildingName = (String) JOptionPane.showInputDialog(
                                                        frame,
                                                        Constants.CHOOSE_BUILDING,
                                                        title,
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, allBuildingName,
                                                        allBuildingName[0]);
                                        if (buildingName == null) {
                                                return;
                                        } else {
                                                List<String> allBuildingsRoom = buildingController
                                                                .getAllRoomFromBuilding(buildingName);
                                                StringBuilder message = new StringBuilder();
                                                if (allBuildingsRoom.isEmpty()) {
                                                        JOptionPane.showMessageDialog(frame,
                                                                        "No Rooms assigned in the Building "
                                                                                        + buildingName,
                                                                        "Message",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                        String[] allRoomsInBuilding = new String[allBuildingsRoom
                                                                        .size()];
                                                        allBuildingsRoom.toArray(allRoomsInBuilding);
                                                        String roomName = (String) JOptionPane.showInputDialog(
                                                                        frame,
                                                                        "Select Room to check for bookings\n",
                                                                        title,
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null, allRoomsInBuilding,
                                                                        allRoomsInBuilding[0]);
                                                        List<String> allBookingList = bookingController
                                                                        .viewBookingForRoom(buildingName.trim(),
                                                                                        roomName.trim());
                                                        if (allBookingList.isEmpty()) {
                                                                JOptionPane.showMessageDialog(frame,
                                                                                "No Booking assigned for the room '"
                                                                                                + roomName
                                                                                                + "'in the building '"
                                                                                                + buildingName + "'",
                                                                                "Message",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                        } else {

                                                                StringBuilder bookingMessage = new StringBuilder();
                                                                bookingMessage.append(
                                                                                "The following Booking assigned for the room '"
                                                                                                + roomName
                                                                                                + "' in the building '"
                                                                                                + buildingName + "'\n");

                                                                for (String building : allBookingList) {
                                                                        bookingMessage.append(building + "\n");

                                                                }
                                                                JOptionPane.showMessageDialog(frame,
                                                                                bookingMessage,
                                                                                "Message",
                                                                                JOptionPane.INFORMATION_MESSAGE);

                                                        }
                                                }
                                        }
                                }
                        } else if (input != null && input
                                        .equals(Constants.bookingAction.get(Interaction.VIEW_BOOKING_BY_BUILDING))) {
                                if (allBuildingList.isEmpty()) {
                                        checkIfBuildingExists(frame, title);
                                } else {
                                        buildingName = (String) JOptionPane.showInputDialog(
                                                        frame,
                                                        Constants.CHOOSE_BUILDING,
                                                        title,
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, allBuildingName,
                                                        allBuildingName[0]);
                                        if (buildingName == null) {
                                                return;
                                        } else {
                                                List<String> allBookingList = bookingController
                                                                .viewBooking(PropertyToCheck.BUILDING,
                                                                                buildingName.trim());
                                                if (allBookingList.isEmpty()) {
                                                        JOptionPane.showMessageDialog(frame,
                                                                        "No Booking assigned for the building '"
                                                                                        + buildingName + "'",
                                                                        "Message",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                        StringBuilder bookingMessage = new StringBuilder();
                                                        bookingMessage.append(
                                                                        "The following Booking assigned for the building '"
                                                                                        + buildingName + "'\n");

                                                        for (String building : allBookingList) {
                                                                bookingMessage.append(building + "\n");

                                                        }
                                                        JOptionPane.showMessageDialog(frame,
                                                                        bookingMessage,
                                                                        "Message",
                                                                        JOptionPane.INFORMATION_MESSAGE);

                                                }
                                        }
                                }
                        } else if (input != null && input
                                        .equals(Constants.bookingAction.get(Interaction.VIEW_BOOKING_BY_USER))) {
                                String userEmail = getUserIfNotExist(frame, title);
                                if (userEmail == null || userEmail.isEmpty()) {
                                        return;
                                } else {

                                        List<String> booking = bookingController.viewBooking(PropertyToCheck.PERSON,
                                                        userEmail);
                                        if (booking.isEmpty()) {
                                                JOptionPane.showMessageDialog(frame,
                                                                "No booking available for user: " + userEmail);

                                        } else {
                                                StringBuilder bookingMessage = new StringBuilder();
                                                bookingMessage.append(
                                                                "The following Booking assigned for the user: '"
                                                                                + userEmail + "'\n");

                                                for (String building : booking) {
                                                        bookingMessage.append(building + "\n");

                                                }
                                                JOptionPane.showMessageDialog(frame,
                                                                bookingMessage,
                                                                "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);

                                        }
                                }
                        } else if (input != null && input
                                        .equals(Constants.bookingAction.get(Interaction.VIEW_FREE_PERIOD))) {
                                if (allBuildingList.isEmpty()) {
                                        checkIfBuildingExists(frame, title);
                                } else {
                                        buildingName = (String) JOptionPane.showInputDialog(
                                                        frame,
                                                        Constants.CHOOSE_BUILDING,
                                                        title,
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, allBuildingName,
                                                        allBuildingName[0]);
                                        if (buildingName == null) {
                                                return;
                                        } else {
                                                List<String> allBuildingsRoom = buildingController
                                                                .getAllRoomFromBuilding(buildingName);
                                                StringBuilder message = new StringBuilder();
                                                if (allBuildingsRoom.isEmpty()) {
                                                        JOptionPane.showMessageDialog(frame,
                                                                        "No Rooms assigned in the Building "
                                                                                        + buildingName,
                                                                        "Message",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                        String[] allRoomsInBuilding = new String[allBuildingsRoom
                                                                        .size()];
                                                        allBuildingsRoom.toArray(allRoomsInBuilding);
                                                        String roomName = (String) JOptionPane.showInputDialog(
                                                                        frame,
                                                                        "Select Room to check for bookings\n",
                                                                        title,
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null, allRoomsInBuilding,
                                                                        allRoomsInBuilding[0]);

                                                        String date = (String) JOptionPane.showInputDialog(
                                                                        frame,
                                                                        "Enter date to check for available rooms. Format YYYY-mm-dd",
                                                                        title,
                                                                        JOptionPane.PLAIN_MESSAGE,
                                                                        null, null, null);
                                                        List<String[]> allfreeTime = bookingController
                                                                        .getFreePeriod(buildingName.trim(),
                                                                                        roomName.trim(), date.trim());
                                                        if (allfreeTime.isEmpty()) {
                                                                JOptionPane.showMessageDialog(frame,
                                                                                "No free period for the given room '"
                                                                                                + roomName
                                                                                                + "'in the building '"
                                                                                                + buildingName + "'",
                                                                                "Message",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                        } else {

                                                                StringBuilder bookingMessage = new StringBuilder();
                                                                bookingMessage.append(
                                                                                "The following free period is available for the room '"
                                                                                                + roomName
                                                                                                + "' in the building '"
                                                                                                + buildingName + "'\n");

                                                                for (String[] timePeriod : allfreeTime) {
                                                                        bookingMessage.append(timePeriod[0] + " - "
                                                                                        + timePeriod[1] + "\n");

                                                                }
                                                                JOptionPane.showMessageDialog(frame,
                                                                                bookingMessage,
                                                                                "Message",
                                                                                JOptionPane.INFORMATION_MESSAGE);

                                                        }
                                                }
                                        }
                                }

                        } else if (input != null && input
                                        .equals(Constants.bookingAction.get(Interaction.VIEW_BY_TIME))) {
                                String date = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Enter date to check for available rooms. Format YYYY-mm-dd",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                String time = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Enter time to check for available rooms. Format HH:MM (24 hours)",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                if (!date.isEmpty() && !time.isEmpty()) {
                                        List<String> rooms = bookingController.getAvailableRoomAtTime(date.trim(),
                                                        time.trim());
                                        if (rooms.isEmpty()) {
                                                JOptionPane.showMessageDialog(frame,
                                                                "No Room available at the given time and date",
                                                                "No Room available",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                                StringBuilder bookingMessage = new StringBuilder();
                                                bookingMessage.append(
                                                                "The following rooms are available at the given time\n");

                                                for (String building : rooms) {
                                                        bookingMessage.append(building + "\n");

                                                }
                                                JOptionPane.showMessageDialog(frame,
                                                                bookingMessage,
                                                                "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                        }
                                }

                        } else if (input != null && input
                                        .equals(Constants.bookingAction.get(Interaction.VIEW_BY_TIME_PERIOD))) {
                                String date = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Enter date to check for available rooms. Format YYYY-mm-dd",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                String checkIn = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Enter time to check-in time to fetch available rooms. Format HH:MM (24 hours)",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                String checkOut = (String) JOptionPane.showInputDialog(
                                                frame,
                                                "Enter time to check out time to fetch available rooms. Format HH:MM (24 hours)",
                                                title,
                                                JOptionPane.PLAIN_MESSAGE,
                                                null, null, null);
                                if (!date.isEmpty() && !checkIn.isEmpty() && !checkOut.isEmpty()) {
                                        List<String> rooms = bookingController.getAvailableRoomDuringPeriod(date.trim(),
                                                        checkIn.trim(), checkOut.trim());
                                        if (rooms.isEmpty()) {
                                                JOptionPane.showMessageDialog(frame,
                                                                "No Room available in the given time period and date",
                                                                "No Room available",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                                StringBuilder bookingMessage = new StringBuilder();
                                                bookingMessage.append(
                                                                "The following rooms are available at the given time period\n");

                                                for (String building : rooms) {
                                                        bookingMessage.append(building + "\n");

                                                }
                                                JOptionPane.showMessageDialog(frame,
                                                                bookingMessage,
                                                                "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                        }
                                }

                        } else {
                                return;
                        }

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame,
                                        ex.getLocalizedMessage(),
                                        "Error while performing room booking action",
                                        JOptionPane.WARNING_MESSAGE);
                }
                bookAction(frame);
        }

        public void checkIfBuildingExists(JFrame frame, String title) {
                List<String> allBuildingList = buildingController.getAllBuilding();
                if (allBuildingList.isEmpty()) {
                        Object[] options = { "Add Building",
                                        "Return to Menu" };
                        int addBuilding = JOptionPane.showOptionDialog(frame,
                                        "No Building Found in the system. Add New Building?",
                                        title,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        options,
                                        options[1]);

                        // Adding building if not exists
                        if (addBuilding == 0) {
                                buildingAction(frame, true);
                        }
                }
        }

        public void checkIfRoomExists(JFrame frame, String buildingName, String title) {
                List<String> allBuildingRoomList = buildingController.getAllRoomFromBuilding(buildingName);
                if (allBuildingRoomList.isEmpty()) {
                        Object[] options = { "Add Rooms",
                                        "Return to Menu" };
                        int addRoom = JOptionPane.showOptionDialog(frame,
                                        "No Rooms Found in the system. Add New Room?",
                                        title,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        options,
                                        null);

                        // Adding building if not exists
                        if (addRoom == 0) {
                                roomAction(frame, true);
                        }
                }
        }

        public String getRoomInputFromBuilding(JFrame frame, String title, String buildingName) {
                String room = "";
                List<String> allRoomInBuilding = buildingController.getAllRoomFromBuilding(buildingName);
                String[] allRoomsInBuildingName = new String[allRoomInBuilding.size()];
                allRoomInBuilding.toArray(allRoomsInBuildingName);

                if (allRoomInBuilding.isEmpty()) {
                        Object[] options = { "Add Room",
                                        "Return to Menu" };
                        int addRoom = JOptionPane.showOptionDialog(frame,
                                        "No Rooms Found in the Building. Add New Room?",
                                        title,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        options,
                                        options[0]);

                        // Adding Rooms if it does not exist
                        if (addRoom == 0) {
                                room = roomAction(frame, true);
                        }

                } else {
                        return (String) JOptionPane.showInputDialog(
                                        frame,
                                        "Select Room Name",
                                        title,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null, allRoomsInBuildingName, allRoomsInBuildingName[0]);
                }
                return room;
        }

        public String getUserIfNotExisting(JFrame frame, String title) {
                String user = "";
                List<String> allUserList = userController.getAllUserEmail();
                String[] allUser = new String[allUserList.size()];
                allUserList.toArray(allUser);
                if (allUserList.isEmpty()) {
                        Object[] options = { "Add User",
                                        "Return to Menu" };
                        int addUser = JOptionPane.showOptionDialog(frame,
                                        "No Users were found in the System. Add New User?",
                                        title,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        options,
                                        null);

                        // Adding Rooms if it does not exist
                        if (addUser == 0) {
                                user = userAction(frame, true);
                        }

                } else {
                        return (String) JOptionPane.showInputDialog(
                                        frame,
                                        "Select User Name",
                                        title,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null, allUser, null);
                }
                return user;
        }

        public String getBuildingInput(JFrame frame, String title) {
                List<String> allBuildingNamesList = buildingController.getAllBuildingNames();
                String[] allBuildingName = new String[allBuildingNamesList.size()];
                allBuildingNamesList.toArray(allBuildingName);
                return (String) JOptionPane.showInputDialog(
                                frame,
                                Constants.CHOOSE_BUILDING,
                                title,
                                JOptionPane.PLAIN_MESSAGE,
                                null, allBuildingName,
                                null);
        }

        public String getUserIfNotExist(JFrame frame, String title) {
                String user = "";
                List<String> allUsersList = userController.getAllUserEmail();
                String[] allUsers = new String[allUsersList.size()];
                allUsersList.toArray(allUsers);
                if (allUsersList.isEmpty()) {
                        Object[] options = { "Add User",
                                        "Return to Menu" };
                        int addUser = JOptionPane.showOptionDialog(frame,
                                        "No Users Found in the System. Add New User?",
                                        title,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        options,
                                        null);

                        // Adding Rooms if it does not exist
                        if (addUser == 0) {
                                user = userAction(frame, true);
                        } else {
                                user = "";
                        }

                } else {
                        return (String) JOptionPane.showInputDialog(
                                        frame,
                                        "Select User Email",
                                        title,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null, allUsers, null);

                }
                return user;
        }

        public String getValidDateTime(JFrame frame, String title, String field) {
                boolean isValidDate = false;
                String date = "";
                while (!isValidDate) {
                        date = (String) JOptionPane.showInputDialog(
                                        frame,
                                        "Enter " + field + " in the format: yyyy-mm-dd HH:MM (24hours)",
                                        title,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null, null, null);
                        if (date == null) {
                                return "";
                        } else if (!date.isEmpty() && bookingController.checkIsValidDate(date)) {
                                isValidDate = true;
                        } else {
                                isValidDate = false;
                                JOptionPane.showOptionDialog(frame,
                                                "Invalid Date time entered. Please enter " + field
                                                                + " in the format: yyyy-mm-dd HH:MM (24hours)",
                                                title,
                                                JOptionPane.OK_OPTION,
                                                JOptionPane.WARNING_MESSAGE,
                                                null,
                                                null,
                                                null);
                        }
                }
                return date;
        }
}
