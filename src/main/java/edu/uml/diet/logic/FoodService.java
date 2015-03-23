package edu.uml.diet.logic;

import edu.uml.diet.model.Day;
import edu.uml.diet.model.Portion;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Interface for searching getting food information from persistence Layer
 */
public interface FoodService {

    //method to return SINGLE portion information to UI Layer
    Portion foodSearch(String food) throws FoodServiceException;

    //method to return LIST of portions information to UI Layer
    List<Portion> foodListSearch(String food) throws FoodServiceException;

    /**
     * Mthod to retrieve Day object from Persistance
     * @param username
     * @param date
     * @return Day object specified by Date & username
     * @throws FoodServiceException
     */
    Day getDay(String username, DateTime date) throws FoodServiceException;

    /**
     * method to save Day object to Persistence
     * @param day
     */
    void addOrUpdateDay(Day day) throws FoodServiceException;
}
