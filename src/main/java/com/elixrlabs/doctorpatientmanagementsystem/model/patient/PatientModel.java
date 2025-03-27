package com.elixrlabs.doctorpatientmanagementsystem.model.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constant.DataBaseConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

/**
 * PatientModel class represents patient entity in Doctorpatientmanagementsystem
 * Annotations used
 */
@Document(collection = DataBaseConstants.PATIENT_COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
public class PatientModel {
    @Id
    private UUID id;
    private String patientFirstName;
    private String patientLastName;
    private Date dateOfAdmission;
}
