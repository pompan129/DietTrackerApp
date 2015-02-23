package edu.uml.diet;

/**
 * Factory class for the FoodServiceLogicLayer interface
 */
public class FoodServiceFactory {

    private FoodServiceFactory(){};

    public static FoodService getInstance(){
        return new BasicFoodService();
    }

}
