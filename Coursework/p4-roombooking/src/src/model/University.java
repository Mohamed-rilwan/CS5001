package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class University implements Serializable {
    /*
     * Building names need to be unique, so they are added as hashmap.
     * No two building can have same name.
     */
    private LinkedHashMap<String, Building> buildings;

    /**
     * @return buildins available in the university
     */
    public LinkedHashMap<String, Building> getBuildings() {
        return buildings;
    }

    /**
     * @param buildings - stores the list of building in the university
     */
    public void setBuildings(LinkedHashMap<String, Building> buildings) {
        this.buildings = buildings;
    }

    /*
     * Person email need to be unique, so they are added as hashmap.
     * No two person can have same email.
     */
    private LinkedHashMap<String, Person> persons;

    /**
     * @return users registered in the university.
     */
    public LinkedHashMap<String, Person> getPersons() {
        return persons;
    }

    public void setPersons(LinkedHashMap<String, Person> persons) {
        this.persons = persons;
    }

    /*
     * Booking are collection of information, so they are added as arraylist.
     */
    private List<Booking> bookings;

    /**
     * @return the list of booking in the university
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * @param bookings - used to set the building information in the university
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    // /**
    //  * @param buildings - building information in the university
    //  * @param persons   - registered users in the university
    //  * @param bookings  - room booking information in the university.
    //  */
    // public University(LinkedHashMap<String, Building> buildings,
    //         LinkedHashMap<String, Person> persons,
    //         List<Booking> bookings) {
    //     this.buildings = buildings;
    //     this.persons = persons;
    //     this.bookings = bookings;
    // }
    

    public University() {
        this.buildings = new LinkedHashMap<>();
        this.persons = new LinkedHashMap<>();
        this.bookings = new ArrayList<>();
    }

    public University(LinkedHashMap<String,Building> buildings,LinkedHashMap<String,Person> persons,ArrayList<Booking> bookings){
        this.buildings = buildings;
        this.persons = persons;
        this.bookings = bookings;
    }
}