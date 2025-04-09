package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class InvalidJsonOperationException extends RuntimeException {
    public InvalidJsonOperationException(String errorMessage) {
        super(errorMessage);
    }
}
