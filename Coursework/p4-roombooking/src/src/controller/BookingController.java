package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import model.Booking;
import model.Constants;
import model.Room;
import model.University;
import model.Constants.PropertyToCheck;
import service.BookingService;
import service.UniversityService;

/**
 * Represents a booking controller used to perform
 * actions related to booking a room in the university.
 */
public class BookingController {
    private BookingService bookingService;
    private University university;

    /**
     * Constructs booking controller
     * 
     * @param universityModel - univeristy related information such as buildings,
     *                        rooms, bookings and users.
     */
    public BookingController(University universityModel, UniversityService universityService) {
        this.university = universityModel;

        bookingService = universityService.getBookingService();
    }

    /**
     * controller used to add a room booking in the system.
     * 
     * @param buildingName - building in the university.
     * @param roomName     - room in the building that needs to be booked.
     * @param userEmail    - user email who is booking.
     * @param checkInTime  - start date time of booking.
     * @param checkOutTime - end date time of booking.
     * @return true if the booking was successfull.
     */
    public boolean addBooking(String buildingName, String roomName, String userEmail, String checkInTime,
            String checkOutTime) {

        Booking newBooking = new Booking(buildingName, roomName, userEmail, checkInTime, checkOutTime);
        bookingService.addBooking(newBooking);
        return true;

    }

    /**
     * controller to remove booking in the system using booking Id.
     * 
     * @param index - Booking vakue index in the system.
     */
    public void removeBookingByIndex(int index) {
        bookingService.removeBookingByIndex(index);
    }

    /**
     * controller to remove booking using related property.
     * 
     * @param property - Relates to Booking, Room, User
     * @param value    - value for the given property.
     */
    public void removeBooking(PropertyToCheck property, String value) {
        bookingService.removeBookingByProperty(property, value);
    }

    /**
     * controller to view booking using related property.
     * 
     * @param property - Relates to Booking, Room, User
     * @param value    - value for the given property.
     * @return list of booking matching the given parameters.
     */
    public List<String> viewBooking(PropertyToCheck property, String value) {
        return bookingService.viewBookingByProperty(property, value);
    }

    /**
     * controller to view booking using building name and room name or number
     * property.
     * 
     * @param buildingName - name of the building in the university.
     * @param roomName     - name or number of the room.
     * @return list of booking matching the given parameters.
     */
    public List<String> viewBookingForRoom(String buildingName, String roomName) {
        return bookingService.viewBookingByRoom(buildingName, roomName);
    }

    // #region Free room in the given period

    public List<String> getAvailableRoomAtTime(String date, String time) {
        if (!date.matches(Constants.DATE_FORMAT_REGEX)) {
            throw new IllegalArgumentException("The entered date does not match the format YYYY-mm-dd");
        }
        if (!time.matches(Constants.TIME_FORMAT_REGEX)) {
            throw new IllegalArgumentException("The entered time does not match the format HH:mm (24 hours)");
        }
        return bookingService.listAvailableRoomAtTime(date, time);
    }

    public List<String> getAvailableRoomDuringPeriod(String date, String checkInTime, String checkOutTime) {
        return bookingService.listAvailableRoomAtPeriod(date, checkInTime, checkOutTime);
    }

    public List<String[]> getFreePeriod(String building, String room, String date) {
        if (!university.getBuildings().containsKey(building)) {
            throw new IllegalArgumentException("The entered building does not exists in the University");
        }
        if (university.getBuildings().containsKey(building)) {
            boolean roomExists = false;
            for (Room roomInBuilding : university.getBuildings().get(building).getRooms()) {
                if (roomInBuilding.getName().equals(room)) {
                    roomExists = true;
                }
            }
            if (!roomExists) {
                throw new IllegalArgumentException("The entered room does not exists in the building");
            }
        }
        if (!date.matches(Constants.DATE_FORMAT_REGEX)) {
            throw new IllegalArgumentException("The entered date does not match the format YYYY-mm-dd");
        }
        return bookingService.freePeriodForRoom(building, room, date);
    }

    // #endregion

    /**
     * @param date - date time for validating format.
     * @return - true if the date time was of required format.
     */
    public boolean checkIsValidDate(String date) {
        return bookingService.isValidDate(date);
    }

    /**
     * This controller saves booking related information in the system.
     */
    public void saveToFile() {
        bookingService.saveToFile();
    }

    /**
     * This controller loads booking related information into the system.
     */
    public University readFromFile() {
        return bookingService.updateFromFile();
    }
}
