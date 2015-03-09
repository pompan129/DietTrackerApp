package edu.uml.diet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import static org.junit.Assert.*;

/**
 * Test class fo DatabaseBuilder class
 * Created by Raymond on 2/22/2015.
 */
public class DbBuilderTest {
    private DatabaseConnector databaseConnector;
    private String databaseName;
    private String falseDatabaseName;
    private String userTableName;
    private String foodTableName;
    private boolean createdDatabase;
    private boolean createdFoodTable;
    private boolean createdUserTable;

    @Before
    public void setup() throws DatabaseConnectorException {
        databaseName = "DietTracker";
        falseDatabaseName = "DietTrackerFalse";
        databaseConnector = new DatabaseConnector();
        userTableName = "USERS";
        foodTableName = "FOOD";

        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        if(!databaseBuilder.checkIfDbExists()) {
            databaseBuilder.createDatabase();
            createdDatabase = true;
        }
        if(!databaseBuilder.checkIfTableExists(foodTableName)){
            databaseBuilder.createFoodTable();
            createdFoodTable = true;
        }
        if(!databaseBuilder.checkIfTableExists(userTableName)){
            databaseBuilder.createUserTable();
            createdUserTable = true;
        }
    }

    @Test
    public void testGetDatabaseConnector(){
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertEquals(databaseConnector, databaseBuilder.getDatabaseConnector());
    }

    @Test
    public void testGetDbName(){
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertEquals(databaseName, databaseBuilder.getDatabaseName());
    }

    @Test
    public void testCheckIfDbExistsNegative()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, falseDatabaseName);
        assertFalse(databaseBuilder.checkIfDbExists());
    }

    @Test
    public void testCreateDatabase()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertTrue(databaseBuilder.checkIfDbExists());
    }

    @Test
    public void testCheckIfTableExists()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertTrue(databaseBuilder.checkIfTableExists(userTableName));
    }

    @Test
    public void testCreateUserTable()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertTrue(databaseBuilder.checkIfTableExists(userTableName));
    }

    @Test
    public void testCreateFoodTable()throws DatabaseConnectorException, PersistanceFoodServiceException, IOException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertTrue(databaseBuilder.checkIfTableExists(foodTableName));
    }

    @After
    public void teardown() throws DatabaseConnectorException, SQLException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        if(createdUserTable) {
            Statement stmt = databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP TABLE USERS";
            stmt.executeUpdate(sql);
            assertFalse(databaseBuilder.checkIfTableExists("USERS"));
        }
        if(createdFoodTable) {
            Statement stmt = databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP TABLE FOOD";
            stmt.executeUpdate(sql);
            assertFalse(databaseBuilder.checkIfTableExists("FOOD"));
        }
        if(createdDatabase) {
            Statement stmt = databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP DATABASE " + databaseBuilder.getDatabaseName();
            stmt.executeUpdate(sql);
            assertFalse(databaseBuilder.checkIfDbExists());
        }
    }

}
