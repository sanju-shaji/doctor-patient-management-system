package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Base DTO for patient class for setting success and error
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientResponseDto {
    private boolean success;
    private List<PatientDto> patients;
    private List<String> errors;

    public PatientResponseDto(boolean success, List<PatientDto> patients) {
        this.success = true;
        this.patients = patients;
    }

    public PatientResponseDto(List<String> errors, boolean success) {
        this.success = false;
        this.errors = errors;
    }
}
