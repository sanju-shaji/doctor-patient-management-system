package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidAssignmentDataException extends RuntimeException {
    private final List<String> errors;

    /**
     * Constructs a new InvalidAssignmentDataException with a list of error messages.
     *
     * @param errors List of error messages to be returned.
     */
    public InvalidAssignmentDataException(List<String> errors) {
        this.errors = errors;
    }
}
