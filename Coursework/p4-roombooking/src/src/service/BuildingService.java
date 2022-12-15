package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Building;
import model.Constants;
import model.Room;
import model.University;

/**
 * Assumption
 * 1. User info is not needed when adding a room.
 * 2. No two building can have same name.
 */
public class BuildingService {
    private University university;
   
    private static final String FILE_PATH = Constants.BUILDING_PROFILE_STORAGE_PATH;



    private UniversityService universityService;

    /**
     * Constructs the building service with initial values.
     * 
     * @param university -university information contians buildings.
     */
    public BuildingService(University university,UniversityService universityService) {
        this.university = university;
        this.universityService = universityService;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    /**
     * This method adds a building to the university.
     * 
     * @param name    - name of the building
     * @param address - address of the building
     */
    public void addBuilding(String name, String address) {
        if (university.getBuildings().containsKey(name)) {
            throw new IllegalArgumentException("Building with name " + name + " already registered in the system");
        }

        university.getBuildings().put(name, new Building(name, address));
    }

    /**
     * This method removes building from the university.
     * 
     * @param name - building name used to remove it from university
     */
    public void removeBuilding(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Enter a valid building name to be removed");
        } else if (! university.getBuildings().containsKey(name)) {
            throw new IllegalArgumentException("No building exists with name " + name);
        } else {
            university.getBuildings().remove(name);
        }
    }

    /**
     * This method lists all the buildings in the university
     * 
     * @return list of all building names and address
     */
    public List<String> viewAllBuilding() {
        List<String> allBuildingNames = new ArrayList<>();
        int index = 1;
        for (Building b : this.university.getBuildings().values()) {
            allBuildingNames.add(index + ".Building Name: " + b.name + ", Address: " + b.address);
            index++;
        }
        return allBuildingNames;
    }

    /**
     * This method lists all the buildings in the university.
     * 
     * @return list of all building names.
     */
    public List<String> listAllBuildingName() {
        List<String> allBuildingNames = new ArrayList<>();
        for (Building b :  university.getBuildings().values()) {
            allBuildingNames.add(b.name);
        }
        return allBuildingNames;
    }

    /**
     * This method retreives the building stored in the univerity.
     * 
     * @return buildings stored in the university
     */
    public Map<String, Building> getBuildings() {
        return this.university.getBuildings();
    }

    /**
     * Checks if the building name exisits in the system.
     * 
     * @param buildingName - name of the building to check
     * @return - true if the building exists.
     */
    public boolean isValidBuilding(String buildingName) {
        if (buildingName.isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_BUILDING_NAME);
        }
        Building building = getBuildings().get(buildingName);
        return building != null;
    }

    /**
     * This method adds rooms to the building information.
     * 
     * @param buildingName - name of the building into which rooms is to be added
     * @param roomName     - name of the room
     */
    public void addRoom(String buildingName, String roomName) {
        if (buildingName.isEmpty() || roomName.isEmpty()) {
            throw new IllegalArgumentException("Invalid Building name or Room Name");
        }
        Building building = checkIfBuildingExists(buildingName);
        for (Room room : building.rooms) {
            if (room != null && room.getName().equalsIgnoreCase(roomName)) {
                throw new IllegalArgumentException(
                        "Room with name " + roomName + " already exists in building " + buildingName);
            }
        }
        building.rooms.add(new Room(roomName));
    }

    /**
     * This method is used to remove a room in a building.
     *
     * @param buildingName - name of the building from which rooms is to be removed.
     * @param roomName     - name of the room.
     */
    public void removeRoom(String buildingName, String roomName) {
        if (buildingName.isEmpty() || roomName.isEmpty()) {
            throw new IllegalArgumentException("Invalid Building name or Room Name");
        }
        boolean removed = false;
        Building building = checkIfBuildingExists(buildingName);
        for (Room room : building.rooms) {
            if (room != null && room.getName().equalsIgnoreCase(roomName)) {
                building.rooms.remove(room);
                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new IllegalArgumentException(
                    "Room with name " + roomName + " does not exists in building " + buildingName);
        }
    }

    /**
     * This method checks and retrieves building information using name of the
     * building.
     * 
     * @param buildingName - name of building to check.
     * @return building related information for given parameter.
     */
    public Building checkIfBuildingExists(String buildingName) {
        if (buildingName.isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_BUILDING_NAME);
        }
        Building building = getBuildings().get(buildingName);
        if (building == null) {
            throw new IllegalArgumentException("No building with name " + buildingName + " exists");
        } else {
            return building;
        }
    }

    /**
     * This method returns all the rooms related information for a building.
     * 
     * @param buildingName - building from which rooms are to be fetched.
     * @return - list of all rooms in the building.
     */
    public List<String> getRoomsFromBuilding(String buildingName) {
        if (buildingName.isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_BUILDING_NAME);
        }
        List<String> rooms = new ArrayList();
        Building building =  university.getBuildings().get(buildingName);
        if (building != null) {
            for (Room room : building.rooms) {
                rooms.add(room.getName());
            }
        }
        return rooms;
    }

    /**
     * This method is used for saving all the university booking related
     * information.
     */
    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(new File(FILE_PATH));
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject( university.getBuildings());
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Internal Server Error. Please retry after rebooting the system");
        }
    }

    /**
     * This method is used to load all the university building related information.
     */
   
    public void updateFromFile() {
        try (
                FileInputStream fi = new FileInputStream(new File(Constants.UNIVERSITY_PROFILE_STORAGE_PATH));
                ObjectInputStream oi = new ObjectInputStream(fi);) {
            // Read objects
            University university = (University) oi.readObject();

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check if the file exists before loading.");
        } catch (IOException e) {
            System.out.println("Internal Server Error. Please retry after rebooting the system");
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf.getLocalizedMessage());
        }
    }
}
