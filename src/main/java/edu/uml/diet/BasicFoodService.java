package edu.uml.diet;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub of Basic food searching service used by UI
 */
public class BasicFoodService implements FoodService{

   PersistanceFoodService persistanceFoodService = PersistanceServiceFactory.getPersistanceFoodServiceInstance();


    public Portion foodSearch(String food) {
        return new Portion(persistanceFoodService.SearchForFood(food));
    }

    @Override
    public List<Portion> foodListSearch(String food) {
        ArrayList<Portion> portionList =  new ArrayList<Portion>();
        List<BasicFood> basicFoodList = persistanceFoodService.SearchForFoodList(food);
         new BasicFood(food,1,1,1,1);
        for(BasicFood basicFood: basicFoodList){
            portionList.add(new Portion(basicFood));
        }
        return portionList;
    }
}
