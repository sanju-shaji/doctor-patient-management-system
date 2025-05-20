package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Exception thrown when the username or password provided is invalid.
 */
public class InvalidUserNameOrPasswordException extends RuntimeException {

    /**
     * Constructs the exception with a message indicating invalid login credentials.
     */
    public InvalidUserNameOrPasswordException(String message) {
        super(message);
    }
}
