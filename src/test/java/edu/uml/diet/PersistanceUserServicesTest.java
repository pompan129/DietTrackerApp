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
public class PersistanceUserServicesTest {

    private String ExistingUsername;
    private String NonExistingUsername;
    private String NewUsername;
    private String NewPassword;
    private static PersistanceUserServices persistanceUserServices;


    @Before
    public void setup() throws SQLException{
        ExistingUsername = "rgoolishian";
        NonExistingUsername = "test";
        NewUsername = "rgoolishian";
        NewPassword = "PASSWORD1";
        persistanceUserServices = new PersistanceUserServices();

        // create database to test
        if(!persistanceUserServices.dbBuilder.CheckIfDbExists()){
            persistanceUserServices.dbBuilder.CreateDatabase();
            if(!persistanceUserServices.dbBuilder.CheckIfTableExists("users")) {
                persistanceUserServices.dbBuilder.CreateUserTable();
            }
        }
    }

    @Test
    public void TestCreateUser() throws SQLException{

        persistanceUserServices.createUser(NewUsername,NewPassword);
        assertTrue(persistanceUserServices.verifyUsername(NewUsername));
    }

    @Test
    public void TestVerifyUsername() throws SQLException{
        PersistanceUserServices persistanceUserServices = new PersistanceUserServices();
        assertTrue(persistanceUserServices.verifyUsername(ExistingUsername));
    }

    @Test
    public void TestVerifyUsernameNegative() throws SQLException{
        PersistanceUserServices persistanceUserServices = new PersistanceUserServices();
        assertFalse(persistanceUserServices.verifyUsername(NonExistingUsername));
    }

    @Test
    public void TestGetPassword() throws SQLException{
        PersistanceUserServices persistanceUserServices = new PersistanceUserServices();
        assertEquals(NewPassword,persistanceUserServices.getPassword(ExistingUsername));
    }

    @AfterClass
    public static void teardown() throws SQLException{
        if(persistanceUserServices.dbBuilder.CheckIfDbExists()) {
            Statement stmt = persistanceUserServices.databaseConnector.ConnectToDatabase().createStatement();
            String sql = "DROP DATABASE " + persistanceUserServices.dbBuilder.getDbName();
            stmt.executeUpdate(sql);
        }
        assertFalse(persistanceUserServices.dbBuilder.CheckIfDbExists());
    }


}
