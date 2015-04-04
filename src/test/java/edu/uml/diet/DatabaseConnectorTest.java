package edu.uml.diet;

import edu.uml.diet.persistence.DatabaseBuilder;
import edu.uml.diet.persistence.DatabaseConnector;
import edu.uml.diet.persistence.DatabaseConnectorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test class for DatabaseConnector class
 */
public class DatabaseConnectorTest {
    private DatabaseConnector databaseConnector;
    private DatabaseBuilder databaseBuilder;
    private boolean createdDatabase;

    @Before
    public void setup() throws DatabaseConnectorException {
        String databaseName = "DietTracker";
        databaseConnector = new DatabaseConnector();
        databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);

        if(!databaseBuilder.checkIfDbExists()){
            databaseBuilder.initializeDatabase();
            createdDatabase = true;
        }
    }

    @Test
    public void testGetServerConnection() throws  DatabaseConnectorException, SQLException{
        Connection connection = databaseConnector.getServerConnection();
        assertFalse(connection.isClosed());
    }

    @Test
    public void testGetDatabaseConnection()throws DatabaseConnectorException, SQLException{
        Connection connection = databaseConnector.getDatabaseConnection();
        assertTrue(connection != null);
    }

    @After
    public void deleteDatabaseConnector() throws DatabaseConnectorException, SQLException{
        databaseConnector = null;
        if(createdDatabase) {
            databaseBuilder.tearDownDatabase();
        }
    }
}
