package com.elixrlabs.doctorpatientmanagementsystem.model.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * The entity class representing a doctor in the system.
 * This class is mapped to a MongoDB collection and stores doctor-related information.
 */
@Document(collection = DataBaseConstants.DOCTOR_COLLECTION_NAME)
@Getter
@Builder
@Setter
@AllArgsConstructor
public class DoctorEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
