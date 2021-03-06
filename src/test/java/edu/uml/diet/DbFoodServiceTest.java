package edu.uml.diet;


import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
import edu.uml.diet.persistence.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Test class for DbFoodService class
 */
public class DbFoodServiceTest {
    private static DatabaseConnector databaseConnector;
    private static BasicFood basicFood1;
    private static BasicFood basicFood2;
    private static BasicFood basicFood3;
    private static BasicFood basicFood4;
    private List<BasicFood> basicFoodList;
    private static Connection connection;
    private static Session session;
    private String userName;
    private DateTime dateTime;

   @Before
    public void setup()throws DatabaseConnectorException, PersistanceFoodServiceException, IOException{
       String databaseName = "DietTracker";
       DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, databaseName);
        databaseConnector = new DatabaseConnector();
        basicFood1 = new BasicFood("testcheese1", 1, 2, 3, 4, 5, "test");
        basicFood2 = new BasicFood("testcheese2", 1, 2, 3, 4, 5, "test");
        basicFood3 = new BasicFood("testcheese3", 1, 2, 3, 4, 5, "test");
        basicFood4 = new BasicFood("testcheese31", 1, 2, 3, 4, 5, "test");
        databaseBuilder.initializeDatabase();
        connection = databaseConnector.getDatabaseConnection();
        session = databaseConnector.getSessionFactory().openSession();
        dateTime = DateTime.now();
        userName = "testuser";
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
        BasicFood basicFood5 = dbFoodService.searchForFood(basicFood2.getName());
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
        basicFoodList = dbFoodService.searchForFoodList("");
        assertEquals(basicFoodList.size(), 8615);
    }

    @Test
    public void testGetDay() throws PersistanceFoodServiceException{
        DbFoodService dbFoodService = new DbFoodService();
        Day day = dbFoodService.getDay(userName, dateTime);
        assertTrue(DateTimeComparator.getDateOnlyInstance().compare(dateTime, day.getDate()) == 0);
    }

    @Test
    public void testAddOrUpdateDay() throws PersistanceFoodServiceException, PersistanceUserServicesException{
        DbFoodService dbFoodService = new DbFoodService();

        // create first day, add meals and portions... save to db
        Day day = dbFoodService.getDay(userName, dateTime);
        Portion portion = new Portion();
        List<Portion> portions = new ArrayList<>();
        portions.add(portion);
        ArrayList<Meal> dayMeals = new ArrayList<>(day.getMeals());
        dayMeals.get(0).setPortions(portions);
        dbFoodService.addOrUpdateDay(day);

        // create second day, add meals and portions... save to db
        Day day2 = dbFoodService.getDay(userName, dateTime.plusDays(1));
        Portion portion2 = new Portion();
        List<Portion> portions2 = new ArrayList<>();
        portions2.add(portion2);
        ArrayList<Meal> dayMeals2 = new ArrayList<>(day2.getMeals());
        dayMeals2.get(0).setPortions(portions2);
        dbFoodService.addOrUpdateDay(day2);

        // modify first day and save updates
        Day day3 = dbFoodService.getDay(userName, dateTime);
        Portion portion3 = new Portion();
        List<Portion> portions3 = new ArrayList<>();
        portions3.add(portion3);
        ArrayList<Meal> dayMeals3 = new ArrayList<>(day3.getMeals());
        dayMeals3.get(1).setPortions(portions3);
        dbFoodService.addOrUpdateDay(day3);

        // get all portions from first day
        Day testDay = dbFoodService.getDay(userName, dateTime);
        ArrayList<Meal> testMeals = new ArrayList<>(testDay.getMeals());
        int portionCount = testMeals.get(0).getPortions().size() + testMeals.get(1).getPortions().size();

        assertTrue(portionCount == 2);
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
    }



}
