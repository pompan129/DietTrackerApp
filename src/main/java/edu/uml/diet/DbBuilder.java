package edu.uml.diet;

import com.mysql.jdbc.*;

import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;

/**
 * Class used to check whether DB and required tables exist
 * and create DB and tabled if not existing
 *
 * Created by Ray Goolishian on 2/22/2015.
 */
public class DbBuilder {

    private DatabaseConnector databaseConnector;
    private String dbName;

    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    public void setDatabaseConnector(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public DbBuilder(DatabaseConnector databaseConnector, String dbName){
        this.databaseConnector = databaseConnector;
        this.dbName = dbName;
    }

    /**
     * Method to check whether or not database has already been created locally
     *
     * @param dbName    name of database
     * @return          returns true if specified database exists
     */
    public boolean CheckIfDbExists(String dbName) throws SQLException{
        Statement stmt = null;
        Connection conn = databaseConnector.ConnectToDatabaseServer();
        try{
            ResultSet resultSet = conn.getMetaData().getCatalogs();

            while(resultSet.next()){
                String databaseName = resultSet.getString(1);
                if (databaseName.equals(dbName)){
                    return true;
                }
            }
            resultSet.close();
        }
        finally{
            if(stmt!=null)
                stmt.close();
            if(conn!=null)
                conn.close();
        }
        return false;
    }

    /**
     * Method to check whether or not table exists in database has already been created locally
     *
     * @param tableName    name of table to be checked for
     * @return          returns true if specified database exists
     */
    public boolean CheckIfTableExists(String tableName) throws SQLException{
        Statement stmt = null;
        Connection conn = databaseConnector.ConnectToDatabase();
        try{
            DatabaseMetaData md = conn.getMetaData();
            ResultSet resultSet = md.getTables(null, null, "%",null);

            while(resultSet.next()){
                String tblName = resultSet.getString(3);
                if (tblName.equals(tableName)){
                    return true;
                }
            }
            resultSet.close();
        }
        finally{
            if(stmt!=null)
                stmt.close();
            if(conn!=null)
                conn.close();
        }
        return false;
    }

    /**
     * Method used to create local database
     *
     */
    public void CreateDatabase() throws SQLException{
        Statement stmt = null;
        Connection conn = databaseConnector.ConnectToDatabaseServer();

        try{
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(sql);
            conn.close();
        }
        finally{
            if(stmt!=null)
                stmt.close();
            if(conn!=null)
                conn.close();
        }
    }

    /**
     *  Method used to create User database table
     *
     */
    public void CreateUserTable() throws SQLException{
        databaseConnector.setDB_URL(databaseConnector.getDB_URL());
        Connection conn = databaseConnector.ConnectToDatabase();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE USERS (" +
                    "id INTEGER not NULL, " +
                    "first_name VARCHAR(255)," +
                    "last_name VARCHAR(255))";
            stmt.executeUpdate(sql);
        }
        finally{
            //finally block used to close resources
            if(stmt!=null)
               stmt.close();
            if(conn!=null)
                conn.close();

        }
    }
}
