package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO class for creating a new patient
 */

@Getter
@Setter
public class RequestDto {
    private UUID id;
    private String patientFirstName;
    private String patientLastName;
}
