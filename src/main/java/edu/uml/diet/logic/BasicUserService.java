package edu.uml.diet.logic;

import edu.uml.diet.persistence.DuplicateUserException;
import edu.uml.diet.persistence.PersistanceUserServices;
import edu.uml.diet.persistence.PersistanceUserServicesException;
import org.jasypt.digest.StandardStringDigester;

/**
 * concrete class of UserService interface. verifies user information. encrypts passwords
 */
public class BasicUserService implements UserService {

    private final String ALGORITHM = "SHA-1"; //algorithm used to encrypt passwords
    private final int ITERATIONS = 2000;    //number of times encryption algorithm is run on password
    private final StandardStringDigester digester;
    private final PersistanceUserServices persistanceUserService;

    /**
     * constructor method.
     *
     * @param persistanceUserService interface
     */
    public BasicUserService(PersistanceUserServices persistanceUserService){
        this.persistanceUserService = persistanceUserService;
        //setup encryption
        digester = new StandardStringDigester();
        digester.setAlgorithm(ALGORITHM);
        digester.setIterations(ITERATIONS);

    }

    /**
     * method to verify password against persistence layer. converts password into encrypted Key and then
     * compares that key with the password key stored in the persistence layer. uses jasypt library
     *
     * @param username String of users username
     * @param password String of users password
     * @return boolean return false if username is NOT in persistence layer
     */
    public boolean verifyUser(String username, String password) throws UserServiceException {
        //return false if username is NOT in persistence layer
        try {
            if(!persistanceUserService.verifyUsername(username)){

                return false;}
        } catch (PersistanceUserServicesException e) {
            throw new UserServiceException("Cannot verify user", e);
        }


        //compare user passkey with passKey in Persistence layer
        try {
            if(digester.matches(password, persistanceUserService.getPassword(username))) {return true;}
        } catch (PersistanceUserServicesException e) {
            throw new UserServiceException("password does not verify", e);
        }

        //if test fails return false
        return false;
    }

    /**
     * Method to create new user account
     *
     * @param username String of users username
     * @param password String of users password
     * @return boolean. true if user creation is successful. false if username already in use.
     */
    public boolean createUser(String username, String password) throws UserServiceException, edu.uml.diet.logic.DuplicateUserException {
        //if username already exists return false
        try {
            if (persistanceUserService.verifyUsername(username)) {
                throw new edu.uml.diet.logic.DuplicateUserException("UserName already in use");
            }
        } catch (PersistanceUserServicesException e) {
            throw new UserServiceException("Cannot verify user", e);
        }


        //create passkey
        String passKey = digester.digest(password);

        //create new user account in persistence
        try {
            persistanceUserService.createUser(username, passKey);
        } catch (PersistanceUserServicesException e) {
            throw new UserServiceException("Cannot create user", e);
        } catch (DuplicateUserException e) {
            throw new edu.uml.diet.logic.DuplicateUserException("UserName already in use", e);
        }
        return true;
    }

}
