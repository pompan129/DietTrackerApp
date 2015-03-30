package edu.uml.diet.model;

import edu.uml.diet.persistence.DatabaseBuilder;
import edu.uml.diet.persistence.DatabaseConnector;
import edu.uml.diet.persistence.DatabaseConnectorException;
import edu.uml.diet.persistence.PersistanceFoodServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class PortionTest {

    private DatabaseBuilder databaseBuilder;
    private BasicFood food;
    private Double portionSize;
    private Portion portion;
    private String householdWeightDescription;
    private int calories;
    private int id;
    private double householdWeight;


    @Before
    public void setUp() throws DatabaseConnectorException {
        householdWeightDescription = "1 package";
        householdWeight = 15.0;
        calories = 270;
        householdWeight = 5.0;
        portionSize = 3.0;
        id = 10;
        food = new BasicFood("Fake_Food", calories, 1, 1, 1, householdWeight, householdWeightDescription);

        String databaseName = "DietTracker";
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        databaseBuilder.initializeDatabase();

    }

    /**
     * test to verify that correct  BasicFood  is returned by getFood()
     */
    @Test
    public void testGetFood() {
        portion = new Portion(food);
        assertEquals("testGetFood: Positive", food, portion.getFood());

    }


    @Test
    public void testSetFood() throws Exception {
        BasicFood newBasicFood = new BasicFood("newFood", calories + 1, 9, 2, 46, householdWeight + 1,
                householdWeightDescription + "test");
        Portion newPortion = new Portion(food);
        newPortion.setFood(newBasicFood);
        assertEquals("foods are equal after setFood()", newBasicFood, newPortion.getFood());

    }


    /**
     * test to verify that correct  portionSize  is returned by getPortionSize()
     */
    @Test
    public void testGetPortionSize() throws PersistanceFoodServiceException {
        portion = new Portion(food);
        portion.setPortionSize(portionSize);
        assertEquals("testGetPortionSize: Positive", portionSize, portion.getPortionSize());
    }

    /**
     * Negative test for getPortionSize()
     */
    @Test
    public void testGetPortionSizeNegative() {
        portion = new Portion(food);
        portion.setPortionSize(portionSize);
        assertFalse("testGetPortionSize: Negative", (portionSize + 1.0) == portion.getPortionSize());

    }

    /**
     * test of the setPortionSize() method
     *
     * @throws Exception
     */
    @Test
    public void testSetPortionSize() throws Exception {
        portion = new Portion(food);
        portion.setPortionSize(portionSize);
        assertEquals("testGetPortionSize: Positive", portionSize, portion.getPortionSize());

    }


    /**
     * Test of the getID() method
     *
     * @throws Exception
     */
    @Test
    public void testGetId() throws Exception {
        portion = new Portion(food);
        portion.setId(id);
        assertTrue("ids equal after testGetId()", id == portion.getId());

    }


    /**
     * test of the setId(() method
     *
     * @throws Exception
     */
    @Test
    public void testSetId() throws Exception {
        portion = new Portion(food);
        portion.setId(id);
        assertTrue("ids equal after testGetId()", id == portion.getId());
    }


    /**
     * test of the getMeal() method
     *
     * @throws Exception
     */
    @Test
    public void testGetMeal() throws Exception {
        Meal meal = new Meal();
        meal.setId(id);
        portion = new Portion();
        portion.setMeal(meal);
        assertTrue("meals have same ID", Objects.equals(meal.getId(), portion.getMeal().getId()));

    }

    /**
     * test of the setMeal() method
     *
     * @throws Exception
     */
    @Test
    public void testSetMeal() throws Exception {
        Meal meal = new Meal();
        meal.setId(id);
        portion = new Portion();
        portion.setMeal(meal);
        assertTrue("meals have same ID", Objects.equals(meal.getId(), portion.getMeal().getId()));
    }

    /**
     * test of the get calories method.
     *
     * @throws Exception
     */
    @Test
    public void testGetCalories() throws Exception {
        portion = new Portion(food);
        portion.setPortionSize(3.0);
        assertEquals("calories calculated correctly", portion.getCalories(),
                (int) (3.0 * (food.getCalories() / 100.0 * householdWeight)));
    }

    /**
     * test of the getCalories() method. when BasicFood has a householdweight of 0
     *
     * @throws Exception
     */
    @Test
    public void testGetCaloriesWithZeroWeight() throws Exception {
        BasicFood testFood = new BasicFood("testFood", calories, 9, 2, 46, 0, "0");
        portion = new Portion(testFood);
        assertEquals("calories calculated correctly with 0 householdweight", calories, portion.getCalories());

    }

    /**
     * test of the getHouseholdWeightDescription() method
     *
     * @throws Exception
     */
    @Test
    public void testGetHouseholdWeightDescription() throws Exception {
        portion = new Portion(food);
        assertEquals("household descriptions match", householdWeightDescription, portion.getHouseholdWeightDescription());

    }

    /**
     * test of the getHouseholdWeightDescription() method when the description in DB  is empty
     *
     * @throws Exception
     */
    @Test
    public void testGetHouseholdWeightDescriptionWithZero() throws Exception {
        BasicFood testFood = new BasicFood("testFood", calories, 9, 2, 46, 0, "0");
        portion = new Portion(testFood);
        assertEquals("household descriptions match", "100 grams", portion.getHouseholdWeightDescription());

    }


    @After
    public void tearDown() throws Exception {
        databaseBuilder.initializeDatabase();

    }


}