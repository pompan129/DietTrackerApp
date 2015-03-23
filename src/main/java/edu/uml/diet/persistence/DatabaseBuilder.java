package edu.uml.diet.persistence;

import com.ibatis.common.jdbc.ScriptRunner;
import com.ibatis.common.jdbc.*;
import java.io.*;
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
     * @throws edu.uml.diet.persistence.DatabaseConnectorException
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
     *
     * @throws DatabaseConnectorException
     */
    public void initializeDatabase() throws DatabaseConnectorException{
        String dbInitializationScript = "./src/main/resources/diettracker_db_init.sql";

        Connection connection = databaseConnector.getServerConnection();
        try{
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new FileInputStream(dbInitializationScript);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            runner.runScript(inputStreamReader);
            inputStreamReader.close();
            connection.commit();
            connection.close();
        }
        catch(IOException | SQLException e){
            throw new DatabaseConnectorException("Could not build database " + e.getMessage(), e);
        }
    }

    /**
     *
     * @throws DatabaseConnectorException
     */
    public void tearDownDatabase() throws DatabaseConnectorException{
        String dbTearDownScript = "./src/main/resources/diettracker_db_teardown.sql";

        Connection connection = databaseConnector.getServerConnection();
        try{
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new FileInputStream(dbTearDownScript);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            runner.runScript(inputStreamReader);
            inputStreamReader.close();
            connection.commit();
            connection.close();
        }
        catch(IOException | SQLException e){
            throw new DatabaseConnectorException("Could not build database " + e.getMessage(), e);
        }
    }
}