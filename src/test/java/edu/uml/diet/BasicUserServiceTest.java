package edu.uml.diet;


        import junit.framework.TestCase;
        import org.junit.Before;
        import org.junit.Test;
        import org.mockito.Mockito;
        import org.mockito.invocation.InvocationOnMock;
        import org.mockito.stubbing.Answer;

        import java.sql.Statement;

        import static org.mockito.Matchers.any;
        import static org.mockito.Mockito.doAnswer;
        import static org.mockito.Mockito.when;
        import static org.mockito.Mockito.mock;

public class BasicUserServiceTest extends TestCase{

    String username;
    String password;
    String passKey;
    PersistanceUserServices persistanceUserServicesMock;

    DatabaseBuilder databaseBuilder;
    BasicUserService basicUserService;


    @Before
    public void setUp() throws PersistanceUserServicesException, DuplicateUserException {
        persistanceUserServicesMock= Mockito.mock(PersistanceUserServices.class);
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
        assertTrue("testCreateUser:User not in database - no exceptions", basicUserService.createUser(username,password));
    }

    /**
     *Negetive test to verify unsuccessful  createUser method. user already in database
     * @throws Exception
     */
    @Test(expected = UserServiceException.class)
    public void testCreateUserNegative() throws Exception {
        when(persistanceUserServicesMock.verifyUsername(any(String.class))).thenReturn(false);
        basicUserService.createUser("testName", "testPassword");
    }


    /**
     * Test for verifyUser method. Calls createUser() and stores encrypted password in
     * database (passKey). then then calls verifyUser() with same password. Mocks database return of passKey.
     * @throws Exception
     */
    @Test
    public void testVerifyUser() throws Exception {

        DbUserServices dbUserServices = new DbUserServices();
        if(!dbUserServices.databaseBuilder.checkIfDbExists()) {
            dbUserServices.databaseBuilder.createDatabase();
        }
        if(!dbUserServices.databaseBuilder.checkIfTableExists("users")) {
            dbUserServices.databaseBuilder.createUserTable();
        }
        UserService newBasicUserService = ServiceFactory.getUserServiceInstance();
        newBasicUserService.createUser(username, password);
        assertTrue(newBasicUserService.verifyUser(username, password));
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