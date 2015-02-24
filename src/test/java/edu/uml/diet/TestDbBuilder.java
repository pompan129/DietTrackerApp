package edu.uml.diet;

import javafx.scene.control.Tab;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;
import static org.junit.Assert.*;

/**
 * Created by Raymond on 2/22/2015.
 */
public class TestDbBuilder {
    private static String JDBC_DRIVER;
    private static String HOST_URL;
    private static String DB_URL;
    private static String USER;
    private static String PASS;
    private static DatabaseConnector databaseConnector;
    private static String DbName;
    private static String TableName;

    @Before
    public void setup() {
        DbName = "diettracker";
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        HOST_URL = "jdbc:mysql://localhost/";
        DB_URL = "jdbc:mysql://localhost/" + DbName;
        USER = "root";
        PASS = "PASSWORD";
        databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
        TableName = "users";
    }

    @Test
    public void testGetDatabaseConnector(){
        DbBuilder dbBuilder = new DbBuilder(databaseConnector, DbName);
        assertEquals(databaseConnector,dbBuilder.getDatabaseConnector());
    }

    @Test
    public void testGetDbName(){
        DbBuilder dbBuilder = new DbBuilder(databaseConnector, DbName);
        assertEquals(DbName,dbBuilder.getDbName());
    }

    @Test
    public void testCheckIfDbExists(){
        DbBuilder dbBuilder = new DbBuilder(databaseConnector, DbName);
        assertFalse(dbBuilder.CheckIfDbExists(DbName));
    }

    @Test
    public void testCreateDatabase(){
        DbBuilder dbBuilder = new DbBuilder(databaseConnector,DbName);
        assertFalse(dbBuilder.CheckIfDbExists(DbName));

        dbBuilder.CreateDatabase();
        assertTrue(dbBuilder.CheckIfDbExists(DbName));
    }

    @Test
    public void testCheckIfTableExists(){
        DbBuilder dbBuilder = new DbBuilder(databaseConnector, DbName);
        assertFalse(dbBuilder.CheckIfTableExists(TableName));
    }

    @Test
    public void testCreateUserTable(){
        DbBuilder dbBuilder = new DbBuilder(databaseConnector,DbName);

        if(!dbBuilder.CheckIfDbExists(DbName)){
            dbBuilder.CreateDatabase();
        }

        dbBuilder.CreateUserTable();
        assertTrue(dbBuilder.CheckIfTableExists(TableName));
    }

    @After
    public void teardown() throws SQLException{
        DbBuilder dbBuilder = new DbBuilder(databaseConnector,DbName);
        if(dbBuilder.CheckIfDbExists(DbName)) {
            Statement stmt = databaseConnector.ConnectToDatabase().createStatement();
            String sql = "DROP DATABASE " + DbName;
            stmt.executeUpdate(sql);
        }
        assertFalse(dbBuilder.CheckIfDbExists(DbName));
    }

}
