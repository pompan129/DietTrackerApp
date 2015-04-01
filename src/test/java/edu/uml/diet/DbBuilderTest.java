package edu.uml.diet;

import edu.uml.diet.persistence.DatabaseBuilder;
import edu.uml.diet.persistence.DatabaseConnector;
import edu.uml.diet.persistence.DatabaseConnectorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

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

    @Before
    public void setup() throws DatabaseConnectorException {
        databaseName = "DietTracker";
        falseDatabaseName = "DietTrackerFalse";
        databaseConnector = new DatabaseConnector();
        userTableName = "USERS";
        foodTableName = "FOOD";
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.checkIfDbExists()){
            databaseBuilder.initializeDatabase();
            createdDatabase = true;
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
     public void testInitializeDatabase() throws DatabaseConnectorException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        assertTrue(databaseBuilder.checkIfDbExists());
        assertTrue(databaseBuilder.checkIfTableExists(userTableName));
        assertTrue(databaseBuilder.checkIfTableExists(foodTableName));
    }

    @After
    public void tearDown() throws DatabaseConnectorException{
        if(createdDatabase) {
            DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
            databaseBuilder.tearDownDatabase();
        }
    }

}
