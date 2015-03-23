package edu.uml.diet.logic;

import edu.uml.diet.persistence.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Factory class for the FoodServiceLogicLayer interface
 */
public class ServiceFactory {

    private ServiceFactory(){}; //stop instantiation

    public static FoodService getFoodServiceInstance() throws FoodServiceException {
        PersistanceFoodService persistanceFoodService;

        try {
            persistanceFoodService = PersistanceServiceFactory.getPersistanceFoodServiceInstance();
        } catch (SQLException e) {
            throw new FoodServiceException("UserService Error: ", e);
        } catch (PersistanceFoodServiceException e) {
            throw new FoodServiceException("FoodService Error: ", e);

        } catch (DatabaseConnectorException e) {
            throw new FoodServiceException("FoodService Error: ", e);

        } catch (DuplicateFoodException e) {
            throw new FoodServiceException("Service Error: Duplicate foods", e);

        } catch (IOException e) {
            throw new FoodServiceException("Service connection Error: ", e);

        }

        return new BasicFoodService(persistanceFoodService);

    }

    public static UserService getUserServiceInstance() throws UserServiceException {
        try {
            return new BasicUserService(PersistanceServiceFactory.getPersistanceUserServicesInstance());
        } catch (PersistanceUserServicesException e) {
            throw new UserServiceException("cannot connect to User services", e);
        }

    }

}
