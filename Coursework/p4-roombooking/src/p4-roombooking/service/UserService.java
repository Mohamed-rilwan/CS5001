package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Constants;
import model.Person;
import model.University;

public class UserService {
    /**
     * The key for the map is the email of the user.
     * Preventing duplication of email.
     */
    private Map<String, Person> persons = new HashMap<>();
    private University university = new University();
    private UniversityService universityService;

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setPersons(Map<String, Person> persons) {
        this.persons = persons;
    }

    public UserService(University university,UniversityService universityService) {
        this.university = university;
        this.persons = university.getPersons();
        this.universityService= universityService;
    }

    private String FILE_PATH = Constants.USER_PROFILE_STORAGE_PATH;

    public void addUser(String name, String email) {
        if (persons.containsKey(email)) {
            throw new IllegalArgumentException("User already registered in the system");
        }
        persons.put(email, new Person(email, name));
    }

    public void removeUser(String email) {
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Enter a valid user email to be removed");
        } else if (!persons.containsKey(email)) {
            throw new IllegalArgumentException("No user exists with email: " + email);
        } else {
            persons.remove(email);
        }
    }

    public List<String> getAllPerson() {
        List<String> users = new ArrayList<>();
        int index = 1;
        for (Person p : persons.values()) {
            users.add("Email: " + p.email + ", Name: " + p.name);
            index++;
        }
        return users;
    }

    public List<String> getAllPersonEmail() {
        List<String> users = new ArrayList<>();
        for (Person p : persons.values()) {
            users.add(p.email);
        }
        return users;
    }

    public boolean checkIfUserExists(String email) {
        for (Person p : persons.values()) {
            if (p.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfValidUserName(String userName) {
        return userName.matches(Constants.VALID_EMAIL_REGEX);
    }

    /**
     * The main save file method for saving all the university related information
     */
    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(new File(FILE_PATH));
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(persons);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public void updateFromFile() {
        try (
                FileInputStream fi = new FileInputStream(new File(FILE_PATH));
                ObjectInputStream oi = new ObjectInputStream(fi);) {
            // Read objects
            setPersons((Map<String, Person>) oi.readObject());
            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf.getLocalizedMessage());
        }
    }

    public void updateFromFile1() {
        try (
                FileInputStream fi = new FileInputStream(new File(Constants.UNIVERSITY_PROFILE_STORAGE_PATH));
                ObjectInputStream oi = new ObjectInputStream(fi);) {
            // Read objects
            University university = (University) oi.readObject();
            persons = (university.getPersons());
            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf.getLocalizedMessage());
        }
    }

}
