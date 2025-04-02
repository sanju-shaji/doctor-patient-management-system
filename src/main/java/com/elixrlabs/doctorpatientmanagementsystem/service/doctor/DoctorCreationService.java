package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
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
     * @param doctorResponseDto-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */
    @Transactional
    public ResponseEntity<DoctorResponseDto> createDoctor(DoctorResponseDto doctorResponseDto) {
        try {
            DoctorEntity doctorEntity = DoctorEntity.builder().id(UUID.randomUUID()).
                    firstName(doctorResponseDto.getFirstName() == null ? null : doctorResponseDto.getFirstName().trim())
                    .lastName(doctorResponseDto.getLastName() == null ? null : doctorResponseDto.getLastName().trim()).
                    department(doctorResponseDto.getDepartment() == null ? null : doctorResponseDto.getDepartment()).build();
            List<String> errorMessageList = doctorValidation.validatePostDoctor(doctorEntity);
            if (!errorMessageList.isEmpty()) {
                DoctorResponseDto errorResponseDto = DoctorResponseDto.builder().id(null).firstName(null).
                        lastName(null).department(null)
                        .success(false).errors(errorMessageList).build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
            }
            DoctorEntity saveDoctor = doctorRepository.save(doctorEntity);
            DoctorResponseDto responseDto = DoctorResponseDto.builder().id(saveDoctor.getId()).firstName(saveDoctor.getFirstName()).
                    lastName(saveDoctor.getLastName()).department(saveDoctor.getDepartment())
                    .success(true).errors(null).build();
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (Exception exception) {
            DoctorResponseDto errorResponseDto = DoctorResponseDto.builder().id(null).firstName(null).
                    lastName(null).department(null)
                    .success(false).errors(List.of(ApplicationConstants.SERVER_ERROR + exception.getMessage())).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
        }
    }
}
