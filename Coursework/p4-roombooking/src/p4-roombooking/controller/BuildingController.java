package controller;

import java.util.List;

import model.University;
import service.BookingService;
import service.BuildingService;
import service.UniversityService;

/**
 * This controller handles all the buidling and its sub rooms
 * as rooms are a part of building, a seperate controller was build to perform
 * all these operations.
 *
 */
public class BuildingController {

    public BuildingService buildingService;
    public UniversityService universityService;

    /**
     * Constructs building using university information.
     * 
     * @param universityModel
     */
    public BuildingController(University universityModel,UniversityService universityService) {
        buildingService = universityService.getBuildingService();

    }

    /**
     * This controller adds a building to the university.
     * 
     * @param name    - name of the building
     * @param address - addresss of the building
     */
    public void addBuilding(String name, String address) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Building Name cannot be empty. Please enter a valid Name");
        }
        if (address.isEmpty()) {
            throw new IllegalArgumentException("Building Address cannot be empty. Please enter a valid Address");
        }
        buildingService.addBuilding(name, address);
    }

    /**
     * This controller removes a building in the university.
     * As the building names cannot be duplicated, it can be used to delete the
     * building.
     * 
     * @param name- building name that needs to be removed.
     */
    public void removeBuilding(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Please Enter a Valid Building Name");
        }
        buildingService.removeBuilding(name);
    }

    /**
     * This controller retrieves all buidling information used for viewing.
     * 
     * @return list of all building names and address.
     */
    public List<String> getAllBuilding() {
        return buildingService.viewAllBuilding();
    }

    /**
     * This controller retrieves all buidling name information used for viewing.
     * 
     * @return list of all building names.
     */
    public List<String> getAllBuildingNames() {
        return buildingService.listAllBuildingName();
    }

    /**
     * Used to validate if a building is present in the system.
     * 
     * @param buildingName - name of the building for checking
     * @return true if the building exists in the system.
     */
    public boolean checkIfBuildingExists(String buildingName) {
        if (buildingName.isEmpty()) {
            throw new IllegalArgumentException("Please Enter a Valid Building Name");
        }
        return buildingService.isValidBuilding(buildingName);
    }

    /**
     * This controller adds a room to a building.
     * 
     * @param buildingName - name of the building.
     * @param roomName     - name of the room.
     */
    public void addRoomToBuilding(String buildingName, String roomName) {
        if (buildingName.isEmpty()) {
            throw new IllegalArgumentException("Please Enter a Valid Building Name");
        }
        if (roomName.isEmpty()) {
            throw new IllegalArgumentException("Please Enter a Valid Room Name or Number");
        }
        buildingService.addRoom(buildingName, roomName);
    }

    /**
     * This controller is used to remove a room from the building.
     * 
     * @param buildingName - name of the building.
     * @param roomName     - name of the room.
     */
    public void removeRoomFromBuilding(String buildingName, String roomName) {
        if (buildingName.isEmpty()) {
            throw new IllegalArgumentException("Please Enter a Valid Building Name");
        }
        if (roomName.isEmpty()) {
            throw new IllegalArgumentException("Please Enter a Valid Room Name or Number");
        }
        buildingService.removeRoom(buildingName, roomName);
    }

    /**
     * This controller is used to retrieve all rooms in a building/
     * 
     * @param buildingName - name of the building.
     * @return list of room names in the building
     */
    public List<String> getAllRoomFromBuilding(String buildingName) {
        if (buildingName.isEmpty()) {
            throw new IllegalArgumentException("Please Enter a Valid Building Name");
        }
        return buildingService.getRoomsFromBuilding(buildingName);
    }

    /**
     * This method saves building related information in the system.
     */
    public void saveToFile() {
        buildingService.saveToFile();
    }

    /**
     * This method loads building related information into the system.
     */
    public void readFromFile() {
        buildingService.updateFromFile();
    }
}
