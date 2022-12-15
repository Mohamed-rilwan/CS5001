package controller;

import java.util.List;

import model.University;
import service.UniversityService;
import service.UserService;

/**
 * Represents a user controller used to perform
 * actions related to users in the university.
 */
public class UserController {

    public UserService userService;
    public UniversityService universityService;

    /**
     * Constructs user controller
     * 
     * @param universityModel - all university related information.
     */
    public UserController(University universityModel,UniversityService universityService) {
        userService = universityService.getUserService();
    }

    /**
     * This method adds user to the system.
     * 
     * @param email -Email id of the user
     * @param name  - Name of the user
     */
    public void addUsers(String email, String name) {
        userService.addUser(email, name);
    }

    /**
     * This method removes a user from the system.
     * 
     * @param email :The email needed for the user to be removed is entered
     */
    public void removeUser(String email) {
        userService.removeUser(email);
    }

    /**
     * This method lists emails of all users in th system.
     * 
     * @return Returns an array list of all users,
     *         which needs to be handled from the view.
     */
    public List<String> getAllUserEmail() {
        return userService.getAllPersonEmail();
    }

    /**
     * This controller checks if the user is registered in the system.
     * 
     * @param email - email of the user.
     * @return true if the user is registered.
     */
    public boolean checkUser(String email) {
        return userService.checkIfUserExists(email);
    }

    /**
     * This method saves user related information in the system.
     */
    public void saveToFile() {
        userService.saveToFile();
    }

    /**
     * This method loads user related information into the system.
     */
    public void readFromFile() {
        userService.updateFromFile1();
    }
}
