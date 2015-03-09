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

    @Before
    public void setup() {
        databaseName = "DietTracker";
        falseDatabaseName = "DietTracker";
        databaseConnector = new DatabaseConnector();
        userTableName = "USERS";
        foodTableName = "FOOD";
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
        assertFalse(databaseBuilder.checkIfDbExists());

        databaseBuilder.createDatabase();
        assertTrue(databaseBuilder.checkIfDbExists());
    }

    @Test
    public void testCheckIfTableExistsNegative()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.checkIfDbExists()) {
            databaseBuilder.createDatabase();
        }

        assertFalse(databaseBuilder.checkIfTableExists(userTableName));
    }

    @Test
    public void testCreateUserTable()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.checkIfDbExists()){
            databaseBuilder.createDatabase();
        }

        databaseBuilder.createUserTable();
        assertTrue(databaseBuilder.checkIfTableExists(userTableName));
    }

    @Test
    public void testCreateFoodTable()throws DatabaseConnectorException, PersistanceFoodServiceException, IOException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.checkIfDbExists()){
            databaseBuilder.createDatabase();
        }

        databaseBuilder.createFoodTable();
        assertTrue(databaseBuilder.checkIfTableExists(foodTableName));
    }

    @After
    public void teardown() throws DatabaseConnectorException, SQLException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        if(databaseBuilder.checkIfDbExists()) {
            Statement statement = databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP TABLE IF EXISTS " + foodTableName;
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS " + userTableName;
            statement.executeUpdate(sql);
            sql = "DROP DATABASE IF EXISTS " + databaseName;
            statement.executeUpdate(sql);
        }
        assertFalse(databaseBuilder.checkIfDbExists());
    }

}
