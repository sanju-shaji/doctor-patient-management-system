package com.elixrlabs.doctorpatientmanagementsystem.model.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = DataBaseConstants.DOCTOR_COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
/** The entity class representing a doctor */
public class DoctorEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
