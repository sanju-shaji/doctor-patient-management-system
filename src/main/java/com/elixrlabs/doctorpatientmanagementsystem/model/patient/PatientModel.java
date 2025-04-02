package com.elixrlabs.doctorpatientmanagementsystem.model.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * PatientModel class represents patient entity in Doctor patient management system
 */
@Document(collection = DataBaseConstants.PATIENT_COLLECTION_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientModel {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
}
