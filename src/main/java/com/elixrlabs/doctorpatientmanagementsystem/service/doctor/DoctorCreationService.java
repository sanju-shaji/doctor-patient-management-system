package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
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
     * @param doctorDto-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */
    @Transactional
    public ResponseEntity<DoctorDto> createDoctor(DoctorDto doctorDto) {
        try {
            List<String> errorMessageList = doctorValidation.validatePostDoctor(doctorDto);
            if (!errorMessageList.isEmpty()) {
                DoctorDto errorResponseDto = DoctorDto.builder()
                        .success(false).errors(errorMessageList).build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
            }
            DoctorEntity doctorEntity = DoctorEntity.builder().id(UUID.randomUUID()).
                    firstName(doctorDto.getFirstName().trim())
                    .lastName(doctorDto.getLastName().trim()).
                    department(doctorDto.getDepartment()).build();
            doctorEntity = doctorRepository.save(doctorEntity);
            DoctorDto responseDto = DoctorDto.builder().id(doctorEntity.getId()).firstName(doctorEntity.getFirstName()).
                    lastName(doctorEntity.getLastName()).department(doctorEntity.getDepartment())
                    .success(true).build();
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (Exception exception) {
            DoctorDto errorResponseDto = DoctorDto.builder().success(false)
                    .errors(List.of(ApplicationConstants.SERVER_ERROR + exception.getMessage())).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
        }
    }
}
