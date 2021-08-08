package com.andreig.model;


import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String userName;

    public User() {
    }

    public User(String firstName, String lastName, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String toString(){
        return "First name: " + getFirstName() + "\n Last name: " + getLastName() +
                "\n User name: " + getUserName();
    }
}
