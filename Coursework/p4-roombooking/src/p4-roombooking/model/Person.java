package model;

import java.io.Serializable;

public class Person implements Serializable {

    public String name;
    public String email;

    public Person(String email, String name) {
        this.isValidEmail(email);
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public void isValidEmail(String email) {
        if (!email.matches(Constants.VALID_EMAIL_REGEX)) {
            throw new IllegalArgumentException("The entered Email is not valid. Please check your input.");
        }
    }
}