package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class PatientAlreadyAssignedException extends Exception {
    public PatientAlreadyAssignedException(String message) {
        super(message);
    }
}
