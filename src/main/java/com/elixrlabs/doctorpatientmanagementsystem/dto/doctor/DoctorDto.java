package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * DTO representing doctor details for data transfer.
 */
@Data
@Builder
public class DoctorDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
