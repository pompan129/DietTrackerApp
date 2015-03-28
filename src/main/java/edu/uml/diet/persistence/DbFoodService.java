package edu.uml.diet.persistence;

import edu.uml.diet.model.BasicFood;

import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;
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
 * Created by rgoolishian on 3/4/2015.
 */
public class DbFoodService implements PersistanceFoodService {

    public DatabaseConnector databaseConnector = new DatabaseConnector();
    public DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector,"DietTracker");
    private String tableName = "FOOD";

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
     * @return
     */
    public BasicFood searchForFood(String food) throws PersistanceFoodServiceException{
        Connection connection = null;
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
        Connection connection = null;
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
                Query query = null;
                if(food == ""){
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
     * @param basicFood
     * @param connection
     * @param session
     * @return true if duplicate food is found, else return false
     */
    private boolean checkForDuplicateFood(BasicFood basicFood, Connection connection, Session session){
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
     * @param connection
     * @param session
     * @throws PersistanceFoodServiceException
     * @throws DuplicateFoodException
     */
    public void createFood(BasicFood basicFood, Connection connection, Session session)
            throws PersistanceFoodServiceException, DuplicateFoodException {

        if(checkForDuplicateFood(basicFood,connection,session)){
            throw new DuplicateFoodException("Could not create new food, food already exists ", null);
        }

        if(basicFood.getHouseholdWeight() == 0){

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
        DbFoodService dbFoodService = null;
        try {
            dbFoodService = new DbFoodService();
            File file = new File(getClass().getClassLoader().getResource("asciiFoodDatabase.txt").getFile());
            ArrayList<DbParser.dbFood> dbFoodArrayList = dbParser.importDatabase(file.getPath());

            for (DbParser.dbFood dbFood : dbFoodArrayList) {
                BasicFood basicFood = new BasicFood(dbFood.getName(), (int) dbFood.getCalories(),
                        (int) (dbFood.getMonounsaturatedFat() + dbFood.getPolyunsaturatedFat() + dbFood.getSaturatedFat()),

                        (int) dbFood.getCarbohydrate(), (int) dbFood.getProtein(), dbFood.getHouseholdWeight1(),
                        dbFood.getHouseholdWeight1Description());
                basicFoodArrayList.add(basicFood);
            }
        }
        catch(IOException e){
            throw new PersistanceFoodServiceException("Could not populate database database." + e.getMessage(), e);
        }

        Connection connection = null;
        Session session = null;
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
     * @param username
     * @param date
     * @return
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

                List<Meal> meals = new ArrayList<Meal>();
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
     *
     * @param day Day object to be added or updated
     * @return
     */
    public void addOrUpdateDay(Day day){

        //Session session = databaseConnector.getSessionFactory().getCurrentSession();
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

    /**
     * Get a list of portions in a meal
     *
     * @param meal the Meal
     * @return a list of Portion instances
     */

    /*@SuppressWarnings("unchecked")
    public List<Portion> getPortions(Meal meal) {
        Session session =  databaseConnector.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        List<Portion> portions = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Portion.class);
            //criteria.add(Restrictions.eq("meal", meal));
            // NOTE criteria.list(); generates unchecked warning so SuppressWarnings
            portions = criteria.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return portions;

    }*/



}
