package edu.uml.diet.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;

public class DayTest extends TestCase {

    Meal meal_1;
    Meal meal_2;
    int id;
    String name;
    Portion portion_1, portion_2;
    BasicFood food;
    String foodName;
    int calories;
    int fat;
    int carbs;
    int protein;
    double householdWeight;
    String householdWeightDescription;
    Day day;

    @Before
    public void setUp() {
        id = 7;
        name = "LUNCH";
        foodName = "TEST_FOOD";
        calories = 150;
        fat = 5;
        protein = 6;
        carbs = 7;
        householdWeight = 3.0;
        householdWeightDescription = "some oz.";
        food = new BasicFood(foodName, calories, fat, carbs, protein, householdWeight, householdWeightDescription);
        portion_1 = new Portion(food);
        portion_2 = new Portion(food);
        portion_2.setPortionSize(2.0);
        meal_1 = new Meal();
        meal_1.setPortion(portion_1);
        meal_2 = new Meal();
        meal_2.setPortion(portion_2);


    }

    @Test
    public void testGetId() throws Exception {

    }

    @Test

    public void testSetId() throws Exception {

    }

    @Test
    public void testGetDate() throws Exception {

    }

    @Test
    public void testSetDate() throws Exception {

    }

    public void testGetMeals() throws Exception {

    }

    public void testSetMeals() throws Exception {

    }

    @Test
    public void testGetUser() throws Exception {

    }

    @Test
    public void testSetUser() throws Exception {

    }

    @Test
    public void testGetCalories() throws Exception {
        day = new Day();
        ArrayList<Meal> mealArrayList= new ArrayList<>();
        mealArrayList.add(meal_1);
        mealArrayList.add(meal_2);
        day.setMeals(mealArrayList);
        int calories = meal_1.getCalories() + meal_2.getCalories();
        assertTrue("calories are equal", calories == day.getCalories());

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }
}