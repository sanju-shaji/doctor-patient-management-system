package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class PatientValidationException extends RuntimeException {
    public PatientValidationException(String message) {
        super(message);
    }
}
