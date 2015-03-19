package edu.uml.diet.logic;

import edu.uml.diet.persistence.PersistanceServiceFactory;
import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Portion;
import edu.uml.diet.persistence.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub of Basic food searching service used by UI
 */
public class BasicFoodService implements FoodService {

   PersistanceFoodService persistanceFoodService;


    public BasicFoodService(PersistanceFoodService persistanceFoodService){
        this.persistanceFoodService = persistanceFoodService;
    }


    public Portion foodSearch(String foodName) throws FoodServiceException {
        BasicFood food;

        try {
            food = persistanceFoodService.searchForFood(foodName);
        } catch (PersistanceFoodServiceException e) {
            throw new FoodServiceException("FoodService Error: ", e);
        } catch (SQLException e) {
            throw new FoodServiceException("FoodService Error: ", e);
        }
        return new Portion(food);
    }

    @Override
    public List<Portion> foodListSearch(String food) throws FoodServiceException {
        ArrayList<Portion> portionList =  new ArrayList<Portion>();
        List<BasicFood> basicFoodList = null;
        try {
            basicFoodList = persistanceFoodService.searchForFoodList(food);
        } catch (PersistanceFoodServiceException e) {
            throw new FoodServiceException("FoodService Error: ", e);
        } catch (SQLException e) {
            throw new FoodServiceException("FoodService Error: ", e);
        }

        for(BasicFood basicFood: basicFoodList){
            portionList.add(new Portion(basicFood));
        }
        return portionList;
    }
}
