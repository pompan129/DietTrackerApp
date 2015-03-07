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
    private String userTableName;
    private String foodTableName;

    @Before
    public void setup() {
        databaseName = "DietTracker";
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
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertFalse(databaseBuilder.CheckIfDbExists());
    }

    @Test
    public void testCreateDatabase()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertFalse(databaseBuilder.CheckIfDbExists());

        databaseBuilder.CreateDatabase();
        assertTrue(databaseBuilder.CheckIfDbExists());
    }

    @Test
    public void testCheckIfTableExistsNegative()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.CheckIfDbExists()) {
            databaseBuilder.CreateDatabase();
        }

        assertFalse(databaseBuilder.CheckIfTableExists(userTableName));
    }

    @Test
    public void testCreateUserTable()throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.CheckIfDbExists()){
            databaseBuilder.CreateDatabase();
        }

        databaseBuilder.CreateUserTable();
        assertTrue(databaseBuilder.CheckIfTableExists(userTableName));
    }

    @Test
    public void testCreateFoodTable()throws DatabaseConnectorException, PersistanceFoodServiceException, IOException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.CheckIfDbExists()){
            databaseBuilder.CreateDatabase();
        }

        databaseBuilder.CreateFoodTable();
        assertTrue(databaseBuilder.CheckIfTableExists(foodTableName));
    }

    @After
    public void teardown() throws DatabaseConnectorException, SQLException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        if(databaseBuilder.CheckIfDbExists()) {
            Statement statement = databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP TABLE IF EXISTS " + foodTableName;
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS " + userTableName;
            statement.executeUpdate(sql);
            sql = "DROP DATABASE IF EXISTS " + databaseName;
            statement.executeUpdate(sql);
        }
        assertFalse(databaseBuilder.CheckIfDbExists());
    }

}
