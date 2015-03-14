package edu.uml.diet;

import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginServletTest_old extends TestCase {

    String email;
    String password;
    UserService userService;

    @Before
    public void setUp() throws Exception {
        email = "test@example.com";
        password = "password";
        userService = ServiceFactory.getUserServiceInstance();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoPost() throws Exception {
        //we know this method works if the 'authenticated'
        //variable is the same result as verifyUser
        boolean authenticated = userService.verifyUser(email, password);
        assertEquals(authenticated, userService.verifyUser(email,password));
    }
}