package com.elixrlabs.doctorpatientmanagementsystem.model.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * PatientModel class represents patient entity in Doctor patient management system
 */
@Document(collection = DataBaseConstants.PATIENT_COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientModel {
    @Id
    private UUID id;
    private String patientFirstName;
    private String patientLastName;
}
