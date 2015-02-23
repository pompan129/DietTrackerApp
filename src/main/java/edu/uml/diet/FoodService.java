package edu.uml.diet;

import java.util.List;

/**
 * Interface for searching getting food information from persistence Layer
 */
public interface FoodService {

    //method to return SINGLE portion information to UI Layer
    Portion foodSearch(String food);

    //method to return LIST of portions information to UI Layer
    List<Portion> foodListSearch(String food);
}
