package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class InvalidUserInputException extends Exception{
    public InvalidUserInputException(String message) {
        super(message);
    }
}
