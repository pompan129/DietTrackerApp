package edu.uml.diet;

import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Class used to check whether DB and required tables exist
 * and create DB and tabled if not existing
 *
 * Created by Ray Goolishian on 2/22/2015.
 */
public class DatabaseBuilder {

    private DatabaseConnector databaseConnector;
    private String databaseName;

    /**
     *
     * @return DatabaseConnector object
     */
    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    /**
     * Specify DatabaseConnector object
     *
     * @param databaseConnector DatabaseConnector to be specified
     */
    public void setDatabaseConnector(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    /**
     *
     * @return name of database to be connected to
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Specify the name of the database to be connected to
     *
     * @param databaseName name of database as a String
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * Initialize DatabaseBuilder object with DatabaseConnector object
     * and database name as String
     *
     * @param databaseConnector DatabaseConnector object with database properties
     * @param databaseName name of database to be connected to as a String
     */
    public DatabaseBuilder(DatabaseConnector databaseConnector, String databaseName){
        this.databaseConnector = databaseConnector;
        this.databaseName = databaseName;
    }

    /**
     * Method to check whether or not database has already been created locally
     *
     * @return          returns true if specified database exists
     * @throws DatabaseConnectorException
     */
    public boolean checkIfDbExists() throws DatabaseConnectorException {
        Statement statement = null;
        Connection connection = databaseConnector.getServerConnection();
        boolean exists = false;
        try{
            ResultSet resultSet = connection.getMetaData().getCatalogs();

            while(resultSet.next()){
                String databaseName = resultSet.getString(1).toLowerCase();
                if (databaseName.equals(this.databaseName.toLowerCase())){
                    exists = true;
                }
            }
            resultSet.close();
        }
        catch(SQLException e){
            throw new DatabaseConnectorException("Could not connect to database." + e.getMessage(), e);
        }
        finally{
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            }
            catch(SQLException e){
                throw new DatabaseConnectorException("Could not connect to database." + e.getMessage(), e);
            }
        }
        return exists;
    }

    /**
     * Method to check whether or not table exists in database has already been created locally
     *
     * @param tableName    name of table to be checked for
     * @return          returns true if specified database exists
     * @throws DatabaseConnectorException
     */
    public boolean checkIfTableExists(String tableName) throws DatabaseConnectorException{
        Statement statement = null;
        Connection connection = databaseConnector.getDatabaseConnection();
        boolean exists = false;
        try{
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", null);

            while(resultSet.next()){
                String tName = resultSet.getString(3).toLowerCase();
                if (tName.equals(tableName.toLowerCase())){
                    exists =  true;
                }
            }
            resultSet.close();
        }
        catch(SQLException e){
            throw new DatabaseConnectorException("Could not connect to database." + e.getMessage(), e);
        }
        finally{
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            }
            catch(SQLException e){
                throw new DatabaseConnectorException("Could not connect to database." + e.getMessage(), e);
            }

        }
        return exists;
    }

    /**
     * Method used to create local database
     * @throws DatabaseConnectorException
     */
    public void createDatabase() throws DatabaseConnectorException{
        Statement stmt = null;
        Connection conn = databaseConnector.getServerConnection();

        try{
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            stmt.executeUpdate(sql);
            conn.close();
        }
        catch(SQLException e){
            throw new DatabaseConnectorException("Could not create database." + e.getMessage(), e);
        }
        finally{
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch(SQLException e){
                throw new DatabaseConnectorException("Could not create database." + e.getMessage(), e);
            }
        }
    }

    /**
     *  Method used to create USER database table
     * @throws DatabaseConnectorException
     */
    public void createUserTable() throws DatabaseConnectorException{
        Connection connection = databaseConnector.getDatabaseConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String sql = "CREATE TABLE USERS (" +
                    "id INTEGER not NULL AUTO_INCREMENT, " +
                    "first_name VARCHAR(255)," +
                    "last_name VARCHAR(255)," +
                    "username VARCHAR(255)," +
                    "password VARCHAR(255), "+
                    "PRIMARY KEY(id))";
            statement.executeUpdate(sql);
        }
        catch(SQLException e){
            throw new DatabaseConnectorException("Could not create database." + e.getMessage(), e);
        }
        finally{
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            }
            catch(SQLException e){
                throw new DatabaseConnectorException("Could not create database." + e.getMessage(), e);
            }
        }
    }

    /**
     * Method used to create FOOD database table
     * @throws DatabaseConnectorException
     */
    public void createFoodTable() throws DatabaseConnectorException {
        Connection connection = databaseConnector.getDatabaseConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String sql = "CREATE TABLE FOOD (" +
                    "id INTEGER not NULL AUTO_INCREMENT, " +
                    "name VARCHAR(255)," +
                    "calories VARCHAR(255)," +
                    "fat VARCHAR(255)," +
                    "carbohydrates VARCHAR(255)," +
                    "protein VARCHAR(255), " +
                    "PRIMARY KEY(id))";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DatabaseConnectorException("Could not create database." + e.getMessage(), e);
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new DatabaseConnectorException("Could not create database." + e.getMessage(), e);
            }
        }
    }
}