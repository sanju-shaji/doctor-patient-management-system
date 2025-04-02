package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * DTO class for API responses
 * Contains success status, patient details and error messages
 */
public class ResponseDto {
    private boolean success;
    private Object data;
    private List<String> errors;

    /**
     * Constructor to initialize successful response with patient details
     */
    public ResponseDto(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    /**
     * Constructor to initialize an error response with validation messages
     */
    public ResponseDto(boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
    }
}
