package edu.uml.diet;

import edu.uml.diet.model.BasicFood;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub of Basic food searching service used by UI
 */
public class BasicFoodService implements FoodService{


    @Override
    public Portion foodSearch(String food) {
        return new Portion(new BasicFood(food, 1,1,1,1));
    }

    @Override
    public List<Portion> foodListSearch(String food) {
        ArrayList<Portion> portionList =  new ArrayList<Portion>();
         new BasicFood(food,1,1,1,1);
        for(int i = 0; i<10;i++){
           Portion portion = new Portion(new BasicFood(food+i, 1,1,1,1));
            portionList.add(portion);
        }
        return portionList;
    }
}
