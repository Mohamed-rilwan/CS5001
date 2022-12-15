package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.SpringLayout.Constraints;

import model.Booking;
import model.Building;
import model.Constants;
import model.Constants.PropertyToCheck;
import model.Person;
import model.Room;
import model.University;

public class BookingService {

    private University university;

    public void setUniversity(University university) {
        this.university = university;
    }


    private UniversityService universityService;

    /**
     * Constructs a Booking service.
     * 
     * @param university - related information containing booking, building, room
     *                   and users.
     */
    public BookingService(University university, UniversityService universityService) {
        this.university = university;

        this.universityService = universityService;
    }

    /**
     * The following method is used to add a booking
     * entry for a room in the university.
     * 
     * @param bookingInfo - THe input with building, room, user and time of booking.
     * @throws IllegalArgumentException when the given parameters for booking are
     *                                  invalid.
     */
    public void addBooking(Booking bookingInfo) throws IllegalArgumentException {
        // Check if the building exists
        if (!university.getBuildings().containsKey(bookingInfo.getBuilding())) {
            throw new IllegalArgumentException("No building named " + bookingInfo.getBuilding() + " in the university");
        }
        // Check if the user is registered in the system
        if (!university.getPersons().containsKey(bookingInfo.getPerson())) {
            throw new IllegalArgumentException(
                    "No user with email " + bookingInfo.getPerson() + " registered in the university");
        }

        // check if the room exists in the building
        checkIfRoomAndBuildingExists(bookingInfo.getBuilding(), bookingInfo.getRoom());

        // Validating check-in date time format
        if (!isValidDate(bookingInfo.getCheckInTime())) {
            throw new IllegalArgumentException(
                    "Invalid check-in  date time. Expected format: yyyy-mm-dd HH:MM (24hours)");
        }

        // Validating check-out date time format
        if (!isValidDate(bookingInfo.getCheckOutTime())) {
            throw new IllegalArgumentException(
                    "Invalid check-out  date time. Expected format: yyyy-mm-dd HH:MM (24hours)");
        }

        // Validating check-in and checkout time difference and if it doesnt exceed a
        // day
        validDateRange(bookingInfo.getCheckInTime(), bookingInfo.getCheckOutTime());

        // Check if the booking already exists in the date time for the room
        checkOverlappingBooking(bookingInfo);

        university.getBookings().add(bookingInfo);
    }

    /**
     * This method removes booking using index of the booking row.
     * @param bookingId - index at which the booking is present
     */
    // #region Remove Booking
    public void removeBookingByIndex(int bookingId) {
        if (university.getBookings().get(bookingId) != null) {
            university.getBookings().remove(bookingId);
        } else {
            throw new IllegalArgumentException("Booking Id '" + bookingId
                    + "'does not match available bookings.Please View the bookings for updates made in the system before removing.");
        }
    }

    /**
     * This method is used to remove a booking in the system.
     * 
     * @param bookingInfo - THe input with building, room, user and time of booking.
     */
    public void removeBooking(Booking bookingInfo) {
        List<Booking> newBookingList = new ArrayList<>(university.getBookings());
        for (Booking booking : university.getBookings()) {
            if (booking.getBuilding().equals(bookingInfo.getBuilding())
                    && booking.getPerson().equals(bookingInfo.getPerson()) &&
                    booking.getRoom().equals(bookingInfo.getRoom()) &&
                    booking.getCheckInTime().equals(bookingInfo.getCheckInTime()) &&
                    booking.getCheckOutTime().equals(bookingInfo.getCheckOutTime())) {
                newBookingList.remove(booking);
            }
        }
        university.setBookings(newBookingList);
    }

    /**
     * This method deletes booking made for a building.
     * 
     * @param buildingName - name of building for which all the booking needs to be
     *                     removed.
     */
    public void removeBookingByBuilding(String buildingName) {
        List<Booking> newBookingList = new ArrayList<>(university.getBookings());
        for (Booking booking : university.getBookings()) {
            if (booking.getBuilding().equals(buildingName)) {
                newBookingList.remove(booking);
            }
        }
        university.setBookings(newBookingList);
    }

    /**
     * This method removes booking made by the given user.
     * 
     * @param userEmail - user whoses bookings must be deleted
     */
    public void removeBookingByUser(String userEmail) {
        for (Booking booking : university.getBookings()) {
            List<Booking> newBookingList = new ArrayList<>(university.getBookings());
            if (booking.getPerson().equals(userEmail)) {
                newBookingList.remove(booking);
                university.setBookings(newBookingList);
            }
        }
    }

    /***
     * The following methods is useful for Building and user as they have unique
     * values. The rooms can be having duplicated naming across buildings.
     *
     * @param property - Property of the type building, person or room
    * @param value - value that needs to be checked and deleted
     */

    public void removeBookingByProperty(PropertyToCheck property, String value) {
        List<Booking> newBookingList = new ArrayList<>(university.getBookings());
        for (Booking booking : university.getBookings()) {
            if (property == Constants.PropertyToCheck.BUILDING && booking.getBuilding().equals(value) ||
                    property == Constants.PropertyToCheck.PERSON && booking.getPerson().equals(value) ||
                    property == Constants.PropertyToCheck.ROOM && booking.getRoom().equals(value)) {
                newBookingList.remove(booking);
            }
        }
        university.setBookings(newBookingList);
    }

    // #endregion

    // #region Display Booking

    /**
     * This method displays booking information for the given room and building.
     * 
     * @param building - building name to match with booking.
     * @param room     - room name to match with the booking.
     * @return - List of bookings are returned.
     */
    public List<String> viewBookingByRoom(String building, String room) {
        List<String> bookingsByRoom = new ArrayList<>();
        checkIfRoomAndBuildingExists(building, room);
        int index = 0;
        for (Booking booking : university.getBookings()) {
            if (booking.getRoom().equals(room) && booking.getBuilding().equals(building)) {
                bookingsByRoom.add("BookingId: " + index + " | " + "Building: " + booking.getBuilding() + " | "
                        + "Room: " + booking.getRoom() + " | "
                        + "User: " + booking.getPerson() + " | " + "Check-In: " + booking.getCheckInTime() + " | "
                        + "Check-Out: " + booking.getCheckOutTime());
            }
            index++;
        }
        return bookingsByRoom;
    }

    /***
     * The following methods is useful for Building and user as they have unique
     * values.
     * The rooms can be having duplicated naming across buildings.
     */
    public List<String> viewBookingByProperty(PropertyToCheck property, String value) {
        List<String> viewBookingList = new ArrayList<>();
        int index = 0;
        for (Booking booking : university.getBookings()) {
            if ((property == Constants.PropertyToCheck.BUILDING && booking.getBuilding().equals(value)
                    && university.getBuildings().containsKey(value)) ||
                    (property == Constants.PropertyToCheck.PERSON && booking.getPerson().equals(value)
                            && university.getPersons().containsKey(value))) {
                if (university.getBuildings().containsKey(booking.getBuilding())
                        && university.getPersons().containsKey(booking.getPerson())) {
                    viewBookingList.add("BookingId: " + index + " | " + "Building: " + booking.getBuilding() + " | "
                            + "Room: " + booking.getRoom() + " | "
                            + "User: " + booking.getPerson() + " | " + "Check-In: " + booking.getCheckInTime() + " | "
                            + "Check-Out: " + booking.getCheckOutTime());
                }
            }
            index++;
        }
        return viewBookingList;
    }

    /**
     * This method validates the enterd check in and check out time period.
     * 
     * @param checkInDate  - validates the check in date time.
     * @param checkOutDate - validates the check out date time.
     * @return - true if both the values are havign a time
     *         differnece of at least 5 minutes and not exceeding the given date.
     * @throws IllegalArgumentException - if the given dates does match the given
     *                                  conditions of booking.
     */
    public boolean validDateRange(String checkInDate, String checkOutDate) throws IllegalArgumentException {
        boolean isValidDateRange = false;
        // default, ISO_LOCAL_DATE
        LocalDate localCheckInDate = LocalDate.parse(checkInDate.split(" ")[0]);
        LocalDate localCheckOutDate = LocalDate.parse(checkOutDate.split(" ")[0]);
        if (localCheckOutDate.isAfter(localCheckInDate)) {
            throw new IllegalArgumentException("A booking cannot stretch across multiple dates.");
        } else if (localCheckOutDate.isEqual(localCheckInDate)) {
            String checkInTime = checkInDate.split(" ")[1];
            String checkOutTime = checkOutDate.split(" ")[1];
            int checkInTimeInMinutes = Integer.parseInt((checkInTime.split(":")[0])) * 60
                    + Integer.parseInt((checkInTime.split(":"))[1]);
            int checkOutTimeInMinutes = Integer.parseInt((checkOutTime.split(":")[0])) * 60
                    + Integer.parseInt((checkOutTime.split(":"))[1]);
            if (checkOutTimeInMinutes < checkInTimeInMinutes || (checkOutTimeInMinutes > checkInTimeInMinutes
                    && (checkOutTimeInMinutes - checkInTimeInMinutes) % 5 != 0)
                    || checkOutTimeInMinutes == checkInTimeInMinutes) {
                throw new IllegalArgumentException(
                        "The block of time must be at least 5 minutes, and its length must be a multiple of 5 minutes.");
            }
        } else {
            isValidDateRange = true;
        }
        return isValidDateRange;
    }

    /**
     * This methods is used to check if check-In and check-out time overlaps with
     * exisiting
     * bookings in the system.
     * 
     * @param bookingInfo - parameters used to get checkIn and checkOut time.
     * @throws IllegalArgumentException if the given dates are not valid
     */
    public void checkOverlappingBooking(Booking bookingInfo) throws IllegalArgumentException {
        for (Booking booking : university.getBookings()) {
            if (booking.getRoom().equals(bookingInfo.getRoom())
                    && booking.getBuilding().equals(bookingInfo.getBuilding())) {

                LocalDate localCheckInDate = LocalDate.parse(bookingInfo.getCheckInTime().split(" ")[0]);
                LocalDate localCheckOutDate = LocalDate.parse(bookingInfo.getCheckOutTime().split(" ")[0]);
                LocalDate existingCheckInDate = LocalDate.parse(booking.getCheckInTime().split(" ")[0]);
                LocalDate existingCheckOutDate = LocalDate.parse(booking.getCheckOutTime().split(" ")[0]);
                if (localCheckInDate.isEqual(existingCheckInDate)) {
                    String checkInTime = bookingInfo.getCheckInTime().split(" ")[1];
                    String checkOutTime = bookingInfo.getCheckOutTime().split(" ")[1];

                    int checkInTimeInMinutes = Integer.parseInt((checkInTime.split(":")[0])) * 60
                            + Integer.parseInt((checkInTime.split(":"))[1]);
                    int checkOutTimeInMinutes = Integer.parseInt((checkOutTime.split(":")[0])) * 60
                            + Integer.parseInt((checkOutTime.split(":"))[1]);
                    String existingCheckInTime = booking.getCheckInTime().split(" ")[1];
                    String existingCheckOutTime = booking.getCheckOutTime().split(" ")[1];
                    int existingCheckInTimeInMinutes = Integer.parseInt((existingCheckInTime.split(":")[0])) * 60
                            + Integer.parseInt((existingCheckInTime.split(":"))[1]);
                    int existingCheckOutTimeInMinutes = Integer.parseInt((existingCheckOutTime.split(":")[0])) * 60
                            + Integer.parseInt((existingCheckOutTime.split(":"))[1]);
                    if (checkInTimeInMinutes < existingCheckOutTimeInMinutes
                            && checkInTimeInMinutes >= existingCheckInTimeInMinutes) {

                        throw new IllegalArgumentException(
                                "Entered Check-In and Check-Out time is overlapping with an existing booking.");
                    }
                }
            }
        }
    }

    /**
     * @param input - date to check its formatting.
     * @return true if the input date time is of the required format.
     */
    public boolean isValidDate(String input) {
        boolean isValidDate = false;
        Pattern pattern = Pattern.compile(Constants.DATE_TIME_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(input);
        isValidDate = matcher.matches();
        return isValidDate;
    }

    /**
     * This method is used to check if a room under a building exisits.
     * 
     * @param buildingName - building in which the room must be checked in.
     * @param roomName     - room name under the buikding that needs to be check if
     *                     it exists.
     * @throws IllegalArgumentException - if the room or buildind are not available
     *                                  in the system.
     */
    public void checkIfRoomAndBuildingExists(String buildingName, String roomName) throws IllegalArgumentException {
        boolean containsRoom = false, containBuilding = false;
        containBuilding = university.getBuildings().containsKey(buildingName);
        if (containBuilding) {
            for (Room room : university.getBuildings().get(buildingName).getRooms()) {
                if (room.getName().equals(roomName)) {
                    containsRoom = true;
                    break;
                }
            }
            if (!containsRoom) {
                throw new IllegalArgumentException(
                        "No room named '" + roomName + "'' in the building '" + buildingName + "'");
            }
        } else {
            throw new IllegalArgumentException("No building named " + buildingName + " in the university");
        }
    }

    /**
     * This method is used for saving all the university booking related
     * information.
     */
    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(new File(Constants.UNIVERSITY_PROFILE_STORAGE_PATH));
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(university);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    /**
     * This method is used to load all the university booking related information.
     */
    public University updateFromFile() {
        University universityData = new University();
        try (
                FileInputStream fi = new FileInputStream(new File(Constants.UNIVERSITY_PROFILE_STORAGE_PATH));
                ObjectInputStream oi = new ObjectInputStream(fi);) {
            // Read objects
            this.university = ((University) oi.readObject());
            universityService.refreshServices(universityData);

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf.getLocalizedMessage());
        }
        return universityData;
    }

    // #region Available Rooms

    /**
     * This method gives a freePeriod of room
     * 
     * @param building - building name
     * @param room     - room name
     * @param date     - date of checking
     * @return - list of all free period
     */
    public List<String[]> freePeriodForRoom(String building, String room, String date) {
        LocalDate inputDate = LocalDate.parse(date);
        Booking freePeriodAllDay = new Booking(building, room, null, date + " 00:00", date + " 23:59");
        List<String[]> freePeriod = new ArrayList<>();
        freePeriod.add(new String[] { "00:00", "23:59" });
        List<Booking> sameDateBookedPeriod = new ArrayList<>();
        List<Booking> sameDateFreePeriod = new ArrayList<>();

        for (Booking booking : university.getBookings()) {
            LocalDate localCheckInDate = LocalDate.parse(booking.getCheckInTime().split(" ")[0]);
            // Checking if a booking is available for that date ofr the given room
            if (booking.getBuilding().equals(building) && booking.getRoom().equals(room)
                    && inputDate.isEqual(localCheckInDate)) {
                sameDateBookedPeriod.add(booking);
            }
        }
        if (sameDateBookedPeriod.isEmpty()) {
            sameDateFreePeriod.add(freePeriodAllDay);
        }
        // remove the timing booked in the day and show remaining free time.
        else {
            List<Booking> sortedBookingTimeRange = sortByDateTime(sameDateBookedPeriod);
            int index = 0;
            freePeriod = new ArrayList<>();
            for (Booking sortBooking : sortedBookingTimeRange) {
                String[] timePeriod = new String[2];
                if (index == 0) {
                    if (getTimeInMinutes(sortBooking.getCheckInTime()) != 0) {
                        timePeriod[0] = "00:00";
                        timePeriod[1] = sortBooking.getCheckInTime().split(" ")[1];
                        freePeriod.add(timePeriod);
                    }
                    if (getTimeInMinutes(sortBooking.getCheckOutTime()) != 1439 && sortedBookingTimeRange.size() == 1) {
                        timePeriod = new String[2];
                        timePeriod[0] = sortBooking.getCheckOutTime().split(" ")[1];
                        timePeriod[1] = "23:59";
                        freePeriod.add(timePeriod);
                    }
                } else if (index != 0) {
                    timePeriod = new String[2];
                    if (!(sortedBookingTimeRange.get(index - 1).getCheckOutTime())
                            .equals(sortBooking.getCheckInTime())) {
                        timePeriod[0] = sortedBookingTimeRange.get(index - 1).getCheckOutTime().split(" ")[1];
                        timePeriod[1] = sortBooking.getCheckInTime().split(" ")[1];
                        freePeriod.add(timePeriod);
                    }
                    if (index == sortedBookingTimeRange.size() - 1
                            && !sortBooking.getCheckOutTime().split(" ")[1].equals("23:59")) {
                        timePeriod = new String[2];
                        timePeriod[0] = sortBooking.getCheckOutTime().split(" ")[1];
                        timePeriod[1] = "23:59";
                        freePeriod.add(timePeriod);
                    }
                } else {
                    timePeriod = new String[2];
                    timePeriod[0] = sortBooking.getCheckInTime().split(" ")[1];
                    timePeriod[1] = sortBooking.getCheckOutTime().split(" ")[1];
                }
                index++;
            }
        }
        return (freePeriod);

    }

    /**
     * This method lists available rooms at the given time period
     * @param date - date on which the check needs to be done
     * @param time - time to make the check
     * @return - list of available rooms
     */
    public List<String> listAvailableRoomAtTime(String date, String time) {
        List<String> availableRooms = new ArrayList<>();
        for (Building building : university.getBuildings().values()) {
            for (Room room : building.getRooms()) {
                for (Booking booking : university.getBookings()) {
                    if (booking.getBuilding().equals(building.name) && booking.getRoom().equals(room.getName())
                            && booking.getCheckInTime().split(" ")[0].equals(date)) {
                        String existingCheckInTime = booking.getCheckInTime().split(" ")[1];
                        String existingCheckOutTime = booking.getCheckOutTime().split(" ")[1];
                        int existingCheckInTimeInMinutes = Integer.parseInt((existingCheckInTime.split(":")[0])) * 60
                                + Integer.parseInt((existingCheckInTime.split(":"))[1]);
                        int existingCheckOutTimeInMinutes = Integer.parseInt((existingCheckOutTime.split(":")[0])) * 60
                                + Integer.parseInt((existingCheckOutTime.split(":"))[1]);
                        int inputTimeInMinute = Integer.parseInt(time.split(":")[0]) * 60
                                + Integer.parseInt(time.split(":")[1]);
                        if (inputTimeInMinute >= existingCheckInTimeInMinutes
                                && inputTimeInMinute < existingCheckOutTimeInMinutes) {
                            String available = "Building: " + building.name + " - Room: " + room.getName();
                            availableRooms.remove(available);
                            break;

                        } else {
                            String available = "Building: " + building.name + " - Room: " + room.getName();
                            availableRooms.add(available);

                        }
                    } else {
                        String available = "Building: " + building.name + " - Room: " + room.getName();
                        availableRooms.add(available);
                    }
                }
            }
        }

        // Removing duplicates
        return availableRooms.stream().distinct().collect(Collectors.toList());
    }

    /**
     * This lists available rooms at the given time period.
     *
     * @param date - date on which the check needs to be done
     * @param checkInTime - input check in .
     * @param CheckOutTime - input check-out time.
     * @return lists available room during the time period
     */
    public List<String> listAvailableRoomAtPeriod(String date, String checkInTime, String CheckOutTime) {
        List<String> availableRooms = new ArrayList<>();
        for (Building building : university.getBuildings().values()) {
            for (Room room : building.getRooms()) {
                for (Booking booking : university.getBookings()) {
                    if (booking.getBuilding().equals(building.name) && booking.getRoom().equals(room.getName())
                            && booking.getCheckInTime().split(" ")[0].equals(date)) {

                        String existingCheckInTime = booking.getCheckInTime().split(" ")[1];
                        String existingCheckOutTime = booking.getCheckOutTime().split(" ")[1];

                        int existingCheckInTimeInMinutes = Integer.parseInt((existingCheckInTime.split(":")[0])) * 60
                                + Integer.parseInt((existingCheckInTime.split(":"))[1]);
                        int existingCheckOutTimeInMinutes = Integer.parseInt((existingCheckOutTime.split(":")[0])) * 60
                                + Integer.parseInt((existingCheckOutTime.split(":"))[1]);
                        int inputCheckInTimeInMinute = Integer.parseInt(checkInTime.split(":")[0]) * 60
                                + Integer.parseInt(checkInTime.split(":")[1]);
                        int inputCheckOutTimeInMinute = Integer.parseInt(CheckOutTime.split(":")[0]) * 60
                                + Integer.parseInt(CheckOutTime.split(":")[1]);

                        if (inputCheckInTimeInMinute <= existingCheckOutTimeInMinutes
                                || inputCheckOutTimeInMinute >= existingCheckInTimeInMinutes) {
                            String available = "Building: " + building.name + " - Room: " + room.getName();
                            availableRooms.remove(available);
                            break;

                        } else {
                            String available = "Building: " + building.name + " - Room: " + room.getName();
                            availableRooms.add(available);

                        }
                    } else {
                        String available = "Building: " + building.name + " - Room: " + room.getName();
                        availableRooms.add(available);
                    }
                }
            }
        }
        // Removing duplicates
        return availableRooms.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Lists booking ofr a given date.
     * @param date - date on which the booking needs to be fetched.
     * @return - list of all booking for given date.
     */
    public List<Booking> getBookingByDate(String date) {
        List<Booking> bookingList = new ArrayList();
        LocalDate inputDate = LocalDate.parse(date.split(" ")[0]);

        for (Booking booking : university.getBookings()) {
            LocalDate localCheckInDate = LocalDate.parse(booking.getCheckInTime().split(" ")[0]);
            if (localCheckInDate.isEqual(inputDate)) {
                bookingList.add(booking);
            }
        }
        return bookingList;
    }


    /**
     * This method sorts date and time for all the given bookings.
     *
     * @param bookings list of all bookings for sorting
     * @return - sorted booking
     */
    public List<Booking> sortByDateTime(List<Booking> bookings) {
        List<Booking> bookingToSort = new ArrayList<Booking>(bookings);
        Comparator<Booking> comparator = (date1, date2) -> date1.getCheckInTime().compareTo(date2.getCheckInTime());
        Collections.sort(bookingToSort, comparator);
        return bookingToSort;
    }

    /**
     * THis method returns the datetime into minutes.
     * @param dateTime - Time that needs to be converted.
     * @return - returns minutes of the given time.
     */
    public int getTimeInMinutes(String dateTime) {
        String time = dateTime.split(" ")[1];
        return Integer.parseInt((time.split(":")[0])) * 60
                + Integer.parseInt((time.split(":"))[1]);

    }

    // #endregion

    // #region - Unused methods

    /**
     * This method returns list of booking for a given user.
     * @param user - user email that needs to be checked for retrieving booking
     * @return - lists of booking based on matching users
     */
    public List<Booking> viewBookingByUser(String user) {
        List<Booking> bookingsByUser = new ArrayList<>();
        for (Booking booking : university.getBookings()) {
            if (booking.getPerson().equals(user)) {
                bookingsByUser.add(booking);
            }
        }
        return bookingsByUser;
    }

    // #endregion
}
