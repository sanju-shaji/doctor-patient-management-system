package com.elixrlabs.doctorpatientmanagementsystem.doctor.model;

import com.elixrlabs.doctorpatientmanagementsystem.doctor.constants.DoctorConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = DoctorConstants.DOCTOR_COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
public class DoctorEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
