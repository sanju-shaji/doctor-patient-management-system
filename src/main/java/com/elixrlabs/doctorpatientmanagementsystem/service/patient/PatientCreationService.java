package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<PatientResponse> createPatient(PatientDto patientDto) throws Exception {
        // Perform validation
        patientValidation.validatePatient(patientDto);
        UUID patientId = UUID.randomUUID();
        PatientModel patient = PatientModel.builder()
                .id(patientId)
                .firstName(patientDto.getFirstName().trim())
                .lastName(patientDto.getLastName().trim())
                .build();
        patient = patientRepository.save(patient);
        PatientResponse successResponse = PatientResponse.builder()
                .success(true)
                .data(PatientDto.builder()
                        .id(patient.getId())
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
