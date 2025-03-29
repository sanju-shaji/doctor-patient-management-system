package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

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

    public PatientDto(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
