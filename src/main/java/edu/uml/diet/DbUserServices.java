package edu.uml.diet;



import java.io.IOException;
import java.sql.*;

/**
 * concrete class for UserServices.
 *
 * Created by Ray Goolishian on 2/24/2015.
 */
public class DbUserServices implements PersistanceUserServices {

    public DatabaseConnector databaseConnector = new DatabaseConnector();
    public DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector,"DietTracker");
    private String tableName = "USERS";

    /**
     * Default constructor for DbUserServices class, if USERS table doesn't exist
     * constructor will create it
     *
     * @throws DatabaseConnectorException
     * @throws IOException
     * @throws PersistanceFoodServiceException
     * @throws SQLException
     * @throws DuplicateFoodException
     */
    public DbUserServices() throws PersistanceUserServicesException{
        try {
            if (!databaseBuilder.checkIfDbExists()) {
                databaseBuilder.createDatabase();
            }
            if (!databaseBuilder.checkIfTableExists(tableName)) {
                databaseBuilder.createFoodTable();
            }
        }
        catch(DatabaseConnectorException e){
            throw new PersistanceUserServicesException("Could not connect to database." + e.getMessage(), e);
        }
    }

    /**
     * Method to add new User to database
     *
     * @param username username of user to be created as String
     * @param password password of user to be created as String
     * @throws PersistanceUserServicesException
     * @throws DuplicateUserException
     */
    public void createUser(String username, String password)throws PersistanceUserServicesException, DuplicateUserException{
        try(Connection connection = databaseConnector.getDatabaseConnection()){
            if (!databaseBuilder.checkIfDbExists()) {
                databaseBuilder.createDatabase();
            }
            if (!databaseBuilder.checkIfTableExists(tableName)) {
                databaseBuilder.createUserTable();
            }

            if (!verifyUsername(username)) {
                String sqlCommand = "INSERT INTO USERS (username, password) VALUES(?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.execute();
                preparedStatement.close();
                connection.close();
            }
            else{
                throw new DuplicateUserException("User already exists.",null);
            }
        }
        catch(SQLException | DatabaseConnectorException e){
            throw new PersistanceUserServicesException("Could not connect to database." + e.getMessage(), e);
        }
    }

    /**
     * Method to query database to check if User exists
     *
     * @param username
     * @return true if User exists, false if User does not exist
     * @throws PersistanceUserServicesException
     */
    public boolean verifyUsername(String username) throws PersistanceUserServicesException{

        boolean response;
        try(Connection connection = databaseConnector.getDatabaseConnection()){

            String sqlCommand = "SELECT * FROM " + tableName +" WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){response = true;}
            else{response = false;}

            preparedStatement.close();
            connection.close();
        }
        catch(SQLException | DatabaseConnectorException e){
            throw new PersistanceUserServicesException("Could not connect to database." + e.getMessage(), e);
        }
        return response;
    }

    /**
     * Method accepts User name string and returns password
     *
     * @param username name of use to return the password of as String
     *
     * @return user password to as a String
     * @throws PersistanceUserServicesException
     */
    public String getPassword(String username) throws PersistanceUserServicesException{

        String password = null;
        try(Connection connection = databaseConnector.getDatabaseConnection()){

            String sqlCommand = "SELECT password FROM " + tableName + " WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                password = resultSet.getString(1);
            }
        }
        catch(SQLException | DatabaseConnectorException e){
            throw new PersistanceUserServicesException("Could not connect to database." + e.getMessage(), e);
        }
        return password;
    }
}
