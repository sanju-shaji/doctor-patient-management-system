package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for creating a new patient
 */

@Getter
@Setter
public class PostPatientDto {
    private String patientFirstName;
    private String patientLastName;
}
