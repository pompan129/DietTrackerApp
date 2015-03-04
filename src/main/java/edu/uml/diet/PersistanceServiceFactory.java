package edu.uml.diet;

/**
 * Created by Kurt Johnson on 3/3/2015.
 */
public class PersistanceServiceFactory {

    private PersistanceServiceFactory(){}; //stop instantiation

    public static PersistanceUserServices getPersistanceUserServicesInstance(){
        return new DbUserServices();
    }
}
