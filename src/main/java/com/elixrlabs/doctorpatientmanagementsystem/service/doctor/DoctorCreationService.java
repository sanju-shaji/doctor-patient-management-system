package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service Class for Doctor Module
 */
@Service
public class DoctorCreationService {
    private final DoctorRepository doctorRepository;
    private final DoctorValidation doctorValidation;

    public DoctorCreationService(DoctorRepository doctorRepository, DoctorValidation doctorValidation) {
        this.doctorRepository = doctorRepository;
        this.doctorValidation = doctorValidation;
    }

    /**
     * Method which contains the business logic to validate the inputs and post doctor details to database
     *
     * @param doctorResponseDto-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */
    public ResponseEntity<DoctorResponseDto> createDoctor(DoctorResponseDto doctorResponseDto) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setId(UUID.randomUUID());
        doctorEntity.setFirstName(doctorResponseDto.getFirstName().trim());
        doctorEntity.setLastName(doctorResponseDto.getLastName().trim());
        doctorEntity.setDepartment(doctorResponseDto.getDepartment());
        DoctorEntity createDoctor;
        List<String> errorMessageList = doctorValidation.validatePostDoctor(doctorEntity);
        if (!errorMessageList.isEmpty()) {
            DoctorResponseDto errorResponseDto = new DoctorResponseDto();
            errorResponseDto.setSuccess(false);
            errorResponseDto.setErrors(errorMessageList);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
        }
        createDoctor = doctorRepository.save(doctorEntity);
        DoctorResponseDto responseDto = new DoctorResponseDto(createDoctor);
        responseDto.setSuccess(true);
        responseDto.setErrors(null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
