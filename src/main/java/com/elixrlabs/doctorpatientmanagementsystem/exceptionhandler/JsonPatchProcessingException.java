package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Custom exception to indicate errors during JSON Patch processing.
 */
public class JsonPatchProcessingException extends RuntimeException {
    /**
     * Constructs a new JsonPatchProcessingException with a specific error message.
     */
    public JsonPatchProcessingException(String message) {
        super(message);
    }
}
