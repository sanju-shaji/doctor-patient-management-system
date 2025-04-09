package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class IdReplacementException extends RuntimeException{
    public IdReplacementException(String errorMessage) {
        super(errorMessage);
    }
}
