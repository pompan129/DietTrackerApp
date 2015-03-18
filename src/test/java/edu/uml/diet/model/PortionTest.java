package edu.uml.diet.model;

import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Portion;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PortionTest extends TestCase {

    BasicFood food;
    Double portionSize;
    Portion portion;
    String householdWeightDescription;
    int calories;
    int id;
    double householdWeight;



    @Before
    public void setUp() {
<<<<<<< HEAD
        householdWeightDescription = "1 package";
        calories = 270;
        householdWeight = 5.0;
        food = new BasicFood("Twinkie", calories, 9, 2, 46, householdWeight, householdWeightDescription);
=======
        food = new BasicFood("Twinkie", 270, 9, 2, 46, 1, "test");
>>>>>>> b09011316cf3f85bed2a208b1fde6bcc80efa789
        portionSize = 3.0;
        id = 10;

    }

    /**
     * test to verify that correct  BasicFood  is returned by getFood()
     */
    @Test
    public void testGetFood(){
        portion = new Portion(food);
        assertEquals("testGetFood: Positive", food, portion.getFood());

    }


    public void testSetFood() throws Exception {
        BasicFood newBasicFood = new BasicFood("newFood", calories +1, 9, 2, 46, householdWeight +1,
                householdWeightDescription + "test");
        Portion newPortion = new Portion(food);
        newPortion.setFood(newBasicFood);
        assertEquals("foods are equal after setFood()",newBasicFood,newPortion.getFood());

    }


    /**
     * test to verify that correct  portionSize  is returned by getPortionSize()
     */
    @Test
    public void testGetPortionSize(){
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
        assertFalse("testGetPortionSize: Negative", (portionSize +1.0) == portion.getPortionSize());

    }

    public void testSetPortionSize() throws Exception {
        portion = new Portion(food);
        portion.setPortionSize(portionSize);
        assertEquals("testGetPortionSize: Positive", portionSize, portion.getPortionSize());

    }


    public void testGetId() throws Exception {
        portion = new Portion(food);
        portion.setId(id);
        assertTrue("ids equal after testGetId()", id == portion.getId());

    }

    public void testSetId() throws Exception {
        portion = new Portion(food);
        portion.setId(id);
        assertTrue("ids equal after testGetId()", id == portion.getId());
    }




    public void testGetMeal() throws Exception {
        Meal meal = new Meal();
        meal.setId(id);
        portion = new Portion();
        portion.setMeal(meal);
        assertTrue("meals have same ID", meal.getId()== portion.getMeal().getId());

    }

    public void testSetMeal() throws Exception {
        Meal meal = new Meal();
        meal.setId(id);
        portion = new Portion();
        portion.setMeal(meal);
        assertTrue("meals have same ID", meal.getId()== portion.getMeal().getId());
    }

    public void testGetCalories() throws Exception {

    }

    public void tearDown() throws Exception {

    }


    public void testGetMeal1() throws Exception {

    }

    public void testSetMeal1() throws Exception {

    }

    public void testGetCalories1() throws Exception {

    }

    public void testGetHouseholdWeightDescription() throws Exception {

    }
}