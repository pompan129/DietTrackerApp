package edu.uml.diet.logic;

import edu.uml.diet.persistence.PersistanceServiceFactory;
import edu.uml.diet.persistence.DuplicateUserException;
import edu.uml.diet.persistence.PersistanceUserServices;
import edu.uml.diet.persistence.PersistanceUserServicesException;
import org.jasypt.digest.StandardStringDigester;    //open source library http://jasypt.org/ -for encryption

/**
 * concrete class of UserService interface. verifies user information. encrypts passwords
 */
public class BasicUserService implements UserService {

    private PersistanceUserServices persistanceUserService;
    private final String ALGORITHM = "SHA-1"; //algorithm used to encrypt passwords
    private final int ITERATIONS = 2000;    //number of times encryption algorithm is run on password
    private final StandardStringDigester digester;

    /**
     * constructor method
     *
     */
    /*public BasicUserService() throws UserServiceException {
        try {
            this.persistanceUserService = PersistanceServiceFactory.getPersistanceUserServicesInstance();
        } catch (PersistanceUserServicesException e) {
            throw new UserServiceException("cannot connect to User services", e);
        }

        //setup encryption
        digester = new StandardStringDigester();
        digester.setAlgorithm(ALGORITHM);
        digester.setIterations(ITERATIONS);

    }*/

    /**
     * constructor method. for test. USE FACTORY INSTEAD
     *
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
     * @param username
     * @param password
     * @return
     */
    public boolean verifyUser(String username, String password) throws UserServiceException {
        //return false if username is NOT in persistence layer
        try {
            if(!persistanceUserService.verifyUsername(username)){

                return false;}
        } catch (PersistanceUserServicesException e) {
            throw new UserServiceException("Cannot verify user", e);
        }

        //generate passKey from user supplied password
        String passKey = digester.digest(password);

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
     * @param username
     * @param password
     * @return boolean. true if user creation is successful. false if username already in use.
     */
    public boolean createUser(String username, String password) throws UserServiceException {
        //if username already exists return false
        try {
            if(persistanceUserService.verifyUsername(username)){return false;}
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
            throw new UserServiceException("UserName already in use", e);
        }
        return true;
    }

}
