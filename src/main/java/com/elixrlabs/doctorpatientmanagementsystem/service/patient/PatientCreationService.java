package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for handling business logic related to patient creation
 */
@Service
public class PatientCreationService {
    private final PatientRepository patientRepository;
    private final PatientValidation patientValidation;

    PatientCreationService(PatientRepository patientRepository, PatientValidation patientValidation) {
        this.patientRepository = patientRepository;
        this.patientValidation = patientValidation;
    }

    /**
     * creates a new patient record after performing validations
     * returns 400 response with validation errors if the input is invalid
     * returns 200 response with patient details if the input is valid
     */
    public ResponseEntity<ResponseDto> createPatient(PatientDto patientDto) {
        try {
            // Perform validation
            List<String> validationErrors = patientValidation.validatePatient(patientDto);
            if (!validationErrors.isEmpty()) {
                ResponseDto errorResponse = ResponseDto.builder()
                        .success(false)
                        .errors(validationErrors)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            UUID patientId = UUID.randomUUID();
            PatientModel patient = PatientModel.builder()
                    .id(patientId)
                    .firstName(patientDto.getFirstName().trim())
                    .lastName(patientDto.getLastName().trim())
                    .build();
            patient = patientRepository.save(patient);
            ResponseDto successResponse = ResponseDto.builder()
                    .success(true)
                    .data(PatientDto.builder()
                            .id(patient.getId())
                            .firstName(patient.getFirstName())
                            .lastName(patient.getLastName())
                            .build())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto
                    .builder()
                    .success(false)
                    .errors(List.of(ApplicationConstants.SERVER_ERROR + exception.getMessage()))
                    .build());
        }
    }
}
