package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional
    public ResponseEntity<DoctorResponse> createDoctor(DoctorDto doctorResponse) {
        try {
            List<String> errorMessageList = doctorValidation.validatePostDoctor(doctorResponse);
            if (!errorMessageList.isEmpty()) {
                DoctorResponse errorResponseDto = DoctorResponse.builder()
                        .success(false).errors(errorMessageList).build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
            }
            DoctorEntity doctorEntity = DoctorEntity.builder().id(UUID.randomUUID()).
                    firstName(doctorResponse.getFirstName().trim())
                    .lastName(doctorResponse.getLastName().trim()).
                    department(doctorResponse.getDepartment()).build();
            doctorEntity = doctorRepository.save(doctorEntity);
            DoctorResponse responseDto = DoctorResponse.builder().id(doctorEntity.getId()).firstName(doctorEntity.getFirstName()).
                    lastName(doctorEntity.getLastName()).department(doctorEntity.getDepartment())
                    .success(true).build();
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (Exception exception) {
            DoctorResponse errorResponseDto = DoctorResponse.builder().success(false)
                    .errors(List.of(ApplicationConstants.SERVER_ERROR + exception.getMessage())).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
        }
    }
}
