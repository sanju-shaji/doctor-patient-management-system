package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class InvalidUuidException extends Throwable {
    public InvalidUuidException(String errorMessage) {
        super(errorMessage);
    }
}
