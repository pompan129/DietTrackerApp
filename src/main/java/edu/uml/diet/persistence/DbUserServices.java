package edu.uml.diet.persistence;



import edu.uml.diet.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * concrete class for UserServices.
 *
 * Created by Ray Goolishian on 2/24/2015.
 */
public class DbUserServices implements PersistanceUserServices {

    private final DatabaseConnector databaseConnector = new DatabaseConnector();
    public final DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector,"DietTracker");
    private final String tableName = "USERS";

    /**
     * Default constructor for DbUserServices class, if USERS table doesn't exist
     * constructor will create it
     * @throws PersistanceUserServicesException
     */
    public DbUserServices() throws PersistanceUserServicesException {
        try {
            if (!databaseBuilder.checkIfDbExists()) {
                databaseBuilder.initializeDatabase();
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
                databaseBuilder.initializeDatabase();
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
     * @param username Name of User being verified
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

            response = resultSet.next();

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

    /**
     *
     * @param username Name of User being retrieved
     * @return User object of User with username
     */
    public User getUser(String username) throws PersistanceUserServicesException{
        User user = new User();

        try {
            Session session = databaseConnector.getSessionFactory().openSession();
            Connection connection = null;
            try {
                connection = databaseConnector.getDatabaseConnection();
                session.beginTransaction();
                Query query = session.createQuery("from User where username = :userName");// '%" + food + "%'");
                query.setParameter("userName", username);
                user = (User) query.list().get(0);
                session.getTransaction().commit();
            }  finally {
                if (!connection.isClosed()) {
                    connection.close();
                }
                if (session.isConnected()) {
                    session.disconnect();
                }
            }
        }catch (SQLException | DatabaseConnectorException e) {
            throw new PersistanceUserServicesException("Could not connect to database." + e.getMessage(), e);
        }
        return user;
    }
}
