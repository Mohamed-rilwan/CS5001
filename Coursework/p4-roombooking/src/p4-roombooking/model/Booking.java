package model;

import java.io.Serializable;

public class Booking implements Serializable {

    private String building;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    private String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    private String person;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    private String checkInTime;
    private String checkOutTime;

    public Booking(String building, String room, String person, String checkInDate, String checkOutDate) {
        this.building = building;
        this.person = person;
        this.room = room;
        this.checkInTime = checkInDate;
        this.checkOutTime = checkOutDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }
}