package edu.uml.diet;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Kurt Johnson on 3/3/2015.
 */
public class PersistanceServiceFactory {

    private PersistanceServiceFactory(){}; //stop instantiation

    public static PersistanceUserServices getPersistanceUserServicesInstance(){
        return new DbUserServices();
    }

    public static PersistanceFoodService getPersistanceFoodServiceInstance() throws SQLException, PersistanceFoodServiceException, DatabaseConnectorException, DuplicateFoodException, IOException {

        return new DbFoodService();
    }
}