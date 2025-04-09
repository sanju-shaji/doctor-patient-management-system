package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Exception thrown when an unsupported JSON Patch operation is used,
 * such as "add" or "remove", which are not allowed.
 */
public class InvalidJsonOperationException extends RuntimeException {
/**
 * Creates a new InvalidJsonOperationException with the specified error message.
 */
    public InvalidJsonOperationException(String errorMessage) {
        super(errorMessage);
    }
}
