package edu.uml.diet;

import javax.persistence.*;

/**
 * Container class to represent 1 food item
 * immutable
 *
 */
@Entity
@Table(name="FOOD")
public class BasicFood {
    private int id;
    private String name;
    private int calories;
    private int fat;
    private int carbs;
    private int protein;

    /**
     * default constructor
     */
    public BasicFood() {
        this.name = null;
        this.calories = 0;
        this.fat = 0;
        this.carbs = 0;
        this.protein = 0;
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
    public BasicFood(String name, int calories, int fat, int carbs, int protein) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    /**
     * ID number for each user in the FOOD table - primary key
     *
     * @return user ID number as integer
     */
    @Id
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
     * @return string representing name of food
     */
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * @param name name of food
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return int value representing calories per serving
     */
    @Basic
    @Column(name = "calories")
    public int getCalories() {
        return calories;
    }

    /**
     * @param calories number of calories per 100g in food
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * @return int value representing fat per serving
     */
    @Basic
    @Column(name = "fat")
    public int getFat() {
        return fat;
    }

    /**
     * @param fat number of fat (g) per 100g in food
     */
    public void setFat(int fat) {
        this.fat = fat;
    }

    /**
     * @return int value representing Carbohydrates per serving
     */
    @Basic
    @Column(name = "carbohydrates")
    public int getCarbs() {
        return carbs;
    }

    /**
     * @param carbs number (g) of carbs per 100g in food
     */
    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    /**
     * @return int value representing protein per serving
     */
    @Basic
    @Column(name = "protein")
    public int getProtein() {
        return protein;
    }

    /**
     * @param protein number (g) of protein per 100g in food
     */
    public void setProtein(int protein) {
        this.protein = protein;
    }


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

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + calories;
        result = 31 * result + fat;
        result = 31 * result + carbs;
        result = 31 * result + protein;
        return result;
    }
}
