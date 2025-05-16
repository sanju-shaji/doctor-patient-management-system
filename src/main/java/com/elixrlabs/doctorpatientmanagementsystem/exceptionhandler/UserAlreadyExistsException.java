package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Exception thrown when a user already exists during registration or creation.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs the exception with a custom message indicating user already exists.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
