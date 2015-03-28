package edu.uml.diet.logic;


/**
 * Exception class to indicate duplicate username in Persistence
 */
public class DuplicateUserException extends Exception {
    /**
     * exception caused by duplicate username found in Persistence. notify Persistence admin if possible.
     *
     * @param message
     * @param cause
     */
    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);

    }


}
