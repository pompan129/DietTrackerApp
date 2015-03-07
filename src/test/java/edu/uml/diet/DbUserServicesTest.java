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

    @Before
    public void setup() throws DatabaseConnectorException{
        existingUsername = "rgoolishian";
        nonExistingUsername = "test";
        newUsername = "rgoolishian";
        newPassword = "PASSWORD1";
        dbUserServices = new DbUserServices();

        // create database to test
        if(!dbUserServices.databaseBuilder.CheckIfDbExists()){
            dbUserServices.databaseBuilder.CreateDatabase();
            if(!dbUserServices.databaseBuilder.CheckIfTableExists("users")) {
                dbUserServices.databaseBuilder.CreateUserTable();
            }
        }
    }

    @Test
    public void TestCreateUser() throws PersistanceUserServicesException, DuplicateUserException{
        dbUserServices.createUser(newUsername, newPassword);
        assertTrue(dbUserServices.verifyUsername(newUsername));
    }

    @Test
    public void TestVerifyUsername() throws PersistanceUserServicesException{
        DbUserServices dbUserServices = new DbUserServices();
        assertTrue(dbUserServices.verifyUsername(existingUsername));
    }

    @Test
    public void TestVerifyUsernameNegative() throws PersistanceUserServicesException{
        DbUserServices dbUserServices = new DbUserServices();
        assertFalse(dbUserServices.verifyUsername(nonExistingUsername));
    }

    @Test
    public void TestGetPassword() throws PersistanceUserServicesException{
        DbUserServices dbUserServices = new DbUserServices();
        assertEquals(newPassword, dbUserServices.getPassword(existingUsername));
    }

    @AfterClass
    public static void teardown() throws DatabaseConnectorException, SQLException{
        if(dbUserServices.databaseBuilder.CheckIfDbExists()) {
            Statement stmt = dbUserServices.databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP DATABASE " + dbUserServices.databaseBuilder.getDatabaseName();
            stmt.executeUpdate(sql);
        }
        assertFalse(dbUserServices.databaseBuilder.CheckIfDbExists());
    }


}
