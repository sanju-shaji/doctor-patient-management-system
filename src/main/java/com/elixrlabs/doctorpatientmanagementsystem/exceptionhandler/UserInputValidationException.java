package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

import java.util.List;

@Getter
public class UserInputValidationException extends RuntimeException{
    private final List<String> errors;

    public UserInputValidationException(List<String> errors) {
        this.errors = errors;
    }
}
