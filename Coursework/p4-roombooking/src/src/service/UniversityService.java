package service;

import model.University;

public class UniversityService {
    
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    private BookingService bookingService;
    public BookingService getBookingService() {
        return bookingService;
    }

    private BuildingService buildingService;

    public BuildingService getBuildingService() {
        return buildingService;
    }

    public UniversityService(University universityModel){
        this.userService = new UserService(universityModel,this);
        this.bookingService = new BookingService(universityModel, this);
        this.buildingService = new BuildingService(universityModel,this);
    }

    public void refreshServices(University university){
        this.userService.setUniversity(university);
        this.bookingService.setUniversity(university);
        this.buildingService.setUniversity(university);
    }
}
