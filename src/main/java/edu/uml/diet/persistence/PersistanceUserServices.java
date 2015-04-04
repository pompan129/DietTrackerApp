package edu.uml.diet.persistence;

/**
 * Class to provide various User account services between logic and persistence
 * layers.
 */
public interface PersistanceUserServices {

    /**
     * method to retrieve encrypted password from Persistence Layer
     * @param username User Name of current User
     * @return String containing encrypted password
     */
    public String getPassword(String username) throws PersistanceUserServicesException;


    /**
     * method to verify that username exists in Persistence layer
     * @param username User Name of current User
     */
    public boolean verifyUsername(String username) throws PersistanceUserServicesException;

    /**
     * method to create new User account in persistence layer
     * @param username User Name of current User
     * @param password Password of current User
     */
    public void createUser(String username, String password)throws PersistanceUserServicesException,
            DuplicateUserException;
}