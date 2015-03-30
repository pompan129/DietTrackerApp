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

import javax.xml.ws.Service;

import static java.lang.Math.random;
import static org.junit.Assert.*;

public class LoginServletTest extends TestCase {

    String email;
    String password;
    String wrongEmail;
    String wrongPassword;
    String errorMessage;
    UserService userService;
    FoodService foodService;
    LoginServlet loginServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        email = random() + "@goodExample.com";
        password = "password";
        wrongEmail = random() + "@badExample.com";
        wrongPassword = "password";
        errorMessage = "Username not found. Do you want to <a href = \"register\"> register</a>?";
        loginServlet = new LoginServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        session.setAttribute("loggedIn", false);
        userService = ServiceFactory.getUserServiceInstance();
        foodService = ServiceFactory.getFoodServiceInstance();
    }

    @Test
    public void testDoGet() throws Exception {
        //make sure request is using the correct session
        request.setSession(session);
        //call servlet
        loginServlet.doGet(request, response);

        //we know this test works if the response forwards to
        //the login page
        assertEquals(response.getForwardedUrl(), "/WEB-INF/login.jsp");
    }

    @Test
    public void testDoPost() throws Exception {
        //make sure request is using correct session
        request.setSession(session);

        //make sure user is actually created
        userService.createUser(email, password);

        //pass user login info to request
        request.setParameter("email", email);
        request.setParameter("password", password);
        loginServlet.doPost(request, response);

        //make sure user got forwarded to the welcome page
        assertEquals("welcome", response.getRedirectedUrl());
    }

    @Test
    public void testAuthenticateNegative() throws Exception {
        assertFalse(loginServlet.authenticate(session, wrongEmail, wrongPassword));
    }
}