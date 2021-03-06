package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import edu.uml.diet.model.Day;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static java.lang.Math.random;
import static org.junit.Assert.*;

public class WelcomeServletTest extends TestCase {
    boolean loggedIn;
    WelcomeServlet welcomeServlet;
    String email;
    String password;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    UserService userService;
    FoodService foodService;
    String daySelect;
    Day day;
    DateTime dateTime = DateTime.now();

    @Before
    public void setUp() throws Exception {
        //make sure my fake user is created
        email = random() + "@goodExample.com";
        password = "password";
        userService = ServiceFactory.getUserServiceInstance();
        userService.createUser(email, password);
        loggedIn = true;
        welcomeServlet = new WelcomeServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        foodService = ServiceFactory.getFoodServiceInstance();
        day = foodService.getDay(email, DateTime.now());
        session.setAttribute("day", day);
        session.setAttribute("loggedIn", loggedIn);
        session.setAttribute("email", email);
        session.setAttribute("foodService", foodService);
        request.setSession(session);
    }

    @Test
    public void testDoGet() throws Exception {
        request.setSession(session);
        welcomeServlet.doGet(request, response);
        //make sure that we get to the welcome page unscathed
        assertEquals("/WEB-INF/welcome.jsp", response.getForwardedUrl());
    }

    @Test
    public void testGetNextDay() throws Exception {
        daySelect = "next";
        dateTime = dateTime.plusDays(1);
        request.setParameter("newDay", daySelect);
        Day newDay = welcomeServlet.getDay(session, foodService, day, email, DateTime.now(), daySelect);
        day = foodService.getDay(email, dateTime);
        assertEquals(day, newDay);
    }

    @Test
    public void testGetPreviousDay() throws Exception {
        daySelect = "previous";
        dateTime = dateTime.minusDays(1);
        request.setParameter("newDay", daySelect);
        Day newDay = welcomeServlet.getDay(session, foodService, day, email, DateTime.now(), daySelect);
        day = foodService.getDay(email, dateTime);
        assertEquals(day, newDay);
    }
}