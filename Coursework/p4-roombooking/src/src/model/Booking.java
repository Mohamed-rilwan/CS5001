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

    /**
     * @param building building name
     * @param room room name
     * @param person used email
     * @param checkInDate start date time of booking
     * @param checkOutDate end date time of booking
     */
    public Booking(String building, String room, String person, String checkInDate, String checkOutDate) {
        this.building = building;
        this.person = person;
        this.room = room;
        this.checkInTime = checkInDate;
        this.checkOutTime = checkOutDate;
    }

    /**
     * @return check in time for a booking
     */
    public String getCheckInTime() {
        return checkInTime;
    }

    /**
     * @return check out time of a booking
     */
    public String getCheckOutTime() {
        return checkOutTime;
    }
}