package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * DTO to transfer data of POST doctors api from controller to service layer
 */
@Getter
@Setter
@SuperBuilder
public class DoctorPatientAssignmentDto {
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
}
