package edu.uml.diet.model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class MealTest extends TestCase {

    Meal meal;
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
        day = new Day();
        day.setId(5);
        day.setDate(Date.valueOf("2015-3-21"));

    }

    @Test
    public void testGetId() throws Exception {
        meal = new Meal();
        meal.setId(id);
        assertTrue("id is equal", meal.getId() == id);
    }

    @Test
    public void testSetId() throws Exception {
        meal = new Meal();
        meal.setId(id);
        assertTrue("id is equal", meal.getId() == id);
    }

    @Test
    public void testGetName() throws Exception {
        meal = new Meal();
        meal.setName(name);
        assertEquals("name is equal", meal.getName(), name);
    }

    public void testSetName() throws Exception {
        meal = new Meal();
        meal.setName(name);
        assertEquals("name is equal", meal.getName(),name);
    }

    @Test
    public void testGetDay() throws Exception {
        meal = new Meal();
        meal.setDay(day);
        assertEquals("day is equal", meal.getDay(),day);

    }

    @Test
    public void testSetDay() throws Exception {
        meal = new Meal();
        meal.setDay(day);
        assertEquals("day is equal", meal.getDay(),day);
    }

    @Test
    public void testGetPortions() throws Exception {
        ArrayList<Portion> portionArrayList = new ArrayList<>();
        portionArrayList.add(portion_1);
        portionArrayList.add(portion_2);
        meal = new Meal();
        meal.setPortions(portionArrayList);
        Collection<Portion> portionArrayListCopy = meal.getPortions();
        assertTrue("portion 1 is equal", portionArrayListCopy.contains(portion_1));
        assertTrue("portion 2 is equal", portionArrayListCopy.contains(portion_2));
    }

    @Test
    public void testSetPortions() throws Exception {
        ArrayList<Portion> portionArrayList = new ArrayList<>();
        portionArrayList.add(portion_1);
        portionArrayList.add(portion_2);
        meal = new Meal();
        meal.setPortions(portionArrayList);
        Collection<Portion> portionArrayListCopy = meal.getPortions();
        assertTrue("portion 1 is equal", portionArrayListCopy.contains(portion_1));
        assertTrue("portion 2 is equal", portionArrayListCopy.contains(portion_2));
    }

    @Test
    public void testSetPortion(){
        meal = new Meal();
        meal.setPortion(portion_1);
        meal.setPortion(portion_2);
        Collection<Portion> portionArrayListCopy = meal.getPortions();
        assertTrue("portion 1 is equal", portionArrayListCopy.contains(portion_1));
        assertTrue("portion 2 is equal", portionArrayListCopy.contains(portion_2));

    }

    @Test
    public void testGetCalories() throws Exception {
        meal = new Meal();
        meal.setPortion(portion_1);
        meal.setPortion(portion_2);
        Collection<Portion> portionArrayListCopy = meal.getPortions();
        int calories = (portion_1.getCalories()) + (portion_2.getCalories());
        assertTrue("Calories are equal", meal.getCalories() == calories);

    }

    @Test
    public void testEquals() throws Exception {
        meal = new Meal();
        meal.setPortion(portion_1);
        meal.setPortion(portion_2);
        meal.setId(id);
        meal.setDay(day);
        meal.setName(name);
        Meal mealCopy = new Meal();
        mealCopy.setPortion(portion_1);
        mealCopy.setPortion(portion_2);
        mealCopy.setId(id);
        mealCopy.setDay(day);
        mealCopy.setName(name);
        assertTrue("meals are equal", meal.equals(mealCopy));

    }

    @Test
    public void testHashCode() throws Exception {
        meal = new Meal();
        meal.setPortion(portion_1);
        meal.setPortion(portion_2);
        meal.setId(id);
        meal.setDay(day);
        meal.setName(name);
        Meal mealCopy = new Meal();
        mealCopy.setPortion(portion_1);
        mealCopy.setPortion(portion_2);
        mealCopy.setId(id);
        mealCopy.setDay(day);
        mealCopy.setName(name);
        assertTrue("hashcodes are equal", meal.hashCode() == mealCopy.hashCode());


    }
}