package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorWithPatientsDto;

import java.util.UUID;

/**
 * Data Access Object interface responsible for handling custom database operations related to doctor-patient assignments.
 */

public interface DoctorWithPatientsDAO {
    DoctorWithPatientsDto getAssignedPatientsByDoctorId(UUID id);
}
