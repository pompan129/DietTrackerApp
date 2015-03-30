package edu.uml.diet.model;

import javax.persistence.*;

/**
 * Container class to represent 1 food item
 * immutable
 */
@Entity
@Table(name = "FOOD")
public class BasicFood {
    private int id;
    private String name;
    private int calories;
    private int fat;
    private int carbs;
    private int protein;
    private double householdWeight;
    private String householdWeightDescription;

    /**
     * default constructor
     */
    public BasicFood() {
        this.name = null;
        this.calories = 0;
        this.fat = 0;
        this.carbs = 0;
        this.protein = 0;
        this.householdWeight = 0;
        this.householdWeightDescription = null;
    }

    /**
     * constructor
     *
     * @param name
     * @param calories
     * @param fat
     * @param carbs
     * @param protein
     */
    public BasicFood(String name, int calories, int fat, int carbs, int protein, double householdWeight, String householdWeightDescription) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        this.householdWeight = householdWeight;
        this.householdWeightDescription = householdWeightDescription;
    }

    /**
     * ID number for each user in the FOOD table - primary key
     *
     * @return user ID number as integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Sets the ID number for food
     *
     * @param id unique ID number for food as integer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter to return short String description of food item
     *
     * @return string representing name of food
     */
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * setter to set short String description of food item
     *
     * @param name name of food
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter to return calories of food item
     *
     * @return int value representing calories per serving
     */
    @Basic
    @Column(name = "calories")
    public int getCalories() {
        return calories;
    }

    /**
     * setter to set calories of food item
     *
     * @param calories number of calories per 100g in food
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * getter to return number of fat (g) per 100g in food
     *
     * @return int value representing fat per serving
     */
    @Basic
    @Column(name = "fat")
    public int getFat() {
        return fat;
    }

    /**
     * setter to set  of fat (g) per 100g in food
     *
     * @param fat number of fat (g) per 100g in food
     */
    public void setFat(int fat) {
        this.fat = fat;
    }

    /**
     * getter to return number of carbs (g) per 100g in food
     *
     * @return int value representing Carbohydrates per serving
     */
    @Basic
    @Column(name = "carbohydrates")
    public int getCarbs() {
        return carbs;
    }

    /**
     * setter to set number of carbs (g) per 100g in food
     *
     * @param carbs number (g) of carbs per 100g in food
     */
    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    /**
     * getter to return number of protein (g) per 100g in food
     *
     * @return int value representing protein per serving
     */
    @Basic
    @Column(name = "protein")
    public int getProtein() {
        return protein;
    }

    /**
     * setter to set number of protein (g) per 100g in food
     *
     * @param protein number (g) of protein per 100g in food
     */
    public void setProtein(int protein) {
        this.protein = protein;
    }

    /**
     * getter to return number of grams in typical household measurement for this
     * food. (ie. 8 grams in a tablespoon)
     *
     * @return typical household weight of BasicFood
     */
    @Basic
    @Column(name = "household_weight")
    public double getHouseholdWeight() {
        return householdWeight;
    }

    /**
     * setter to set number of grams in typical household measurement for this
     * food. (ie. 8 grams in a tablespoon)
     *
     * @param householdWeight typical household weight of BasicFood
     */
    public void setHouseholdWeight(double householdWeight) {
        this.householdWeight = householdWeight;
    }

    /**
     * getter to return a short description for the typical household measurement for this
     * food (tablespoon, cup, slice, oz. etc...).
     *
     * @return typical household weight description of BasicFood
     */
    @Basic
    @Column(name = "household_weight_description")
    public String getHouseholdWeightDescription() {
        return householdWeightDescription;
    }

    /**
     * setter to set a short description for the typical household measurement for this
     * food (tablespoon, cup, slice, oz. etc...).
     *
     * @param householdWeightDescription typical household weight description of BasicFood
     */
    public void setHouseholdWeightDescription(String householdWeightDescription) {
        this.householdWeightDescription = householdWeightDescription;
    }


    /**
     * equals method for comparing this object with another.
     *
     * @param o
     * @return boolean (true if they are equal)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicFood basicFood = (BasicFood) o;

        if (calories != basicFood.calories) return false;
        if (carbs != basicFood.carbs) return false;
        if (fat != basicFood.fat) return false;
        if (protein != basicFood.protein) return false;
        if (!name.equals(basicFood.name)) return false;
        if (householdWeight != basicFood.householdWeight) return false;
        if (!householdWeightDescription.equals(basicFood.householdWeightDescription)) return false;

        return true;
    }

    /**
     * generates hashcode for this object
     *
     * @return int representing hashcode for this object
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + calories;
        result = 31 * result + fat;
        result = 31 * result + carbs;
        result = 31 * result + protein;
        result = 31 * result + (int) householdWeight;
        return result;
    }
}
