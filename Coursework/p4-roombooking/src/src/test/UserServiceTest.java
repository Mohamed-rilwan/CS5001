package test;

import model.*;
import org.junit.jupiter.api.Test;
import service.BookingService;
import service.UniversityService;
import service.UserService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    String checkInTime = "2022-12-20 20:30";
    String checkOutTime = "2022-12-20 21:30";
    Room room = new Room("room1");
    Room room2 = new Room("room2");
    List<Room> rooms = new ArrayList<Room>();

    {
        rooms.add(room);
        rooms.add(room2);
    };

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

    Person person1 = new Person("name@domain.com", "name1");
    Person person2 = new Person("name@domain.com", "name2");
    Person person3 = new Person("name@domain.com", "name3");

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

    UserService userService = new UserService(university,universityService);

    @Test
    void setUniversity() {
        userService.setUniversity(university);
        List<String> response = userService.getAllPerson();
        List<String> personList = new ArrayList<>();

        for(String Key: university.getPersons().keySet()){
            personList.add("Email: " + university.getPersons().get(Key).email + ", Name: " + university.getPersons().get(Key).name);
        }
        assertTrue(userService.getAllPerson().equals(personList));
    }

    @Test
    void addUser() {

        userService.addUser("nameNew","nametest@domain.com");
        List<String> personList = new ArrayList<>();

        for(String Key: university.getPersons().keySet()){
            personList.add("Email: " + university.getPersons().get(Key).email + ", Name: " + university.getPersons().get(Key).name);
        }
        assertTrue(userService.getAllPerson().equals(personList));
    }

    @Test
    void removeUser() {
        userService.removeUser(person1.email);
        List<String> personList = new ArrayList<>();

        for(String Key: university.getPersons().keySet()){
            personList.add("Email: " + university.getPersons().get(Key).email + ", Name: " + university.getPersons().get(Key).name);
        }
        assertTrue(userService.getAllPerson().equals(personList));
    }

    @Test
    void getAllPerson() {
        userService.getAllPerson();
        List<String> personList = new ArrayList<>();

        for(String Key: university.getPersons().keySet()){
            personList.add("Email: " + university.getPersons().get(Key).email + ", Name: " + university.getPersons().get(Key).name);
        }
        assertTrue(userService.getAllPerson().equals(personList));
    }

    @Test
    void checkIfUserExists() {
       assertTrue(userService.checkIfUserExists(person1.email));
    }

    @Test
    void checkIfValidUserName() {
        assertTrue(userService.checkIfValidUserName(person1.getEmail()));
        assertFalse(userService.checkIfValidUserName("1@1.com"));
    }


}