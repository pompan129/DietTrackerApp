package edu.uml.diet.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * class to represent a meal in a Day (breakfast, lunch, diner, snacks)
 */
@Entity
@Table(name = "MEALS", schema = "", catalog = "DietTracker")
public class Meal {
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;
    @OneToMany(mappedBy = "meal" )
    private Collection<Portion> portions;


    /**
     * method to unique integer ID for this object
     *
     * @return int ID for this object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    public Integer getId() {
        return id;
    }

    /**
     * method to set unique integer ID for this object
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * method to get String description for this object (ie Breakfast)
     *
     * @return String - description for this object (ie Breakfast)
     */
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * method to set String description for this object (ie Breakfast)
     *
     * @param name - description for this object (ie Breakfast)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method to get Day object associated with this meal.
     *
     * @return Day
     */
    @ManyToOne
    @JoinColumn(name = "day_id")
    public Day getDay() {
        return day;
    }

    /**
     * method to set Day object associated with this meal.
     *
     * @param day
     */
    public void setDay(Day day) {
        this.day = day;
    }

    /**
     * method to return a List of Portion associated with this Meal
     *
     * @return Collection<Portion> containing all the Portions in this meal
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meal" )
    public Collection<Portion> getPortions() {
        return portions;
    }

    /**
     * method to set a List of Portion associated with this Meal
     *
     * @param portions - a Collection of Portion objects to be associated with this meal
     */
    public void setPortions(Collection<Portion> portions) {
        for(Portion portion : portions){
            portion.setMeal(this);
        }
        this.portions = portions;
    }

    /**
     * method to set a Portion to be associated with this Meal
     *
     * @param portion
     */
    @Transient
    public void setPortion(Portion portion) {
        if(portions == null){portions = new ArrayList<Portion>();}
        this.portions.add(portion);
    }

    /**
     * method to return the  sum total of all calories portions for this meal
     *
     * @return int = total calories in meal.
     */
    @Transient
    public int getCalories(){
        int totalCalories = 0;
        for(Portion portion: portions){
            totalCalories += portion.getCalories();
        }

        return totalCalories;
    }

    /**
     * equals method to compare this object with another
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;

        Meal meal = (Meal) o;

        if (day != null ? !day.equals(meal.day) : meal.day != null) return false;
        if (id != null ? !id.equals(meal.id) : meal.id != null) return false;
        if (name != null ? !name.equals(meal.name) : meal.name != null) return false;
        if (portions != null ? !portions.equals(meal.portions) : meal.portions != null) return false;

        return true;
    }

    /**
     * method to generate a hashcode for this object
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (portions != null ? portions.hashCode() : 0);
        return result;
    }
}
