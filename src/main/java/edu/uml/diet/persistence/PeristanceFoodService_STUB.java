package edu.uml.diet.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.uml.diet.model.BasicFood;
import org.hibernate.Session;

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

    /**
     * @param basicFood  adds a new food item to the database
     * @param connection
     * @param session
     */
    @Override
    public void createFood(BasicFood basicFood, Connection connection, Session session) throws PersistanceFoodServiceException, DuplicateFoodException, IOException, SQLException {

    }

    public void populateFoodDatabase() throws IOException, PersistanceFoodServiceException,
            SQLException, DatabaseConnectorException, DuplicateFoodException{

    }
}
