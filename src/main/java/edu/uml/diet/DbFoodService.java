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
     *
     * @param food food item being searched for as String
     * @return
     */
    public BasicFood SearchForFood(String food) throws PersistanceFoodServiceException, SQLException{
        Connection connection = null;
        BasicFood foundFood = null;
        try{
            connection = databaseConnector.getDatabaseConnection();
            if (!databaseBuilder.CheckIfDbExists()) {
                return null;
            }
            if (!databaseBuilder.CheckIfTableExists(tableName)) {
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
    public List<BasicFood> SearchForFoodList(String food) throws PersistanceFoodServiceException, SQLException {
        Connection connection = null;
        List<BasicFood> foundFood = null;
        try{
            connection = databaseConnector.getDatabaseConnection();
            if (!databaseBuilder.CheckIfDbExists()) {
                return null;
            }
            if (!databaseBuilder.CheckIfTableExists(tableName)) {
                return null;
            }
            Session session = databaseConnector.getSessionFactory().openSession();

            try {
                session.beginTransaction();
                Query query = session.createQuery("from BasicFood where name like :foodName");// '%" + food + "%'");
                query.setParameter("foodName", food);
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
     *
     * @param basicFood adds a new food item to the database
     * @throws PersistanceFoodServiceException
     * @throws DuplicateFoodException
     * @throws IOException
     */
    public void CreateFood(BasicFood basicFood)throws PersistanceFoodServiceException, DuplicateFoodException, IOException, SQLException {
        Connection connection = null;
        try{
            connection = databaseConnector.getDatabaseConnection();
            if (!databaseBuilder.CheckIfDbExists()) {
                databaseBuilder.CreateDatabase();
            }
            if (!databaseBuilder.CheckIfTableExists(tableName)) {
                databaseBuilder.CreateFoodTable();
            }

            Session session = databaseConnector.getSessionFactory().openSession();
            Transaction transaction = null;


            try{
                transaction = session.beginTransaction();
                session.saveOrUpdate(basicFood);
                transaction.commit();
            }
            catch (HibernateException e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();  // close transaction
                }
            } finally {
                if (transaction != null && transaction.isActive()) {
                    transaction.commit();
                }
                if(session.isConnected()){
                    session.disconnect();
                }
                if(connection.isClosed()){
                    connection.close();
                }
            }
        }
        catch(DatabaseConnectorException e){
            throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e){
                throw new PersistanceFoodServiceException("Could not connect to database." + e.getMessage(), e);
            }
        }
    }

    /**
     * Method user to populate food database table
     * @throws IOException
     * @throws PersistanceFoodServiceException
     */
    public void PopulateFoodDatabase() throws IOException, PersistanceFoodServiceException, SQLException{
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

        for(BasicFood basicFood : basicFoodArrayList){
            try {
                dbFoodService.CreateFood(basicFood);
            }
            catch(DuplicateFoodException | PersistanceFoodServiceException e){
                throw new PersistanceFoodServiceException("Could not create food " + basicFood.toString() +
                        e.getMessage(), e);
            }
        }
    }
}
