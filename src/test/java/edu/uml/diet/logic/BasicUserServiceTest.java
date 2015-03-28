package edu.uml.diet.logic;


        import edu.uml.diet.logic.BasicUserService;
        import edu.uml.diet.logic.ServiceFactory;
        import edu.uml.diet.logic.UserService;
        import edu.uml.diet.persistence.*;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;
        import org.mockito.Mockito;

        import static junit.framework.TestCase.assertTrue;
        import static org.junit.Assert.assertFalse;
        import static org.mockito.Matchers.any;
        import static org.mockito.Mockito.when;

public class BasicUserServiceTest {

    private String username;
    private String password;
    private PersistanceUserServices persistanceUserServicesMock;
    private DatabaseBuilder  databaseBuilder;

    private BasicUserService basicUserService;


    @Before
    public void setUp() throws PersistanceUserServicesException, DuplicateUserException {
        String databaseName = "DietTracker";
        DatabaseConnector databaseConnector = new DatabaseConnector();
          databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
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
    @Test(expected = DuplicateUserException.class)
    public void testCreateUserNegative() throws Exception {
        when(persistanceUserServicesMock.verifyUsername(any(String.class))).thenReturn(true);
        basicUserService.createUser("BadUsernameTester", "testPassword");
    }


    /**
     * Test for verifyUser method.
     * @throws Exception
     */
    @Test
    public void testVerifyUser() throws Exception {
        String databaseName = "DietTracker";
        DatabaseConnector  databaseConnector = new DatabaseConnector();
        DatabaseBuilder  databaseBuilder = new DatabaseBuilder(databaseConnector,databaseName);
        databaseBuilder.initializeDatabase();
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

    @After
    public void tearDown() throws DatabaseConnectorException {
        databaseBuilder.initializeDatabase();
    }


}