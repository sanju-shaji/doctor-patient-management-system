package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class InvalidUserNameOrPasswordException extends RuntimeException {
    public InvalidUserNameOrPasswordException(String message) {
        super(message);
    }
}
