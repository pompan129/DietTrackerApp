package edu.uml.diet;

/**
 * Created by Kurt Johnson on 3/8/2015.
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