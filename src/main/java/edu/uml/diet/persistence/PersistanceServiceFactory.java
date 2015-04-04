package edu.uml.diet.persistence;

/**
 * Class used to create User and Food Service objects
 */
public class PersistanceServiceFactory {

    /**
     * stop instantiation
     */
    private PersistanceServiceFactory(){}

    /**
     *
     * @return PersistanceUserServices object
     * @throws PersistanceUserServicesException
     */
    public static PersistanceUserServices getPersistanceUserServicesInstance() throws PersistanceUserServicesException {
        return new DbUserServices();

    }

    /**
     *
     * @return PersistanceFoodService object
     * @throws PersistanceFoodServiceException
     */
    public static PersistanceFoodService getPersistanceFoodServiceInstance() throws PersistanceFoodServiceException {

        return new DbFoodService();
    }
}
