package edu.uml.diet;

import org.jasypt.digest.StandardStringDigester;    //open source library http://jasypt.org/ -for encryption

/**
 * concrete class of UserServices interface. verifies user information. encrypts passwords
 */
public class BasicUserService implements UserServices {

    private PersistanceUserServices persistanceUserService;
    private final String ALGORITHM = "SHA-1"; //algorithm used to encrypt passwords
    private final int ITERATIONS = 2000;    //number of times encryption algorithm is run on password
    private final StandardStringDigester digester;

    /**
     * constructor method
     * @param persistanceUserService
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
    public boolean verifyUser(String username, String password){
        //return false if username is NOT in persistence layer
        if(!persistanceUserService.verifyUsername(username)){return false;}

        //generate passKey from user supplied password
        String passKey = digester.digest(password);

        //compare user passkey with passKey in Persistence layer
        if(digester.matches(password, persistanceUserService.getPassword(username))) {return true;}

        //if test fails return false
        return false;
    }

    /**
     * Method to create new user account
     * @param username
     * @param password
     * @return boolean. true if user creation is successful. false if username already in use.
     */
    public boolean createUser(String username, String password){
        //if username already exists return false
        if(persistanceUserService.verifyUsername(username)){return false;}

        //create passkey
        String passKey = digester.digest(password);

        //create new user account in persistence
        persistanceUserService.createUser(username, passKey);
        return true;
    }

}
