package edu.uml.diet.persistence;

import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Day;
import org.hibernate.Session;
import org.joda.time.DateTime;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for PersistenceFoodService class
 */
public interface PersistanceFoodService {

    /**
     *
     * @param food food item being searched for as String
     * @return BasicFood object
     */
    public BasicFood searchForFood(String food)throws PersistanceFoodServiceException, SQLException;

    /**
     *
     * @param food food item being searched for as String
     * @return list of BasicFood objects
     */
    public List<BasicFood> searchForFoodList(String food)throws PersistanceFoodServiceException, SQLException;

    /**
     *
     * @param basicFood adds a new food item to the database
     */
    public void createFood(BasicFood basicFood, Connection connection, Session session)
            throws PersistanceFoodServiceException, DuplicateFoodException, IOException, SQLException;

    /**
     *  Method used to populate food database with data from FDA database
     * @throws IOException
     * @throws PersistanceFoodServiceException
     */
    public void populateFoodDatabase() throws IOException, PersistanceFoodServiceException,
            SQLException, DatabaseConnectorException, DuplicateFoodException;

    /**
     *
     * @param username User Name of User whose day is being opened/created
     * @param dateTime Date of Day object being opened/created
     * @return Day object for User and Date passed to method
     * @throws PersistanceFoodServiceException
     */
    public Day getDay(String username, DateTime dateTime) throws PersistanceFoodServiceException;

    /**
     * Method used to create Day in database or update existing Day
     *
     * @param day Day object to be added or updated
     */
    public void addOrUpdateDay(Day day) throws PersistanceUserServicesException;


}
