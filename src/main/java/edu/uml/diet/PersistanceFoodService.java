package edu.uml.diet;

import java.util.List;

/**
 * Created by Kurt Johnson on 2/15/2015.
 */
public interface PersistanceFoodService {

    //method to return SINGLE food information to Logic Layer
    BasicFood SearchForFood(String food);

    //method to return LIST of food information to Logic Layer
    List<BasicFood> SearchForFoodList(String food);
}
