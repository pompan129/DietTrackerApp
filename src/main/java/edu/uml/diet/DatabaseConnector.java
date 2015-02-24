package edu.uml.diet;

import java.sql.*;
/**
 * Created by Raymond on 2/22/2015.
 */
public class DatabaseConnector {
    private String JDBC_DRIVER;
    private String HOST_URL;
    private String DB_URL;
    private String USER;
    private String PASS;

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public void setJDBC_DRIVER(String JDBC_DRIVER) {
        this.JDBC_DRIVER = JDBC_DRIVER;
    }

    public String getHOST_URL() {
        return HOST_URL;
    }

    public void setHOST_URL(String HOST_URL) {
        this.HOST_URL = HOST_URL;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public DatabaseConnector(String JDBC_DRIVER,String HOST_URL, String DB_URL, String USER, String PASS) {
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.HOST_URL = HOST_URL;
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    /**
     * Method used to open database server connection
     *
     * @return opened database connection
     */
    public Connection ConnectToDatabaseServer() throws SQLException{
        Connection conn = null;
        Driver d = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(d);
        conn = DriverManager.getConnection(HOST_URL,USER,PASS);
        return conn;
    }

    /**
     * Method used to open database connection
     *
     * @return opened database connection
     */
    public Connection ConnectToDatabase() throws SQLException{
        Connection conn = null;
        Driver d = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(d);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        return conn;
    }
}
