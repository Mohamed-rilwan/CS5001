package test;

import model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import service.BookingService;
import service.UniversityService;


class BookingServiceTest {

    String checkInTime = "2022-12-20 20:30";
    String checkOutTime = "2022-12-20 21:30";
    Room room = new Room("room1");
    Room room2 = new Room("room2");
    List<Room> rooms = new ArrayList<Room>();

    {
        rooms.add(room);
        rooms.add(room2);
    }

    ;


    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    Building building1 = new Building(rooms, "building1", "address1");
    Building building2 = new Building(rooms, "building2", "address2");
    Building building3 = new Building(rooms, "building3", "address3");

    LinkedHashMap<String, Building> buildings = new LinkedHashMap<>();

    {
        buildings.put(building1.name, building1);
        buildings.put(building2.name, building2);
        buildings.put(building3.name, building3);
    }

    Person person1 = new Person("name1@domain.com", "name1");
    Person person2 = new Person("name2@domain.com", "name2");
    Person person3 = new Person("name3@domain.com", "name3");

    LinkedHashMap<String, Person> persons = new LinkedHashMap<>();

    {
        persons.put(person1.email, person1);
        persons.put(person2.email, person2);
        persons.put(person3.email, person3);
    }

    Booking booking = new Booking(building1.name, room.getName(), person1.email, checkInTime, checkOutTime);
    Booking booking1 = new Booking(building2.name, room.getName(), person2.email, checkInTime, checkOutTime);

    ArrayList<Booking> bookings = new ArrayList<>();

    University university = new University(buildings, persons, bookings);

    UniversityService universityService = new UniversityService(university);
    BookingService bookingService = new BookingService(university, universityService);


    @Test
    @DisplayName("Adding booking when no building, rooms or user throws error")
    void addBookingErrors() {
        //Adding booking without adding any building.
        University university = new University(new LinkedHashMap<String, Building>(), persons, bookings);
        UniversityService universityService = new UniversityService(university);
        bookingService = new BookingService(university, universityService);
        assertThrows(IllegalArgumentException.class, () -> bookingService.addBooking(booking));
    }

    @Test
    @DisplayName("Removing booking using index after adding a booking, returns empty list")
    void removeBookingByIndex() {
        bookingService.setUniversity(university);
        bookingService.addBooking(booking);
        List<Booking> responseBeforeDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseBeforeDeleting.size());
        bookingService.removeBookingByIndex(0);
        List<Booking> responseAfterDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(0, responseAfterDeleting.size());
    }

    @Test
    @DisplayName("Removing booking using booking info after adding a booking, returns empty list")
    void removeBooking() {
        bookingService.setUniversity(university);
        bookingService.addBooking(booking);
        List<Booking> responseBeforeDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseBeforeDeleting.size());
        bookingService.removeBooking(booking);
        List<Booking> responseAfterDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(0, responseAfterDeleting.size());
    }

    @Test
    @DisplayName("Removing booking using building information after adding a booking, returns empty list")
    void removeBookingByBuilding() {
        bookingService.setUniversity(university);
        bookingService.addBooking(booking);
        List<Booking> responseBeforeDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseBeforeDeleting.size());
        bookingService.removeBookingByBuilding(booking.getBuilding());
        List<Booking> responseAfterDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(0, responseAfterDeleting.size());
    }

    @Test
    @DisplayName("Removing booking using user information after adding a booking, returns empty list")
    void removeBookingByUser() {
        bookingService.setUniversity(university);
        bookingService.addBooking(booking);
        List<Booking> responseBeforeDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseBeforeDeleting.size());
        bookingService.removeBookingByUser(booking.getPerson());
        List<Booking> responseAfterDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(0, responseAfterDeleting.size());

        //If tested against rempove by building
        bookingService.addBooking(booking);
        List<Booking> responseBeforeDeleting1 = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseBeforeDeleting.size());
        bookingService.removeBookingByUser("unknown@gmail.com");
        List<Booking> responseAfterDeleting1 = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseAfterDeleting1.size());

    }

    @Test
    @DisplayName("Removing booking using one of the booking class property")
    void removeBookingByProperty() {

        //Deleting using building information
        bookingService.addBooking(booking);
        List<Booking> responseBeforeDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseBeforeDeleting.size());
        bookingService.removeBookingByProperty(Constants.PropertyToCheck.BUILDING, booking.getBuilding());
        List<Booking> responseAfterDeleting = bookingService.viewBookingByUser(person1.email);
        assertEquals(0, responseAfterDeleting.size());

        //Deleting using user information
        bookingService.addBooking(booking);
        List<Booking> responseBeforeDeleting1 = bookingService.viewBookingByUser(person1.email);
        assertEquals(1, responseBeforeDeleting.size());
        bookingService.removeBookingByProperty(Constants.PropertyToCheck.PERSON, booking.getPerson());
        List<Booking> responseAfterDeleting2 = bookingService.viewBookingByUser(person1.email);
        assertEquals(0, responseAfterDeleting2.size());

    }

    @Test
    @DisplayName("Viewing booking using room of the building class property")
    void viewBookingByRoom() {
        bookingService.addBooking(booking);
        List<String> response = bookingService.viewBookingByRoom(booking.getBuilding(), booking.getRoom());
        assertFalse(response.isEmpty());
        List<String> expected = new ArrayList<>();
        {
            expected.add("BookingId: " + 0 + " | " + "Building: " + booking.getBuilding() + " | "
                    + "Room: " + booking.getRoom() + " | "
                    + "User: " + booking.getPerson() + " | " + "Check-In: " + booking.getCheckInTime() + " | "
                    + "Check-Out: " + booking.getCheckOutTime());
        }
        ;

        assertEquals(expected, response);
    }


    @Test
    @DisplayName("Viewing booking using one of the booking class property")
    void viewBookingByProperty() {
        //Inserting a booking with set of values and fetching using building information
        bookingService.addBooking(booking);
        List<String> response = bookingService.viewBookingByProperty(Constants.PropertyToCheck.BUILDING, booking.getBuilding());
        assertFalse(response.isEmpty());
        List<String> expected = new ArrayList<>();
        {
            expected.add("BookingId: " + 0 + " | " + "Building: " + booking.getBuilding() + " | "
                    + "Room: " + booking.getRoom() + " | "
                    + "User: " + booking.getPerson() + " | " + "Check-In: " + booking.getCheckInTime() + " | "
                    + "Check-Out: " + booking.getCheckOutTime());
        }
        ;
        assertEquals(expected, response);

        //Inserting a booking with different set of values and fetching using person information
        bookingService.addBooking(booking1);
        List<String> response1 = bookingService.viewBookingByProperty(Constants.PropertyToCheck.PERSON, booking.getPerson());
        assertFalse(response1.isEmpty());
        List<String> expected1 = new ArrayList<>();
        {
            expected.add("BookingId: " + 0 + " | " + "Building: " + booking.getBuilding() + " | "
                    + "Room: " + booking.getRoom() + " | "
                    + "User: " + booking.getPerson() + " | " + "Check-In: " + booking.getCheckInTime() + " | "
                    + "Check-Out: " + booking.getCheckOutTime());
            expected.add("BookingId: " + 1 + " | " + "Building: " + booking1.getBuilding() + " | "
                    + "Room: " + booking1.getRoom() + " | "
                    + "User: " + booking1.getPerson() + " | " + "Check-In: " + booking1.getCheckInTime() + " | "
                    + "Check-Out: " + booking1.getCheckOutTime());
        }
        ;
    }


    @Test
    @DisplayName("Check overlapping throws exception if the booking checkIn and Checkout time overlaps")
    void checkOverlappingBooking() {
        bookingService.addBooking(booking);
        assertThrows(IllegalArgumentException.class, () -> bookingService.checkOverlappingBooking(booking));
    }

    @org.junit.jupiter.api.Test
    void isValidDate() {
        boolean response = bookingService.isValidDate("2022-10-20 20:00");
        assertTrue(response);

        boolean response1 = bookingService.isValidDate("2022-10-2020:00");
        assertFalse(response1);

        //Year value must start with 2 - year 2000 and above only.
        boolean response3 = bookingService.isValidDate("1111-10-2020:00");
        assertFalse(response3);

    }

    @org.junit.jupiter.api.Test
    void checkIfRoomAndBuildingExists() {
        assertThrows(IllegalArgumentException.class, () -> bookingService.checkIfRoomAndBuildingExists("buildingDoesntExist", "RoomDoesntExist"));
    }
}


