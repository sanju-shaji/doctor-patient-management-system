package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class PatientAlreadyAssignedException extends RuntimeException {
    public PatientAlreadyAssignedException(String message) {
        super(message);
    }
}
