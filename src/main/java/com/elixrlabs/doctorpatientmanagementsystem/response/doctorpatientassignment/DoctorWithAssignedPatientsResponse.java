package com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Response object of get patients by doctor id api
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorWithAssignedPatientsResponse extends BaseResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    private List<PatientAssignmentDto> patients;
}