package edu.uml.diet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurt Johnson on 3/7/2015.
 */
public class PeristanceFoodService_STUB implements PersistanceFoodService{

    @Override
    public BasicFood searchForFood(String food) {
        return new BasicFood(food,100,1,1,1);
    }

    @Override
    public List<BasicFood> searchForFoodList(String food) {
        ArrayList<BasicFood> basicFoods =  new ArrayList<BasicFood>();
        for(int i = 0; i<10;i++){

            basicFoods.add(new BasicFood(food + "_TEST", 100,1,1,1));
        }
        return basicFoods;
    }
}
