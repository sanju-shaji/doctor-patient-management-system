package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Exception thrown when the provided JWT token is invalid or tampered.
 */
public class InvalidJwtTokenException extends RuntimeException {

    /**
     * Constructs the exception with a message indicating an invalid JWT token.
     */
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
