package com.elixrlabs.doctorpatientmanagementsystem.repository.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DPADto;

import java.util.UUID;

public interface DoctorPatientDAO {
    DPADto getAssignedDoctorsByPatientId(UUID id);
}
