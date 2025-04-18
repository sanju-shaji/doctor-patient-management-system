package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * DTO to transfer data of POST doctors api from controller to service layer
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DoctorPatientAssignmentDto {
    private UUID id;
    private String doctorId;
    private String patientId;
}
