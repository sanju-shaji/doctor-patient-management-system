package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DoctorPatientManagementSystemConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.RequestDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ResponseEntity<ResponseDto> createPatient(RequestDto patientDto) {
        // Perform validation
        List<String> validationErrors = patientValidation.validatePatient(patientDto);
        if (!validationErrors.isEmpty()) {
            ResponseDto errorResponse = new ResponseDto(false, validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        try {
            UUID patientId = UUID.randomUUID();
            PatientModel patient = new PatientModel(
                    patientId,
                    patientDto.getPatientFirstName().trim(),
                    patientDto.getPatientLastName().trim()
            );
            patientRepository.save(patient);
            ResponseDto successResponse = new ResponseDto(true, patient);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (DataAccessException dataAccessException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(false, List.of(DoctorPatientManagementSystemConstants.PATIENT_SAVE_ERROR)));
        }
    }
}
