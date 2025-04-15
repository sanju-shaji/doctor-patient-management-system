package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Dto representing patient details for doctor patient assignment
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientAssignmentData {
    private UUID id;
    private String firstName;
    private String lastName;
    private Date dateOfAdmission;
}
