package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Base DTO for patient class for setting success and error
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientResponseDto {
    private boolean success;
    private List<PatientDto> patients;
    private List<String> errors;
}
