package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Building implements Serializable {
    public List<Room> rooms;
    public String name;
    public String address;

    
    /**
     * This constructor accepts only the name and address
     * and constructs a building with no rooms
     * 
     * @param rooms - all rooms in the building
     * @param name    - name of the building
     * @param address - address of the building
     */
    private Building(List<Room> rooms, String name, String address){
        this.rooms = rooms;
        this.name = name;
        this.address = address;
    }

    /**
     * This constructor accepts only the name and address
     * and constructs a building with no rooms
     * @param name - name of the building
     * @param address - address of the building
     */
    public Building(String name, String address){
        this.rooms = new ArrayList<>();
        this.name = name;
        this.address = address;
    }

    public static Building createBuilding(ArrayList<Room> rooms, String name, String address) {
        return new Building(rooms, name, address);
    }

    
    /**
     * This method returns all th rooms in the building.
     * 
     * @return list of rooms in the building
     */
    public List<Room> getRooms() {
        return rooms;
    }

}