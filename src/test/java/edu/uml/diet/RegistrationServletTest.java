package edu.uml.diet;

import edu.uml.diet.gui.LoginServlet;
import edu.uml.diet.gui.RegistrationServlet;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.Registration;

import static java.lang.Math.random;
import static org.junit.Assert.*;

public class RegistrationServletTest extends TestCase {
    String email;
    String password;
    RegistrationServlet registrationServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        email = random() + "@example.com";
        password = "password";
        registrationServlet = new RegistrationServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        session.setAttribute("loggedIn", false);

    }

    @Test
    public void testDoGet() throws Exception {
        request.setSession(session);
        //run servlet
        registrationServlet.doGet(request, response);
        //just have to make sure we land at the right page
        assertEquals(response.getForwardedUrl(), "/WEB-INF/register.jsp");
    }

    @Test
    public void testDoPost() throws Exception {
        request.setSession(session);
        request.setParameter("email", email);
        request.setParameter("password", password);
        registrationServlet.doPost(request, response);
        //we know it worked if we get redirected to login page
        assertEquals(response.getRedirectedUrl(), "login");
    }
}