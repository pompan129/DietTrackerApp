package edu.uml.diet.persistence;

import edu.uml.diet.model.BasicFood;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kurt Johnson on 2/15/2015.
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
}
