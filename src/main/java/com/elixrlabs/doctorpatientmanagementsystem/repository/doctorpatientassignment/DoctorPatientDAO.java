package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.AssignedDoctorsToPatientDto;

import java.util.UUID;

public interface DoctorPatientDAO {
    AssignedDoctorsToPatientDto getAssignedDoctorsByPatientId(UUID id);
}
