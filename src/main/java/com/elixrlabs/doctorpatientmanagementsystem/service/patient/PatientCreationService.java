package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
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

    public PatientCreationService(PatientRepository patientRepository, PatientValidation patientValidation) {
        this.patientRepository = patientRepository;
        this.patientValidation = patientValidation;
    }

    /**
     * creates a new patient record after performing validations
     * returns 400 response with validation errors if the input is invalid
     * returns 200 response with patient details if the input is valid
     */
    public ResponseEntity<PatientResponse> createPatient(PatientDto patient) throws Exception {
        // Perform validation
        patientValidation.validatePatient(patient);
        UUID patientId = UUID.randomUUID();
        PatientModel patientModel = PatientModel.builder()
                .id(patientId)
                .firstName(patient.getFirstName().trim())
                .lastName(patient.getLastName().trim())
                .build();
        patientModel = patientRepository.save(patientModel);
        PatientResponse successResponse = PatientResponse.builder()
                .success(true)
                .data(buildPatientDto(patientModel))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    private PatientDto buildPatientDto(PatientModel patientModel) {
        return PatientDto.builder()
                .id(patientModel.getId())
                .firstName(patientModel.getFirstName())
                .lastName(patientModel.getLastName())
                .build();
    }
}
