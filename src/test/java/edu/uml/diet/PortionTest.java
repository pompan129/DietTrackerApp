package edu.uml.diet;

import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Portion;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PortionTest extends TestCase {

    BasicFood food;
    Double portionSize;
    Portion portion;


    @Before
    public void setUp() {
        food = new BasicFood("Twinkie", 270, 9, 2, 46);
        portionSize = 3.0;

    }

    /**
     * test to verify that correct  BasicFood  is returned by getFood()
     */
    @Test
    public void testGetFood(){
        portion = new Portion(food);
        assertEquals("testGetFood: Positive", food, portion.getFood());

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
    public void testSetPortionSizeNegative() {
        portion = new Portion(food);
        portion.setPortionSize(portionSize);
        assertFalse("testGetPortionSize: Negative", (portionSize +1.0) == portion.getPortionSize());

    }
}