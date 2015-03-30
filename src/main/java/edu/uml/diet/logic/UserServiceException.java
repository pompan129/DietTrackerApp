package edu.uml.diet.logic;

/**
 * Exception class. Notify client of errors in verifying & creating users.
 */
public class UserServiceException extends Exception {
    /**
     * Creates a new exception with a message passed as String to constructor
     * and cause for exception.
     *
     * @param message explanation for why the exception was thrown
     * @param cause   cause for the exception, null value indicates cause is unknown
     *                or does not exist
     */
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
