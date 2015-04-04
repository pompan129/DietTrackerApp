package edu.uml.diet.persistence;

import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Day;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.joda.time.DateTime;
import java.sql.Date;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Class utilized for all persistent food activities
 */
public class DbFoodService implements PersistanceFoodService {

    private final DatabaseConnector databaseConnector = new DatabaseConnector();
    private final DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector,"DietTracker");
    private final String tableName = "FOOD";

    /**
     * Default constructor for DbFoodService class, if FOOD table doesn't exist
     * constructor will create it
     *
     * @throws PersistanceFoodServiceException
     */
    public DbFoodService() throws PersistanceFoodServiceException{
        try {
            if (!databaseBuilder.checkIfDbExists()) {
                databaseBuilder.initializeDatabase();
            }
        }
        catch (DatabaseConnectorException e){
            throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
        }

    }

    /**
     *
     * @param food food item being searched for as String
     * @return BasicFood object matching String passed to method
     */
    public BasicFood searchForFood(String food) throws PersistanceFoodServiceException{
        Connection connection;
        BasicFood foundFood = null;
        try{
            connection = databaseConnector.getDatabaseConnection();
            if (!databaseBuilder.checkIfDbExists()) {
                return null;
            }
            if (!databaseBuilder.checkIfTableExists(tableName)) {
                return null;
            }
            Session session = databaseConnector.getSessionFactory().getCurrentSession();

            try {
                session.beginTransaction();
                Query query = session.createQuery("from BasicFood where name like :foodName ");// '%" + food + "%'");
                query.setParameter("foodName", food);
                foundFood = (BasicFood)query.list().get(0);
                session.getTransaction().commit();
            }
            finally {
                if(!connection.isClosed()){
                    connection.close();
                }
                if(session.isConnected()){
                    session.disconnect();
                }
            }
        }
        catch(DatabaseConnectorException | SQLException e){
            throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
        }
        return foundFood;
    }

    /**
     *
     * @param food food item being searched for as String
     * @return list of foods like food parameter
     * @throws PersistanceFoodServiceException
     */
    public List<BasicFood> searchForFoodList(String food) throws PersistanceFoodServiceException{
        Connection connection;
        List<BasicFood> foundFood = null;
        try{
            connection = databaseConnector.getDatabaseConnection();
            if (!databaseBuilder.checkIfDbExists()) {
                return null;
            }
            if (!databaseBuilder.checkIfTableExists(tableName)) {
                return null;
            }
            Session session = databaseConnector.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                Query query;
                if(food.equals("")){
                    query = session.createQuery("from BasicFood");
                    foundFood = query.list();
                }
                else {
                    query = session.createQuery("from BasicFood where name like :foodName");
                    query.setParameter("foodName", "%" + food + "%");
                    foundFood = query.list();
                }
                session.getTransaction().commit();
            }
            finally {
                if(!connection.isClosed()){
                    connection.close();
                }
                if(session.isConnected()){
                    session.disconnect();
                }
            }
        }
        catch(DatabaseConnectorException | SQLException e){
            throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
        }
        return foundFood;
    }

    /**
     * Method to query database for duplicate record prior to creating a new food record
     *
     * @param basicFood BasicFood object being checked for existence
     * @param session Session object to be used for transaction
     * @return true if duplicate food is found, else return false
     */
    private boolean checkForDuplicateFood(BasicFood basicFood, Session session){
        boolean isDuplicate = false;
        session.beginTransaction();
        Query query = session.createQuery("from BasicFood where name = :name");
        query.setString("name", basicFood.getName());
        if(!query.list().isEmpty()){
            isDuplicate = true;
        }
        session.getTransaction().commit();
        return isDuplicate;
    }

    /**
     * Method used to create food
     * @param basicFood adds a new food item to the database
     * @param connection Connection object to be used for transaction
     * @param session Session object to be used for transaction
     * @throws PersistanceFoodServiceException
     * @throws DuplicateFoodException
     */
    public void createFood(BasicFood basicFood, Connection connection, Session session)
            throws PersistanceFoodServiceException, DuplicateFoodException {

        if(checkForDuplicateFood(basicFood,session)){
            throw new DuplicateFoodException("Could not create new food, food already exists ", null);
        }

        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(basicFood);
            transaction.commit();
        }
        catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }

    /**
     * Method user to populate food database table
     *
     * @throws PersistanceFoodServiceException
     * @throws DuplicateFoodException
     */
    public void populateFoodDatabase() throws PersistanceFoodServiceException, DuplicateFoodException{
        DbParser dbParser = new DbParser();
        ArrayList<BasicFood> basicFoodArrayList = new ArrayList<>();
        DbFoodService dbFoodService;
        try {
            dbFoodService = new DbFoodService();
            File file = new File(getClass().getClassLoader().getResource("asciiFoodDatabase.txt").getFile());
            ArrayList<DbParser.databaseFood> databaseFoodArrayList = dbParser.importDatabase(file.getPath());

            for (DbParser.databaseFood databaseFood : databaseFoodArrayList) {
                BasicFood basicFood = new BasicFood(databaseFood.getName(), (int) databaseFood.getCalories(),
                        (int) (databaseFood.getMonounsaturatedFat() + databaseFood.getPolyunsaturatedFat() + databaseFood.getSaturatedFat()),

                        (int) databaseFood.getCarbohydrate(), (int) databaseFood.getProtein(), databaseFood.getHouseholdWeight1(),
                        databaseFood.getHouseholdWeight1Description());
                basicFoodArrayList.add(basicFood);
            }
        }
        catch(IOException e){
            throw new PersistanceFoodServiceException("Could not populate database database." + e.getMessage(), e);
        }

        Connection connection = null;
        Session session;
        try {
            connection = databaseConnector.getDatabaseConnection();
            if (!databaseBuilder.checkIfDbExists()) {
                databaseBuilder.initializeDatabase();
            }

            session = databaseConnector.getSessionFactory().openSession();

            for (BasicFood basicFood : basicFoodArrayList) {
                try {
                    dbFoodService.createFood(basicFood, connection, session);
                }
                catch(DuplicateFoodException e){
                    continue;
                }
                catch (PersistanceFoodServiceException e) {
                    throw new PersistanceFoodServiceException("Could not create new food " + e.getMessage(), null);
                }
            }
        }
        catch(DatabaseConnectorException e){
            throw new PersistanceFoodServiceException("Could not populate database database." + e.getMessage(), e);
        }
        finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            catch (SQLException e){
                throw new PersistanceFoodServiceException("Could not close session " + e.getMessage(), null);
            }
        }
    }

   /* public void addOrUpdatePortion(Portion portion){

        Session session = databaseConnector.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(portion);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }*/

    /**
     *
     * @param username User Name of User whose day is being opened/created
     * @param date Date of Day object being opened/created
     * @return Day object for User and Date passed to method
     * @throws PersistanceFoodServiceException
     */
    public Day getDay(String username, DateTime date) throws PersistanceFoodServiceException{
        Day day = null;
        Session session = databaseConnector.getSessionFactory().getCurrentSession();
        Transaction transaction = null;

        try{
            DbUserServices dbUserServices = new DbUserServices();

            transaction = session.beginTransaction();
            Query query = session.createQuery("from Day where date = :date AND user_id = :user_id");// '%" + food + "%'");
            query.setParameter("date", new Date(date.toDate().getTime())) ;
            query.setParameter("user_id", dbUserServices.getUser(username).getId());
            if(query.list().size() > 0) {
                day = (Day) query.list().get(0);
                day.getMeals();
            }
            transaction.commit();

            if(day == null){
                day = new Day();
                day.setUser(dbUserServices.getUser(username));
                day.setDate(new Date(date.toDateMidnight().toDate().getTime()));

                // create empty meals
                Meal breakfast = new Meal();
                breakfast.setName("Breakfast");
                breakfast.setDay(day);

                Meal lunch = new Meal();
                lunch.setName("Lunch");
                lunch.setDay(day);

                Meal dinner = new Meal();
                dinner.setName("Dinner");
                dinner.setDay(day);

                Meal snack = new Meal();
                snack.setName("Snack");
                snack.setDay(day);

                List<Meal> meals = new ArrayList<>();
                meals.add(breakfast);
                meals.add(lunch);
                meals.add(dinner);
                meals.add(snack);

                // add meals to newly created day
                day.setMeals(meals);

                addOrUpdateDay(day);
            }
        }
        catch(PersistanceUserServicesException e){
            throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
        }
        finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return day;
    }

    /**
     * Method used to create Day in database or update existing Day
     *
     * @param day Day object to be added or updated
     */
    public void addOrUpdateDay(Day day){

        Session session = databaseConnector.getSessionFactory().getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(day);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }


}
