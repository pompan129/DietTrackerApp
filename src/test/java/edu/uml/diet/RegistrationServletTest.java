package edu.uml.diet;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

public class RegistrationServletTest extends TestCase {

    String email;
    String password;
    UserService userService;
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);


    @Before
    public void setUp() throws Exception {
        email = "test@example.com";
        password = "PASSWORD";
        userService = ServiceFactory.getUserServiceInstance();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoPost() throws Exception {
        request.getParameter(email);
        userService.createUser(email,password);
        assertTrue(userService.verifyUser(email,password));

    }
}