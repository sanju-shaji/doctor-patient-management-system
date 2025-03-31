package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.RequestDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.beans.factory.annotation.Autowired;
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
     * creates a new patient after performing validations
     *
     * @param patientDto
     * @return
     */
    public ResponseEntity<ResponseDto> createPatient(RequestDto patientDto) {
        // Perform validation
        List<String> validationErrors = patientValidation.validatePatient(patientDto);
        if (!validationErrors.isEmpty()) {
            ResponseDto errorResponse = new ResponseDto(false, validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        UUID patientId = UUID.randomUUID();
        PatientModel patient = new PatientModel(
                patientId,
                patientDto.getPatientFirstName().trim(),
                patientDto.getPatientLastName().trim()
        );
        patientRepository.save(patient);
        ResponseDto successResponse = new ResponseDto(true, patient);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
