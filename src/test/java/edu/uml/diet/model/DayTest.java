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
    Date date;
    User user;

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
        date = Date.valueOf("2015-3-21");
        user = new User();
        user.setId(7);
    }

    @Test
    public void testGetId() throws Exception {
        day = new Day();
        day.setId(id);
        assertTrue("ids are equal", id == day.getId());

    }

    @Test
    public void testSetId() throws Exception {
        day = new Day();
        day.setId(id);
        assertTrue("ids are equal", id == day.getId());
    }

    @Test
    public void testGetDate() throws Exception {
        day = new Day();
        day.setDate(date);
        assertEquals("dates are equal", date, day.getDate());
    }

    @Test
    public void testSetDate() throws Exception {
        day = new Day();
        day.setDate(date);
        assertEquals("dates are equal", date, day.getDate());

    }

    @Test
    public void testGetMeals() throws Exception {
        ArrayList<Meal> mealArrayList = new ArrayList<>();
        mealArrayList.add(meal_1);
        mealArrayList.add(meal_2);
        day = new Day();
        day.setMeals(mealArrayList);
        assertTrue("contains meal 1",mealArrayList.contains(meal_1));
        assertTrue("contains meal 2",mealArrayList.contains(meal_2));

    }

    @Test
    public void testSetMeals() throws Exception {
        ArrayList<Meal> mealArrayList = new ArrayList<>();
        mealArrayList.add(meal_1);
        mealArrayList.add(meal_2);
        day = new Day();
        day.setMeals(mealArrayList);
        assertTrue("contains meal 1",mealArrayList.contains(meal_1));
        assertTrue("contains meal 2",mealArrayList.contains(meal_2));

    }

    @Test
    public void testGetUser() throws Exception {
        day = new Day();
        day.setUser(user);
        assertEquals("user are equal", user, day.getUser());
    }

    @Test
    public void testSetUser() throws Exception {
        day = new Day();
        day.setUser(user);
        assertEquals("user are equal", user, day.getUser());
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
        day = new Day();
        Day dayCopy = new Day();
        ArrayList<Meal> mealArrayList= new ArrayList<>();
        mealArrayList.add(meal_1);
        mealArrayList.add(meal_2);
        day.setMeals(mealArrayList);
        day.setDate(date);
        day.setId(id);
        day.setUser(user);
        dayCopy.setMeals(mealArrayList);
        dayCopy.setDate(date);
        dayCopy.setId(id);
        dayCopy.setUser(user);
        assertTrue("days are equal", day.equals(dayCopy));

    }

    @Test
    public void testHashCode() throws Exception {
        day = new Day();
        Day dayCopy = new Day();
        ArrayList<Meal> mealArrayList= new ArrayList<>();
        mealArrayList.add(meal_1);
        mealArrayList.add(meal_2);
        day.setMeals(mealArrayList);
        day.setDate(date);
        day.setId(id);
        day.setUser(user);
        dayCopy.setMeals(mealArrayList);
        dayCopy.setDate(date);
        dayCopy.setId(id);
        dayCopy.setUser(user);
        assertTrue("hashcode is equal", day.hashCode() == dayCopy.hashCode());

    }
}