package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

import java.util.List;

@Getter
public class JsonPatchProcessingException extends RuntimeException {
    private List<String> errors;

    public JsonPatchProcessingException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
