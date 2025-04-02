package com.elixrlabs.doctorpatientmanagementsystem.model.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
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
@Setter
@AllArgsConstructor
public class DoctorEntity {
    /**
     * The unique identifier for the doctor.
     * This is the primary key in the database.
     */
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
