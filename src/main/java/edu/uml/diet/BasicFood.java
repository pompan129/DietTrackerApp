package edu.uml.diet;

/**
 * Container class to represent 1 food item
 * immutable
 *
 */
public class BasicFood {
    private String name;
    private final int calories;
    private final int fat;
    private final int carbs;
    private final int protein;

    /**
     * constructor
     * @param name
     * @param calories
     * @param fat
     * @param carbs
     * @param protein
     */
    public BasicFood(String name, int calories, int fat, int carbs, int protein){
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    /**
     *
     * @return string representing name of food
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return int value representing calories per serving
     */
    public int getCalories() { return calories; }

    /**
     *
     * @return int value representing fat per serving
     */
    public int getFat() { return fat; }

    /**
     *
     * @return int value representing Carbohydrates per serving
     */
    public int getCarbs() {return carbs; }

    /**
     *
     * @return int value representing protein per serving
     */
    public int getProtein() { return protein; }


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
