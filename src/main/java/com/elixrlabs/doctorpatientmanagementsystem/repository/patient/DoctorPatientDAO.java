package com.elixrlabs.doctorpatientmanagementsystem.repository.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorPatientAssignmentDto;

import java.util.UUID;

public interface DoctorPatientDAO {
    DoctorPatientAssignmentDto getAssignedDoctorsByPatientId(UUID id);
}
