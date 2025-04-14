package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * DTO to transfer data of Get patients by doctor id api
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorWithPatientsDto {
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    private List<PatientDto> patients;
}
