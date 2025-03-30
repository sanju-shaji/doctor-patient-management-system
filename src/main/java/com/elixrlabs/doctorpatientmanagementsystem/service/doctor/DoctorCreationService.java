package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service Class for Doctor Module
 */
@Service
public class DoctorCreationService {
    @Autowired
    DoctorRepository doctorRepository;

    /**
     * Method which contains the business logic to post doctor details to database
     */
    public ResponseEntity<DoctorResponseDto> createDoctor(DoctorResponseDto doctorResponseDto) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setId(UUID.randomUUID());
        doctorEntity.setFirstName(doctorResponseDto.getFirstName().trim());
        doctorEntity.setLastName(doctorResponseDto.getLastName().trim());
        doctorEntity.setDepartment(doctorResponseDto.getDepartment());
        DoctorEntity createDoctor;
        DoctorValidation doctorValidation = new DoctorValidation();
        List<String> errorMessageList = doctorValidation.validatePostDoctor(doctorEntity);
        if (!errorMessageList.isEmpty()) {
            DoctorResponseDto errorResponseDto = new DoctorResponseDto();
            errorResponseDto.setSuccess(false);
            errorResponseDto.setError(errorMessageList);
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(errorResponseDto);
        }
        createDoctor = doctorRepository.save(doctorEntity);
        DoctorResponseDto responseDto = new DoctorResponseDto(createDoctor);
        responseDto.setSuccess(true);
        responseDto.setError(null);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(responseDto);
    }
}
