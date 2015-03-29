package edu.uml.diet.logic;


/**
 * Exception class to indicate duplicate username in Persistence
 */
public class DuplicateUserException extends Exception {
    /**
     * exception caused by duplicate username found in Persistence. notify Persistence admin if possible.
     *
     * @param message  String of problem description
     * @param cause original exception
     */
    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);

    }


    /**
     * exception caused by duplicate username found in Persistence. notify Persistence admin if possible.
     * @param msg String message for client
     */
    public DuplicateUserException(String msg) {
        super(msg);
    }
}
