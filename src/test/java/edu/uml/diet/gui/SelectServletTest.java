package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.model.Portion;
import junit.framework.TestCase;
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

        selectServlet = new SelectServlet();

        foodService = ServiceFactory.getFoodServiceInstance();
        userPortionList =  new ArrayList<>();
        session.setAttribute("loggedIn", true);
        session.setAttribute("userPortionList", userPortionList);
        session.setAttribute("foodService", foodService);
        request.setParameter("portionIDs", portionIDs);
        request.setParameter("portionSize", portionSizes);
        request.setSession(session);
    }

    @Test
    public void testDoGet() throws Exception {
        selectServlet.doGet(request, response);
        assertEquals("/WEB-INF/select.jsp", response.getForwardedUrl());
    }

    @Test
    public void testDoPost() throws Exception {

    }

    @Test
    public void testSetSelectedPortions() throws Exception {
        selectServlet.setSelectedPortions(request, response, session, portionIDs, portionSizesParsed, foodService, userPortionList);\

    }

    @Test
    public void testGetAndUpdateDay() throws Exception {

    }


    protected List<String> parsePortionList(String[] portionSizes) {
        //parse portionSizes to remove empty entries
        List<String> portionSizesParsed = new ArrayList<>(Arrays.asList(portionSizes));
        portionSizesParsed.removeAll(Arrays.asList("", null));
        return portionSizesParsed;
    }

}