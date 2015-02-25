package edu.uml.diet;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import java.sql.*;

/**
 * concrete class for UserServices.
 *
 * Created by Ray Goolishian on 2/24/2015.
 */
public class PersistanceUserServices {

    private String DbName = "diettracker";
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String HOST_URL = "jdbc:mysql://localhost/";
    private String DB_URL = "jdbc:mysql://localhost/" + DbName;
    private String USER = "root";
    private String PASS = "PASSWORD";
    public DatabaseConnector databaseConnector = new DatabaseConnector(JDBC_DRIVER,HOST_URL,DB_URL,USER,PASS);
    public DbBuilder dbBuilder = new DbBuilder(databaseConnector,"diettracker");
    private String TableName = "users";


    /**
     * Method to add new user to database
     *
     * @param username
     * @param password
     */
    public void createUser(String username, String password) throws SQLException{
        try(Connection conn = databaseConnector.ConnectToDatabase()){
            if(!dbBuilder.CheckIfDbExists()) {
                dbBuilder.CreateDatabase();
            }
            if(!dbBuilder.CheckIfTableExists(TableName)){
                dbBuilder.CreateUserTable();
            }

            if(!verifyUsername(username)) {
                String sql = "INSERT INTO users (username, password) VALUES(?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.execute();
                preparedStatement.close();
                conn.close();
            }
        }
    }

    /**
     * Method to query database to check if user exists
     *
     * @param username
     * @return true if user exists, false if user does not exist
     * @throws SQLException
     */
    public boolean verifyUsername(String username) throws SQLException{

        boolean response;
        try(Connection conn = databaseConnector.ConnectToDatabase()){

            String sql = "SELECT * FROM " + TableName +" WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){response = true;}
            else{response = false;}

            preparedStatement.close();
            conn.close();
        }
        return response;
    }

    /**
     * Method accepts user name string and returns password
     *
     * @param username
     * @return
     * @throws SQLException
     */
    public String getPassword(String username) throws SQLException{

        String password = null;
        try(Connection conn = databaseConnector.ConnectToDatabase()){

            String sql = "SELECT password FROM " + TableName + " WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                password = rs.getString(1);
            }
        }
        return password;
    }
}
