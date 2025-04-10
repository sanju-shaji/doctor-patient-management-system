package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

/**
 * Custom exception used when a doctor is not found in the system.
 */
public class DoctorNotFoundException extends RuntimeException {

    /**
     * Creates a new exception with the given error message.
     */
    public DoctorNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
