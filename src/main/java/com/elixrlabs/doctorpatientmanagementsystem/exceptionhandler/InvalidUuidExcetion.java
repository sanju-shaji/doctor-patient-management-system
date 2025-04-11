package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Exception thrown when a provided UUID is not valid or has an incorrect format.
 */
public class InvalidUuidExcetion extends Exception {
    /**
     * Creates a new InvalidUuidExcetion with the specified error message.
     */
    public InvalidUuidExcetion(String message) {
        super(message);
    }
}
