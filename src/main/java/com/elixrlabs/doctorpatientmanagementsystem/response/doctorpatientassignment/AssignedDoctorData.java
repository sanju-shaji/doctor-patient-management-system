package com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * response object that holds each doctor data in doctors array
 */
@Data
@Builder
public class AssignedDoctorData {
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
    private Date dateOfAdmission;
}
