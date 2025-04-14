package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;

import java.util.UUID;

public interface DoctorPatientDAO {
    DoctorPatientAssignmentDto getAssignedDoctorsByPatientId(UUID id);
}
