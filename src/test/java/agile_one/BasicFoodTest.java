package agile_one;

/**
 * Created by Kurt Johnson on 2/8/2015.
 */

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class BasicFoodTest {
    String foodName;
    int calories;

    @Before
    public void setup() {
        foodName = "Twinkie";
        calories = 1000;
    }

    /**
     * test to verify that correct  name is returned by GetName()
     */
    @Test
    public void testGetName(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        assertEquals("testGetName: positive", foodName, basicfood.getName());
    }

    /**
     * negative test for GetName() method
     */
    @Test
    public void testGetNameNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        assertFalse("testGetName: negative", "HoHo".equals(basicfood.getName()));
    }


    /**
     * test to verify that correct  name is returned by setName()
     */
    @Test
    public void testSetName(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        basicfood.setName("DingDong");
        assertEquals("testSetName: positive", "DingDong", basicfood.getName());
    }

    /**
     * negative test for setName() method
     */
    @Test
    public void testSetNameNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        basicfood.setName("DingDong");
        assertFalse("testSetName: negative", foodName.equals(basicfood.getName()));
    }

    /**
     * test to verify that correct  calories is set by setCalories()
     */
    @Test
    public void testSetCalories(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        basicfood.setCalories(500);
        assertEquals("testSetName: positive", 500, basicfood.getCalories());
    }

    /**
     * Negative test for setCalories() method
     */
    @Test
    public void testSetCaloriesNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        basicfood.setCalories(500);
        assertFalse("testSetName: negative", calories == basicfood.getCalories());
    }

    /**
     * test to verify that correct  calories  is returned by GetCalories()
     */
    @Test
    public void testGetCalories(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        assertEquals("testGetCalories: positive", calories, basicfood.getCalories());
    }

    /**
     * Negative test for GetCalories() method
     */
    @Test
    public void testGetCaloriesNegative(){
        BasicFood basicfood = new BasicFood(foodName, calories);
        assertFalse("testGetCalories: negative", 500 == basicfood.getCalories());
    }




}
