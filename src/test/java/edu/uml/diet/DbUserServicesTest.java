package edu.uml.diet;

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
    private String nonExistingUsername;
    private String newUsername;
    private String newPassword;
    private static DbUserServices dbUserServices;
    private static boolean createdDatabase;
    private static boolean createdTable;

    @Before
    public void setup() throws DatabaseConnectorException, DuplicateUserException, PersistanceUserServicesException{
        existingUsername = "rgoolishian";
        nonExistingUsername = "test";
        newUsername = "rgoolishian1";
        newPassword = "PASSWORD1";
        dbUserServices = new DbUserServices();

        // create database to test
        if(!dbUserServices.databaseBuilder.checkIfDbExists()) {
            dbUserServices.databaseBuilder.createDatabase();
            createdDatabase = true;
        }
        if(!dbUserServices.databaseBuilder.checkIfTableExists("users")) {
            dbUserServices.databaseBuilder.createUserTable();
            createdTable = true;
            dbUserServices.createUser(existingUsername, newPassword);

        }

    }


    @Test(expected = DuplicateUserException.class)
    public void testCreateUserNegative() throws PersistanceUserServicesException, DuplicateUserException{
        dbUserServices.createUser(existingUsername, newPassword);
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
        assertEquals(newPassword, dbUserServices.getPassword(existingUsername));
    }

    @AfterClass
    public static void teardown() throws DatabaseConnectorException, SQLException{
        if(createdTable) {
            Statement stmt = dbUserServices.databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP TABLE USERS";
            stmt.executeUpdate(sql);
            assertFalse(dbUserServices.databaseBuilder.checkIfTableExists("USERS"));
        }
        if(createdDatabase) {
            Statement stmt = dbUserServices.databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP DATABASE " + dbUserServices.databaseBuilder.getDatabaseName();
            stmt.executeUpdate(sql);
            assertFalse(dbUserServices.databaseBuilder.checkIfDbExists());
        }
    }


}
