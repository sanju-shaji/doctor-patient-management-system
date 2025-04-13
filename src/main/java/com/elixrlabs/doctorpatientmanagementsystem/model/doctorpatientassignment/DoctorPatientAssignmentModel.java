package com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

/**
 * DoctorPatientAssignmentModel class represents DoctorPatientAssignment entity in Doctor patient management system
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
}
