package com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * DoctorPatientAssignmentModel class represents DoctorPatientAssignment entity in Doctor-patient management system
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = DataBaseConstants.DOCTOR_PATIENT_ASSIGNMENT_COLLECTION_NAME)
public class DoctorPatientAssignmentModel {
    @Id
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
    private Date dateOfAdmission;
    private Boolean isUnAssigned;

    /**
     * Constructor to create a DoctorPatientAssignmentModel from a DoctorPatientAssignmentDto.
     * Automatically generates a new UUID for the assignment and sets the admission date to the current time.
     */
    public DoctorPatientAssignmentModel(DoctorPatientAssignmentDto doctorPatientAssignmentDto) {
        this.id = UUID.randomUUID();
        this.doctorId = UUID.fromString(doctorPatientAssignmentDto.getDoctorId());
        this.patientId = UUID.fromString(doctorPatientAssignmentDto.getPatientId());
        this.dateOfAdmission = Date.from(Instant.now());
        this.isUnAssigned = false;
    }
}
