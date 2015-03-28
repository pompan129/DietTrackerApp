package edu.uml.diet.logic;

import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
import edu.uml.diet.persistence.PersistanceFoodService;
import edu.uml.diet.persistence.PersistanceFoodServiceException;
import edu.uml.diet.persistence.PersistanceUserServicesException;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class of FoodService interface. provides a variety of searching
 */
public class BasicFoodService implements FoodService {

    PersistanceFoodService persistanceFoodService;


    /**
     * constrcuor class. sets PersistanceFoodService
     *
     * @param persistanceFoodService
     */
    public BasicFoodService(PersistanceFoodService persistanceFoodService) {
        this.persistanceFoodService = persistanceFoodService;
    }


    /**
     * returns a Portion object wrapped around a BasicFood object. Search based on BasicFood description.
     *
     * @param foodName
     * @return Portion containing the BasicFood
     * @throws FoodServiceException
     */
    public Portion foodSearch(String foodName) throws FoodServiceException {
        BasicFood food;

        try {
            food = persistanceFoodService.searchForFood(foodName);
        } catch (PersistanceFoodServiceException | SQLException e) {
            throw new FoodServiceException("FoodService Error: Service Unavailable ", e);
        }
        return new Portion(food);
    }

    /**
     * method to return a List of Portion objects based on BasicFood search criteria
     *
     * @param food
     * @return
     * @throws FoodServiceException when unable to connect to Persistence or with bad SQL syntax.
     *                              client should try later or inform Persistence admin.
     */
    @Override
    public List<Portion> foodListSearch(String food) throws FoodServiceException {
        ArrayList<Portion> portionList = new ArrayList<Portion>();
        List<BasicFood> basicFoodList = null;
        try {
            basicFoodList = persistanceFoodService.searchForFoodList(food);
        } catch (PersistanceFoodServiceException | SQLException e) {
            throw new FoodServiceException("FoodService Error: cannot connect to service", e);
        }

        for (BasicFood basicFood : basicFoodList) {
            portionList.add(new Portion(basicFood));
        }
        return portionList;
    }

    /**
     * Method to retrieve Day object from Persistance
     *
     * @param username
     * @param date
     * @return Day object specified by Date & username
     * @throws edu.uml.diet.logic.FoodServiceException when unable to connect to Persistance. client should
     *                                                 try later &/or inform Persistence admin.
     */
    @Override
    public Day getDay(String username, DateTime date) throws FoodServiceException {

        Day day = null;
        try {
            day = persistanceFoodService.getDay(username, date);
        } catch (PersistanceFoodServiceException e) {
            throw new FoodServiceException("Problem retrieveing 'Day' information", e);
        }

        for (Meal meal : day.getMeals()) {
            if (meal.getPortions() == null) {
                meal.setPortions(new ArrayList<Portion>());
            }
        }

        return day;
    }


    /**
     * method to save Day object to Persistence
     *
     * @param day
     * @throws FoodServiceException . error when unable to connect with Persistence. notify Persistence admin
     *                              &/or try later.
     */
    @Override
    public void addOrUpdateDay(Day day) throws FoodServiceException {
        try {
            persistanceFoodService.addOrUpdateDay(day);
        } catch (PersistanceUserServicesException e) {
            throw new FoodServiceException("Service Error: Cannot update 'Day' at this time. ", e);
        }

    }
}
