package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;
import java.util.List;

@Getter
public class UuidValidationException extends RuntimeException {
    private final List<String> errors;

    public UuidValidationException(List<String> errors) {
        this.errors = errors;
    }
}
