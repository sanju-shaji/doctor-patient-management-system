package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
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
    @Autowired
    DoctorValidation doctorValidation;

    /**
     * Method which handles the logic to retrieve doctor data by using ID
     */
    public ResponseEntity<DoctorResponseDto> getDoctorsById(String id) {
        if (doctorValidation.isUUIDNull(id)) {
            DoctorResponseDto responseDto = new DoctorResponseDto();
            responseDto.setSuccess(false);
            responseDto.setError(List.of(DPMSConstants.EMPTY_UUID));
            return ResponseEntity.status(400).body(responseDto);
        }
        if (!doctorValidation.isValidUUID(id)) {
            DoctorResponseDto responseDto = new DoctorResponseDto();
            responseDto.setSuccess(false);
            responseDto.setError(List.of(DPMSConstants.INVALID_UUID_ERROR));
            return ResponseEntity.status(400).body(responseDto);
        }
        UUID Uuid = UUID.fromString(id);
        Optional<DoctorEntity> doctorEntity = docRepo.findById(Uuid);
        if (doctorEntity.isPresent()) {
            DoctorResponseDto responseDto = new DoctorResponseDto(doctorEntity.get());
            responseDto.setSuccess(true);
            responseDto.setError(null);
            return ResponseEntity.ok().body(responseDto);
        }
        List<String> invalidUUID = new ArrayList<>();
        invalidUUID.add(DPMSConstants.USER_NOT_FOUND_ERROR);
        DoctorResponseDto responseDto = new DoctorResponseDto();
        responseDto.setSuccess(false);
        responseDto.setError(invalidUUID);
        return ResponseEntity.status(404).body(responseDto);
    }
}
