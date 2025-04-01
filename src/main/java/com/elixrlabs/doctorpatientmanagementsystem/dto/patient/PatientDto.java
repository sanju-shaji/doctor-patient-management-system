package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import lombok.Data;

import java.util.UUID;
/**
 * Data transfer object for GetByName/Patient API
 */
@Data

public class PatientDto {
    private UUID id;
    private String firstName;
    private String lastName;

    public PatientDto(PatientModel patientModel)
    {
        this.id=patientModel.getId();
        this.firstName=patientModel.getPatientFirstName();
        this.lastName=patientModel.getPatientLastName();
    }
}
