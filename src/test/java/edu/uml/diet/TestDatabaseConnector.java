package edu.uml.diet;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Raymond on 2/22/2015.
 */
public class TestDatabaseConnector {
    private String JDBC_DRIVER;
    private String HOST_URL;
    private String DB_URL;
    private String USER;
    private String PASS;
    private String BADPASS;
    private String DbName;

    @Before
    public void setup() {
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DbName = "mysql";
        DB_URL = "jdbc:mysql://localhost/" + DbName;
        HOST_URL = "jdbc:mysql://localhost/";
        USER = "root";
        PASS = "PASSWORD";
        BADPASS = "BADPASSWORD";
    }

    @Test
    public void testGetJDBC_DRIVER() {
        DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
        assertEquals(JDBC_DRIVER,databaseConnector.getJDBC_DRIVER());
    }

    @Test
    public void testGetDB_URL() {
        DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
        assertEquals(DB_URL,databaseConnector.getDB_URL());
    }

    @Test
    public void testGetUSER() {
        DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
        assertEquals(USER,databaseConnector.getUSER());
    }

    @Test
    public void testGetPASS() {
        DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
        assertEquals(PASS,databaseConnector.getPASS());
    }

    @Test
    public void testConnectToDatabaseServer() throws SQLException{
        DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
        Connection conn = databaseConnector.ConnectToDatabaseServer();
        assertFalse(conn.isClosed()) ;
    }

    @Test
    public void testConnectToDatabase() throws SQLException{
        DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
        Connection conn = databaseConnector.ConnectToDatabase();
        assertFalse(conn.isClosed()) ;
    }

    @Test(expected = SQLException.class)
    public void testConnectToDatabaseNegative() throws SQLException{
        DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,BADPASS);
        Connection conn = databaseConnector.ConnectToDatabase();
        assertNull(conn); ;
    }
}
