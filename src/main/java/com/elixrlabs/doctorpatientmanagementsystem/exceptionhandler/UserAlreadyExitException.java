package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class UserAlreadyExitException extends RuntimeException{
    public UserAlreadyExitException(String message) {
        super(message);
    }
}
