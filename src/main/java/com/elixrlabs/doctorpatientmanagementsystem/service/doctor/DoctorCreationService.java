package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service Class for Doctor Module
 */
@RequiredArgsConstructor
@Service
public class DoctorCreationService {
    private final DoctorRepository doctorRepository;
    private final DoctorValidation doctorValidation;

    /**
     * Method which contains the business logic to validate the inputs and post doctor details to database
     *
     * @param doctorResponse-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */
    public ResponseEntity<DoctorResponse> createDoctor(DoctorDto doctorResponse) throws Exception {
        doctorValidation.validateDoctorDetails(doctorResponse);
        DoctorEntity doctorEntity = DoctorEntity.builder().id(UUID.randomUUID()).
                firstName(doctorResponse.getFirstName().trim())
                .lastName(doctorResponse.getLastName().trim()).
                department(doctorResponse.getDepartment()).build();
        doctorEntity = doctorRepository.save(doctorEntity);
        DoctorResponse responseDto = DoctorResponse.builder().id(doctorEntity.getId()).firstName(doctorEntity.getFirstName()).
                lastName(doctorEntity.getLastName()).department(doctorEntity.getDepartment())
                .success(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
