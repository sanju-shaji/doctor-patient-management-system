package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * service class for implementing business logic for retrieving data from database
 */
@Service
public class DoctorRetrievalService {
    @Autowired
    DoctorRepository docRepo;

    /**
     * Method which handles the logic to retrieve doctor data by using ID
     */
    public ResponseEntity<DoctorResponseDto> getDoctorsById(UUID id) {
        DoctorResponseDto responseDto = new DoctorResponseDto();
        Optional<DoctorEntity> doctorEntity = docRepo.findById(id);
        if (doctorEntity.isPresent()) {
            responseDto.setSuccess(true);
            responseDto.setId(doctorEntity.get().getId());
            responseDto.setFirstName(doctorEntity.get().getFirstName());
            responseDto.setLastName(doctorEntity.get().getLastName());
            responseDto.setDepartment(doctorEntity.get().getDepartment());
            responseDto.setError(null);
            return ResponseEntity.ok().body(responseDto);
        }
        List<String> invalidUUID = new ArrayList<>();
        invalidUUID.add(DPMSConstants.USER_NOT_FOUND_ERROR);
        responseDto.setSuccess(false);
        responseDto.setError(invalidUUID);
        return ResponseEntity.status(400).body(responseDto);
    }
}
