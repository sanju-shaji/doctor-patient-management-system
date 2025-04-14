package com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Response object of get patients by doctor id api
 */
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorWithPatientsResponse extends BaseResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    private List<PatientDto> patients;
}