package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.AssignedDoctorsToPatientDto;

import java.util.UUID;

/**
 * Interface for implementing custom query for patient repository
 */
public interface DoctorPatientDAO {
    AssignedDoctorsToPatientDto getAssignedDoctorsByPatientId(UUID id);
}
