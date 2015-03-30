package edu.uml.diet.logic;

/**
 * Exception class to indicate problems connecting to Persistance
 */
public class FoodServiceException extends Exception {

    /**
     * Creates a new exception with a message passed as String to constructor
     * and cause for exception.
     *
     * @param message explanation for why the exception was thrown
     * @param cause cause for the exception, null value indicates cause is unknown
     *              or does not exist
     */
    public FoodServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}