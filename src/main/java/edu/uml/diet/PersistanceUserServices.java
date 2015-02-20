package edu.uml.diet;

/**
 * Class to provide various user account services between logic and persistence
 * layers.
 */
public interface PersistanceUserServices {

    /**
     * method to retrieve encrypted password from Persistence Layer
     * @param username
     * @param password
     * @return String containing encrypted password
     */
    public String getPassword(String username, String password);


    /**
     * method to verify that username exists in Persistence layer
     * @param username
     * @return boolean. true if username is found. false if username not in Persistence layer
     */
    public boolean verifyUsername(String username);

    /**
     * method to create new user account in persistence layer
     * @param username
     * @param password
     * @return boolean. true if user creation is successful.
     */
    public boolean createUser(String username, String password);
}
