package edu.uml.diet;

import edu.uml.diet.gui.LoginServlet;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

public class LoginServletTest extends TestCase {
    String email;
    String password;
    LoginServlet loginServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        email = "test";
        password = "test";
        loginServlet = new LoginServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();

    }

    @Test
    public void testDoGet() throws Exception {
        //set up session
        session.setAttribute("loggedIn", false);
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

    }
}