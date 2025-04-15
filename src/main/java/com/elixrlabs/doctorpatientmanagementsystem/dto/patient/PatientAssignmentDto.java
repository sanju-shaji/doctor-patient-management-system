package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Dto representing patient details for doctor patient assignment
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientAssignmentDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Date dateOfAdmission;
}
