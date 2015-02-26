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

    private String ExistingUsername;
    private String NonExistingUsername;
    private String NewUsername;
    private String NewPassword;
    private static DbUserServices dbUserServices;


    @Before
    public void setup() throws SQLException{
        ExistingUsername = "rgoolishian";
        NonExistingUsername = "test";
        NewUsername = "rgoolishian";
        NewPassword = "PASSWORD1";
        dbUserServices = new DbUserServices();

        // create database to test
        if(!dbUserServices.dbBuilder.CheckIfDbExists()){
            dbUserServices.dbBuilder.CreateDatabase();
            if(!dbUserServices.dbBuilder.CheckIfTableExists("users")) {
                dbUserServices.dbBuilder.CreateUserTable();
            }
        }
    }

    @Test
    public void TestCreateUser() throws SQLException{
        assertTrue(dbUserServices.createUser(NewUsername,NewPassword));
    }

    @Test
    public void TestVerifyUsername() throws SQLException{
        DbUserServices dbUserServices = new DbUserServices();
        assertTrue(dbUserServices.verifyUsername(ExistingUsername));
    }

    @Test
    public void TestVerifyUsernameNegative() throws SQLException{
        DbUserServices dbUserServices = new DbUserServices();
        assertFalse(dbUserServices.verifyUsername(NonExistingUsername));
    }

    @Test
    public void TestGetPassword() throws SQLException{
        DbUserServices dbUserServices = new DbUserServices();
        assertEquals(NewPassword, dbUserServices.getPassword(ExistingUsername));
    }

    @AfterClass
    public static void teardown() throws SQLException{
        if(dbUserServices.dbBuilder.CheckIfDbExists()) {
            Statement stmt = dbUserServices.databaseConnector.ConnectToDatabase().createStatement();
            String sql = "DROP DATABASE " + dbUserServices.dbBuilder.getDbName();
            stmt.executeUpdate(sql);
        }
        assertFalse(dbUserServices.dbBuilder.CheckIfDbExists());
    }


}
