package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import lombok.Data;

import java.util.UUID;

/**
 * DTO class for creating a new patient
 */
@Data
public class RequestDto {
    private UUID id;
    private String firstName;
    private String lastName;
}
