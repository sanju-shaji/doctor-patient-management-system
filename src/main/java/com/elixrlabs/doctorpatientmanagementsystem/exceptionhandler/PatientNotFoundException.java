package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
