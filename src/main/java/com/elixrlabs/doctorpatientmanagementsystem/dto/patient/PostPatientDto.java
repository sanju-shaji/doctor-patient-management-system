package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import lombok.*;

/**
 * DTO class for creating a new patient
 */

@Getter
@Setter
public class PostPatientDto {
    private String patientFirstName;
    private String patientLastName;
}
