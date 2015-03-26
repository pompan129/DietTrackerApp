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
    String wrongEmail;
    String wrongPassword;
    String errorMessage;
    LoginServlet loginServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        email = "testuser";
        password = "password";
        wrongEmail = "THISWILLNEVERBEANEMAIL@NOTAURL.NOTAURL";
        wrongPassword = "DEFINITELYNOTMYPASSWORD";
        errorMessage = "Username not found. Do you want to <a href = \"register\"> register</a>?";
        loginServlet = new LoginServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        session.setAttribute("loggedIn", false);
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

    //tests positive login
    @Test
    public void testDoPost() throws Exception {
        //make sure request is using correct session
        request.setSession(session);

        //pass user login info to request
        request.setParameter("email", email);
        request.setParameter("password", password);
        loginServlet.doPost(request, response);

        //make sure user got logged in
        assertTrue((boolean) session.getAttribute("loggedIn"));
        //make sure user got forwarded to the welcome page
        assertEquals(response.getForwardedUrl(), "welcome.jsp");
    }

    @Test
    public void testDoPostNegative() throws Exception {
        request.setSession(session);

        //pass bad user login info to request
        request.setParameter("email", wrongEmail);
        request.setParameter("password", wrongPassword);
        loginServlet.doPost(request, response);

        //check to make sure error message is produced
        //login flag still false
        //and login page is reloaded
        assertFalse((boolean) request.getSession().getAttribute("loggedIn"));
        assertEquals(request.getAttribute("error"), errorMessage);
        assertEquals(response.getForwardedUrl(), "/WEB-INF/login.jsp");
    }
}