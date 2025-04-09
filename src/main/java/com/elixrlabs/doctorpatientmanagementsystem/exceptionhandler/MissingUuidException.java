package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class MissingUuidException extends RuntimeException {
    public MissingUuidException(String errorMessage) {
        super(errorMessage);
    }
}
