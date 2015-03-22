package edu.uml.diet.logic;

import edu.uml.diet.logic.BasicFoodService;
import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
import edu.uml.diet.persistence.PersistanceFoodService;
import edu.uml.diet.persistence.PersistanceServiceFactory;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class BasicFoodServiceTest extends TestCase {

    BasicFood basicfood;
    Double portionSize;
    Portion portion;
    String foodName;
    PersistanceFoodService persistanceFoodService_MOCK;

    @Before
    public void setUp() {
        foodName = "Apple";
        persistanceFoodService_MOCK = Mockito.mock(PersistanceFoodService.class);
    }

    /**
     * test to verify foodSearch() method
     */
    @Test
    public void testFoodSearch() throws Exception {
        when(persistanceFoodService_MOCK.searchForFood(any(String.class))).thenReturn(new BasicFood(foodName, 1, 1, 1, 1, 1, foodName));
        BasicFoodService basicFoodService = new BasicFoodService(persistanceFoodService_MOCK);
        Portion portion = basicFoodService.foodSearch(foodName);
        assertEquals("testFoodSearch: Positive", portion.getFood().getName(), foodName);

    }

    /**
     * test to verify foodListSearch. Only tests length for list not empty
     */
    @Test
    public void testFoodListSearch() throws Exception {
        BasicFoodService basicFoodService = new BasicFoodService(persistanceFoodService_MOCK);
        List<BasicFood> basicFoodList = new ArrayList<>();
        basicFoodList.add(new BasicFood(foodName, 1, 1, 1, 1, 1, foodName));
        when(persistanceFoodService_MOCK.searchForFoodList(any(String.class))).thenReturn(basicFoodList);
        List<Portion> portionList = basicFoodService.foodListSearch(foodName);
        assertFalse("testFoodListSearch: positive", portionList.isEmpty());
    }

    /**
     * test of the getDay() method
     * @throws Exception
     */
    @Test
    public void testGetDay() throws Exception {
        String userName = "testName1";
        DateTime dateTime = new DateTime();
        BasicFoodService basicFoodService = new BasicFoodService(PersistanceServiceFactory.getPersistanceFoodServiceInstance());
        BasicUserService basicUserService = new BasicUserService(PersistanceServiceFactory.getPersistanceUserServicesInstance());
        basicUserService.createUser(userName, "password");
        Day day = basicFoodService.getDay(userName, dateTime);
        assertTrue(day.getDate().equals(dateTime));
    }

    @Test
    public void testAddOrUpdateDay() throws Exception{

        String userName = "testName2";
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime dt = fmt.parseDateTime("12/1/2014");
        //DateTime dateTime = new DateTime().withDate(12,1,1);
        BasicFoodService basicFoodService = new BasicFoodService(PersistanceServiceFactory.getPersistanceFoodServiceInstance());
        BasicUserService basicUserService = new BasicUserService(PersistanceServiceFactory.getPersistanceUserServicesInstance());
        basicUserService.createUser(userName, "password");
        Day day = basicFoodService.getDay(userName, dt);
        BasicFood testFood = new BasicFood("TEST_FOOD", 1, 2, 3, 4, 5, "1 oz" );



        Meal meal1 = new Meal();

        meal1.setDay(day);
        meal1.setName("Breakfast");

        Meal meal2 = new Meal();
        meal2.setDay(day);
        meal2.setName("Lunch");

        Meal meal3 = new Meal();
        meal3.setDay(day);
        meal3.setName("Dinner");

        Meal meal4 = new Meal();
        meal4.setDay(day);
        meal4.setName("Snack");

        Collection<Meal> meals = new ArrayList<>();
        meals.add(meal1);
        meals.add(meal2);
        meals.add(meal3);
        meals.add(meal4);

        Portion portion1 = new Portion();
        portion1.setFood(testFood);
        portion1.setPortionSize(12.0);
        portion1.setMeal(meal1);

        Collection<Portion> portions = new ArrayList<>();
        portions.add(portion1);
        meal1.setPortions(portions);

        day.setMeals(meals);


        basicFoodService.addOrUpdateDay(day);


        Day newDay1 = basicFoodService.getDay(userName, dt);
        Day newDay2 = basicFoodService.getDay(userName, dt);
        Day newDay3 = basicFoodService.getDay(userName, dt);
        Day newDay4 = basicFoodService.getDay(userName, dt);
        assertTrue(newDay1.getDate().equals(day.getDate()));
        //assertTrue(newDay.equals(day));
        assertEquals(newDay1.getId(), day.getId());
    }

}