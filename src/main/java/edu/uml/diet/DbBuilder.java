package edu.uml.diet;

import com.mysql.jdbc.Field;
import java.sql.*;
/**
 * Created by Raymond on 2/22/2015.
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
    public boolean CheckIfDbExists(String dbName){
        Statement stmt = null;
        Connection conn = databaseConnector.ConnectToDatabase();
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
        catch(SQLException e){
            return false;
        }
        return false;
    }

    /**
     * Method to check whether or not table exists in database has already been created locally
     *
     * @param tableName    name of table to be checked for
     * @return          returns true if specified database exists
     */
    public boolean CheckIfTableExists(String tableName){
        Statement stmt = null;
        Connection conn = databaseConnector.ConnectToDatabase();
        try{
            ResultSet resultSet = conn.getMetaData().getTables(null, null, null,
                    new String[] {"TABLE"});

            while(resultSet.next()){
                String tblName = resultSet.getString(1);
                if (tblName.equals(tableName)){
                    return true;
                }
            }
            resultSet.close();
        }
        catch(SQLException e){
            return false;
        }
        return false;
    }

    /**
     * Method used to create local database
     *
     */
    public void CreateDatabase(){
        Statement stmt = null;
        Connection conn = databaseConnector.ConnectToDatabaseServer();

        try{
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(sql);
            conn.close();
        }
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    /**
     *  Method used to create User database table
     *
     */
    public void CreateUserTable(){
        databaseConnector.setDB_URL(databaseConnector.getDB_URL() + dbName);
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
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
