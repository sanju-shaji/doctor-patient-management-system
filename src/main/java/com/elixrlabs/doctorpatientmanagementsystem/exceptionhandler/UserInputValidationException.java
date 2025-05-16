package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

import java.util.List;

/**
 * Custom exception for handling user input validation errors with a list of messages.
 */
@Getter
public class UserInputValidationException extends RuntimeException {

    private final List<String> errors;

    /**
     * Initializes the exception with a list of validation error messages.
     */
    public UserInputValidationException(List<String> errors) {
        this.errors = errors;
    }
}
