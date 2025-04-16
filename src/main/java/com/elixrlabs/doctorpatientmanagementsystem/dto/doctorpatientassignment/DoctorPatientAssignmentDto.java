package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;
import lombok.Setter;

import java.util.UUID;

/**
 *  DTO to transfer data of POST doctors api from controller to service layer
 */
@Getter
@Setter
@Builder
public class DoctorPatientAssignmentDto {
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
}
