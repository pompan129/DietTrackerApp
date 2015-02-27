package edu.uml.diet;

/**
 * Created by adil on 2/26/15.
 *
 * basic user class
 * holds relevent user info
 *
 * used for login page
 */
public class User {
    String firstname = "";
    String lastname = "";
    String email = "";
    String password = "";

    boolean loggedin = false;

    //empty constructor
    public void User() {};

    //setter methods
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
