package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * DTO to transfer data of POST doctors api from controller to service layer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DoctorPatientAssignmentDto {
    private String doctorId;
    private String patientId;
    private Date dateOfAdmission;
}
