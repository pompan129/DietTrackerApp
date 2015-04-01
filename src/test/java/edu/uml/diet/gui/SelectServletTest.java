package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class SelectServletTest extends TestCase {
    String[] portionIDs;
    String[] portionSizes;
    FoodService foodService;
    ArrayList<Portion> userPortionList;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    SelectServlet selectServlet;
    List<String> portionSizesParsed;
    String email;
    Day day;
    String mealID;

    @Before
    public void setUp() throws Exception {
        portionIDs = new String[1];
        portionSizes = new String[1];
        portionIDs[0] = "CHEESE,BLUE";
        portionSizes[0] = "2";
        portionSizesParsed = parsePortionList(portionSizes);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();

        email = "123";

        selectServlet = new SelectServlet();

        foodService = ServiceFactory.getFoodServiceInstance();
        day = foodService.getDay(email, DateTime.now());
        userPortionList =  new ArrayList<>();
        session.setAttribute("loggedIn", true);
        session.setAttribute("userPortionList", userPortionList);
        session.setAttribute("foodService", foodService);
        session.setAttribute("day", day);
        request.setParameter("portionIDs", portionIDs);
        request.setParameter("portionSize", portionSizes);
        request.setParameter("mealID", "1");
        request.setSession(session);
    }


    @Test
    public void testDoPost() throws Exception {
        selectServlet.doPost(request, response);
        assertEquals("/WEB-INF/select.jsp", response.getForwardedUrl());
    }

    @Test
    public void testSetSelectedPortions() throws Exception {
        selectServlet.setSelectedPortions(request, response, session, portionIDs, portionSizesParsed, foodService, userPortionList);
        assertEquals("/WEB-INF/select.jsp", response.getForwardedUrl());
    }

    @Test
    public void testGetAndUpdateDay() throws Exception {
        selectServlet.setSelectedPortions(request, response, session, portionIDs, portionSizesParsed, foodService, userPortionList);
        Day newDay = selectServlet.getAndUpdateDay(request, session);

        int mealID = 0;
        ArrayList<Meal> meals = new ArrayList<>(day.getMeals());
        Meal userMeal = meals.get(mealID);
        userMeal.setPortions(userPortionList);

        ArrayList<Meal> newMeals = new ArrayList<>(newDay.getMeals());
        Meal newMeal = newMeals.get(mealID);

        assertEquals(userMeal, newMeal);
    }


    private List<String> parsePortionList(String[] portionSizes) {
        //parse portionSizes to remove empty entries
        List<String> portionSizesParsed = new ArrayList<>(Arrays.asList(portionSizes));
        portionSizesParsed.removeAll(Arrays.asList("", null));
        return portionSizesParsed;
    }

}