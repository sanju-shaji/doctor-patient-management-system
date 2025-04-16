package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Exception thrown when a UUID is missing or empty in the request.
 */
public class EmptyUuidException extends Exception {

    /**
     * Creates a new exception with the given error message.
     */
    public EmptyUuidException(String message) {
        super(message);
    }
}
