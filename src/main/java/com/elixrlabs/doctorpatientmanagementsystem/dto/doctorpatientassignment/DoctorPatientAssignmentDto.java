package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * DTO to transfer data of POST doctors api from controller to service layer
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorPatientAssignmentDto {
    private UUID doctorId;
    private UUID patientId;
    private Date dateOfAdmission;
}
