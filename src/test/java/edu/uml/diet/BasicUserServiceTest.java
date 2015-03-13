package edu.uml.diet;


        import edu.uml.diet.logic.BasicUserService;
        import edu.uml.diet.logic.ServiceFactory;
        import edu.uml.diet.logic.UserService;
        import edu.uml.diet.logic.UserServiceException;
        import edu.uml.diet.persistence.DatabaseBuilder;
        import edu.uml.diet.persistence.*;
        import junit.framework.TestCase;
        import org.junit.AfterClass;
        import org.junit.Before;
        import org.junit.Test;
        import org.mockito.Mockito;

        import java.sql.SQLException;

        import static org.junit.Assert.assertFalse;
        import static org.mockito.Matchers.any;
        import static org.mockito.Mockito.when;
        import static org.mockito.Mockito.mock;

public class BasicUserServiceTest extends TestCase{

    String username;
    String password;
    String passKey;
    PersistanceUserServices persistanceUserServicesMock;
    static boolean createdDatabase;

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
            dbUserServices.databaseBuilder.initializeDatabase();
            createdDatabase = true;
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

    @AfterClass
    public static void teardown() throws DatabaseConnectorException, SQLException, PersistanceUserServicesException {
        if(createdDatabase) {
            DbUserServices dbUserServices = new DbUserServices();
            dbUserServices.databaseBuilder.tearDownDatabase();
            assertFalse(dbUserServices.databaseBuilder.checkIfDbExists());
        }
    }


}