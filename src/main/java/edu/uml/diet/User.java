package edu.uml.diet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Maps object properties to database fields
 * for User object
 */
@Entity
@Table(name="USERS")
public class User {
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    /**
     * ID number for each user in the USER table - primary key
     *
     * @return user ID number as integer
     */
    @Id
    @Column(name="id", nullable = false)
    public int getId(){
        return id;
    }

    /**
     * Sets the ID number for user
     *
     * @param id unique ID number for user as integer
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @return the user's username
     */
    @Basic
    @Column(name="username")
    public String getUserName(){
        return userName;
    }

    /**
     * Set the user's username
     *
     * @param userName
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     *
     * @return the user's password
     */
    @Basic
    @Column(name="password")
    public String getPassword(){
        return password;
    }

    /**
     *Set the user's password
     *
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     *
     * @return the user's first name
     */
    @Basic
    @Column(name="first_name")
    public String getFirstName(){
        return firstName;
    }

    /**
     * Sets the user's first name
     * @param firstName
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     *
     * @return the user's last name
     */
    @Basic
    @Column(name="last_name")
    public String getLastName(){
        return lastName;
    }

    /**
     * Set the user's last name
     *
     * @param lastName
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     *
     * @param o  object being tested for quality to User object
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(this ==o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if(id != user.id) return false;
        if(userName != null ? !userName.equals(user.userName) : user.userName != null){
            return false;
        }
        if(password != null ? !password.equals(user.password) : user.password != null){
            return false;
        }
        if(firstName != null ? !firstName.equals(user.firstName) : user.firstName != null){
            return false;
        }
        if(lastName != null ? !lastName.equals(user.lastName) : user.lastName != null){
            return false;
        }

        return true;
    }

    /**
     *
     * @return formatted user object, does NOT return password
     */
    @Override
    public String toString(){
        return "User{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
