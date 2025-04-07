package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class EmptyUuidException extends Exception {
    public EmptyUuidException(String message) {
        super(message);
    }
}
