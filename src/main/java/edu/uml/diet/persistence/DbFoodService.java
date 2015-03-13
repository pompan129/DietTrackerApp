package edu.uml.diet.persistence;

import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Portion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.io.File;
import java.io.IOException;
import java.sql.*;
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
            Session session = databaseConnector.getSessionFactory().openSession();

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
                Query query = session.createQuery("from BasicFood where name like :foodName");// '%" + food + "%'");
                query.setParameter("foodName","%" + food + "%");
                foundFood = query.list();
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
                        (int) dbFood.getCarbohydrate(), (int) dbFood.getProtein());
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
                if (session.isConnected()) {
                    session.disconnect();
                }
            }
            catch (SQLException e){
                throw new PersistanceFoodServiceException("Could not close session " + e.getMessage(), null);
            }
        }
    }

    public void addOrUpdatePortion(Portion portion){

        Session session = DatabaseConnector.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(portion);
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
