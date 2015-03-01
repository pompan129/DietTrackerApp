package edu.uml.diet;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Raymond on 2/22/2015.
 */
public class TestDatabaseConnector {
    private DatabaseConnector databaseConnector;

    @Before
    public void setup(){
        databaseConnector = new DatabaseConnector();
    }
    @Test
    public void testGetServerConnection() throws  DatabaseConnectorException, SQLException{
        Connection connection = databaseConnector.getServerConnection();
        assertFalse(connection.isClosed());
    }

    @Test(expected = DatabaseConnectorException.class)
    public void testGetDatabaseConnection()throws DatabaseConnectorException, SQLException{
        Connection connection = databaseConnector.getDatabaseConnection();
    }

    @After
    public void deleteDatabaseConnector(){
        databaseConnector = null;
    }
}
