package edu.uml.diet;

/**
 * Created by Kurt Johnson on 2/8/2015.
 */
public class BasicFood {
    private String name;
    private int calories;


    public BasicFood(String name, int calories){
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }




}
