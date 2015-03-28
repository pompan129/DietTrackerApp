package edu.uml.diet.logic;

/**
 * Interface for user verification & creation from Logic layer
 * by UI.
 */
public interface UserService {

    /**
     * method to verify username and password combination.
     *
     * @param username
     * @param password
     * @return returns boolean. true if verification successful, false if username/password combination
     * is incorrect
     */
    public boolean verifyUser(String username, String password) throws UserServiceException;

    /**
     * Method to create new user account
     *
     * @param username
     * @param password
     * @return boolean. true if user creation is successful. false if username already in use.
     */
    public boolean createUser(String username, String password) throws UserServiceException, DuplicateUserException;
}
