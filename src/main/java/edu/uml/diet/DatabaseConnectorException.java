package edu.uml.diet;

/**
 * Used to signal an issue with initializing database connection
 */
public class DatabaseConnectorException extends Exception {

    /**
     *
     * @param message
     * @param cause
     */
    public DatabaseConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
