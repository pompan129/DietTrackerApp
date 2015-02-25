package edu.uml.diet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;
import static org.junit.Assert.*;

/**
 * Test class fo DbBuilder class
 * Created by Raymond on 2/22/2015.
 */
public class TestDbBuilder {
    private String JDBC_DRIVER;
    private String HOST_URL;
    private String DB_URL;
    private String USER;
    private String PASS;
    private DatabaseConnector databaseConnector;
    private String DbName;
    private String TableName;

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
        assertEquals(DbName, dbBuilder.getDbName());
    }

    @Test
    public void testCheckIfDbExistsNegative()throws SQLException{
        DbBuilder dbBuilder = new DbBuilder(databaseConnector, DbName);
        assertFalse(dbBuilder.CheckIfDbExists());
    }

    @Test
    public void testCreateDatabase()throws SQLException{
        DbBuilder dbBuilder = new DbBuilder(databaseConnector,DbName);
        assertFalse(dbBuilder.CheckIfDbExists());

        dbBuilder.CreateDatabase();
        assertTrue(dbBuilder.CheckIfDbExists());
    }

    @Test
    public void testCheckIfTableExistsNegative()throws SQLException{
        DbBuilder dbBuilder = new DbBuilder(databaseConnector, DbName);

        if(!dbBuilder.CheckIfDbExists()) {
            dbBuilder.CreateDatabase();
        }

        assertFalse(dbBuilder.CheckIfTableExists(TableName));
    }

    @Test
    public void testCreateUserTable()throws SQLException{
        DbBuilder dbBuilder = new DbBuilder(databaseConnector,DbName);

        if(!dbBuilder.CheckIfDbExists()){
            dbBuilder.CreateDatabase();
        }

        dbBuilder.CreateUserTable();
        assertTrue(dbBuilder.CheckIfTableExists(TableName));
    }

    @After
    public void teardown() throws SQLException{
        DbBuilder dbBuilder = new DbBuilder(databaseConnector,DbName);
        if(dbBuilder.CheckIfDbExists()) {
            Statement stmt = databaseConnector.ConnectToDatabase().createStatement();
            String sql = "DROP DATABASE " + DbName;
            stmt.executeUpdate(sql);
        }
        assertFalse(dbBuilder.CheckIfDbExists());
    }

}
