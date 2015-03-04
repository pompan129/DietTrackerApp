package edu.uml.diet;

/**
 * Factory class for the FoodServiceLogicLayer interface
 */
public class ServiceFactory {

    private ServiceFactory(){}; //stop instantiation

    public static FoodService getFoodServiceInstance(){
        return new BasicFoodService();
    }

    public static  UserService getUserServiceInstance(){return new BasicUserService();}

}
