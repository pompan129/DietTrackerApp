package edu.uml.diet.model;

import javax.persistence.*;
import java.util.Collection;

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
    private Double weight;
    private Double goalWeight;
    private Double height;
    @OneToMany(mappedBy = "user" )
    private Collection<Day> days;

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

    @Basic
    @Column(name="weight_lbs")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name="goal_weight_lbs")
    public Double getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(Double goalWeight) {
        this.goalWeight = goalWeight;
    }

    @Basic
    @Column(name="height_ft")
    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @OneToMany(mappedBy = "user" )
    public Collection<Day> getDays() {
        return days;
    }

    public void setDays(Collection<Day> days) {
        this.days = days;
    }

    /**
     *
     * @param o  object being tested for quality to User object
     * @return true if objects are equal, false otherwise
     */

    /**
     * method to calculate a person's BMI based on the formula
     * English Units: BMI = Weight (lb) / (Height (in) x Height (in)) x 703
     * @return
     */
    @Transient
    public Double getBMI(){
        Double heightInches = height * 12.0;
        return weight/(heightInches * heightInches) * 703.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (days != null ? !days.equals(user.days) : user.days != null) {return false;}
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {return false;}
        if (goalWeight != null ? !goalWeight.equals(user.goalWeight) : user.goalWeight != null) {return false;}
        if (height != null ? !height.equals(user.height) : user.height != null) {return false;}
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {return false;}
        if (password != null ? !password.equals(user.password) : user.password != null) {return false;}
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) {return false;}
        if (weight != null ? !weight.equals(user.weight) : user.weight != null) {return false;}

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (goalWeight != null ? goalWeight.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (days != null ? days.hashCode() : 0);
        return result;
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
