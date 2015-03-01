package edu.uml.diet;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class BasicUserServiceTest extends TestCase{

    String username;
    String password;
    String passKey;
    PersistanceUserServices persistanceUserServicesMock;
    BasicUserService basicUserService;


    @Before
    public void setUp(){
        persistanceUserServicesMock= Mockito.mock(PersistanceUserServices.class);

        when(persistanceUserServicesMock.createUser(any(String.class),any(String.class))).thenReturn(true);
        basicUserService = new BasicUserService(persistanceUserServicesMock);
        username = "SomeGuy";
        password = "somepass";
    }

    /**
     *test to verify successful createUser method
     * @throws Exception
     */
    @Test
    public void testCreateUser() throws Exception {
        when(persistanceUserServicesMock.verifyUsername(any(String.class))).thenReturn(false);
        when(persistanceUserServicesMock.createUser(any(String.class),any(String.class))).thenReturn(true);
        assertTrue("testCreateUser:User not in database should return true", basicUserService.createUser(username,password));
    }

    /**
     *Negetive test to verify unsuccessful  createUser method. user already in database
     * @throws Exception
     */
    @Test
    public void testCreateUserNegative() throws Exception {
        when(persistanceUserServicesMock.verifyUsername(any(String.class))).thenReturn(true);
        when(persistanceUserServicesMock.createUser(any(String.class),any(String.class))).thenReturn(true);
        assertFalse("testCreateUser:User is in database should return false", basicUserService.createUser(username, password));
    }

    /**
     * Test for verifyUser method. Calls createUser() and stores encrypted password in
     * variable (passKey). then then calls verifyUser() with same password. Mocks database return of passKey.
     * @throws Exception
     */
    @Test
    public void testVerifyUser() throws Exception {
        //basicUserServices.createUser called & passKey stored in variable
        when(persistanceUserServicesMock.verifyUsername(any(String.class))).thenReturn(false);
        when(persistanceUserServicesMock.createUser(any(String.class), any(String.class))).thenAnswer(new Answer<Boolean>() {
                public Boolean answer(InvocationOnMock invocation) throws Throwable {
                    Object[] args = invocation.getArguments();
                    passKey = (String)args[1];

                    return true;
                }
            });
        basicUserService.createUser(username,password);

        //now call verifyUser and compare with stored passKey
        when(persistanceUserServicesMock.verifyUsername(any(String.class))).thenReturn(true);
        when(persistanceUserServicesMock.getPassword(any(String.class))).thenReturn(passKey);

        assertTrue(basicUserService.verifyUser(username,password));
    }

    /**
     * Negative Test for verifyUser method.
     * @throws Exception
     */
    @Test
    public void testVerifyUserNegative() throws Exception {

       assertFalse(basicUserService.verifyUser(username, "anotherPassword"));
    }


}