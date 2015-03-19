package edu.uml.diet;


import com.ibatis.common.jdbc.ScriptRunner;
import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
import edu.uml.diet.persistence.DatabaseBuilder;
import edu.uml.diet.persistence.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by rgoolishian on 3/4/2015.
 */
public class DbFoodServiceTest {
    private static DatabaseConnector databaseConnector;
    private static BasicFood basicFood1;
    private static BasicFood basicFood2;
    private static BasicFood basicFood3;
    private static BasicFood basicFood4;
    private BasicFood basicFood5;
    private List<BasicFood> basicFoodList;
    private static DatabaseBuilder databaseBuilder;
    private static Connection connection;
    private static Session session;
    private static boolean createdDatabase;
    private String databaseName;

   @Before
    public void setup()throws DatabaseConnectorException, PersistanceFoodServiceException, IOException{
        databaseName = "DietTracker";
        databaseBuilder = new DatabaseBuilder(databaseConnector,databaseName);
        databaseConnector = new DatabaseConnector();
        basicFood1 = new BasicFood("testcheese1", 1, 2, 3, 4, 5, "test");
        basicFood2 = new BasicFood("testcheese2", 1, 2, 3, 4, 5, "test");
        basicFood3 = new BasicFood("testcheese3", 1, 2, 3, 4, 5, "test");
        basicFood4 = new BasicFood("testcheese31", 1, 2, 3, 4, 5, "test");
        if (!databaseBuilder.checkIfDbExists()) {
            databaseBuilder.initializeDatabase();
            createdDatabase = true;
        }


        connection = databaseConnector.getDatabaseConnection();
        session = databaseConnector.getSessionFactory().openSession();
    }


    @Test
    public void testCreateFood() throws PersistanceFoodServiceException,IOException, DuplicateFoodException,
            SQLException, DatabaseConnectorException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.createFood(basicFood1, connection, session);
        BasicFood basicFood = dbFoodService.searchForFood(basicFood1.getName());
        assertTrue(basicFood != null);
    }

    @Test
    public void testSearchForFood() throws PersistanceFoodServiceException, IOException, DuplicateFoodException,
            SQLException, DatabaseConnectorException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.createFood(basicFood2, connection, session);
        basicFood5 = dbFoodService.searchForFood(basicFood2.getName());
        assertTrue(basicFood2.equals(basicFood5));
    }

    @Test
    public void testSearchForFoodList()throws PersistanceFoodServiceException, IOException, DuplicateFoodException,
            SQLException, DatabaseConnectorException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.createFood(basicFood3, connection, session);
        dbFoodService.createFood(basicFood4, connection, session);
        basicFoodList = dbFoodService.searchForFoodList("testcheese3%");
        assertTrue(basicFoodList.size() == 2);
    }

    @Test
    public void testPopulateFoodDatabase()throws PersistanceFoodServiceException, IOException,
            SQLException, DatabaseConnectorException, DuplicateFoodException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.populateFoodDatabase();
    }

   @Test
    public void testAddOrUpdatePortion() throws PersistanceFoodServiceException, DuplicateFoodException {
        DbFoodService dbFoodService = new DbFoodService();
        //Meal meal = new Meal();
       //meal.setId(99);
        BasicFood portionTestFood = new BasicFood("PORTION_TEST_FOOD", 1, 2, 3, 4, 2.0, "some_weight");
        dbFoodService.createFood(portionTestFood, connection, session);
        portionTestFood = dbFoodService.searchForFood("PORTION_TEST_FOOD");
        Portion portion = new Portion();
        portion.setFood(portionTestFood);
        portion.setPortionSize(5.0);
       portion.getFood().setCarbs(1000);
       //portion.setMeal(meal);

       dbFoodService.addOrUpdatePortion(portion);

       // List<Portion> portionList =  dbFoodService.getPortions(meal);
       //assertTrue("List is not empty", portionList.size() > 0);

    }


   @After
    public void tearDown() throws DatabaseConnectorException, SQLException {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from BasicFood where name= :name1 " +
                "OR name= :name2 " +
                "OR name= :name3 " +
                "OR name= :name4 ");
        query.setString("name1", basicFood1.getName());
        query.setString("name2", basicFood2.getName());
        query.setString("name3", basicFood3.getName());
        query.setString("name4", basicFood4.getName());
        query.executeUpdate();
        transaction.commit();
        if (createdDatabase) {
            databaseBuilder.tearDownDatabase();
        }
    }



}
