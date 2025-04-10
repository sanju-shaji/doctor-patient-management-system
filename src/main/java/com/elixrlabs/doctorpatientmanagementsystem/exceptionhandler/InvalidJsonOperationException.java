package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

import java.util.List;

/**
 * Exception thrown when an unsupported JSON Patch operation is used,
 * such as "add" or "remove", which are not allowed.
 */
@Getter
public class InvalidJsonOperationException extends RuntimeException {
    private final List<String> errors;

    /**
     * Creates a new InvalidJsonOperationException with the specified error message.
     */
    public InvalidJsonOperationException(List<String> errors) {
        this.errors = errors;
    }
}
