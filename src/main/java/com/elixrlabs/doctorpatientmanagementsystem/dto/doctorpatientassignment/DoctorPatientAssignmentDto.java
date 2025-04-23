package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * DTO to transfer data of POST doctors api from controller to service layer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DoctorPatientAssignmentDto {
    private UUID doctorId;
    private UUID patientId;
    private Date dateOfAdmission;
}
