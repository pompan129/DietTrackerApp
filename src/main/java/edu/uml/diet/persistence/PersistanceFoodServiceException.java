package edu.uml.diet.persistence;

/**
 * Used to signal an issue with PersistanceFoodService
 */
public class PersistanceFoodServiceException extends Exception {
    /**
     * Creates a new exception with a message passed as String to constructor
     * and cause for exception.
     *
     * @param message explanation for why the exception was thrown
     * @param cause cause for the exception, null value indicates cause is unknown
     *              or does not exist
     */
    public PersistanceFoodServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
