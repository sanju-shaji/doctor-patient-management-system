package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Exception thrown when an attempt is made to modify the doctor's ID using a JSON Patch.
 */
public class IdReplacementException extends RuntimeException{
/**
 * Creates a new IdReplacementException with the specified error message.
 */
    public IdReplacementException(String errorMessage) {
        super(errorMessage);
    }
}
