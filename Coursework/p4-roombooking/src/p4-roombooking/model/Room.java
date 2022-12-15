package model;

import java.io.Serializable;

public class Room implements Serializable{

    private String name;
    private boolean isBooked = false;

    public Room(String name){
        this.name = name;
        this.isBooked = false;
    }

    public Room(String name, boolean isBooked){
        this.name = name;
        this.isBooked = isBooked;
    }

    public String getName(){
        return this.name;
    }
}