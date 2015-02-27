package edu.uml.diet;

/**
 * Used to signal an issue with PersistanceUserServices
 */
public class PersistanceUserServicesException extends Exception {

    /**
     *
     * @param message
     * @param cause
     */
    public PersistanceUserServicesException(String message, Throwable cause) {
        super(message, cause);
    }
}
