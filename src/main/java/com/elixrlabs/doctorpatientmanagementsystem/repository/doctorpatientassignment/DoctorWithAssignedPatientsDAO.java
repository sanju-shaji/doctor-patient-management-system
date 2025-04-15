package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorWithAssignedPatientsDto;

import java.util.UUID;

/**
 * Data Access Object interface responsible for handling custom database operations related to doctor-patient assignments.
 */

public interface DoctorWithAssignedPatientsDAO {
    DoctorWithAssignedPatientsDto getAssignedPatientsByDoctorId(UUID id);
}
