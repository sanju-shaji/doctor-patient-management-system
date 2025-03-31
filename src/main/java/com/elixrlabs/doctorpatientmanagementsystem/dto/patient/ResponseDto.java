package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * DTO class for API responses
 * Contains success status, patient details and error messages
 */
public class ResponseDto {
    private boolean success;
    private String id;
    private String patientFirstName;
    private String patientLastName;
    private List<String> errors;

    /**
     * Constructor to initialize successful response with patient details
     */
    public ResponseDto(boolean success, String id, String patientFirstName, String patientLastName) {
        this.success = success;
        this.id = id;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
    }

    /**
     * Constructor to initialize an error response with validation messages
     */
    public ResponseDto(boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
    }
}
