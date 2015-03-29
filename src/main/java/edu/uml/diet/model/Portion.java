package edu.uml.diet.model;

import javax.persistence.*;

/**
 * Class that represents a Portion of food consumed in a Meal.
 */
@Entity
@Table(name = "PORTIONS", schema = "", catalog = "DietTracker")
public class Portion {
    private Integer id;
    private Double portionSize;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_id")
    private BasicFood food;
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    /**
     * constructor with no parameters. sets portion size to default of 1.0
     */
    public Portion() {
        this.portionSize = 1.0;
    }

    ; //for persistence mapping

    /**
     * constructor method. sets food type and portions size to default of 1.0
     *
     * @param food
     */
    public Portion(BasicFood food) {
        this.food = food;
        this.portionSize = 1.0;
    }

    /**
     * method to return unique integer ID for this object
     *
     * @return int - unique ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
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
     * method to return BasicFood object associated with this Portion
     *
     * @return BasicFood - consumed in this Portion
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_id")
    public BasicFood getFood() {
        return food;
    }

    /**
     * method to set BasicFood object associated with this Portion
     *
     * @param food - BasicFood object associated with this Portion
     */
    public void setFood(BasicFood food) {
        this.food = food;
    }

    /**
     * method to get size of this Portion object
     *
     * @return Double - represents size of Portion
     */
    @Basic
    @Column(name = "portion_size")
    public Double getPortionSize() {
        return portionSize;
    }

    /**
     * method to get size of this Portion object
     *
     * @param portionSize Double - represents size of Portion
     */
    public void setPortionSize(Double portionSize) {
        this.portionSize = portionSize;
    }

    /**
     * method to return Meal object associated with this Portion
     *
     * @return Meal that contains this Portion
     */
    @ManyToOne
    @JoinColumn(name = "meal_id")
    public Meal getMeal() {
        return meal;
    }

    /**
     * method to set Meal object associated with this Portion
     *
     * @param meal Meal that contains this Portion
     */
    public void setMeal(Meal meal) {
        this.meal = meal;
    }


    /**
     * method to return calories present in this portion size or this food.
     * based on common household wieght & Portion size. If no household weight present in Persistence, then calories
     * based on 100 grams.
     *
     * @return int number of calories in Portion based on portion size & householdweight
     */
    @Transient
    public int getCalories() {
        double householdWeight = food.getHouseholdWeight();

        if (householdWeight == 0) {
            householdWeight = 100.0;
        }
        return (int) ((food.getCalories() / 100.0) * householdWeight * portionSize);
    }

    /**
     * Method to return the common household weight of a food (ie. 1 cup, 1 tablespoon)
     * if there is no common wieht in the DB the method will return the standard 100 grams
     *
     * @return String representing household weight description
     */
    @Transient
    public String getHouseholdWeightDescription() {
        String description = food.getHouseholdWeightDescription();
        if (description.equals("0")) {
            description = "100 grams";
        }

        return description;
    }


    /**
     * equals method to compare this object with another
     *
     * @param o
     * @return boolean (true if objects are equal)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portion portion = (Portion) o;

        if (id != null ? !id.equals(portion.id) : portion.id != null) return false;
        if (meal != null ? !meal.equals(portion.meal) : portion.meal != null) return false;
        if (portionSize != null ? !portionSize.equals(portion.portionSize) : portion.portionSize != null) return false;

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
        result = 31 * result + (portionSize != null ? portionSize.hashCode() : 0);
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        return result;
    }
}
