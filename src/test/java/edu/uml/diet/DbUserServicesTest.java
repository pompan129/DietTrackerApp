package edu.uml.diet;

import edu.uml.diet.persistence.DatabaseConnectorException;
import edu.uml.diet.persistence.DbUserServices;
import edu.uml.diet.persistence.DuplicateUserException;
import edu.uml.diet.persistence.PersistanceUserServicesException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by Ray Goolishian on 2/24/2015.
 */
public class DbUserServicesTest {

    private String existingUsername;
    private String existingPassword;
    private String nonExistingUsername;
    private String newUsername;
    private String newPassword;
    private static DbUserServices dbUserServices;
    private static boolean createdDatabase;

    @Before
    public void setup() throws DatabaseConnectorException, DuplicateUserException, PersistanceUserServicesException {
        existingUsername = "testuser";
        existingPassword = "password";
        nonExistingUsername = "test";
        newUsername = "rgoolishian1";
        newPassword = "PASSWORD1";
        dbUserServices = new DbUserServices();

        // create database to test
        if(!dbUserServices.databaseBuilder.checkIfDbExists()) {
            dbUserServices.databaseBuilder.initializeDatabase();
            dbUserServices.createUser(existingUsername, newPassword);
            createdDatabase = true;
        }
    }

    @Test(expected = DuplicateUserException.class)
    public void testCreateUserNegative() throws PersistanceUserServicesException, DuplicateUserException{
        dbUserServices.createUser(existingUsername, existingPassword);
        assertTrue(dbUserServices.verifyUsername(newUsername));
    }

    @Test
    public void testVerifyUsername() throws PersistanceUserServicesException{
        DbUserServices dbUserServices = new DbUserServices();
        assertTrue(dbUserServices.verifyUsername(existingUsername));
    }

    @Test
    public void testVerifyUsernameNegative() throws PersistanceUserServicesException{
        DbUserServices dbUserServices = new DbUserServices();
        assertFalse(dbUserServices.verifyUsername(nonExistingUsername));
    }

    @Test
    public void testGetPassword() throws PersistanceUserServicesException{
        DbUserServices dbUserServices = new DbUserServices();
        assertEquals( existingPassword, dbUserServices.getPassword(existingUsername));
    }

    @AfterClass
    public static void teardown() throws DatabaseConnectorException, SQLException{
        if(createdDatabase) {
            dbUserServices.databaseBuilder.tearDownDatabase();
            assertFalse(dbUserServices.databaseBuilder.checkIfDbExists());
        }
    }


}
