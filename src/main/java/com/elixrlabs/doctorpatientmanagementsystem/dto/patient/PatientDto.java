package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO class for creating a new patient
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {
    private UUID id;
    private String firstName;
    private String lastName;

    public PatientDto(PatientModel patientModel) {
        this.id = patientModel.getId();
        this.firstName = patientModel.getFirstName();
        this.lastName = patientModel.getLastName();
    }
}
