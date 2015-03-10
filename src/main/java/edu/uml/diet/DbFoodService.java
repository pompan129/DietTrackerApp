package edu.uml.diet;

import edu.uml.diet.model.BasicFood;
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

     * @throws DatabaseConnectorException
     * @throws IOException
     * @throws PersistanceFoodServiceException
     * @throws SQLException
     * @throws DuplicateFoodException
     */
    public DbFoodService() throws DatabaseConnectorException, IOException, PersistanceFoodServiceException,
            SQLException, DuplicateFoodException{
        if (!databaseBuilder.checkIfDbExists()) {
            databaseBuilder.createDatabase();
        }
        if (!databaseBuilder.checkIfTableExists(tableName)) {
            databaseBuilder.createFoodTable();
            populateFoodDatabase();
        }
    }

    /**
     *
     * @param food food item being searched for as String
     * @return
     */
    public BasicFood searchForFood(String food) throws PersistanceFoodServiceException, SQLException{
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
        catch(DatabaseConnectorException e){
            throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
        }
        return foundFood;
    }

    /**
     *
     * @param food food item being searched for as String
     * @return list of foods like food parameter
     */
    public List<BasicFood> searchForFoodList(String food) throws PersistanceFoodServiceException, SQLException{
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
        catch(DatabaseConnectorException e){
            throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
        }
        return foundFood;
    }

    /**
     * Method to query database for duplicate record prior to creating a new food record
     * @param basicFood BasicFood object being searched for
     * @param connection open connection
     * @param session open session
     * @return true if a duplicate was found, false otherwise
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
     *
     * @param basicFood adds a new food item to the database
     * @param connection open connection
     * @param session open session
     * @throws PersistanceFoodServiceException
     * @throws DuplicateFoodException
     * @throws SQLException
     */
    public void createFood(BasicFood basicFood, Connection connection, Session session)
            throws PersistanceFoodServiceException, DuplicateFoodException, SQLException {

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
     * @throws IOException
     * @throws PersistanceFoodServiceException
     */
    public void populateFoodDatabase() throws IOException, PersistanceFoodServiceException,
            SQLException, DatabaseConnectorException, DuplicateFoodException{
        DbParser dbParser = new DbParser();
        DbFoodService dbFoodService = new DbFoodService();

        File file = new File(getClass().getClassLoader().getResource("asciiFoodDatabase.txt").getFile());
        ArrayList<DbParser.dbFood> dbFoodArrayList = dbParser.importDatabase(file.getPath());
        ArrayList<BasicFood> basicFoodArrayList = new ArrayList<>();
        for(DbParser.dbFood dbFood : dbFoodArrayList ){
            BasicFood basicFood = new BasicFood(dbFood.getName(), (int)dbFood.getCalories(),
                    (int)(dbFood.getMonounsaturatedFat() + dbFood.getPolyunsaturatedFat() + dbFood.getSaturatedFat()),
                    (int)dbFood.getCarbohydrate(), (int)dbFood.getProtein());
            basicFoodArrayList.add(basicFood);
        }

        Connection connection = null;
        Session session = null;
        try {
            connection = databaseConnector.getDatabaseConnection();
            if (!databaseBuilder.checkIfDbExists()) {
                databaseBuilder.createDatabase();
            }
            if (!databaseBuilder.checkIfTableExists(tableName)) {
                databaseBuilder.createFoodTable();
            }

            session = databaseConnector.getSessionFactory().openSession();

            for (BasicFood basicFood : basicFoodArrayList) {
                try {
                    dbFoodService.createFood(basicFood, connection, session);
                }
                catch(DuplicateFoodException e){
                }
                catch (PersistanceFoodServiceException | SQLException e) {
                    throw new PersistanceFoodServiceException("Could not create new food " + e.getMessage(), null);
                }
            }
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
}
