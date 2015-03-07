package edu.uml.diet;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BasicFoodServiceTest extends TestCase {

    BasicFood basicfood;
    Double portionSize;
    Portion portion;
    String foodName;

    @Before
    public void setup() {
        foodName = "Apple";


    }

    /**
     * test to verify foodSearch() method
     */
    @Test
    public void testFoodSearch() {
        BasicFoodService basicFoodService = new BasicFoodService();
        Portion portion = basicFoodService.foodSearch(foodName);
        assertEquals("testFoodSearch: Positive", portion.getFood().getName(), foodName);

    }

    /**
     *test to verify foodListSearch. Only tests length for list not empty
     */
    @Test
    public void testFoodListSearch(){
        BasicFoodService basicFoodService = new BasicFoodService();
        List<Portion> portionList = basicFoodService.foodListSearch(foodName);
        assertFalse("testFoodListSearch: positive",portionList.isEmpty());
    }
}