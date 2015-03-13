package edu.uml.diet;

import edu.uml.diet.gui.SearchServlet;
import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.model.Portion;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class SearchServletTest extends TestCase {
    String query; //search query
    boolean loggedIn; //session variable
    SearchServlet searchServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private FoodService foodService;

    @Before
    public void setUp() throws Exception {
        query = "cheese";
        loggedIn = true;
        searchServlet = new SearchServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
    }


    @Test
    public void testDoGet() throws Exception {
        //make sure session is set correctly
        //user IS logged in
        session.setAttribute("loggedIn", true);
        //make sure to give request the correct session
        request.setSession(session);
        searchServlet.doGet(request, response);
        //if logged in, user should go to search page
        assertEquals(response.getForwardedUrl(), "/WEB-INF/search.jsp");

    }
    @Test
    public void testDoGetNegative() throws Exception {
        //make sure session is set correctly
        //user is NOT logged in
        session.setAttribute("loggedIn", false);
        //make sure to pass request with correct session
        request.setSession(session);
        searchServlet.doGet(request, response);
        //if not logged in, user gets kicked to login page
        assertEquals(response.getRedirectedUrl(), "login.html");
    }

    @Test
    public void testDoPost() throws Exception {
        //set login info for session
        session.setAttribute("loggedIn", true);
        request.setSession(session);
        //set search query info
        request.setParameter("query", query);
        searchServlet.doPost(request, response);

        //now do db stuff manually to make sure it worked
        FoodService foodService = ServiceFactory.getFoodServiceInstance();
        List<Portion> foodList = foodService.foodListSearch(query);
        assertEquals(foodList, request.getAttribute("foodList"));
    }
}