package edu.uml.diet;

/**
 * Used to signal an issue with PersistanceUserServices
 */
public class DuplicateUserException extends Exception {

    /**
     *
     * @param message
     * @param cause
     */
    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
