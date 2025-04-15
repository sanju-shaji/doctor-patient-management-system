package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientAssignmentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * DTO to transfer data of Get patients by doctor id api
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorWithAssignedPatientsData {
    private String id;
    private String firstName;
    private String lastName;
    private String department;
    private List<PatientAssignmentData> patients;
}
