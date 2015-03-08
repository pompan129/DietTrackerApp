package edu.uml.diet;

/**
 * Created by Kurt Johnson on 2/8/2015.
 */

import edu.uml.diet.model.BasicFood;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BasicFoodTest {
    String foodName;
    int calories;
    int fat;
    int carbs;
    int protein;

    @Before
    public void setup() {
        foodName = "Twinkie";
        calories = 270;
        fat = 9;
        protein = 2;
        carbs = 46;
    }

    /**
     * test to verify that correct  name is returned by getName()
     */
    @Test
    public void testGetName(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertEquals("testGetName: positive", foodName, basicfood.getName());
    }

    /**
     * negative test for getName() method
     */
    @Test
    public void testGetNameNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertFalse("testGetName: negative", "HoHo".equals(basicfood.getName()));
    }

    /**
     * test to verify that correct  calories  is returned by getCalories()
     */
    @Test
    public void testGetCalories(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertEquals("testGetCalories: positive", calories, basicfood.getCalories());
    }

    /**
     * Negative test for getCalories() method
     */
    @Test
    public void testGetCaloriesNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertFalse("testGetCalories: negative", (calories+10) == basicfood.getCalories());
    }

    /**
     * test to verify that correct  fat  is returned by getFat()
     */
    @Test
    public void testGetFat(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertEquals("testGetFat: positive", fat, basicfood.getFat());
    }

    /**
     * Negative test for getCalories() method
     */
    @Test
    public void testGetFatNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertFalse("testGetFat: negative", (fat+10) == basicfood.getFat());
    }

    /**
     * test to verify that correct  carbs  is returned by getCarbs()
     */
    @Test
    public void testGetCarbs(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertEquals("testGetCalories: positive", carbs, basicfood.getCarbs());
    }

    /**
     * Negative test for GetCarbs() method
     */
    @Test
    public void testGetCarbsNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertFalse("testGetCalories: negative", (carbs + 10) == basicfood.getCarbs());
    }

    /**
     * test to verify that correct  protein  is returned by getProtein()
     */
    @Test
    public void testGetProtein(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertEquals("testGetCalories: positive", protein, basicfood.getProtein());
    }

    /**
     * Negative test for GetCarbs() method
     */
    @Test
    public void testGetProteinNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        assertFalse("testGetCalories: negative", (protein + 10) == basicfood.getProtein());
    }

    /**
     * test to verify equals() method in BasicFood Class
     */
    public void testEquals() {
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        BasicFood basicfood2 = new BasicFood(basicfood.getName(), basicfood.getCalories(),
                basicfood.getFat(), basicfood.getCarbs(), basicfood.getProtein());

        assertTrue("testEquals:Positive ", basicfood.equals(basicfood2));

    }

    /**
     * Negative test to verify equals() method in BasicFood Class
     */
    public void testEqualsNegative() {
        BasicFood basicfood = new BasicFood(foodName, calories, fat, carbs, protein);
        //add one to protein for mismatch on basicfood2
        BasicFood basicfood2 = new BasicFood(basicfood.getName(), basicfood.getCalories(),
                basicfood.getFat(), basicfood.getCarbs(), basicfood.getProtein()+1);//add one to protein for mismatch

        assertFalse("testEquals:Negative ", basicfood.equals(basicfood2));

    }
}
