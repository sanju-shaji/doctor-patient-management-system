package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientAssignmentDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Date dateOfAdmission;
}
